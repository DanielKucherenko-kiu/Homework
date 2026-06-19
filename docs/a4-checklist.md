# Assignment 4 Checklist

## Maven project setup

Implemented with `pom.xml`.

Standard layout:

```text
src/main/java
src/main/resources
src/test/java
```

## Standard build commands

Build/compile:

```bash
mvn clean compile
```

Test:

```bash
mvn test
```

Package:

```bash
mvn clean package
```

Run:

```bash
mvn exec:java
```

## Test integration

JUnit 5 tests run through Maven Surefire.

## Logging

Logging uses SLF4J + Logback.

Logged event types:

- game start
- player turn
- card played
- card drawn
- invalid input
- round end
- game end

## Docker

Docker support is in `Dockerfile`.

Build:

```bash
docker build -t uno-a4-a5 .
```

Run:

```bash
docker run -it --rm uno-a4-a5
```

## README commands

`README.md` documents local build, test, run, package, Docker build, and Docker run commands.
