package com.school_system.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import com.school_system.model.User;
import com.school_system.dao.UserDAO;
import com.school_system.service.LoginService;
import com.school_system.util.DatabaseConnector;

import java.io.IOException;
import java.sql.Connection;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private LoginService loginService;

    @FXML
    public void initialize() {
        Connection conn = DatabaseConnector.connect();
        if (conn != null) {
            loginService = new LoginService();
        } else {
            errorLabel.setText("❌ Keine Verbindung zur Datenbank!");
        }
    }

    @FXML
    public void onLoginButtonClick() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Bitte Benutzername und Passwort eingeben!");
            return;
        }

        User user = loginService.login(username, password);

        if (user != null) {
            errorLabel.setText("✅ Login erfolgreich!");
            openDashboard(user);
        } else {
            errorLabel.setText("❌ Benutzername oder Passwort falsch!");
        }
    }

    private void openDashboard(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/school_system/view/dashboard.fxml"));
            Parent root = loader.load();

            // Zugriff auf den Controller der Dashboard-Szene
            DashboardController controller = loader.getController();
            controller.setUser(user); // Benutzerobjekt übergeben

            Scene scene = new Scene(root);
            // 🎨 CSS-Datei global einbinden
            scene.getStylesheets().add(getClass().getResource("/com/school_system/style/style.css").toExternalForm());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Schulverwaltung – Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Fehler beim Laden des Dashboards!");
        }
    }
}
