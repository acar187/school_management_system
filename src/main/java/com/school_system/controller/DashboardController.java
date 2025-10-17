package com.school_system.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

import com.school_system.model.Student;
import com.school_system.model.User;

public class DashboardController {

    @FXML
    private Label roleLabel;
    @FXML
    private Text welcomeText;

    private User currentUser;

    // Wird von LoginController aufgerufen, wenn Benutzer sich erfolgreich anmeldet
    public void setUser(User user) {
        this.currentUser = user;
        welcomeText.setText("Willkommen, " + user.getUsername() + "!");
        roleLabel.setText("Rolle: " + user.getRole());
    }

    // Logout – zurück zum Login
    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/school_system/view/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) roleLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login – Schulverwaltung");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

   @FXML
    private void showStudents() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/school_system/view/student.fxml"));
            Parent studentView = loader.load();

            // Zugriff auf StudentController, falls du später User brauchst
            StudentController controller = loader.getController();
            controller.setUser(currentUser);
            // Das Root-Layout des Dashboards (BorderPane) holen
            BorderPane root = (BorderPane) roleLabel.getScene().getRoot();
            root.setCenter(studentView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void showCourses() {
        showInfoAlert("Kursverwaltung", "Hier werden später Kurse angezeigt.");
    }

    @FXML
    private void showInfo() {
        showInfoAlert("Über", "Schulverwaltung v1.0\nErstellt mit JavaFX & MySQL.");
    }

    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
