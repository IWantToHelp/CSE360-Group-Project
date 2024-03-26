package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrototypeCode extends Application {
    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        primaryStage.setTitle("Innovated Pediatric's Office System");

        initializeHomeView();

        primaryStage.show();
    }

    private void initializeHomeView() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> displayLoginView());

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> displayRegisterView());

        layout.getChildren().addAll(new Label("Welcome to the Innovated Pediatrics Office System"), loginButton, registerButton);


        Scene homeScene = new Scene(layout, 820, 520);
        stage.setScene(homeScene);
    }

    private void displayLoginView() {
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(25));

        Label userTypeLabel = new Label("User Type:");
        ComboBox<String> userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll("Patient", "Nurse", "Doctor");
        userTypeComboBox.setValue("Patient");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username or Patient ID");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Log In");
        loginButton.setOnAction(e -> {
            // Placeholder for login logic
            switch (userTypeComboBox.getValue()) {
                case "Patient":
                    displayPatientPortal();
                    break;
                case "Nurse":
                    displayNursePortal();
                    break;
                case "Doctor":
                    displayDoctorPortal();
                    break;
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> initializeHomeView());

        layout.add(userTypeLabel, 0, 0);
        layout.add(userTypeComboBox, 1, 0);
        layout.add(new Label("Username:"), 0, 1);
        layout.add(usernameField, 1, 1);
        layout.add(new Label("Password:"), 0, 2);
        layout.add(passwordField, 1, 2);
        layout.add(loginButton, 1, 3);
        layout.add(backButton, 1, 4);

        Scene loginScene = new Scene(layout, 820, 520);
        stage.setScene(loginScene);
    }

    private void displayRegisterView() {
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(25));

        // Creating form fields for patient registration
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        DatePicker birthDatePicker = new DatePicker();
        birthDatePicker.setPromptText("Birth Date");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField addressField = new TextField();
        addressField.setPromptText("Home Address");
        TextField insuranceProviderField = new TextField();
        insuranceProviderField.setPromptText("Insurance Provider");
        TextField policyNumberField = new TextField();
        policyNumberField.setPromptText("Policy Number");
        TextField pharmacyAddressField = new TextField();
        pharmacyAddressField.setPromptText("Preferred Pharmacy Address");
        
        // Password fields for account creation
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Create Password");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        // Registration button
        Button registerButton = new Button("Register");
        // Placeholder for registration logic; add actual logic for handling registration

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> initializeHomeView());

        // Adding fields to the layout
        layout.add(new Label("First Name:"), 0, 0);
        layout.add(firstNameField, 1, 0);
        layout.add(new Label("Last Name:"), 0, 1);
        layout.add(lastNameField, 1, 1);
        layout.add(new Label("Date of Birth:"), 0, 2);
        layout.add(birthDatePicker, 1, 2);
        layout.add(new Label("Phone Number:"), 0, 3);
        layout.add(phoneField, 1, 3);
        layout.add(new Label("Email Address:"), 0, 4);
        layout.add(emailField, 1, 4);
        layout.add(new Label("Home Address:"), 0, 5);
        layout.add(addressField, 1, 5);
        layout.add(new Label("Insurance Provider:"), 0, 6);
        layout.add(insuranceProviderField, 1, 6);
        layout.add(new Label("Policy Number:"), 0, 7);
        layout.add(policyNumberField, 1, 7);
        layout.add(new Label("Preferred Pharmacy Address:"), 0, 8);
        layout.add(pharmacyAddressField, 1, 8);
        layout.add(new Label("Password:"), 0, 9);
        layout.add(passwordField, 1, 9);
        layout.add(new Label("Confirm Password:"), 0, 10);
        layout.add(confirmPasswordField, 1, 10);
        layout.add(registerButton, 1, 11);
        layout.add(backButton, 0, 11);

        Scene registerScene = new Scene(layout, 820, 520);
        stage.setScene(registerScene);
    }

 // Add to the PrototypeCode class

    private void displayPatientPortal() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Patient's personal information section
        Button updateInfoButton = new Button("Update Personal Information");
        updateInfoButton.setOnAction(e -> {
            // Placeholder for update information logic
        });

        // Viewing past visits and health records
        Button viewPastVisitsButton = new Button("View Past Visits");
        viewPastVisitsButton.setOnAction(e -> {
            // Placeholder for view past visits logic
        });

        // Messaging with doctor/nurse
        Button messageDoctorButton = new Button("Message Doctor/Nurse");
        messageDoctorButton.setOnAction(e -> {
            // Placeholder for messaging logic
        });

        Button backToHomeButton = new Button("Back to Home");
        backToHomeButton.setOnAction(e -> initializeHomeView());

        layout.getChildren().addAll(new Label("Patient Portal"), updateInfoButton, viewPastVisitsButton, messageDoctorButton, backToHomeButton);

        Scene patientPortalScene = new Scene(layout, 820, 520);
        stage.setScene(patientPortalScene);
    }

    private void displayNursePortal() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Check-in process
        TextField patientIDField = new TextField();
        patientIDField.setPromptText("Enter Patient ID for Check-In");
        Button checkInButton = new Button("Check-In Patient");
        checkInButton.setOnAction(e -> {
            // Placeholder for check-in logic
        });

        // Record patient vitals
        Button recordVitalsButton = new Button("Record Patient Vitals");
        recordVitalsButton.setOnAction(e -> {
            // Placeholder for recording vitals logic
        });

        // View patient history
        Button viewHistoryButton = new Button("View Patient History");
        viewHistoryButton.setOnAction(e -> {
            // Placeholder for viewing patient history logic
        });

        Button backToHomeButton = new Button("Back to Home");
        backToHomeButton.setOnAction(e -> initializeHomeView());

        layout.getChildren().addAll(new Label("Nurse Portal"), patientIDField, checkInButton, recordVitalsButton, viewHistoryButton, backToHomeButton);

        Scene nursePortalScene = new Scene(layout, 820, 520);
        stage.setScene(nursePortalScene);
    }

    private void displayDoctorPortal() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // View patient list and select a patient
        Button viewPatientsButton = new Button("View Patients");
        viewPatientsButton.setOnAction(e -> {
            // Placeholder for view patients logic
        });

        // Enter examination notes
        Button enterNotesButton = new Button("Enter Examination Notes");
        enterNotesButton.setOnAction(e -> {
            // Placeholder for entering notes logic
        });

        // Send medication
        Button sendMedicationButton = new Button("Send Medication");
        sendMedicationButton.setOnAction(e -> {
            // Placeholder for send medication logic
        });

        // Messaging with patients
        Button messagePatientsButton = new Button("Message Patients");
        messagePatientsButton.setOnAction(e -> {
            // Placeholder for messaging logic
        });

        Button backToHomeButton = new Button("Back to Home");
        backToHomeButton.setOnAction(e -> initializeHomeView());

        layout.getChildren().addAll(new Label("Doctor Portal"), viewPatientsButton, enterNotesButton, sendMedicationButton, messagePatientsButton, backToHomeButton);

        Scene doctorPortalScene = new Scene(layout, 820, 520);
        stage.setScene(doctorPortalScene);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

