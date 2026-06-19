package com.example.uno.cli;

import com.example.uno.persistence.DatabaseManager;
import com.example.uno.persistence.GameHistoryRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseManager databaseManager = DatabaseManager.createDefault();
        databaseManager.initializeSchema();
        GameHistoryRepository repository = new GameHistoryRepository(databaseManager.getSqlSessionFactory());
        new ConsoleApp(new Scanner(System.in), repository).run();
    }
}
