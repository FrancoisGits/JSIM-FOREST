package org.cesi.jsimforest;

import javafx.embed.swing.JFXPanel;

/**
 * Hello world!
 *
 */
public class Main {

    public static void main(String[] args) {
        new JFXPanel();
        DataBaseConnection.connect();
        Client.launchClient();
    }
}
