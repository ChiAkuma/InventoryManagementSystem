package de.fm.testing;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SecondaryWindow {

    public void show() {
        // Create a new stage for the secondary window
        Stage newStage = new Stage();
        newStage.setTitle("Secondary Window");

        // Create a label for the new window
        Label secondaryLabel = new Label("Hello from the secondary window!");

        // Create a layout pane and add the label
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondaryLabel);

        // Create a scene with the layout pane
        Scene secondScene = new Scene(secondaryLayout, 230, 100);

        // Set the scene on the new stage
        newStage.setScene(secondScene);

        // Show the new stage
        newStage.show();
    }
}