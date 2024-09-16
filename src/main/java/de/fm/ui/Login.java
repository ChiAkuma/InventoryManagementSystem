package de.fm.ui;

import de.fm.database.Database;
import java.util.Scanner;

public class Login {

    private Database db = null;

    public Login(Database db) {
        this.db = db;
    }

    public boolean abfrageDaten() {
        Scanner scanner = new Scanner(System.in);

        boolean LoginErfolgreich = false;
        while (!LoginErfolgreich) {
            System.out.println("Bitte einloggen mit Benutzername und Passwort");

            System.out.print("Benutzername eingeben: ");
            String username = scanner.nextLine();
            System.out.print("Passwort eingeben: ");
            String password = scanner.nextLine();

            System.out.println("Benutzername: " + username + " -- Passwort: " + password);

            if (db.checkLogin(username, password)) {
                System.out.println("Login erfolgreich");
                LoginErfolgreich = true;
            } else {
                System.out.println("Login gescheitert!");
            }
        }
        return LoginErfolgreich;
    }

}
