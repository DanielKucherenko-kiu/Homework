# Database Documentation

## Selected database

This project uses H2 for local development and testing.

Default database URL:

```text
jdbc:h2:file:./data/uno;AUTO_SERVER=FALSE;DATABASE_TO_UPPER=false
```

The test suite uses isolated in-memory H2 databases, so tests do not depend on private machine state.

## Selected persistence framework

This project uses MyBatis as the structured persistence mapper.

The game logic does not contain raw SQL. SQL is kept in the MyBatis mapper interface:

```text
src/main/java/com/example/uno/persistence/GameMapper.java
```

Repository-level persistence operations are in:

```text
src/main/java/com/example/uno/persistence/GameHistoryRepository.java
```

## Schema setup

The schema file is:

```text
src/main/resources/schema.sql
```

It creates these tables:

- `players`
- `games`
- `rounds`
- `scores`

The schema stores:

- player names
- game start timestamp
- game end timestamp
- final winner
- number of rounds
- round winners
- round points awarded
- cumulative round scores
- final scores

The schema initializes automatically when the CLI starts.

## How to run persistence tests

```bash
mvn test
```

The main persistence test is:

```text
src/test/java/com/example/uno/persistence/GameHistoryRepositoryTest.java
```

It verifies that game results are saved and that the report queries work.

## How to view game history and statistics

Run the app:

```bash
mvn exec:java
```

Use the CLI menu:

- option `2`: list recent games
- option `3`: show player win counts
- option `4`: show highest scores

## Query/report features

The required Assignment 5 reports are implemented:

1. recent games
2. player win count
3. highest scores
