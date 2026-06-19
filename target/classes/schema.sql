CREATE TABLE IF NOT EXISTS players (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS games (
    id IDENTITY PRIMARY KEY,
    started_at TIMESTAMP NOT NULL,
    ended_at TIMESTAMP NOT NULL,
    winner_player_id BIGINT NOT NULL,
    total_rounds INT NOT NULL,
    CONSTRAINT fk_games_winner FOREIGN KEY (winner_player_id) REFERENCES players(id)
);

CREATE TABLE IF NOT EXISTS rounds (
    id IDENTITY PRIMARY KEY,
    game_id BIGINT NOT NULL,
    round_number INT NOT NULL,
    winner_player_id BIGINT NOT NULL,
    points_awarded INT NOT NULL,
    CONSTRAINT fk_rounds_game FOREIGN KEY (game_id) REFERENCES games(id),
    CONSTRAINT fk_rounds_winner FOREIGN KEY (winner_player_id) REFERENCES players(id)
);

CREATE TABLE IF NOT EXISTS scores (
    id IDENTITY PRIMARY KEY,
    game_id BIGINT NOT NULL,
    round_id BIGINT NULL,
    player_id BIGINT NOT NULL,
    score INT NOT NULL,
    score_type VARCHAR(20) NOT NULL,
    recorded_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_scores_game FOREIGN KEY (game_id) REFERENCES games(id),
    CONSTRAINT fk_scores_round FOREIGN KEY (round_id) REFERENCES rounds(id),
    CONSTRAINT fk_scores_player FOREIGN KEY (player_id) REFERENCES players(id)
);
