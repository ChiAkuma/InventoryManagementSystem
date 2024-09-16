package de.fm.database;

import java.sql.*;

public class Database {

    //Daten für Datenbanverbindung --------------------------------
    private String ip = "127.0.0.1";
    private int port = 3306;
    private String username = "root";
    private String database = "ims";

    // password nur notwendig, wenn Passwort abgerufen werden muss!!!
    // niemals Passwort in echten Programmen reinschreiben! #NUR ZUM TESTEN OK UND NIRGENDS HOCHLADEN GITHUB z.b.#
    private String password = "";
    //--------------------------------------------------------------

    // Verbindungszeugs -------------------------------------------
    private boolean jdbc_driver_found = false;
    private Connection dbconnection = null;

    public Connection getConnection() {
        return this.dbconnection;
    }

    /**
     * schließt die datenbank (theoretisch, wenn kein fehler aber heute egal)
     */
    public void closeDatabase() {
        try {
            this.dbconnection.close();
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            // nö danke heute kein fehler
        }
    }
    // ------------------------------------------------------------

    /**
     * Klassenkonstruktor Datenbank
     */
    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            jdbc_driver_found = true;
            System.out.println("Datenbanktreiber gefunden und geladen");

        } catch (ClassNotFoundException e) {
            jdbc_driver_found = false;
            System.err.println("ERROR: Datenbanktreiber wurde nicht geladen!");
            e.printStackTrace(System.err);
        }
    }

    /**
     * Verbindet mit der Datenbank
     *
     * @return true wenn erfolgreich, false wenn nicht
     */
    public boolean connect() {
        //jdbc:mysql://[host1][:port1]/[database]][?propertyName1=propertyValue1[&propertyName2=propertyValue2]...]
        //"jdbc:mysql://127.0.0.1:3306/dbname"
        String jdbc_url = "jdbc:mysql://" + ip + ":" + port + "/" + database;

        // check if jdbc driver is found
        if (jdbc_driver_found) {
            //TODO: nur wenn gefunden wurde mach das
            try {
                // connect to database
                this.dbconnection = DriverManager.getConnection(jdbc_url, username, password);
                System.out.println("Datenbankverbindung erfolgreich!");
                return true;

            } catch (SQLException e) {
                // connection failed
                System.out.println("Datenbankverbindung fehlgeschlagen!");
                e.printStackTrace(System.err);
            }
        }
        return false;
    }

    /**
     *
     * Der Teil der Datenbankabfrage die in jeder Funktion gleich ist
     * Gibt direkt ein ResultSet zurück
     * @param sql
     * @return ResultSet von dem angegebenen SQL Befehl
     * @throws SQLException
     */
    public ResultSet executeSQL(String sql) throws SQLException {
        if (!this.jdbc_driver_found) throw new SQLException("JDBC Driver error!");
        // wenn datenbankverbindung geschlossen ist gebe false zurück
        if (this.dbconnection.isClosed()) throw new SQLException("SQL Connection closed!");

        // bereite ein statement vor um es zum server zu schicken
        PreparedStatement prep = this.dbconnection.prepareStatement(sql);
        // schicke die anfrage ab
        prep.execute();
        // gebe zurück was der server zurück gibt
        ResultSet result = prep.getResultSet();
        if (result != null) {
            return result;
        } else {
            throw new SQLException("No Data found!");
        }
    }

    /**
     * schaut ob der user im der datenbank ist und überprüft das passwort
     *
     * @param username
     * @param password
     * @return
     */
    public boolean checkLogin(String username, String password) {
        // SELECT * FROM users WHERE Name = 'Max Mustermann' AND passwort = 'helga';
        // Name Tabelle für Lagerbestand im SQL = Lager
        String sql = "SELECT * FROM users WHERE Name = '" + username + "' AND passwort = '" + password + "';";

        // wenn treiber nicht gefunden wurde wird false zurückgegeben
        try {
            ResultSet result = executeSQL(sql);

            // schaue ob die daten leer sind, die zurück gegeben werden
            if (!result.isBeforeFirst()) {
                System.out.println("No data");
                return false;
            }

            while (result.next()) {
                // ausgabe ( TEST )
                String ausgabe = "ID: " + result.getInt("id") +
                        " | Name: " + result.getString("Name") +
                        " | email: " + result.getString("email") +
                        " | age: " + result.getInt("age") +
                        " | password: " + result.getString("passwort");
                System.out.println(ausgabe);
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    /**
     * zeige alles an was im inventar steht
     * @return
     */
    public boolean showInventory() {
        String sqllagerabruf = "select * from lager";

        try {
            PreparedStatement prepLager = dbconnection.prepareStatement(sqllagerabruf);
            prepLager.execute();
            ResultSet result = prepLager.getResultSet();

            if (!result.isBeforeFirst()){
                System.out.println("Keine Daten vorhanden");
                return false;
            }

            while (result.next()) {
                // ausgabe ( TEST )
                String ausgabe = "ID: " + result.getInt("id") +
                        " | Artikel Name: " + result.getString("ArtikelName") +
                        " | Beschreibung: " + result.getString("Beschreibung") +
                        " | Bestand: " + result.getString("Bestand") +
                        " | Preis: " + result.getString("Preis")+
                        " | Lagerort: " + result.getString("Lagerort")+
                        " | Eintragungs Datum: " + result.getTimestamp("ErstelltAm");

                System.out.println(ausgabe);
            }
            return true;

        } catch (SQLException fehler) {
            fehler.printStackTrace(System.err);

        }
        return false;

    }

}