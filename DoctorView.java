package application;

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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class DoctorView {
    private final Stage stage;
    private final phase3base app;

    public DoctorView(Stage stage, phase3base app) {
        this.stage = stage;
        this.app = app;
    }

    public void displayViewPatient() {
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(20));

        TextField patientIDField = new TextField();
        patientIDField.setPromptText("Enter Patient ID");
        Button viewPatientButton = new Button("View Patient Information");
        viewPatientButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        viewPatientButton.setStyle("-fx-text-fill: #000000;");
        Text feedbackText = new Text();
        TextArea patientInfoArea = new TextArea();
        patientInfoArea.setEditable(false);

        viewPatientButton.setOnAction(e -> {
            String patientID = patientIDField.getText().trim();
            File patientFile = new File(patientID + "_PatientInfo.txt");
            if (patientFile.exists()) {
                try {
                    String content = Files.lines(Paths.get(patientFile.toURI()))
                            .collect(Collectors.joining("\n"));
                    patientInfoArea.setText(content);
                    feedbackText.setText(""); // Clear any previous error message
                } catch (IOException ex) {
                    feedbackText.setText("Error reading file: " + ex.getMessage());
                }
            } else {
                patientInfoArea.setText(""); // Clear any previous content
                feedbackText.setText("Patient ID not found.");
            }
        });

        Button backButton = createBackButton();
        backButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        backButton.setStyle("-fx-text-fill: #000000;");

        layout.add(new Label("Enter Patient ID"), 0, 0);
        layout.add(patientIDField, 1, 0);
        layout.add(viewPatientButton, 1, 1);
        layout.add(feedbackText, 1, 2);
        layout.add(new Label("Patient Information:"), 0, 3);
        GridPane.setColumnSpan(patientInfoArea, 2);
        layout.add(patientInfoArea, 0, 4);
        layout.add(backButton, 1, 5);

        Scene scene = new Scene(layout, 920, 620);
        stage.setScene(scene);
    }

    public void displayEnterExaminationNotes() {
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(20));

        TextField patientIDField = new TextField();
        patientIDField.setPromptText("Patient ID");
        TextArea examinationNotes = new TextArea();
        examinationNotes.setPromptText("Enter examination notes here...");
        Button saveButton = new Button("Save Notes");
        saveButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        saveButton.setStyle("-fx-text-fill: #000000;");

        Text feedbackText = new Text();

        saveButton.setOnAction(e -> {
            String patientID = patientIDField.getText().trim();
            File patientFile = new File(patientID + "_PatientInfo.txt");
            if (patientFile.exists()) {
                try {
                    FileWriter fw = new FileWriter(patientFile, true); // Append to the file
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw);
                    out.println("Examination Notes: " + examinationNotes.getText());
                    out.close();
                    feedbackText.setText("Examination notes for Patient ID: " + patientID + " saved successfully.");
                } catch (IOException ex) {
                    feedbackText.setText("Error saving examination notes: " + ex.getMessage());
                }
            } else {
                feedbackText.setText("Patient ID not found.");
            }
        });

        Button backButton = createBackButton();
        backButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        backButton.setStyle("-fx-text-fill: #000000;");
        
        Label patientTitle = new Label("Patient ID");
        patientTitle.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        patientTitle.setStyle("-fx-text-fill: #000000;");
        
        Label examNotesTitle = new Label("Examination Notes");
        examNotesTitle.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        examNotesTitle.setStyle("-fx-text-fill: #000000;");
        
        layout.add(patientTitle, 0, 0);
        layout.add(patientIDField, 1, 0);
        layout.add(examNotesTitle, 0, 1);
        GridPane.setColumnSpan(examinationNotes, 2);
        layout.add(examinationNotes, 0, 2);
        layout.add(saveButton, 1, 3);
        layout.add(feedbackText, 1, 4);
        layout.add(backButton, 1, 5);

        Scene scene = new Scene(layout, 920, 620);
        stage.setScene(scene);
    }

    public void displaySendMedication() {
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(20));

        TextField patientIDField = new TextField();
        patientIDField.setPromptText("Patient ID");
        TextArea medicationDetails = new TextArea();
        medicationDetails.setPromptText("Enter medication details here...");
        TextArea pharmacyInfo = new TextArea();
        pharmacyInfo.setPromptText("Enter pharmacy information here...");
        Button sendButton = new Button("Send Medication");
        sendButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        sendButton.setStyle("-fx-text-fill: #000000;");
        Text feedbackText = new Text();

        sendButton.setOnAction(e -> {
            String patientID = patientIDField.getText().trim();
            File patientFile = new File(patientID + "_PatientInfo.txt");
            if (patientFile.exists()) {
                try {
                    FileWriter fw = new FileWriter(patientFile, true); // Append to the file
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw);
                    out.println("Medication Details: " + medicationDetails.getText());
                    out.println("Pharmacy Info: " + pharmacyInfo.getText());
                    out.close();
                    feedbackText.setText("Medication details for Patient ID: " + patientID + " sent successfully.");
                } catch (IOException ex) {
                    feedbackText.setText("Error sending medication details: " + ex.getMessage());
                }
            } else {
                feedbackText.setText("Patient ID not found.");
            }
        });

        Button backButton = createBackButton();
        
        Label patientIDLabel = new Label("Patient ID");
        patientIDLabel.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        patientIDLabel.setStyle("-fx-text-fill: #000000;");
        
        Label medTitle = new Label("Medication Details");
        medTitle.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        medTitle.setStyle("-fx-text-fill: #000000;");
        
        Label pharmacyLabel = new Label("Pharmacy Info");
        pharmacyLabel.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        pharmacyLabel.setStyle("-fx-text-fill: #000000;");

        layout.add(patientIDLabel, 0, 0);
        layout.add(patientIDField, 1, 0);
        layout.add(medTitle, 0, 1);
        GridPane.setColumnSpan(medicationDetails, 2);
        layout.add(medicationDetails, 0, 2);
        layout.add(pharmacyLabel, 0, 3);
        GridPane.setColumnSpan(pharmacyInfo, 2);
        layout.add(pharmacyInfo, 0, 4);
        layout.add(sendButton, 1, 5);
        layout.add(feedbackText, 1, 6);
        layout.add(backButton, 1, 7);

        Scene scene = new Scene(layout, 920, 620);
        stage.setScene(scene);
    }
    public void displayMessagePatients() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Enter your message...");

        Button sendMessageButton = new Button("Send Message");
        sendMessageButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        sendMessageButton.setStyle("-fx-text-fill: #000000;");
        sendMessageButton.setOnAction(e -> {
            // Logic to send a message to patients
            System.out.println("Message sent to patients: " + messageArea.getText());
        });

        Button backButton = createBackButton();

        layout.getChildren().addAll(new Label("Message Patients"), messageArea, sendMessageButton, backButton);

        Scene scene = new Scene(layout, 820, 520);
        stage.setScene(scene);
    }


    private Button createBackButton() {
        Button backButton = new Button("Back to Doctor Portal");
        backButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        backButton.setStyle("-fx-text-fill: #000000;");
        backButton.setOnAction(e -> app.displayDoctorPortal());
        return backButton;
        
    }
}