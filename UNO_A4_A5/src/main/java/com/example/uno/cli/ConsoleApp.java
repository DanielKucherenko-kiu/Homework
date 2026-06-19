package com.example.uno.cli;

import com.example.uno.domain.Player;
import com.example.uno.game.GameResult;
import com.example.uno.game.RoundResult;
import com.example.uno.game.UnoGame;
import com.example.uno.persistence.GameHistoryRepository;
import com.example.uno.persistence.HighScoreRow;
import com.example.uno.persistence.RecentGameRow;
import com.example.uno.persistence.WinCountRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private static final Logger log = LoggerFactory.getLogger(ConsoleApp.class);

    private final Scanner scanner;
    private final GameHistoryRepository repository;

    public ConsoleApp(Scanner scanner, GameHistoryRepository repository) {
        this.scanner = scanner;
        this.repository = repository;
    }

    public void run() {
        System.out.println("UNO CLI - Assignment 4 + Assignment 5 version");
        while (true) {
            System.out.println();
            System.out.println("1) Start new UNO game");
            System.out.println("2) List recent games");
            System.out.println("3) Show player win counts");
            System.out.println("4) Show highest scores");
            System.out.println("5) Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> startGame();
                case "2" -> showRecentGames();
                case "3" -> showWinCounts();
                case "4" -> showHighestScores();
                case "5" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> {
                    System.out.println("Invalid menu choice.");
                    log.warn("Invalid input: main menu choice {}", choice);
                }
            }
        }
    }

    private void startGame() {
        int totalPlayers = readInt("Total players (2-4): ", 2, 4);
        int humanPlayers = readInt("Human players (0-" + totalPlayers + "): ", 0, totalPlayers);
        int targetScore = readInt("Target score, e.g. 100 for quick test or 500 for normal UNO: ", 1, 5000);

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= totalPlayers; i++) {
            boolean bot = i > humanPlayers;
            if (bot) {
                players.add(new Player("Bot " + (i - humanPlayers), true));
            } else {
                System.out.print("Name for player " + i + ": ");
                String name = scanner.nextLine().trim();
                if (name.isBlank()) {
                    name = "Player " + i;
                }
                players.add(new Player(name, false));
            }
        }

        UnoGame game = new UnoGame(players, targetScore);
        GameResult result = game.play(new ConsoleDecisionProvider(scanner));
        repository.saveGameResult(result);
        printGameResult(result);
        System.out.println("Game saved to the local H2 database.");
    }

    private void printGameResult(GameResult result) {
        System.out.println();
        System.out.println("Winner: " + result.getWinnerName());
        System.out.println("Rounds played: " + result.getTotalRounds());
        System.out.println("Final scores:");
        result.getFinalScores().forEach((name, score) -> System.out.println("  " + name + ": " + score));
        System.out.println("Round summary:");
        for (RoundResult round : result.getRounds()) {
            System.out.printf("  Round %d: %s won %d points%n",
                    round.getRoundNumber(), round.getWinnerName(), round.getPointsAwarded());
        }
    }

    private void showRecentGames() {
        List<RecentGameRow> rows = repository.listRecentGames(10);
        if (rows.isEmpty()) {
            System.out.println("No saved games yet.");
            return;
        }
        rows.forEach(row -> System.out.printf(
                "Game #%d | %s | winner: %s | rounds: %d%n",
                row.getId(), row.getEndedAt(), row.getWinnerName(), row.getTotalRounds()));
    }

    private void showWinCounts() {
        List<WinCountRow> rows = repository.listPlayerWinCounts();
        if (rows.isEmpty()) {
            System.out.println("No players found yet.");
            return;
        }
        rows.forEach(row -> System.out.printf("%s: %d wins%n", row.getPlayerName(), row.getWinCount()));
    }

    private void showHighestScores() {
        List<HighScoreRow> rows = repository.listHighestScores(10);
        if (rows.isEmpty()) {
            System.out.println("No scores found yet.");
            return;
        }
        rows.forEach(row -> System.out.printf("%s: %d%n", row.getPlayerName(), row.getScore()));
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.printf("Please enter a number between %d and %d.%n", min, max);
            log.warn("Invalid input: expected integer between {} and {}", min, max);
        }
    }
}
