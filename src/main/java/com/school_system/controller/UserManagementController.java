package com.school_system.controller;

// removed unused Connection import
import java.util.List;

import com.school_system.dao.UserDAO;
import com.school_system.model.User;
import com.school_system.util.DatabaseConnector;
import com.school_system.util.PasswordUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UserManagementController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private TableView<User> userTable; // Placeholder, replace ? with actual User type when available
    @FXML private javafx.scene.control.TableColumn<User, Integer> colId;
    @FXML private javafx.scene.control.TableColumn<User, String> colUsername;
    @FXML private javafx.scene.control.TableColumn<User, String> colRole;
    @FXML private javafx.scene.control.TableColumn<User, Void> colActions;
    
    private UserDAO userDAO;
    private ObservableList<User> userList = FXCollections.observableArrayList();
    // currently editing user (null when adding a new user)
    private User editingUser = null;

    @FXML
    public void initialize() {
        // ensure DB is reachable (logs on connect)
        DatabaseConnector.connect();
        userDAO = new UserDAO();
        roleComboBox.setItems(FXCollections.observableArrayList("STUDENT", "TEACHER", "ADMIN"));
        // configure table columns
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colUsername.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getUsername()));
        colRole.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRole()));
        // actions column (Edit/Delete)
        colActions.setCellFactory(tc -> new javafx.scene.control.TableCell<>() {
            private final javafx.scene.control.Button editButton = new javafx.scene.control.Button("Bearbeiten");
            private final javafx.scene.control.Button deleteButton = new javafx.scene.control.Button("Löschen");

            {
                editButton.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    if (user != null) {
                        // populate fields for editing
                        editingUser = user;
                        usernameField.setText(user.getUsername());
                        // we don't have the plain password, so clear the field and leave it optional
                        passwordField.clear();
                        roleComboBox.setValue(user.getRole());
                    }
                });

                deleteButton.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    if (user != null) {
                        userDAO.deleteUser(user.getId());
                        loadUsers();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    javafx.scene.layout.HBox box = new javafx.scene.layout.HBox(5, editButton, deleteButton);
                    setGraphic(box);
                }
            }
        });

        loadUsers();    

    }

    private void loadUsers() {
        userList.clear();
        try{
            List<User> users = userDAO.getAllUsers();
            userList.addAll(users);
            userTable.setItems(userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addUser() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || role == null) {
            System.err.println("❌ Benutzername und Rolle müssen ausgefüllt sein!");
            return;
        }

        if (editingUser == null) {
            // create new user - password required
            if (password.isEmpty()) {
                System.err.println("❌ Passwort ist erforderlich für neue Benutzer!");
                return;
            }
            String pwd_hash = PasswordUtil.hashPassword(password);
            User newUser = new User(0, username, pwd_hash, role);
            userDAO.createUser(newUser);
        } else {
            // update existing user
            editingUser.setUsername(username);
            editingUser.setRole(role);
            if (!password.isEmpty()) {
                String pwd_hash = PasswordUtil.hashPassword(password);
                editingUser.setPasswordHash(pwd_hash);
            }
            userDAO.updateUser(editingUser);
            editingUser = null;
        }

        loadUsers();
        usernameField.clear();
        passwordField.clear();
        roleComboBox.getSelectionModel().clearSelection();
    }
}
