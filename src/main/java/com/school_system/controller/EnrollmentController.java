package com.school_system.controller;

import com.school_system.dao.CourseDAO;
import com.school_system.dao.EnrollmentDAO;
import com.school_system.dao.StudentDAO;
import com.school_system.model.Course;
import com.school_system.model.Enrollment;
import com.school_system.model.Student;
import com.school_system.util.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class EnrollmentController {

    @FXML private ComboBox<Student> studentCombo;
    @FXML private ComboBox<Course> courseCombo;
    @FXML private TableView<Course> enrolledCoursesTable;
    @FXML private TableColumn<Course, Integer> courseIdColumn;
    @FXML private TableColumn<Course, String> courseNameColumn;
    @FXML private Label statusLabel;

    private EnrollmentDAO enrollmentDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;

    // connection was previously opened here but not used; removed to avoid leaks

    @FXML
    public void initialize() {
        enrollmentDAO = new EnrollmentDAO();
        studentDAO = new StudentDAO();
        courseDAO = new CourseDAO();

        courseIdColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        courseNameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        loadData();

        studentCombo.setOnAction(e -> loadEnrolledCourses());
    }

    @FXML
    public void loadData() {
        studentCombo.setItems(FXCollections.observableArrayList(studentDAO.getAllStudents()));
        courseCombo.setItems(FXCollections.observableArrayList(courseDAO.getAllCourses()));
    }

    private void loadEnrolledCourses() {
        Student selectedStudent = studentCombo.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) return;

        // EnrollmentDAO returns a list of course names for the student
        List<String> enrolledCourseNames = enrollmentDAO.getCoursesByStudentId(selectedStudent.getId());
        ObservableList<Course> courses = FXCollections.observableArrayList();

        for (Course c : courseDAO.getAllCourses()) {
            // compare by course name since the DAO returns names
            if (enrolledCourseNames.contains(c.getName())) {
                courses.add(c);
            }
        }

        enrolledCoursesTable.setItems(courses);
        statusLabel.setText("📚 " + courses.size() + " Kurse für " + selectedStudent.getName());
    }

    @FXML
    public void handleAddEnrollment() {
        Student s = studentCombo.getSelectionModel().getSelectedItem();
        Course c = courseCombo.getSelectionModel().getSelectedItem();
        if (s == null || c == null) {
            showAlert("Fehler", "Bitte wähle Schüler und Kurs aus!");
            return;
        }

        enrollmentDAO.addEnrollment(new Enrollment(s.getId(), c.getId()));
        loadEnrolledCourses();
        statusLabel.setText("✅ Kurs zugewiesen: " + c.getName());
    }

    @FXML
    public void handleRemoveEnrollment() {
        Student s = studentCombo.getSelectionModel().getSelectedItem();
        Course c = enrolledCoursesTable.getSelectionModel().getSelectedItem();
        if (s == null || c == null) {
            showAlert("Fehler", "Bitte wähle Schüler und Kurs aus!");
            return;
        }

        enrollmentDAO.removeEnrollment(s.getId(), c.getId());
        loadEnrolledCourses();
        statusLabel.setText("🗑️ Kurs entfernt: " + c.getName());
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
