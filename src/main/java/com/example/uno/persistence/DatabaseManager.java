package com.example.uno.persistence;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseManager {
    private static final String DEFAULT_DB_URL = "jdbc:h2:file:./data/uno;AUTO_SERVER=FALSE;DATABASE_TO_UPPER=false";

    private final SqlSessionFactory sqlSessionFactory;
    private final DataSource dataSource;

    private DatabaseManager(String jdbcUrl) {
        this.dataSource = new UnpooledDataSource("org.h2.Driver", jdbcUrl, null, null);
        Environment environment = new Environment("development", new JdbcTransactionFactory(), dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.addMapper(GameMapper.class);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    public static DatabaseManager createDefault() {
        String jdbcUrl = System.getenv().getOrDefault("UNO_DB_URL", DEFAULT_DB_URL);
        return new DatabaseManager(jdbcUrl);
    }

    public static DatabaseManager createForUrl(String jdbcUrl) {
        return new DatabaseManager(jdbcUrl);
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void initializeSchema() {
        String schema = readSchemaSql();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            for (String sql : schema.split(";")) {
                String trimmed = sql.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Could not initialize database schema.", ex);
        }
    }

    private String readSchemaSql() {
        InputStream stream = DatabaseManager.class.getClassLoader().getResourceAsStream("schema.sql");
        if (stream == null) {
            throw new IllegalStateException("schema.sql was not found in resources.");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception ex) {
            throw new IllegalStateException("Could not read schema.sql.", ex);
        }
    }
}
