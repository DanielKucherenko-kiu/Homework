# Assignment 5 Checklist

## ORM/framework configuration and schema

Implemented with:

- H2 database
- MyBatis persistence mapper
- `schema.sql`
- `DatabaseManager`
- `GameMapper`
- `GameHistoryRepository`

Schema supports:

- players
- games
- rounds
- scores
- winner
- timestamp

## Persist core game data

When a game finishes, the app saves:

- player names
- game start timestamp
- game end timestamp
- rounds played
- per-player cumulative round scores
- per-player final scores
- final winner

## Query/report features

The CLI menu exposes:

- recent games
- player win count
- highest scores

## Persistence tests

Persistence tests use isolated in-memory H2 databases.

Run:

```bash
mvn test
```

## Database documentation

Database documentation is in:

```text
docs/database.md
```
