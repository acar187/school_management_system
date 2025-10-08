package com.school_system;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

import java.sql.*;

public class ReportChartController extends Application {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/schooldb", "root", "");
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("📊 Studenten pro Kurs");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Kurs");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Anzahl Studenten");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Studenten pro Kurs");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Teilnehmerzahlen");

        String sql = """
            SELECT c.name AS course_name, COUNT(e.student_id) AS student_count
            FROM courses c
            LEFT JOIN enrollments e ON c.id = e.course_id
            GROUP BY c.id
            ORDER BY student_count DESC
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                series.getData().add(
                    new XYChart.Data<>(rs.getString("course_name"), rs.getInt("student_count"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        barChart.getData().add(series);

        Scene scene = new Scene(barChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

