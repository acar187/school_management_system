package com.school_system.controller;

import java.sql.SQLException;
import java.time.LocalDate;

import com.school_system.dao.CourseDAO;
import com.school_system.dao.GradeDAO;
import com.school_system.dao.StudentDAO;
import com.school_system.model.Course;
import com.school_system.model.Grade;
import com.school_system.model.Student;
import com.school_system.util.DatabaseConnector;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class GradeController {

    @FXML private ComboBox<Student> studentComboBox;
    @FXML private ComboBox<Course> courseComboBox;
    // Weitere UI-Komponenten und Methoden zur Verwaltung der Noten
    @FXML private TextField gradeField;

    @FXML private TableView<Grade> gradeTable;
    @FXML private TableColumn<Grade, Integer> colId;
    @FXML private TableColumn<Grade, Integer> colStudent;
    @FXML private TableColumn<Grade, Integer> colCourse;
    @FXML private TableColumn<Grade, Double> colGrade;
    @FXML private TableColumn<Grade, LocalDate> colDate;

    private GradeDAO gradeDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    
    @FXML
    public void initialize() throws SQLException {
        //DatabaseConnector.connect();
        gradeDAO = new GradeDAO();
        studentDAO = new StudentDAO();
        courseDAO = new CourseDAO();

            colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
            colStudent.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getStudentId()).asObject());
            colCourse.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getCourseId()).asObject());
            colGrade.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getGradeValue()).asObject());
            colDate.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getDateAssigned()));
            loadData();
    }

    private void loadData() {
        gradeTable.getItems().setAll(gradeDAO.getAllGrades());
        studentComboBox.getItems().setAll(studentDAO.getAllStudents());
        courseComboBox.getItems().setAll(courseDAO.getAllCourses());
        //studentCombo.setItems(FXCollections.observableArrayList(studentDAO.getAllStudents()));
        //courseCombo.setItems(FXCollections.observableArrayList(courseDAO.getAllCourses()));

        //gradeTable.setItems(FXCollections.observableArrayList(gradeDAO.getAllGrades()));
    }

    @FXML
    private void onAddGrade() throws SQLException {
        try {
            Student selectedStudent = studentComboBox.getValue();
            Course selectedCourse = courseComboBox.getValue();
            double gradeValue = Double.parseDouble(gradeField.getText());

            if (selectedStudent == null || selectedCourse == null) {
                showAlert("Fehler", "Bitte wählen Sie einen Studenten und einen Kurs aus.");
                return;
            }

            Grade newGrade = new Grade(selectedStudent.getId(), selectedCourse.getId(), gradeValue);
            gradeDAO.addGrade(newGrade);
            loadData();
            showAlert("Erfolg", "Note erfolgreich hinzugefügt.");
        } catch (NumberFormatException e) {
            showAlert("Fehler", "Ungültiger Notenwert.");
        }
    }

    @FXML 
    private void onUpdateGrade() {
        Grade selectedGrade = gradeTable.getSelectionModel().getSelectedItem();
        if (selectedGrade == null) {
            showAlert("Fehler", "Bitte wählen Sie eine Note aus der Tabelle aus.");
            return;
        }
        try {
            double newGradeValue = Double.parseDouble(gradeField.getText());
            selectedGrade.setGradeValue(newGradeValue);
            gradeDAO.updateGrade(selectedGrade);
            loadData();
            showAlert("Erfolg", "Note erfolgreich aktualisiert.");
        } catch (NumberFormatException e) {
            showAlert("Fehler", "Ungültiger Notenwert.");
        }
    }

    @FXML
    private void onDeleteGrade() throws SQLException {
        Grade selectedGrade = gradeTable.getSelectionModel().getSelectedItem();
        if (selectedGrade == null) {
            showAlert("Fehler", "Bitte wählen Sie eine Note aus der Tabelle aus.");
            return;
        }
        gradeDAO.deleteGrade(selectedGrade.getId());
        loadData();
        showAlert("Erfolg", "Note erfolgreich gelöscht.");
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
