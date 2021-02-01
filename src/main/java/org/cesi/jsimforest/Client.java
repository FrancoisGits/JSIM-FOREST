package org.cesi.jsimforest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Client extends Application {

    private static final GridPane grid = new GridPane();
    private static final HBox hboxSteps = new HBox();
    private static final HBox hboxDensity = new HBox();
    private static final Label densityTree = new Label();
    private static final Label densityBush = new Label();
    private static final Label densityYoungTree = new Label();
    private static final Label densityFire = new Label();
    private static final Label densityInfected = new Label();
    private static final Label steps = new Label();
    private static int i;
    private static int j;

    public static void launchClient() {
        launch();
    }

    // =======================================
    // *------------ Init Grille ------------*
    // =======================================
    public static void updateGrid(int rowNumber, int columnNumber){
        grid.getChildren().clear();
        for(i = 0; i < columnNumber; i++){
            for (j = 0; j < rowNumber; j++){
                Pane newCell = new Pane();
                newCell.setId(i + "," + j);
                newCell.setLayoutX(i * 9);
                newCell.setLayoutY(j * 9);
                newCell.setMinSize(9, 9);
                switch (ClientController.sim.getGrid().getMatrix()[i][j].getState()) {
                    case empty:
                        newCell.getChildren().clear();
                        newCell.getStyleClass().add("empty");
                        break;
                    case bush:
                        newCell.getChildren().clear();
                        newCell.getStyleClass().add("bush");
                        break;
                    case youngTree:
                        newCell.getChildren().clear();
                        newCell.getStyleClass().add("youngTree");
                        break;
                    case tree:
                        newCell.getChildren().clear();
                        newCell.getStyleClass().add("tree");
                        break;
                    case infected:
                        newCell.getChildren().clear();
                        newCell.getStyleClass().add("infected");
                        break;
                    case burning:
                        newCell.getChildren().clear();
                        newCell.getStyleClass().add("burning");
                        break;
                }
                newCell.setOnMouseClicked(e -> {
                    List<String> coordonateCell = Arrays.asList(newCell.getId().split("\\s*,\\s*"));
                    System.out.println(coordonateCell);
                    System.out.println(ClientController.getStateSelected());
                    if(ClientController.getStateSelected() == null){
                        ClientController.sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.empty);
                    }
                    else {
                        switch (ClientController.getStateSelected()){
                            case empty :
                                newCell.getChildren().clear();
                                newCell.getStyleClass().removeAll("empty", "bush", "youngTree", "tree", "infected", "burning");
                                newCell.getStyleClass().add("empty");
                                ClientController.sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.empty);
                                break;
                            case bush:
                                newCell.getChildren().clear();
                                newCell.getStyleClass().removeAll("empty", "bush", "youngTree", "tree", "infected", "burning");
                                newCell.getStyleClass().add("bush");
                                ClientController.sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.bush);
                                break;
                            case youngTree:
                                newCell.getChildren().clear();
                                newCell.getStyleClass().removeAll("empty", "bush", "youngTree", "tree", "infected", "burning");
                                newCell.getStyleClass().add("youngTree");
                                ClientController.sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.youngTree);
                                break;
                            case tree:
                                newCell.getChildren().clear();
                                newCell.getStyleClass().removeAll("empty", "bush", "youngTree", "tree", "infected", "burning");
                                newCell.getStyleClass().add("tree");
                                ClientController.sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.tree);
                                break;
                            case infected:
                                newCell.getChildren().clear();
                                newCell.getStyleClass().removeAll("empty", "bush", "youngTree", "tree", "infected", "burning");
                                newCell.getStyleClass().add("infected");
                                ClientController.sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.infected);
                                break;
                            case burning:
                                newCell.getChildren().clear();
                                newCell.getStyleClass().removeAll("empty", "bush", "youngTree", "tree", "infected", "burning");
                                newCell.getStyleClass().add("burning");
                                ClientController.sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.burning);
                                break;
                        }
                    }
                });
                grid.add(newCell, i, j);
            }
        }
    }

    public static void updateStep(){
        hboxSteps.getChildren().clear();
        steps.setText(ClientController.sim.getStep() + 1 + " / " + ClientController.config.getStepsNumber());
        hboxSteps.getChildren().add(steps);
    }

    public static void updateDensity(){
        hboxDensity.getChildren().clear();
        densityTree.setText("0.0");
        densityTree.setPadding(new Insets(0, 125, 0, 0));
        densityBush.setText("0.0");
        densityBush.setPadding(new Insets(0, 165, 0, 0));
        densityYoungTree.setText("0.0");
        densityYoungTree.setPadding(new Insets(0, 100, 0, 0));
        densityFire.setText("0.0");
        densityFire.setPadding(new Insets(0, 150, 0, 0));
        densityInfected.setText("0.0");
        hboxDensity.getChildren().addAll(densityTree, densityBush, densityYoungTree, densityFire, densityInfected);

    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        // =======================================
        // *------------- Set Root --------------*
        // =======================================
        AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/Client.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/Client.css").toExternalForm());

        // =======================================
        // *----------- Import Grille -----------*
        // =======================================
        ScrollPane sp = new ScrollPane();
        sp.pannableProperty().set(true); //Permet de se deplacer avec la souris
        sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.getStyleClass().add("scroll");
        sp.setContent(grid);
        sp.setLayoutX(45);
        sp.setLayoutY(90);
        sp.setMaxSize(910, 910);
        root.getChildren().add(sp);
        grid.getStyleClass().add("grid");
        root.getChildren().add(grid);
        // *--------- Grille par defaut ---------*
        for(i = 0; i < 100; i++) {
            for (j = 0; j < 100; j++) {
                Pane newCell = new Pane();
                newCell.setLayoutX(i * 9);
                newCell.setLayoutY(j * 9);
                newCell.setMinSize(9, 9);
                newCell.getStyleClass().add("empty");
                grid.add(newCell, i, j);
            }
        }
        // =======================================
        // *----------- Import Steps ------------*
        // =======================================
        hboxSteps.setLayoutX(920);
        hboxSteps.setLayoutY(52);
        root.getChildren().add(hboxSteps);
        steps.setText("0 / 0");
        hboxSteps.getChildren().add(steps);

        // =======================================
        // *----------- Import Steps ------------*
        // =======================================
        hboxDensity.setLayoutX(135);
        hboxDensity.setLayoutY(52);
        root.getChildren().add(hboxDensity);
        densityTree.setText("0.0");
        densityTree.setPadding(new Insets(0, 125, 0, 0));
        densityBush.setText("0.0");
        densityBush.setPadding(new Insets(0, 165, 0, 0));
        densityYoungTree.setText("0.0");
        densityYoungTree.setPadding(new Insets(0, 100, 0, 0));
        densityFire.setText("0.0");
        densityFire.setPadding(new Insets(0, 150, 0, 0));
        densityInfected.setText("0.0");
        hboxDensity.getChildren().addAll(densityTree, densityBush, densityYoungTree, densityFire, densityInfected);

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
