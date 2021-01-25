package org.cesi.jsimforest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {

    public static void launchClient() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Client.fxml"));

        // Setting up root
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/Client.css").toExternalForm());

        // Setting grid Scene
//        Pane gridPane = new Pane();
//        root.getChildren().add(gridPane);
//        int simHeight = 50;
//        int simWidth = 50;
//
//        for(int i = 0; i < simHeight;i++){
//            for (int j = 0;j < simWidth; j++){
//                Pane newCell = new Pane();
//                newCell.setLayoutX(i*20);
//                newCell.setLayoutY(j*20);
//                newCell.setMinSize(20, 20);
//                newCell.getStyleClass().addAll(Arrays.asList("null", "cell"));
//                gridPane.getChildren().add(newCell);
//            }
//        }
//

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
