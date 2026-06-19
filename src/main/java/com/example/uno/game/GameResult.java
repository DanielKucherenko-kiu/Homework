package com.example.uno.game;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameResult {
    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;
    private final String winnerName;
    private final int totalRounds;
    private final Map<String, Integer> finalScores;
    private final List<RoundResult> rounds;

    public GameResult(LocalDateTime startedAt,
                      LocalDateTime endedAt,
                      String winnerName,
                      int totalRounds,
                      Map<String, Integer> finalScores,
                      List<RoundResult> rounds) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.winnerName = winnerName;
        this.totalRounds = totalRounds;
        this.finalScores = Collections.unmodifiableMap(new LinkedHashMap<>(finalScores));
        this.rounds = List.copyOf(rounds);
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public Map<String, Integer> getFinalScores() {
        return finalScores;
    }

    public List<RoundResult> getRounds() {
        return rounds;
    }
}
