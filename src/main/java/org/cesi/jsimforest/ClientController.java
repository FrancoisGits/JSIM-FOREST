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
import java.util.*;

public class ClientController implements Initializable {

    private static Stage popUpProfil = new Stage();
    private static Stage popUp = new Stage();
    public TextField textFieldHeight;
    public TextField textFieldWidth;
    public TextField textFieldStep;
    public TextField textFieldSpeed;
    public TextField textFieldSaveName;
    public static State state;

    protected static Configuration config =  new Configuration(1, 1, 100, 100);
    protected static Simulation sim = new Simulation(config);
    protected static boolean instanceAlive = true;


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
        config = new Configuration(Integer.parseInt(textFieldSpeed.getText()), Integer.parseInt(textFieldStep.getText()), Integer.parseInt(textFieldHeight.getText()), Integer.parseInt(textFieldWidth.getText()));
//        config.setStepsPerSecond(Integer.parseInt(textFieldSpeed.getText()));
//        config.setStepsNumber(Integer.parseInt(textFieldStep.getText()));
//        config.setColumnNumber(Integer.parseInt(textFieldHeight.getText()));
//        config.setRowNumber(Integer.parseInt(textFieldWidth.getText()));
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
        instanceAlive = false;
        sim = new Simulation(config);
        Client.updateGrid(sim.getConfig().getRowNumber(), sim.getConfig().getColumnNumber());
        popUp.close();
    }

    public void saveSim(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/PopUp.fxml"));
        Scene savePopUp = new Scene(root);
        popUpProfil.setScene(savePopUp);
        popUpProfil.show();
    }

    public void exportCSV(ActionEvent actionEvent) {
        System.out.println("exportation");
        popUp.close();
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
        System.out.println(readAllSave);
        grid.setHgap(10);
        for(int i = 0; i < readAllSave.size(); i++) {
            Label simName = new Label();
            simName.setText(readAllSave.get(i));
            simName.setMaxWidth(350);
            simName.setMaxHeight(10);
            simName.getStyleClass().add("empty");
            simName.setOnMouseClicked(e -> {System.out.println("importation");});
            Button importButton = new Button("Importer");
            importButton.setOnMouseClicked(e -> {importSelectedSim(simName.getText());});
            importButton.setMaxHeight(5);
            importButton.setMaxWidth(100);
            grid.add(importButton, 2, i);
            grid.add(simName, 1, i);
        }
        popUpProfil.setScene(importPopUp);
        popUpProfil.show();
    }

    public void importSelectedSim(String selectedSimulation) {
        String simulationDateTime = selectedSimulation.substring(selectedSimulation.indexOf(":")+1);
        String simulationName = selectedSimulation.substring(0,selectedSimulation.indexOf(":"));
        Map<String, Integer> simInfos = sim.readOneSimulation(simulationName, simulationDateTime);
        Map<String, Integer> configInfos = sim.getConfig().readOneConfig(simInfos.get("ID_Configuration"));
        Map<String, Integer> gridInfos = sim.getGrid().readOneGrid(simInfos.get("ID_Grid"));
        Map<String, String> cellsInfos = sim.getGrid().readAllCellsInOneGrid(simInfos.get("ID_Grid"));
        config = new Configuration(configInfos.get("stepsPerSecond"), configInfos.get("stepNumber"), configInfos.get("rowNumber"), configInfos.get("columnNumber"));
        sim = new Simulation(config);
        for (int i = 0; i < sim.getGrid().getMatrix().length ; i++) {
            for (int j = 0; j < sim.getGrid().getMatrix()[i].length; j++) {
                String stateAndAge = cellsInfos.get(Integer.toString(i) + ':' + Integer.toString(j));
                State state = State.valueOf(stateAndAge.substring(0, stateAndAge.indexOf(":")));
                int age = Integer.parseInt(stateAndAge.substring(stateAndAge.indexOf(":")+1));
                sim.getGrid().getMatrix()[i][j].setState(state);
                sim.getGrid().getMatrix()[i][j].setAge(age);
            }
        }
        Client.updateGrid(sim.getGrid().getRow(), sim.getGrid().getColumn());
        popUpProfil.close();
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
        AnchorPane root = FXMLLoader.load(ClientController.class.getResource("/fxml/PopUpFinSim.fxml"));
        Scene popUpValider = new Scene(root);
        popUpValider.getStylesheets().add(ClientController.class.getResource("/css/Client.css").toExternalForm());
        popUp.setScene(popUpValider);
        popUp.show();
    }

    public void closePopUp(ActionEvent actionEvent) {
        popUp.close();
    }

    public void modeDestruction(ActionEvent actionEvent){
        System.out.println("destruction");
        popUp.close();
    }
}
