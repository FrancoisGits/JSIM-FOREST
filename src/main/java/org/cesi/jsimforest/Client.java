package org.cesi.jsimforest;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {

    private static State state;
    private static Pane grid = new Pane();

    public static State getState() { return state; }

    public static void launchClient() {
        launch();
    }

    // =======================================
    // *----------- Config Grille -----------*
    // =======================================
    public static void updateGrid(int columnNumber, int rowNumber){
        grid.getChildren().clear();
        System.out.println(columnNumber);
        for(int i = 0; i < columnNumber; i++){
            for (int j = 0; j < rowNumber; j++){
                Pane newCell = new Pane();
                newCell.setLayoutX(i*20);
                newCell.setLayoutY(j*20);
                newCell.setMinSize(20, 20);
                newCell.getStyleClass().addAll("cell");
                newCell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        State actualState = getState();
                        System.out.println(actualState);
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
