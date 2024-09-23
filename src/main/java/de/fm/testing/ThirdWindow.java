package de.fm.testing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import de.fm.testing.RowID_Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class ThirdWindow {

    @FXML
    private Button hello;

    @FXML
    private TableView<RowID_Test> tablefx;

    public void load() {
        try {
                System.out.println(getClass().getResource("/ThirdWindow.fxml"));


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ThirdWindow.fxml"));
            Parent rx = fxmlLoader.load();

            Stage newStage = new Stage();
            System.out.println(newStage.getMinHeight());
            newStage.setMinHeight(500);
            newStage.setMinWidth(400);


            Scene fxrx = new Scene(rx);
            newStage.setScene(fxrx);

            newStage.setTitle("FXML Example");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        TableColumn<RowID_Test, String> column1 = new TableColumn<>("Reihe");
        TableColumn<RowID_Test, String> column2 = new TableColumn<>("Name");

        column1.setCellValueFactory(new PropertyValueFactory<>("row"));
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        tablefx.getColumns().clear();
        tablefx.getColumns().add(column1);
        tablefx.getColumns().add(column2);
    }

    public void ollayclick(ActionEvent actionEvent) {
        System.out.println("JavaFx Working Properly! Message will be destroyed in 3 seconds...");
        hello.setText("I've been Clicked!");

        RowID_Test txtest = new RowID_Test("test", "" + (tablefx.getItems().size()+1));
        tablefx.getItems().add(txtest);
    }

    public void tablefx_clicked(MouseEvent mouseEvent) {
        try {
            RowID_Test txtest = (RowID_Test) tablefx.getSelectionModel().getSelectedItem();
            System.out.println(txtest.getRow());
        } catch (NullPointerException e) {
            System.out.println("Hier ist nichts zu sehen!");
        }
    }
}
