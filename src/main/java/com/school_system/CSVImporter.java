package com.school_system;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

import com.school_system.dao.StudentDAO;
import com.school_system.model.Student;

public class CSVImporter {
    public static void importStudentFromCSV(String filePath, StudentDAO studentDAO) {

        // Implementierung des CSV-Imports
        System.out.println("Importiere Daten aus " + filePath);
        // Beispiel: Lesen der Datei und Verarbeiten der Daten
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) { // Überspringe die Kopfzeile
                    firstLine = false;
                    continue;
                    
                }

                String[] parts = line.split(",");
                // Annahme: CSV-Format ist ID,Name,Email,Matrikelnummer
                if (parts.length >= 3) {
                    String name = parts[0].trim();
                    String email = parts[1].trim();
                    String matriculationNumber = parts[2].trim();
                    Student s = new Student(name, email, matriculationNumber);
                    // Fügen Sie den Studenten zur Datenbank oder Liste hinzu
                    studentDAO.addStudent(s); // Beispiel
                }
            }

            reader.close();
        }
        catch (FileNotFoundException e) {
                System.out.println("❌ Datei nicht gefunden: " + e.getMessage());
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("✅ Daten wurden erfolgreich aus " + filePath + " importiert.");
    }
    
}
