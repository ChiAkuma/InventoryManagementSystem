package de.fm.InventoryManagementSystem;

import de.fm.database.Database;
import de.fm.ui.Login;

public class InventoryManagementSystem {

    public static void main(String[] args) {
        //Willkommensnachricht
        System.out.println("Willkommen im Inventory-Management-System! \n");

        //Datenbankverbindung aufbauen
        Database database = new Database();
        boolean db_connected = database.connect();

        System.out.print("\n");
        if (db_connected) {
            //TODO: Aufruf von Login
            Login login = new Login(database);
            if (login.abfrageDaten()) {
                database.showInventory();

                Inventory inv = new Inventory(database);
                inv.selectFunction();
            }

        }

    }

}