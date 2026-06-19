# UNO CLI - Assignment 4 + Assignment 5

This is a standard Maven Java project for a playable UNO command-line game.

It includes:

- Maven build/test/package/run setup
- JUnit tests executed through Maven
- SLF4J + Logback logging
- Docker support
- H2 local database
- MyBatis persistence layer
- CLI reports for saved game history

## Requirements

- Java 17+
- Maven 3.9+
- Docker, only if running the Docker version


## Local build

```bash
mvn clean compile
```

## Local test

```bash
mvn test
```

## Local run

```bash
mvn exec:java
```

## Package creation

```bash
mvn clean package
```

The runnable JAR is created at:

```text
target/uno-a4-a5-1.0.0.jar
```

Run the packaged game with:

```bash
java -jar target/uno-a4-a5-1.0.0.jar
```

## Docker build

```bash
docker build -t uno-a4-a5 .
```

## Docker run

Interactive CLI run:

```bash
docker run -it --rm uno-a4-a5
```

With a persistent database folder on your machine:

```bash
docker run -it --rm -v "%cd%/data:/app/data" uno-a4-a5
```

On macOS/Linux, use:

```bash
docker run -it --rm -v "$(pwd)/data:/app/data" uno-a4-a5
```

## Database configuration

By default, the game stores history in a local H2 database file:

```text
./data/uno
```

You can override the database URL with an environment variable:

```bash
UNO_DB_URL="jdbc:h2:file:./data/uno_custom;DATABASE_TO_UPPER=false" mvn exec:java
```

No private machine-specific database is required.

## CLI menu

When you run the app, the menu lets you:

1. Start a new UNO game
2. List recent games
3. Show player win counts
4. Show highest scores
5. Exit

## Logging

Logging uses SLF4J with Logback. The game logs important diagnostic events:

- game start
- player turn
- card played
- card drawn
- invalid input
- round end
- game end

Normal player-facing CLI output is still printed separately, so logs do not replace the game interface.
