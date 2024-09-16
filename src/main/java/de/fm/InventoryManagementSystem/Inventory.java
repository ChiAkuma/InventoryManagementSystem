package de.fm.InventoryManagementSystem;

import de.fm.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Inventory {

    private Database db = null;

    public Inventory(Database db) {
        this.db = db;
    }

    /**
     * Lagert ein Item ein welches man eingibt.
     * Wenn das Item im Lager ist wird dieses einfach addiert
     * Sollte es nicht im Lager sein wird es als neues hinzugefügt
     */
    private void einlagern() {
        System.out.println("Einlagerungsprozess gestartet");
        Scanner scanner1 = new Scanner(System.in);

        try {
            System.out.println("Name des Artikels eingeben");
            String artikelname = scanner1.nextLine();
            System.out.println("Anzahl der Artikel eingeben");
            int stueckzahl = scanner1.nextInt();

            //fragt erstmal ab ob das item im lager existiert
            String sql = "SELECT ArtikelName FROM lager WHERE ArtikelName='" + artikelname + "';";
            System.out.println(sql);
            ResultSet rs = this.db.executeSQL(sql);

            String sqlBefehl = "";
            // Wenn bei der Abfrage nichts gefunden wurde mache einen insert
            if (!rs.isBeforeFirst()) {
                System.out.println("Artikel nicht im Lager vorhanden bitte weitere Daten angeben");

                //Frage noch offene Daten ab für die Datenbank
                //neuen scanner öffnen weil sonst irgendwie fehler im lesen entsteht
                Scanner x = new Scanner(System.in);
                System.out.print("Beschreibung hinzufügen: ");
                String beschreibung = x.nextLine();
                System.out.print("Preis hinzufügen: ");
                String preis = x.nextLine();
                //ersetze beim preis ein , komma direkt zu einem . punkt
                preis = preis.replace(",", ".");
                System.out.print("Lagerort hinzufügen: ");
                String lagerort = x.nextLine();

                //Der sql befehl um ein item hinzuzufügen
                sqlBefehl = "INSERT INTO lager (ArtikelName, Beschreibung, Bestand, Preis, Lagerort) VALUES ('" + artikelname + "', '" + beschreibung + "', " + stueckzahl + ", " + preis + ", '" + lagerort + "');";
            } else {
                //Wenn bei der Abfrage etwas gefunden wurde, wollen wir updaten
                // Update sql befehl
                sqlBefehl = "UPDATE lager SET Bestand = Bestand + " + stueckzahl + " WHERE ArtikelName = '" + artikelname + "';";
            }

            //den sql befehl ausgeben (Testzwecke)
            System.out.println(sqlBefehl);

            //Datenbank updaten
            PreparedStatement prepEinlagern = db.getConnection().prepareStatement(sqlBefehl);
            int x = prepEinlagern.executeUpdate();
            //Abfragecode oder Fehlercode vom Server
            System.out.println("Zurück vom INSERT: " + x);

            // Alpha Version. In Beta ( Vollversion ) Ausgabe von einzelnem eingelagerten Produkt.--------
            this.db.showInventory();
            //--------------------------------------------------------------------------------------------

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    private void aktualisieren() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        this.db.showInventory();
    }

    public void auslagern() {}

    public void filter() {}

    /**
     * Auswahl Funktion
     */
    public void selectFunction() {
        boolean programRunning = true;
        while (programRunning) {
            System.out.println("Funktion bitte auswählen\n" +
                    "1. Einlagerung ins Lager\n" +
                    "2. Auslagern (WIP)\n" +
                    "3. Filtersuche(WIP)\n" +
                    "4. Aktualisieren\n" +
                    "5. Programm Beenden");

            Scanner scanner = new Scanner(System.in);
            int eingabeNutzer = scanner.nextInt();
            switch (eingabeNutzer) {
                case 1:
                    System.out.println("Sie haben " + eingabeNutzer + " ausgewählt: Einlagerung ins Lager");
                    this.einlagern();
                    break;

                case 2:
                    System.out.println("Sie haben " + eingabeNutzer + " ausgewählt: Auslagern (WIP)");
                    this.auslagern();
                    break;

                case 3:
                    System.out.println("Sie haben " + eingabeNutzer + " ausgewählt: Filtersuche (WIP)");
                    this.filter();
                    break;

                case 4:
                    System.out.println("Sie haben " + eingabeNutzer + " ausgewählt: Aktualisieren (WIP)");
                    this.aktualisieren();
                    break;

                case 5:
                    System.out.println("Sie haben " + eingabeNutzer + " ausgewählt: Programm Beenden");
                    programRunning = false;
                    this.db.closeDatabase();
                    break;

                default:
                    System.out.println("Falsche Zahl eingegeben versuchen Sie es noch einmal Sie schaffen das!!");
                    // in while schleife packen ;)
                    break;
            }
        }
        System.out.println("Programm wurde ordnungsgemäß beendet. ♥♥♥ Auf wiedersehen ♥♥♥");

    }

}
