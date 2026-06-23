# Final Project Report

## Project overview

This project is a command-line UNO application written in Java.

It continues the earlier UNO work and turns it into a more complete product with:

- fuller UNO-style rules
- playable CLI flow
- separated game logic and console input
- Maven build support
- tests
- logging
- Docker support
- database persistence from Assignment 5
- final project documentation

## Implemented UNO rules

The project implements the following UNO-style rules:

- correct 108-card deck composition
- legal play validation
- Skip
- Reverse
- Draw Two
- Wild
- Wild Draw Four
- draw/pass behavior
- UNO call and missed-UNO penalty
- round scoring
- multi-round game to a target score

The detailed rule list and simplifications are documented in:

```text
docs/rules-supported.md
```

## CLI gameplay

The game can be played from the command line.

Run:

```bash
mvn exec:java
```

The menu allows the player to:

```text
1) Start new UNO game
2) List recent games
3) Show player win counts
4) Show highest scores
5) Exit
```

When starting a game, the user chooses:

- total number of players
- number of human players
- target score

During a human turn, the CLI shows:

- top card
- active color
- the player's hand
- which cards are legal

The player can:

- type a card number to play a card
- type `D` to draw/pass
- choose a color after playing a Wild or Wild Draw Four card
- call UNO when reaching one card

Invalid input is handled by asking the user again instead of crashing.

## Architecture

The project separates rule logic from console interaction.

Important packages:

```text
com.example.uno.domain
```

Contains core model classes:

- `Card`
- `CardColor`
- `CardType`
- `Deck`
- `Player`

```text
com.example.uno.game
```

Contains game and rule logic:

- `UnoGame`
- `UnoRules`
- `TurnEngine`
- `TurnEffect`
- `DrawPassRules`
- `UnoPenalty`
- `GameResult`
- `RoundResult`
- `PlayerDecisionProvider`

```text
com.example.uno.cli
```

Contains console interaction:

- `Main`
- `ConsoleApp`
- `ConsoleDecisionProvider`

```text
com.example.uno.persistence
```

Contains Assignment 5 persistence:

- `DatabaseManager`
- `GameHistoryRepository`
- `GameMapper`
- record/row classes for saved game history and reports

This separation makes the rule logic testable without relying only on console input.

## Tests added

The project includes tests for:

- deck composition
- legal play validation
- scoring values
- action card behavior
- Skip
- Reverse
- Draw Two
- Wild Draw Four
- draw/pass behavior
- UNO missed-call penalty
- round and game result data
- persistence repository behavior

Tests are run with:

```bash
mvn test
```

## Persistence

The Assignment 5 persistence layer is still included.

The project uses:

- H2 database
- MyBatis persistence mapper
- schema initialization from `schema.sql`
- repository methods for saving games and reading reports

The CLI includes report options for:

- recent games
- player win counts
- highest scores

More database details are documented in:

```text
docs/database.md
```

## Build and run

Compile:

```bash
mvn clean compile
```

Run tests:

```bash
mvn test
```

Run game:

```bash
mvn exec:java
```

Package:

```bash
mvn clean package
```

Run packaged JAR:

```bash
java -jar target/uno-a4-a5-1.0.0.jar
```

Docker build:

```bash
docker build -t uno-final .
```

Docker run:

```bash
docker run -it --rm uno-final
```

## Limitations

The project intentionally keeps some simplifications:

- no graphical user interface
- no online multiplayer
- no Wild Draw Four challenge rule
- no Draw Two stacking
- simple bot strategy
- text-based input and output only
- starting discard card is kept simple by using a number card

These simplifications are documented in `docs/rules-supported.md`.
