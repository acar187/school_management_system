package com.school_system.main;

import java.sql.Connection;

import com.school_system.controller.LoginController;
import com.school_system.util.DatabaseConnector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainJavaFX extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/school_system/view/login.fxml"));
        Scene scene = new Scene(loader.load());

        // Verbindung zur DB herstellen
        //Connection conn = DatabaseConnector.connect();

        // Controller holen und Connection übergeben
        LoginController controller = loader.getController();
        //controller.setConnection();

        // 🎨 CSS-Datei global einbinden
        scene.getStylesheets().add(getClass().getResource("/com/school_system/style/style.css").toExternalForm());


        stage.setTitle("Schulverwaltung - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
}
