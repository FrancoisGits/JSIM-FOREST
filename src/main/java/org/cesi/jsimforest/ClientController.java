package org.cesi.jsimforest;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    private static Stage popUpProfil = new Stage();
    public TextField textFieldHeight;
    public TextField textFieldWidth;
    public TextField textFieldStep;
    public TextField textFieldSpeed;
    public TextField textFieldSaveName;
    public static State state;

    protected static Configuration config =  new Configuration(1, 1, 100, 100);
    protected static Simulation sim = new Simulation(config);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ok");
    }

    public void setWidthGrid(KeyEvent keyEvent) throws IOException {
        textFieldWidth.getStylesheets().add(getClass().getResource("/css/Client.css").toExternalForm());
        if(textFieldWidth.getText().matches("^[0-9]*$")){
            textFieldWidth.getStyleClass().add("true");
        }
        else {
            textFieldWidth.getStyleClass().add("false");
        }
    }

    public void setHeightGrid(KeyEvent keyEvent) {
        textFieldHeight.getStylesheets().add(getClass().getResource("/css/Client.css").toExternalForm());
        if(textFieldHeight.getText().matches("^[0-9]*$")){
            textFieldHeight.getStyleClass().add("true");
        }
        else {
            textFieldHeight.getStyleClass().add("false");
        }
    }

    public void setStep(KeyEvent keyEvent) {
        textFieldStep.getStylesheets().add(getClass().getResource("/css/Client.css").toExternalForm());
        if(textFieldStep.getText().matches("^[0-9]*$")){
            textFieldStep.getStyleClass().add("true");
        }
        else {
            textFieldStep.getStyleClass().add("false");
        }
    }

    public void setSpeed(KeyEvent keyEvent) {
        textFieldSpeed.getStylesheets().add(getClass().getResource("/css/Client.css").toExternalForm());
        if(textFieldSpeed.getText().matches("^[0-9]*$")){
            textFieldSpeed.getStyleClass().add("true");
        }
        else {
            textFieldSpeed.getStyleClass().add("false");
        }
    }

    public void applyConfig(ActionEvent actionEvent) {
        config.setStepsPerSecond(Integer.parseInt(textFieldSpeed.getText()));
        config.setStepsNumber(Integer.parseInt(textFieldStep.getText()));
        config.setColumnNumber(Integer.parseInt(textFieldHeight.getText()));
        config.setRowNumber(Integer.parseInt(textFieldWidth.getText()));
        sim = new Simulation(config);
        Client.updateGrid(config.getRowNumber(), config.getColumnNumber());
        Client.updateStep();
    }

    public void setSelectedEmpty(ActionEvent actionEvent) {
        state = State.empty;
    }

    public void setSelectedInfected(ActionEvent actionEvent) {
        state = State.infected;
    }

    public void setSelectedBurning(ActionEvent actionEvent) {
        state = State.burning;
    }

    public void setSelectedBush(ActionEvent actionEvent) {
        state = State.bush;
    }

    public void setSelectedYoungTree(ActionEvent actionEvent) {
        state = State.youngTree;
    }

    public void setSelectedTree(ActionEvent actionEvent) {
        state = State.tree;
    }

    public static State getStateSelected() { return state; }

    public void playButton(ActionEvent actionEvent) throws InterruptedException, IOException {
        sim.process();
    }

    public void stepButton(ActionEvent actionEvent) throws IOException {
        sim.processOneStep();
    }

    public void pauseButton(ActionEvent actionEvent) {
    }

    public void stopButton(ActionEvent actionEvent) {
        sim = new Simulation(config);
        Client.updateGrid(sim.getConfig().getRowNumber(), sim.getConfig().getColumnNumber());
    }

    public void saveSim(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/PopUp.fxml"));
        Scene savePopUp = new Scene(root);
        popUpProfil.setScene(savePopUp);
        popUpProfil.show();
    }

    public void exportCSV(ActionEvent actionEvent) {
    }

    public void importSim(ActionEvent actionEvent) throws IOException, SQLException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/PopUpImport.fxml"));
        Scene importPopUp = new Scene(root);
        importPopUp.getStylesheets().add(getClass().getResource("/css/Client.css").toExternalForm());
        GridPane grid = new GridPane();
        grid.setLayoutX(10);
        grid.setLayoutY(50);
        root.getChildren().add(grid);
        ArrayList<String> readAllSave;
        readAllSave = sim.readAllSimulation();
        grid.setHgap(10);
        for(int i = 0; i < readAllSave.size(); i++) {
            Label simName = new Label();
            simName.setText(readAllSave.get(i));
            simName.setMaxWidth(350);
            simName.setMaxHeight(10);
            simName.getStyleClass().add("empty");
            simName.setOnMouseClicked(e -> {System.out.println("importation");});
            Button importButton = new Button("Importer");
            importButton.setOnMouseClicked(e -> {System.out.println("importation");});
            importButton.setMaxHeight(5);
            importButton.setMaxWidth(100);
            grid.add(importButton, 2, i);
            grid.add(simName, 1, i);
        }
        popUpProfil.setScene(importPopUp);
        popUpProfil.show();
    }

    public void setSaveName(ActionEvent actionEvent) throws InterruptedException, IOException, SQLException {
        popUpProfil.close();
        sim.saveEntireSimulation(textFieldSaveName.getText());
    }

    public static void popUpErreur() throws IOException {
        AnchorPane root = FXMLLoader.load(ClientController.class.getResource("/fxml/PopUpErreur.fxml"));
        Scene popUpErreur = new Scene(root);
        popUpProfil.setScene(popUpErreur);
        popUpProfil.show();
    }
    public static void popUpValider() throws IOException {
        Stage popUp = new Stage();
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        AnchorPane root = FXMLLoader.load(ClientController.class.getResource("/fxml/PopUpValider.fxml"));
        Scene popUpValider = new Scene(root);
        popUp.setX(1720);
        popUp.setY(1080);
        popUp.setScene(popUpValider);
        popUp.initStyle(StageStyle.UNDECORATED);
        popUp.show();
        delay.setOnFinished( event -> popUp.close() );
        delay.play();
    }
    public static void popUpFinSim() throws IOException {
        Stage popUp = new Stage();
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        AnchorPane root = FXMLLoader.load(ClientController.class.getResource("/fxml/PopUpFinSim.fxml"));
        Scene popUpValider = new Scene(root);
        popUp.setX(1580);
        popUp.setY(1080);
        popUp.setScene(popUpValider);
        popUp.initStyle(StageStyle.UNDECORATED);
        popUp.show();
        delay.setOnFinished( event -> popUp.close() );
        delay.play();
    }
}
