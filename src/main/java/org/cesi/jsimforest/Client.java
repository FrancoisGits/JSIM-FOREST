package org.cesi.jsimforest;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Arrays;

public class Client extends Application{

    public static void launchClient(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // Setting up root
        HBox root = new HBox();
        root.getStyleClass().add("mainHbox");
        root.setSpacing(100);
        root.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(root, 1920, 1080);
        // scene.getStylesheets().add(getClass().getResource("/com/sim.css").toExternalForm());

        // Setting grid Scene
        Pane gridPane = new Pane();
        root.getChildren().add(gridPane);
        int simHeight = 50;
        int simWidth = 50;

        for(int i = 0; i < simHeight;i++){
            for (int j = 0;j < simWidth; j++){
                Pane newCell = new Pane();
                newCell.setLayoutX(i*20);
                newCell.setLayoutY(j*20);
                newCell.setMinSize(20, 20);
                newCell.getStyleClass().addAll(Arrays.asList("null", "cell"));
                gridPane.getChildren().add(newCell);
            }
        }

        //Config interface
        VBox rectDivConfig = new VBox();
        rectDivConfig.setId("configDiv");
        GridPane configArea = new GridPane();
        configArea.setVgap(25);
        configArea.setAlignment(Pos.BASELINE_CENTER);

        // =======================================
        // *---------- Boutons Profile ----------*
        // =======================================
        HBox profileButton = new HBox(5);
        profileButton.setPadding(new Insets(30, 0, 70, 0)); // Ajout du Padding
        profileButton.setAlignment(Pos.CENTER); // Centrer les boutons
        profileButton.getStyleClass().add("buttonsGrid"); // Style CSS

        // *------ Sauvegarder Simulation -------*
        Button saveSimulation = new Button("Sauvegarder Simulation");
        saveSimulation.getStyleClass().add("button"); // Style CSS
        saveSimulation.setOnAction((e) -> {
            // Action quand on clique
        });

        // *-------- Charger Simulation ---------*
        Button loadSimulation = new Button("Charger Simulation");
        loadSimulation.getStyleClass().add("button"); // Style CSS
        loadSimulation.setOnAction((e) -> {
            // Action quand on clique
        });

        // *-------- Exporter Simulation --------*
        Button exportCSV = new Button("Exporter CSV");
        exportCSV.getStyleClass().add("button"); // Style CSS
        exportCSV.setOnAction((e) -> {
            // Action quand on clique
        });

        // *------- Ajout dans Interface --------*
        profileButton.getChildren().addAll(saveSimulation, loadSimulation, exportCSV);
        rectDivConfig.getChildren().add(profileButton);

        // =======================================
        // *-------- Zone Configuration ---------*
        // =======================================
        Text configTitle = new Text("Configuration :");
        configTitle.getStyleClass().add("configTitle");
        configTitle.setTextAlignment(TextAlignment.CENTER);
        configTitle.setLayoutY(0);
        rectDivConfig.getChildren().add(configTitle);

        // *------ InputBox Largeur Grille ------*
        Label gridWidthText = new Label("Largeur de la grille : ");
        gridWidthText.getStyleClass().add("text"); // Style CSS
        configArea.add(gridWidthText, 0, 1); // Emplacement
        TextField gridWidthInputBox = new TextField();
        gridWidthInputBox.getStyleClass().add("inputBox"); // Style CSS
        configArea.add(gridWidthInputBox, 1, 1); // Emplacement

        // *------ InputBox Hauteur Grille ------*
        Label gridHeightText = new Label("Hauteur de la grille : ");
        gridHeightText.getStyleClass().add("text"); // Style CSS
        configArea.add(gridHeightText, 0, 2); // Emplacement
        TextField gridHeightInputBox = new TextField();
        gridHeightInputBox.getStyleClass().add("inputBox"); // Style CSS
        configArea.add(gridHeightInputBox, 1, 2); // Emplacement

        // *---------- InputBox Nb Pas ----------*
        Label simulationStepText = new Label("Nombre de pas dans la grille : ");
        simulationStepText.getStyleClass().add("text"); // Style CSS
        configArea.add(simulationStepText, 0, 3); // Emplacement
        TextField simulationStepInputBox = new TextField();
        simulationStepInputBox.getStyleClass().add("inputBox"); // Style CSS
        configArea.add(simulationStepInputBox, 1, 3); // Emplacement

        // *----- Echelle Vitesse Execution -----*
        Label simulationSpeedText = new Label("Nombre de pas dans la grille : ");
        simulationSpeedText.getStyleClass().add("text");
        configArea.add(simulationSpeedText, 0, 4);
        // TODO
        // Une echelle (graduation)
        TextField simulationSpeedInput = new TextField();
        simulationSpeedInput.getStyleClass().add("inputBox");
        configArea.add(simulationSpeedInput, 1, 4);

        // *------- Ajout dans Interface --------*
        rectDivConfig.getChildren().add(configArea);

        // =======================================
        // *----- Bouton Control Simulation -----*
        // =======================================
        HBox controlButtons = new HBox(10);
        controlButtons.getStyleClass().add("controlButtons"); // Style CSS
        controlButtons.setPadding(new Insets(30)); // Ajout du Padding
        controlButtons.setAlignment(Pos.CENTER); // Centrer les boutons

        // *------------ Bouton Play ------------*
        SVGPath playSVG = new SVGPath();
        playSVG.setContent("M424.4 214.7L72.4 6.6C43.8-10.3 0 6.1 0 47.9V464c0 37.5 40.7 60.1 72.4 41.3l352-208c31.4-18.5 31.5-64.1 0-82.6z");
        Button playButton = new Button("", playSVG);
        playSVG.setScaleX(0.1);
        playSVG.setScaleY(0.1);
        playSVG.setFill(Color.WHITE);
        playButton.getStyleClass().add("playButton");

        // *----------- Bouton Pause ------------*
        SVGPath pauseSVG = new SVGPath();
        pauseSVG.setContent("M144 479H48c-26.5 0-48-21.5-48-48V79c0-26.5 21.5-48 48-48h96c26.5 0 48 21.5 48 48v352c0 26.5-21.5 48-48 48zm304-48V79c0-26.5-21.5-48-48-48h-96c-26.5 0-48 21.5-48 48v352c0 26.5 21.5 48 48 48h96c26.5 0 48-21.5 48-48z");
        Button pauseButton = new Button("", pauseSVG);
        pauseSVG.setScaleX(0.1);
        pauseSVG.setScaleY(0.1);
        pauseSVG.setFill(Color.WHITE);
        pauseButton.getStyleClass().add("playButton");

        // *------- Ajout dans Interface --------*
        controlButtons.getChildren().addAll(playButton, pauseButton);
        rectDivConfig.getChildren().add(controlButtons);
        root.getChildren().add(rectDivConfig);

        // =======================================
        // *----- Config Fenêtre Simulation -----*
        // =======================================
        primaryStage.setTitle("JSIM-FOREST"); // Titre de la Fenêtre
        primaryStage.setMaximized(true); // FullScreen
        primaryStage.setScene(scene); // Ajouts des Scènes
        primaryStage.show(); // Afficher la fenêtre
        primaryStage.centerOnScreen(); // Centrage de la fenêtre
    }
}
