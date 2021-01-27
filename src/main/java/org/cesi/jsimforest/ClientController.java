package org.cesi.jsimforest;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    Configuration configuration =  new Configuration(1, 1, 10, 10);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ok");
    }

    @FXML
    private TextField textFieldHeight;

    @FXML
    private TextField textFieldWidth;

    public void setWidthGrid(ActionEvent actionEvent) {
        configuration.setRowNumber(Integer.parseInt(textFieldWidth.getText()));
        Client.updateGrid(configuration.getRowNumber(), configuration.getColumnNumber());
    }

    public void setHeightGrid(ActionEvent actionEvent) {
        configuration.setColumnNumber(Integer.parseInt(textFieldHeight.getText()));
        Client.updateGrid(configuration.getRowNumber(), configuration.getColumnNumber());
    }

    public void selectEmpty(ActionEvent actionEvent) {
        State state = State.empty;
        System.out.println(state);
    }

    public void selectTree(ActionEvent actionEvent) {
        State state = State.tree;
        System.out.println(state);
    }

    public void selectInfected(ActionEvent actionEvent) {
        State state = State.infected;
        System.out.println(state);
    }
}
