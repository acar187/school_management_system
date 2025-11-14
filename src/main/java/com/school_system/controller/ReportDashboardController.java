package com.school_system.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import java.sql.*;

import com.school_system.util.DatabaseConnector;

public class ReportDashboardController {

    @FXML
    private BarChart<String, Number> studentsPerCourseChart;

    @FXML
    private PieChart teachersPerCourseChart;

    public void initialize() {
        loadStudentsPerCourse();
        loadTeachersPerCourse();
    }

    private void loadStudentsPerCourse() {
        String query = """
            SELECT c.name AS course_name, COUNT(e.student_id) AS student_count
            FROM courses c
            LEFT JOIN enrollments e ON c.id = e.course_id
            GROUP BY c.name
        """;

        try (Connection conn = DatabaseConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Schüler pro Kurs");

            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString("course_name"), rs.getInt("student_count")));
            }

            studentsPerCourseChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTeachersPerCourse() {
        // TODO: Implement this when teacher management is added
        // For now, add some dummy data to show the chart layout
        teachersPerCourseChart.getData().addAll(
            new PieChart.Data("Mathematik", 2),
            new PieChart.Data("Deutsch", 3),
            new PieChart.Data("Englisch", 2),
            new PieChart.Data("Geschichte", 1)
        );
    }
}

