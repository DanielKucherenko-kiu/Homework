package com.example.uno.game;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class RoundResult {
    private final int roundNumber;
    private final String winnerName;
    private final int pointsAwarded;
    private final Map<String, Integer> cumulativeScores;

    public RoundResult(int roundNumber, String winnerName, int pointsAwarded, Map<String, Integer> cumulativeScores) {
        this.roundNumber = roundNumber;
        this.winnerName = winnerName;
        this.pointsAwarded = pointsAwarded;
        this.cumulativeScores = Collections.unmodifiableMap(new LinkedHashMap<>(cumulativeScores));
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public int getPointsAwarded() {
        return pointsAwarded;
    }

    public Map<String, Integer> getCumulativeScores() {
        return cumulativeScores;
    }
}
