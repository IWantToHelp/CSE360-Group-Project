package application;

import java.io.BufferedWriter; // I imported the Buffered Writer so it can overwrite .txt files
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths; // makes it easier to look through files
import java.util.List;
import java.time.LocalDate;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class phase3base extends Application {
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
        loginButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 18));
        loginButton.setStyle("-fx-background-color: #6495ed; -fx-text-fill: #000000;");

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> displayRegisterView());
        registerButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 18));
        registerButton.setStyle("-fx-background-color: #6495ed; -fx-text-fill: #000000;");

        
        Label title = new Label("Welcome to the Innovated Pediatrics Office System");
        title.setFont(Font.font("Helvetica",FontWeight.BOLD, 25));
        title.setStyle("-fx-text-fill: #000000;");

        layout.getChildren().addAll(title, loginButton, registerButton);


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
        userTypeLabel.setFont(Font.font("Helvetica",FontWeight.BOLD, 18));
        userTypeLabel.setStyle("-fx-text-fill: #000000;");
        ComboBox<String> userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll("Patient", "Nurse", "Doctor");
        userTypeComboBox.setValue("Patient");

        TextField usernameField = new TextField();
        usernameField.setPromptText("5 Digit Valid ID");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Log In");
        loginButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 14));
        loginButton.setStyle("-fx-background-color: #6495ed; -fx-text-fill: #000000;");
        Label errorMessage = new Label("Invalid ID or Password");
        errorMessage.setStyle("-fx-text-fill: #000000;");
        errorMessage.setVisible(false);
        

        loginButton.setOnAction(e -> {
          
            switch (userTypeComboBox.getValue()) {
                case "Patient":
                    String providedID = usernameField.getText().trim();
                    String providedPassword = passwordField.getText().trim();
                    String filepath = providedID + "_PatientInfo.txt";

                    try {
                        String patientData = new String(Files.readAllBytes(Paths.get(filepath)));
                        String[] lines = patientData.split("\n");
                        if (lines.length >= 2) {
                            String patientIDFromFile = lines[0].trim(); // First line is ID
                            String passwordFromFile = lines[1].trim(); // Second line is Password
                            if (patientIDFromFile.equals(providedID) && passwordFromFile.equals(providedPassword)) {
                                displayPatientPortal();
                                return;
                            }
                        } else {
                            System.out.println("File format is incorrect. Cannot authenticate user.");
                        }
                    } catch (IOException ex) {
                        System.out.println("Error reading file: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                    System.out.println("Authentication failed. Invalid ID or Password.");
                    errorMessage.setVisible(true);
                    break;

                case "Nurse":
                	 String nurseID = "12345";
                     String nursePassword = "nurse";
                     String providedNurseID = usernameField.getText().trim();
                     String providedNursePassword = passwordField.getText().trim();
                  
                     if (nurseID.equals(providedNurseID) && nursePassword.equals(providedNursePassword)) {
                    	 displayNursePortal();
                         return;
                     } else {
             System.out.println("Authentication failed. Invalid ID or Password.");
             errorMessage.setVisible(true);
                     }
                    break;

                case "Doctor":
                    String doctorID = "54321";
                    String doctorPassword = "doctor";
                    String providedDoctorID = usernameField.getText().trim();
                    String providedDoctorPassword = passwordField.getText().trim();
                 
                    if (doctorID.equals(providedDoctorID) && doctorPassword.equals(providedDoctorPassword)) {
                    	displayDoctorPortal();
                   	    return;
                    } else {
            System.out.println("Authentication failed. Invalid ID or Password.");
            errorMessage.setVisible(true);
                    }
                    break;
            }
        });

        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 14));
        backButton.setStyle("-fx-background-color: #6495ed; -fx-text-fill: #000000;");
        backButton.setOnAction(e -> initializeHomeView());
        
        Label enterLabel = new Label("Enter ID:");
        enterLabel.setFont(Font.font("Helvetica",FontWeight.BOLD, 18));
        enterLabel.setStyle("-fx-text-fill: #000000;");
        Label passLabel = new Label("Password:");
        passLabel .setFont(Font.font("Helvetica",FontWeight.BOLD, 18));
        passLabel.setStyle("-fx-text-fill: #000000;");

        layout.add(userTypeLabel, 0, 0);
        layout.add(userTypeComboBox, 1, 0);
        layout.add(enterLabel, 0, 1);
        layout.add(usernameField, 1, 1);
        layout.add(passLabel, 0, 2);
        layout.add(passwordField, 1, 2);
        layout.add(loginButton, 1, 3);
        layout.add(backButton, 1, 4);
        layout.add(errorMessage, 1, 5);

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
        
        Text errorMessage = new Text();

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
        layout.add(errorMessage, 0, 12);

        Scene registerScene = new Scene(layout, 820, 520);
        stage.setScene(registerScene);
       
        //error handling if passwords aren't the same is needed
        
         //creating the save button
        registerButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 20));
        registerButton.setStyle("-fx-background-color: #6495ed; -fx-text-fill: #ffffff;");
        registerButton.setOnAction(e ->{  
            if(firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || birthDatePicker.getValue() == null || phoneField.getText().isEmpty() ||
            		emailField.getText().isEmpty() || addressField.getText().isEmpty() || insuranceProviderField.getText().isEmpty() || policyNumberField.getText().isEmpty() ||
            		pharmacyAddressField.getText().isEmpty() || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()){
            	errorMessage.setText("Error: A field is missing");
            } else {
            	saveFile(firstNameField.getText(), lastNameField.getText(), birthDatePicker.getValue(), phoneField.getText(),
            			emailField.getText(), addressField.getText(), insuranceProviderField.getText(), policyNumberField.getText(), pharmacyAddressField.getText(), passwordField.getText(), confirmPasswordField.getText(), errorMessage);
            	firstNameField.clear();
            	lastNameField.clear();
            	birthDatePicker.setValue(null);
            	phoneField.clear();
            	emailField.clear();
            	addressField.clear();
            	insuranceProviderField.clear();
            	policyNumberField.clear();
            	pharmacyAddressField.clear();
            	passwordField.clear();
            	confirmPasswordField.clear();
         }}); //saving the information gathered
          
    }
    
    
    
    private void updatePatientInfo() {
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
        TextField patientIDField = new TextField();
        patientIDField.setPromptText("Enter your ID");
        // Password fields for account creation
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Create Password");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        // Registration button
        Button saveButton = new Button("Save");
        // Placeholder for registration logic; add actual logic for handling registration

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> initializeHomeView());
        
        Text errorMessage = new Text();

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
        layout.add(new Label("Enter ID:"), 0, 11);
        layout.add(patientIDField, 1, 11);
        
        layout.add(saveButton, 1, 12);
        layout.add(backButton, 0, 12);
        layout.add(errorMessage, 0, 13);

        Scene updateScene = new Scene(layout, 820, 520);
        stage.setScene(updateScene);
       
        //error handling if passwords aren't the same is needed
        
         //creating the save button
        saveButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 20));
        saveButton.setStyle("-fx-background-color: #6495ed; -fx-text-fill: #ffffff;");
        saveButton.setOnAction(e ->{  
            if(firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || birthDatePicker.getValue() == null || phoneField.getText().isEmpty() ||
            		emailField.getText().isEmpty() || addressField.getText().isEmpty() || insuranceProviderField.getText().isEmpty() || policyNumberField.getText().isEmpty() ||
            		pharmacyAddressField.getText().isEmpty() || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty() || patientIDField.getText().isEmpty()){
            	errorMessage.setText("Error: A field is missing");
            } else {
            	updatePatientFile(patientIDField.getText(), firstNameField.getText(), lastNameField.getText(), birthDatePicker.getValue(), phoneField.getText(),
            			emailField.getText(), addressField.getText(), insuranceProviderField.getText(), policyNumberField.getText(), pharmacyAddressField.getText(), passwordField.getText(), confirmPasswordField.getText(), errorMessage);
            	patientIDField.clear();
            	firstNameField.clear();
            	lastNameField.clear();
            	birthDatePicker.setValue(null);
            	phoneField.clear();
            	emailField.clear();
            	addressField.clear();
            	insuranceProviderField.clear();
            	policyNumberField.clear();
            	pharmacyAddressField.clear();
            	passwordField.clear();
            	confirmPasswordField.clear();
         }}); //saving the information gathered
    }
    
    
    private void updatePatientFile(String originalPatientID, String firstNameField, String lastNameField, LocalDate birthDatePicker, String phoneField, //save all of the information gathered
            String emailField, String addressField, String insuranceProviderField, String policyNumberField, String pharmacyAddressField, String passwordField, String confirmPasswordField, Text errorMessage) {
    	String patientFile = (originalPatientID + "_PatientInfo.txt"); //making sure the file name is correctly formatted

    	try (FileWriter writer = new FileWriter(patientFile)) { //creating the file and writing to it so that information can be properly stored
    		writer.write(originalPatientID + "\n");
    		writer.write(passwordField + "\n");
    		writer.write(firstNameField + "\n");
			writer.write(lastNameField + "\n");
			writer.write(birthDatePicker + "\n");
			writer.write(phoneField + "\n");
			writer.write(emailField + "\n");
			writer.write(addressField + "\n");
			writer.write(insuranceProviderField + "\n");
			writer.write(policyNumberField + "\n");
			writer.write(pharmacyAddressField + "\n");
		System.out.println("Information successfully saved to " + patientFile);
		errorMessage.setText("Patient ID: " + originalPatientID);
		errorMessage.setFont(Font.font("Helvetica", 18));
	} catch (IOException e) {
		errorMessage.setText("Error, information not properly collected: " + e.getMessage());
	}
}
    
    private String idGenerator() {  //generates a random 5 digit number
    	Random randomNumber = new Random();
        int randomNum = randomNumber.nextInt(90000) + 10000;
    	return String.valueOf(randomNum);
    }

    
    
    private void saveFile(String firstNameField, String lastNameField, LocalDate birthDatePicker, String phoneField, //save all of the information gathered
            String emailField, String addressField, String insuranceProviderField, String policyNumberField, String pharmacyAddressField, String passwordField, String confirmPasswordField, Text errorMessage) {
    	String patientID = idGenerator();
    	String patientFile = (patientID + "_PatientInfo.txt"); //making sure the file name is correctly formatted

    	try (FileWriter writer = new FileWriter(patientFile)) { //creating the file and writing to it so that information can be properly stored
    		writer.write(patientID + "\n");
    		writer.write(passwordField + "\n");
    		writer.write(firstNameField + "\n");
			writer.write(lastNameField + "\n");
			writer.write(birthDatePicker + "\n");
			writer.write(phoneField + "\n");
			writer.write(emailField + "\n");
			writer.write(addressField + "\n");
			writer.write(insuranceProviderField + "\n");
			writer.write(policyNumberField + "\n");
			writer.write(pharmacyAddressField + "\n");
		System.out.println("Information successfully saved to " + patientFile);
		errorMessage.setText("Patient ID Created: " + patientID);
		errorMessage.setFont(Font.font("Helvetica", 18));
	} catch (IOException e) {
		errorMessage.setText("Error, information not properly collected: " + e.getMessage());
	}
}
    private void displayPatientHistoryScene(String patientID)
    {
    	VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        // this is so it reads through all the lines of the file
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(patientID + "_PatientInfo.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        TextArea historyTextArea = new TextArea(); // this is where the patient history is then displayed
        historyTextArea.setEditable(false);
        for (String line : lines) {
            historyTextArea.appendText(line + "\n");
        }
        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> displayNursePortal());
        layout.getChildren().addAll(new Label("Patient History"), historyTextArea, backButton);
        Scene patientHistoryScene = new Scene(layout, 820, 520);
        stage.setScene(patientHistoryScene);
    }
    
    private void displayPatientHistorySceneII(String patientID)
    {
    	VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        // this is so it reads through all the lines of the file
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(patientID + "_PatientInfo.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        TextArea historyTextArea = new TextArea(); // this is where the patient history is then displayed
        historyTextArea.setEditable(false);
        for (String line : lines) {
            historyTextArea.appendText(line + "\n");
        }
        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> displayPatientPortal());
        layout.getChildren().addAll(new Label("Patient History"), historyTextArea, backButton);
        Scene patientHistoryScene = new Scene(layout, 820, 520);
        stage.setScene(patientHistoryScene);
    }


 // Add to the PrototypeCode class

    public void displayPatientPortal() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        
        Text errorMessage = new Text();
        Text successMessage = new Text();
        
        TextField patientIDField = new TextField();
        patientIDField.setPromptText("Re-Enter Patient ID");
        Button checkInButton = new Button("Confirm");
        checkInButton.setOnAction(e -> {
            String patientID = patientIDField.getText().trim();
            // Makes sure the patient ID is 5 digits long
            if (patientID.length() != 5 || !patientID.matches("\\d+")) {
                errorMessage.setText("Error: Patient ID is invalid");
            } else {
                File patientFile = new File(patientID + "_PatientInfo.txt");
                // if patientFile not found it displays the error
                if (!patientFile.exists()) {
                    errorMessage.setText("Error: Patient File is not found");
                } else {
                    successMessage.setText("Successfully checked in " + patientID);
                }
            }
        });
           
        // Patient's personal information section
        Button updateInfoButton = new Button("Update Personal Information");
        updateInfoButton.setOnAction(e -> {
            updatePatientInfo();
        });

        // Viewing past visits and health records
        Button viewHistoryButton = new Button("View Patient History");
        viewHistoryButton.setOnAction(e -> {
            // Placeholder for viewing patient history logic
        	String patientID = patientIDField.getText().trim();
            // makes sure the patient ID is 5 digits long
            if (patientID.length() != 5 || !patientID.matches("\\d+")) {
                errorMessage.setText("Error: Patient ID is invalid"); // if the patient ID is not 5 digits long, then an error is displayed
            } else {
                File patientFile = new File(patientID + "_PatientInfo.txt"); // // opens file if found
                if (!patientFile.exists()) {
                    errorMessage.setText("Error: Patient File is not found"); // if file not found error is displayed
                } else {
                    displayPatientHistorySceneII(patientID); // displays if the file is found
                }
            }
        });

        // Messaging with doctor/nurse
        Button messageDoctorButton = new Button("Message Doctor/Nurse");
        MessagingCode patientMessages = new MessagingCode(this.stage, this);
        messageDoctorButton.setOnAction(e -> {
            patientMessages.displayMessageView("Patient");
        });

        Button backToHomeButton = new Button("Back to Home");
        backToHomeButton.setOnAction(e -> initializeHomeView());

        layout.getChildren().addAll(new Label("Patient Portal"), patientIDField, checkInButton, updateInfoButton, viewHistoryButton, messageDoctorButton, backToHomeButton, errorMessage, successMessage);

        Scene patientPortalScene = new Scene(layout, 820, 520);
        stage.setScene(patientPortalScene);
    }

    void displayNursePortal() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Nurse-specific functionality buttons
        Button checkInPatientButton = new Button("Check-In Patient");
        checkInPatientButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 18));
        checkInPatientButton.setStyle("-fx-text-fill: #000000;");
        Button recordVitalsButton = new Button("Record Patient Vitals");
        recordVitalsButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 18));
        recordVitalsButton.setStyle("-fx-text-fill: #000000;");
        Button viewPatientHistoryButton = new Button("View Patient History");
        viewPatientHistoryButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 18));
        viewPatientHistoryButton.setStyle("-fx-text-fill: #000000;");


        // NurseView actions setup
        checkInPatientButton.setOnAction(e -> new NurseView(this.stage, this).displayCheckInPatient());
        recordVitalsButton.setOnAction(e -> new NurseView(this.stage, this).displayRecordVitals());
        viewPatientHistoryButton.setOnAction(e -> new NurseView(this.stage, this).displayPatientHistory());


        Button backToHomeButton = new Button("Back to Home");
        backToHomeButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 18));
        backToHomeButton.setStyle("-fx-text-fill: #000000;");
        
        backToHomeButton.setOnAction(e -> initializeHomeView());
        
        Label nurseTitle = new Label("Nurse Portal");
        nurseTitle.setFont(Font.font("Helvetica",FontWeight.BOLD, 20));
        nurseTitle.setStyle("-fx-text-fill: #000000;");
        

        layout.getChildren().addAll(nurseTitle, checkInPatientButton, recordVitalsButton, viewPatientHistoryButton, backToHomeButton);

        Scene nursePortalScene = new Scene(layout, 820, 520);
        stage.setScene(nursePortalScene);
    }
    
    void displayDoctorPortal() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        MessagingCode doctorMessage = new MessagingCode(this.stage, this);
        // View patient list and select a patient
        Button viewPatientButton = new Button("View Patient");
        viewPatientButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        viewPatientButton.setStyle("-fx-text-fill: #000000;");
        

        // Enter examination notes
        Button enterNotesButton = new Button("Enter Examination Notes");
        enterNotesButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        enterNotesButton.setStyle("-fx-text-fill: #000000;");
       

        // Send medication
        Button sendMedicationButton = new Button("Send Medication");
        sendMedicationButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        sendMedicationButton.setStyle("-fx-text-fill: #000000;");
       
  

        // Messaging with patients
        Button messagePatientsButton = new Button("Message Patients");
        messagePatientsButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        messagePatientsButton.setStyle("-fx-text-fill: #000000;");
        
        viewPatientButton.setOnAction(e -> new DoctorView(this.stage, this).displayViewPatient());
        enterNotesButton.setOnAction(e -> new DoctorView(this.stage, this).displayEnterExaminationNotes());
        sendMedicationButton.setOnAction(e -> new DoctorView(this.stage, this).displaySendMedication());
        messagePatientsButton.setOnAction(e -> doctorMessage.displayMessageView("Doctor"));
        
        Button backToHomeButton = new Button("Back to Home");
        backToHomeButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        backToHomeButton.setStyle("-fx-text-fill: #000000;");
        
        backToHomeButton.setOnAction(e -> initializeHomeView());
        
        Label doctorPortalTitle = new Label("Doctor Portal");
        doctorPortalTitle.setFont(Font.font("Helvetica",FontWeight.BOLD, 20));
        doctorPortalTitle.setStyle("-fx-text-fill: #000000;");

        layout.getChildren().addAll(doctorPortalTitle, viewPatientButton, enterNotesButton, sendMedicationButton, messagePatientsButton, backToHomeButton);

        Scene doctorPortalScene = new Scene(layout, 820, 520);
        stage.setScene(doctorPortalScene);
    }


    public static void main(String[] args) {
        launch(args);
    }
}