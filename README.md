# Schulverwaltungssystem – JavaFX & MySQL

## Demo
https://github.com/acar187/school_management_system/releases/tag/demo#:~:text=through%20the%20app.-,Assets,-3

## Übersicht

Dieses Projekt ist ein **Schulverwaltungssystem** in JavaFX mit MySQL-Anbindung.  
Es ermöglicht:

- Verwaltung von Schülern und Kursen  
- Benutzerverwaltung mit **rollenbasiertem Login** (Admin, Lehrer, Schüler)  
- Reporting & Statistiken (Schüler pro Kurs)  
- Export von Daten als CSV  

---
| Komponente | Version |
|-----------|---------|
| Java | 17+ |
| Maven | 3.8+ |
| MySQL | 8+ |
| JavaFX | über Maven eingebunden |
---

## Datenbank Setup & Installation

1. **MySQL** installieren und starten  
2. Datenbank erstellen: schooldb
3. Importiere schooldb_backup.sql oder schooldb_schema.sql
4. SchoolManager-1.0.8.dmg funktioniert mit folgendem Datenbank URL, Benutzer und Password konfiguration:
   ```
    URL = jdbc:mysql://localhost:3306/schooldb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    USER = schooluser
    PASSWORD = schoolpass
   ```

## Run
1. doppel klick auf SchoolManager-1.0.8.dmg auf einem Macbook

## Features
Benutzerverwaltung

Admin: volle Rechte inkl. Benutzerverwaltung
Lehrer: Zugriff auf Schüler & Kurse
Student: Zugriff auf eigene Daten

Reporting & Export
Dashboard mit Balken- und Kreisdiagrammen

Filter nach Kurs möglich

Export: CSV-Dateien

Speicherort auswählbar per JavaFX FileChooser

