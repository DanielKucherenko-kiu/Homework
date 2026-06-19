package com.example.uno.persistence;

import com.example.uno.game.GameResult;
import com.example.uno.game.RoundResult;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GameHistoryRepositoryTest {
    @Test
    void savesGameResultAndSupportsReports() {
        String url = "jdbc:h2:mem:uno_" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false";
        DatabaseManager databaseManager = DatabaseManager.createForUrl(url);
        databaseManager.initializeSchema();
        GameHistoryRepository repository = new GameHistoryRepository(databaseManager.getSqlSessionFactory());

        LocalDateTime started = LocalDateTime.now().minusMinutes(5);
        LocalDateTime ended = LocalDateTime.now();
        RoundResult round = new RoundResult(1, "Alice", 42, Map.of("Alice", 42, "Bob", 0));
        GameResult result = new GameResult(started, ended, "Alice", 1, Map.of("Alice", 42, "Bob", 0), List.of(round));

        repository.saveGameResult(result);

        List<RecentGameRow> recent = repository.listRecentGames(10);
        assertFalse(recent.isEmpty());
        assertEquals("Alice", recent.get(0).getWinnerName());
        assertEquals(1, recent.get(0).getTotalRounds());

        List<WinCountRow> wins = repository.listPlayerWinCounts();
        WinCountRow aliceWins = wins.stream().filter(row -> row.getPlayerName().equals("Alice")).findFirst().orElseThrow();
        assertEquals(1, aliceWins.getWinCount());

        List<HighScoreRow> scores = repository.listHighestScores(10);
        HighScoreRow high = scores.stream().filter(row -> row.getPlayerName().equals("Alice")).findFirst().orElseThrow();
        assertEquals(42, high.getScore());
    }
}
