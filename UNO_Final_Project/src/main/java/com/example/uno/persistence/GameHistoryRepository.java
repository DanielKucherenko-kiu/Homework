package com.example.uno.persistence;

import com.example.uno.game.GameResult;
import com.example.uno.game.RoundResult;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameHistoryRepository {
    private final SqlSessionFactory sqlSessionFactory;

    public GameHistoryRepository(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void saveGameResult(GameResult result) {
        try (SqlSession session = sqlSessionFactory.openSession(false)) {
            GameMapper mapper = session.getMapper(GameMapper.class);

            Map<String, PlayerRecord> players = new LinkedHashMap<>();
            for (String name : result.getFinalScores().keySet()) {
                players.put(name, findOrCreatePlayer(mapper, name));
            }

            GameRecord game = new GameRecord();
            game.setStartedAt(result.getStartedAt());
            game.setEndedAt(result.getEndedAt());
            game.setWinnerPlayerId(players.get(result.getWinnerName()).getId());
            game.setTotalRounds(result.getTotalRounds());
            mapper.insertGame(game);

            for (RoundResult roundResult : result.getRounds()) {
                RoundRecord round = new RoundRecord();
                round.setGameId(game.getId());
                round.setRoundNumber(roundResult.getRoundNumber());
                round.setWinnerPlayerId(players.get(roundResult.getWinnerName()).getId());
                round.setPointsAwarded(roundResult.getPointsAwarded());
                mapper.insertRound(round);

                for (Map.Entry<String, Integer> entry : roundResult.getCumulativeScores().entrySet()) {
                    mapper.insertScore(score(game.getId(), round.getId(), players.get(entry.getKey()).getId(), entry.getValue(), "ROUND", result.getEndedAt()));
                }
            }

            for (Map.Entry<String, Integer> entry : result.getFinalScores().entrySet()) {
                mapper.insertScore(score(game.getId(), null, players.get(entry.getKey()).getId(), entry.getValue(), "FINAL", result.getEndedAt()));
            }

            session.commit();
        }
    }

    public List<RecentGameRow> listRecentGames(int limit) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return session.getMapper(GameMapper.class).listRecentGames(limit);
        }
    }

    public List<WinCountRow> listPlayerWinCounts() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return session.getMapper(GameMapper.class).listPlayerWinCounts();
        }
    }

    public List<HighScoreRow> listHighestScores(int limit) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return session.getMapper(GameMapper.class).listHighestScores(limit);
        }
    }

    private PlayerRecord findOrCreatePlayer(GameMapper mapper, String name) {
        PlayerRecord existing = mapper.findPlayerByName(name);
        if (existing != null) {
            return existing;
        }
        PlayerRecord created = new PlayerRecord(name);
        mapper.insertPlayer(created);
        return created;
    }

    private ScoreRecord score(Long gameId, Long roundId, Long playerId, int score, String type, LocalDateTime recordedAt) {
        ScoreRecord record = new ScoreRecord();
        record.setGameId(gameId);
        record.setRoundId(roundId);
        record.setPlayerId(playerId);
        record.setScore(score);
        record.setScoreType(type);
        record.setRecordedAt(recordedAt);
        return record;
    }
}
