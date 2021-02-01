package org.cesi.jsimforest;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    private static Stage popUpProfil = new Stage();
    public TextField textFieldHeight;
    public TextField textFieldWidth;
    public TextField textFieldStep;
    public TextField textFieldSpeed;
    public TextField textFieldSaveName;
    private static State state;


    protected static Configuration config =  new Configuration(1, 1, 10, 10);
    protected static Simulation sim = new Simulation(config);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ok");
    }

    public void applyConfig(ActionEvent actionEvent) {
        config.setStepsPerSecond(Integer.parseInt(textFieldSpeed.getText()));
        config.setStepsNumber(Integer.parseInt(textFieldStep.getText()));
        config.setColumnNumber(Integer.parseInt(textFieldHeight.getText()));
        config.setRowNumber(Integer.parseInt(textFieldWidth.getText()));
        Client.updateGrid(config.getRowNumber(), config.getColumnNumber());
        Client.updateStep();
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

    public void playButton(ActionEvent actionEvent) throws InterruptedException, IOException {
        sim.process();
    }

    public void stepButton(ActionEvent actionEvent) {
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

    public void importSim(ActionEvent actionEvent) throws IOException {
        popUpValider();
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
