# UNO Final Project

This is a Java command-line UNO project prepared for Assignment 4, Assignment 5, and the Final Project.

It includes:

- playable UNO CLI game
- Maven project setup
- JUnit tests
- SLF4J + Logback logging
- Docker support
- H2 database persistence through MyBatis
- recent game and player statistics reports
- final project rule documentation

## Requirements

- Java 17+
- Maven 3.9+
- Docker, only for Docker run/build

## How to run in IntelliJ

Open this folder as a Maven project. The folder must directly contain `pom.xml`.

Main class:

```text
com.example.uno.cli.Main
```

You can run `Main.java` directly, or use the Maven command below.

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
docker build -t uno-final .
```

## Docker run

```bash
docker run -it --rm uno-final
```

## CLI usage

When the game starts, choose from the menu:

```text
1) Start new UNO game
2) List recent games
3) Show player win counts
4) Show highest scores
5) Exit
```

For a quick test game, choose:

```text
Total players: 2
Human players: 1
Target score: 50
```

During a human turn, cards marked with `*legal*` can be played.

Type a card number to play a card.

Type `D` to draw/pass.

If you play a Wild or Wild Draw Four card, choose one of:

```text
red
yellow
green
blue
```

## Implemented UNO rules

The project implements:

- correct 108-card UNO deck
- legal play validation
- Skip
- Reverse
- Draw Two
- Wild
- Wild Draw Four
- draw/pass behavior
- UNO call and missed-UNO penalty
- round scoring
- multi-round target-score game

Details are documented in:

```text
docs/rules-supported.md
```

## Assignment 4 support

Assignment 4 infrastructure is included:

- Maven build
- tests through Maven
- logging
- Dockerfile
- README commands

Details:

```text
docs/a4-checklist.md
```

## Assignment 5 support

Assignment 5 persistence is included:

- H2 database
- MyBatis mapper/repository
- schema setup
- saved game history
- recent games report
- win count report
- high score report
- persistence tests

Details:

```text
docs/a5-checklist.md
docs/database.md
```

## Final project documentation

Final documents:

```text
docs/rules-supported.md
docs/final-report.md
```

## Tests included

The tests cover:

- deck composition
- legal play validation
- scoring
- action-card behavior
- draw/pass behavior
- UNO penalty behavior
- round/game result behavior
- persistence repository behavior

Run all tests with:

```bash
mvn test
```
