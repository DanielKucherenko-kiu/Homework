package com.example.uno.persistence;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GameMapper {
    @Select("SELECT id, name FROM players WHERE name = #{name}")
    PlayerRecord findPlayerByName(@Param("name") String name);

    @Insert("INSERT INTO players(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertPlayer(PlayerRecord player);

    @Insert("""
            INSERT INTO games(started_at, ended_at, winner_player_id, total_rounds)
            VALUES(#{startedAt}, #{endedAt}, #{winnerPlayerId}, #{totalRounds})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertGame(GameRecord game);

    @Insert("""
            INSERT INTO rounds(game_id, round_number, winner_player_id, points_awarded)
            VALUES(#{gameId}, #{roundNumber}, #{winnerPlayerId}, #{pointsAwarded})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertRound(RoundRecord round);

    @Insert("""
            INSERT INTO scores(game_id, round_id, player_id, score, score_type, recorded_at)
            VALUES(#{gameId}, #{roundId}, #{playerId}, #{score}, #{scoreType}, #{recordedAt})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertScore(ScoreRecord score);

    @Select("""
            SELECT g.id, g.started_at, g.ended_at, p.name AS winner_name, g.total_rounds
            FROM games g
            JOIN players p ON p.id = g.winner_player_id
            ORDER BY g.ended_at DESC
            LIMIT #{limit}
            """)
    List<RecentGameRow> listRecentGames(@Param("limit") int limit);

    @Select("""
            SELECT p.name AS player_name, CAST(COUNT(g.id) AS INT) AS win_count
            FROM players p
            LEFT JOIN games g ON g.winner_player_id = p.id
            GROUP BY p.id, p.name
            ORDER BY win_count DESC, p.name ASC
            """)
    List<WinCountRow> listPlayerWinCounts();

    @Select("""
            SELECT p.name AS player_name, MAX(s.score) AS score
            FROM scores s
            JOIN players p ON p.id = s.player_id
            WHERE s.score_type = 'FINAL'
            GROUP BY p.id, p.name
            ORDER BY score DESC, p.name ASC
            LIMIT #{limit}
            """)
    List<HighScoreRow> listHighestScores(@Param("limit") int limit);
}
