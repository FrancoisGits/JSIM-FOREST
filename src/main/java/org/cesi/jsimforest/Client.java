package org.cesi.jsimforest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Client extends Application {

    private static final Pane grid = new Pane();
    private static Simulation sim;
    private static Configuration config;
    private static Cell cellInfo;
    private static int i;
    private static int j;


    public static void launchClient() {
        launch();
    }

    // =======================================
    // *------------ Init Grille ------------*
    // =======================================
    public static void initGrid(int rowNumber, int columnNumber){
        grid.getChildren().clear();
        for(i = 0; i < columnNumber; i++){
            for (j = 0; j < rowNumber; j++){
                Pane newCell = new Pane();
                newCell.setId(i + "," + j);
                newCell.setLayoutX(i*20);
                newCell.setLayoutY(j*20);
                newCell.setMinSize(20, 20);
                switch (sim.getGrid().getMatrix()[i][j].getState()) {
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
                        sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.empty);
                    }
                    else {
                        switch (ClientController.getStateSelected()){
                            case empty :
                                newCell.getChildren().clear();
                                newCell.getStyleClass().add("empty");
                                sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.empty);
                                break;
                            case bush:
                                newCell.getChildren().clear();
                                newCell.getStyleClass().add("bush");
                                sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.bush);
                                break;
                            case youngTree:
                                newCell.getChildren().clear();
                                newCell.getStyleClass().add("youngTree");
                                sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.youngTree);
                                break;
                            case tree:
                                newCell.getChildren().clear();
                                newCell.getStyleClass().add("tree");
                                sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.tree);
                                break;
                            case infected:
                                newCell.getChildren().clear();
                                newCell.getStyleClass().add("infected");
                                sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.infected);
                                break;
                            case burning:
                                newCell.getChildren().clear();
                                newCell.getStyleClass().add("burning");
                                sim.getGrid().getMatrix()[Integer.parseInt(coordonateCell.get(0))][Integer.parseInt(coordonateCell.get(1))].setState(State.burning);
                                break;
                        }
                    }
                });
                grid.getChildren().add(newCell);
            }
        }
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
        root.getChildren().add(grid);

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
