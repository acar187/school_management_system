package com.school_system.controller;

import com.school_system.dao.CourseDAO;
import com.school_system.model.Course;
import com.school_system.util.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;

public class CourseController {

    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, Integer> idColumn;
    @FXML private TableColumn<Course, String> nameColumn;
    @FXML private TableColumn<Course, String> descriptionColumn;
    @FXML private TextField nameField;
    @FXML private TextField descriptionField;
    @FXML private Label statusLabel;

    private ObservableList<Course> courseList;
    private CourseDAO courseDAO;

    @FXML
    public void initialize() {
        Connection conn = DatabaseConnector.connect();
        courseDAO = new CourseDAO();

        idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        //descriptionColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescription()));

        loadCourses();

        courseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                nameField.setText(newSel.getName());
                //descriptionField.setText(newSel.getDescription());
            }
        });
    }

    @FXML
    public void loadCourses() {
        courseList = FXCollections.observableArrayList(courseDAO.getAllCourses());
        courseTable.setItems(courseList);
        statusLabel.setText("🔄 " + courseList.size() + " Kurse geladen.");
    }

    @FXML
    public void handleAddCourse() {
        if (nameField.getText().trim().isEmpty()) {
            showAlert("Fehler", "Kursname darf nicht leer sein!");
            return;
        }

        Course c = new Course(nameField.getText().trim());
        courseDAO.addCourse(c);
        loadCourses();
        clearFields();
        statusLabel.setText("✅ Kurs hinzugefügt.");
    }

    @FXML
    public void handleUpdateCourse() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Fehler", "Bitte wähle einen Kurs aus!");
            return;
        }

        selected.setName(nameField.getText().trim());
        //selected.setDescription(descriptionField.getText().trim());
        courseDAO.updateCourse(selected);
        loadCourses();
        clearFields();
        statusLabel.setText("✏️ Kurs aktualisiert.");
    }

    @FXML
    public void handleDeleteCourse() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Fehler", "Bitte wähle einen Kurs zum Löschen aus!");
            return;
        }

        courseDAO.deleteCourse(selected.getId());
        loadCourses();
        clearFields();
        statusLabel.setText("🗑️ Kurs gelöscht.");
    }

    private void clearFields() {
        nameField.clear();
        descriptionField.clear();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

