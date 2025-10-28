package com.school_system.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import java.io.IOException;

import com.school_system.model.Student;
import com.school_system.model.User;

public class DashboardController {

    @FXML
    private Label roleLabel;
    @FXML
    private Text welcomeText;
    @FXML
    private BorderPane mainPane;

    private User currentUser;

    @FXML private MenuItem manageStudentsMenu;
    @FXML private MenuItem manageCoursesMenu;
    @FXML private MenuItem adminOnlyMenu; // z.B. Benutzerverwaltung

    // Wird von LoginController aufgerufen, wenn Benutzer sich erfolgreich anmeldet
    public void setUser(User user) {
        this.currentUser = user;
        welcomeText.setText("Willkommen, " + user.getUsername() + "!");
        roleLabel.setText("Rolle: " + user.getRole());
        applayRoleBasedAccess(user.getRole());
    }

    private void applayRoleBasedAccess(String role) {
        switch (role) {
            case "ADMIN":
                // Admin hat vollen Zugriff
                adminOnlyMenu.setDisable(false);
                break;
            case "TEACHER":
                // Lehrer haben eingeschränkten Zugriff
                // z.B. könnten Admin-Funktionen deaktiviert werden
                adminOnlyMenu.setDisable(true);
                break;
            case "STUDENT":
                // Schüler haben noch eingeschränkteren Zugriff
                // z.B. könnten nur Kurs- und Notenansicht erlaubt sein
                adminOnlyMenu.setDisable(true);
                break;
            default:
                // Unbekannte Rolle – Zugriff verweigern oder Standardzugriff gewähren
                break;
        }
    }

    // Logout – zurück zum Login
    @FXML
    private void handleLogout() {
        // try {
        //     FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/school_system/view/login.fxml"));
        //     Parent root = loader.load();
        //     Stage stage = (Stage) roleLabel.getScene().getWindow();
        //     stage.setScene(new Scene(root));
        //     stage.setTitle("Login – Schulverwaltung");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        String userName = (currentUser != null ? currentUser.getUsername() : "(unbekannt)");
        System.out.println("Logout erfolgreich für Benutzer: " + userName);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/school_system/view/login.fxml"));
        try {
            Parent root = loader.load();

            // Try to obtain a valid Stage from known nodes. Some nodes may not yet be attached
            // to a Scene when this handler is invoked, so check several candidates.
            Stage stage = null;
            if (mainPane != null && mainPane.getScene() != null) {
                stage = (Stage) mainPane.getScene().getWindow();
            } else if (roleLabel != null && roleLabel.getScene() != null) {
                stage = (Stage) roleLabel.getScene().getWindow();
            } else if (welcomeText != null && welcomeText.getScene() != null) {
                stage = (Stage) welcomeText.getScene().getWindow();
            }

            if (stage != null) {
                stage.setScene(new Scene(root));
                stage.setTitle("Login – Schulverwaltung");
            } else {
                // As a fallback, open the login view in a new stage so logout still works.
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.setTitle("Login – Schulverwaltung");
                newStage.show();
            }
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
            Parent studentView = FXMLLoader.load(getClass().getResource("/com/school_system/view/student.fxml"));
            mainPane.setCenter(studentView); // z. B. BorderPane in deinem Dashboard
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showCourses() {
        try {
            Parent courseView = FXMLLoader.load(getClass().getResource("/com/school_system/view/course.fxml"));
            mainPane.setCenter(courseView); // z. B. BorderPane in deinem Dashboard
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showUserManagement() {
        try {
            Parent courseView = FXMLLoader.load(getClass().getResource("/com/school_system/view/user_management.fxml"));
            mainPane.setCenter(courseView); // z. B. BorderPane in deinem Dashboard
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showEnrollment() {
        try {
            Parent courseView = FXMLLoader.load(getClass().getResource("/com/school_system/view/enrollment.fxml"));
            mainPane.setCenter(courseView); // z. B. BorderPane in deinem Dashboard
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onManageGrades() throws IOException {
        try {
            Parent gradeView = FXMLLoader.load(getClass().getResource("/com/school_system/view/grade.fxml"));
            mainPane.setCenter(gradeView); // z. B. BorderPane in deinem Dashboard
        } catch (IOException e) {
            e.printStackTrace();
        }
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
