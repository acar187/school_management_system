package com.school_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.school_system.model.Student;
import com.school_system.model.User;
import com.school_system.dao.StudentDAO;
import com.school_system.util.DatabaseConnector;

import java.sql.Connection;
import java.util.List;

public class StudentController {

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Integer> colId;
    @FXML private TableColumn<Student, String> colName;
    @FXML private TableColumn<Student, String> colEmail;
    @FXML private TableColumn<Student, String> colMatriculation;
    @FXML private TextField searchField;

    @FXML private TextField nameField, emailField, matriculationField;

    private StudentDAO studentDAO;
    private ObservableList<Student> students;
    private ObservableList<Student> studentList;
    private FilteredList<Student> filteredList;

    private User user;

    public void setUser(User user) {
        this.user = user;
        // später z. B. unterschiedliche Rechte prüfen
    }


    @FXML
    public void initialize() {
        Connection conn = DatabaseConnector.connect();
        studentDAO = new StudentDAO();

        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        colEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));
        colMatriculation.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMatriculationNumber()));

        loadStudents();

        // Daten holen
        List<Student> studentsFromDB = studentDAO.getAllStudents();
        if (studentsFromDB == null) {
            System.err.println("❌ Keine Studenten aus DB erhalten!");
            studentsFromDB = List.of(); // leere Liste, um NullPointer zu vermeiden
        }

        // ObservableList initialisieren
        studentList = FXCollections.observableArrayList(studentsFromDB);

        // Jetzt ist studentList != null
        FilteredList<Student> filteredData = new FilteredList<>(studentList, p -> true);

        // TableView mit Daten befüllen
        studentTable.setItems(filteredData);

         // 🔍 Filter-Funktion
        filteredList = new FilteredList<>(studentList, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(student -> {
                // Wenn Suchfeld leer → alles anzeigen
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (student.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (student.getEmail() != null && student.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (student.getMatriculationNumber() != null && student.getMatriculationNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // kein Treffer
            });
        });

        // SortedList mit TableView verbinden
        SortedList<Student> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(studentTable.comparatorProperty());
        studentTable.setItems(sortedList);

        // Klick auf Zeile → Formular befüllen
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                nameField.setText(newSel.getName());
                emailField.setText(newSel.getEmail());
                matriculationField.setText(newSel.getMatriculationNumber());
            }
        });
    }

    @FXML
    private void loadStudents() {
        students = FXCollections.observableArrayList(studentDAO.getAllStudents());
        studentTable.setItems(students);
    }

    @FXML
    private void handleAdd() {
        String name = nameField.getText();
        String email = emailField.getText();
        String matriculation = matriculationField.getText();

        if (name.isEmpty() || email.isEmpty() || matriculation.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Bitte alle Felder ausfüllen!").show();
            return;
        }

        Student s = new Student(name, email, matriculation);
        studentDAO.addStudent(s);{
        System.out.println("Student hinzugefügt: " + s.getId());
        if (s.getId() != 0) {
            loadStudents();
            nameField.clear();
            emailField.clear();
            matriculationField.clear();
            }    
        }
    }

    @FXML
    private void handleUpdateStudent() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Bitte wähle einen Schüler aus!").show();
            return;
        } else {

        String name = nameField.getText();
        String email = emailField.getText();
        String matriculation = matriculationField.getText();

        selected.setName(name);
        selected.setEmail(email);
        selected.setMatriculationNumber(matriculation);

        boolean updated = false;
        try {
            studentDAO.updateStudent(selected);
            updated = true;
        } catch (Exception e) {
            System.out.println("Fehler beim Aktualisieren: " + e.getMessage());
        }

        // Always reload the list and refresh the table so UI reflects DB state
        loadStudents();
        studentTable.refresh();
        clearFields();

        if (updated) {
            new Alert(Alert.AlertType.INFORMATION, "Student erfolgreich aktualisiert.").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Update möglicherweise fehlgeschlagen.").show();
        }
        
        }
    }

    @FXML
    private void handleDelete() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            studentDAO.deleteStudent(selected.getId());
            loadStudents();
        }
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        matriculationField.clear();
    }

}
