package org.cesi.jsimforest;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    public TextField textFieldHeight;
    public TextField textFieldWidth;
    public TextField textFieldStep;
    public TextField textFieldSpeed;
    private static State state;

    Configuration config =  new Configuration(1, 1, 105, 105);
    Simulation sim = new Simulation(config);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ok");
    }

    public void setWidthGrid(ActionEvent actionEvent) {
        config.setRowNumber(Integer.parseInt(textFieldWidth.getText()));
        Client.updateGrid(config.getRowNumber(), config.getColumnNumber());
    }

    public void setHeightGrid(ActionEvent actionEvent) {
        config.setColumnNumber(Integer.parseInt(textFieldHeight.getText()));
        Client.updateGrid(config.getRowNumber(), config.getColumnNumber());
    }

    public void setStep(ActionEvent actionEvent) {
        config.setStepsNumber(Integer.parseInt(textFieldStep.getText()));
    }

    public void setSpeed(ActionEvent actionEvent) {
        config.setStepsPerSecond(Integer.parseInt(textFieldSpeed.getText()));
    }

    public void setSelectedEmpty(ActionEvent actionEvent) {
        ClientController.state = State.empty;
    }

    public void setSelectedInfected(ActionEvent actionEvent) {
        ClientController.state = State.infected;
    }

    public void setSelectedBurning(ActionEvent actionEvent) {
        ClientController.state = State.burning;
    }

    public void setSelectedBush(ActionEvent actionEvent) {
        ClientController.state = State.bush;
    }

    public void setSelectedYoungTree(ActionEvent actionEvent) {
        ClientController.state = State.youngTree;
    }

    public void setSelectedTree(ActionEvent actionEvent) {
        ClientController.state = State.tree;
    }

    public static State getStateSelected() { return state; }

    public void playButton(ActionEvent actionEvent) throws InterruptedException {
        sim.process();
    }

    public void stepButton(ActionEvent actionEvent) {
        sim.processOneStep();
    }

    public void pauseButton(ActionEvent actionEvent) {
    }

    public void stopButton(ActionEvent actionEvent) {
        Simulation sim = new Simulation(config);
        Client.updateGrid(config.getRowNumber(), config.getColumnNumber());
    }
}
