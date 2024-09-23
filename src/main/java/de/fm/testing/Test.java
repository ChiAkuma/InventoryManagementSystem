package de.fm.testing;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import de.fm.testing.SecondaryWindow;
import de.fm.testing.ThirdWindow;

import javafx.fxml.*;

import java.io.IOException;
import java.util.Objects;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a label
        Label helloLabel = new Label("Hello, World!");

        // Create a button to open the new window
        Button openNewWindowButton = new Button("Open New Window");
        openNewWindowButton.setOnAction(e -> {
            // Create and show the secondary window
            SecondaryWindow secondaryWindow = new SecondaryWindow();
            secondaryWindow.show();

            new ThirdWindow().load();
        });

        // Create a layout pane and add the label and button
        StackPane root = new StackPane();
        root.getChildren().addAll(helloLabel, openNewWindowButton);

        // Create a scene with the layout pane
        Scene scene = new Scene(root, 300, 200);

        // Set the title of the stage (window)
        primaryStage.setTitle("Hello World JavaFX");

        // Set the scene on the stage
        primaryStage.setScene(scene);

        // Display the stage
        primaryStage.show();
    }
}