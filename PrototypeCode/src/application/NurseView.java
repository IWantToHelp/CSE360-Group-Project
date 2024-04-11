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
import java.util.List;


public class NurseView {
    private final Stage stage;
    private final phase3base app;

    public NurseView(Stage stage, phase3base app) {
        this.stage = stage;
        this.app = app;
    }

    public void displayCheckInPatient() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        TextField patientIDField = new TextField();
        patientIDField.setPromptText("Enter Patient ID for Check-In");
        Button checkInButton = new Button("Check-In Patient");
        checkInButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        checkInButton.setStyle("-fx-text-fill: #000000;");
        Text feedbackText = new Text();

        checkInButton.setOnAction(e -> {
            String patientID = patientIDField.getText().trim();
            File patientFile = new File(patientID + "_PatientInfo.txt");
            if (!patientFile.exists()) {
                feedbackText.setText("Error: Patient File not found");
            } else {
                feedbackText.setText("Patient " + patientID + " checked in successfully.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        backButton.setStyle("-fx-text-fill: #000000;");
        backButton.setOnAction(e -> app.displayNursePortal());
        
        Label checkIn = new Label("Check-In Patient");
        checkIn.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        checkIn.setStyle("-fx-text-fill: #000000;");

        layout.getChildren().addAll(checkIn, patientIDField, checkInButton, feedbackText, backButton);

        Scene scene = new Scene(layout, 820, 520);
        stage.setScene(scene);
    }

    public void displayRecordVitals() {
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(25));

        TextField patientIDField = new TextField();
        patientIDField.setPromptText("Patient ID");
        TextField temperatureField = new TextField();
        temperatureField.setPromptText("Temperature");
        TextField heartRateField = new TextField();
        heartRateField.setPromptText("Heart Rate");
        TextField breathingRateField = new TextField();
        breathingRateField.setPromptText("Breathing Rate");
        TextField bloodPressureField = new TextField();
        bloodPressureField.setPromptText("Blood Pressure");
        Button saveButton = new Button("Save Vitals");
        saveButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 14));
        saveButton.setStyle("-fx-text-fill: #000000;");

        Text feedbackText = new Text();

        saveButton.setOnAction(e -> {
            String patientID = patientIDField.getText().trim();
            try {
                FileWriter fw = new FileWriter(patientID + "_PatientInfo.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);

                out.println("Temperature: " + temperatureField.getText());
                out.println("Heart Rate: " + heartRateField.getText());
                out.println("Breathing Rate: " + breathingRateField.getText());
                out.println("Blood Pressure: " + bloodPressureField.getText());
                out.close();

                feedbackText.setText("Vitals for Patient ID: " + patientID + " saved successfully.");
            } catch (IOException ex) {
                feedbackText.setText("Error saving vitals: " + ex.getMessage());
            }
        });

        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        backButton.setStyle("-fx-text-fill: #000000;");
        
        backButton.setOnAction(e -> app.displayNursePortal());
        
        Label patient = new Label("Patient ID");
        patient.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        patient.setStyle("-fx-text-fill: #000000;");
        Label temp = new Label("Temperature");
        temp.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        temp.setStyle("-fx-text-fill: #000000;");
        Label heartRate = new Label("Heart Rate");
        heartRate.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        heartRate.setStyle("-fx-text-fill: #000000;");
        Label breathing = new Label("Breathing Rate");
        breathing.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        breathing.setStyle("-fx-text-fill: #000000;");
        Label bloodPressure = new Label("Blood Pressure");
        bloodPressure.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        bloodPressure.setStyle("-fx-text-fill: #000000;");
       
        layout.add(patient, 0, 0);
        layout.add(patientIDField, 1, 0);
        layout.add(temp, 0, 1);
        layout.add(temperatureField, 1, 1);
        layout.add(heartRate, 0, 2);
        layout.add(heartRateField, 1, 2);
        layout.add(breathing, 0, 3);
        layout.add(breathingRateField, 1, 3);
        layout.add(bloodPressure, 0, 4);
        layout.add(bloodPressureField, 1, 4);
        layout.add(saveButton, 1, 5);
        layout.add(feedbackText, 1, 6);
        layout.add(backButton, 1, 7);

        Scene scene = new Scene(layout, 820, 520);
        stage.setScene(scene);
    }

    public void displayPatientHistory() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        TextField patientIDField = new TextField();
        patientIDField.setPromptText("Enter Patient ID");
        Button viewHistoryButton = new Button("View History");
        viewHistoryButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        viewHistoryButton.setStyle("-fx-text-fill: #000000;");
        TextArea historyArea = new TextArea();
        historyArea.setEditable(false);

        viewHistoryButton.setOnAction(e -> {
            String patientID = patientIDField.getText().trim();
            File patientFile = new File(patientID + "_PatientInfo.txt");
            if (patientFile.exists()) {
                try {
                    List<String> lines = Files.readAllLines(Paths.get(patientFile.toURI()));
                    lines.forEach(line -> historyArea.appendText(line + "\n"));
                } catch (IOException ex) {
                    historyArea.setText("Error reading file: " + ex.getMessage());
                }
            } else {
                historyArea.setText("Patient file not found.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
        backButton.setStyle("-fx-text-fill: #000000;");
        backButton.setOnAction(e -> app.displayNursePortal());
        
        Label history = new Label("View Patient History");

        layout.getChildren().addAll(history, patientIDField, viewHistoryButton, historyArea, backButton);

        Scene scene = new Scene(layout, 820, 520);
        stage.setScene(scene);
    }
   

    // Additional helper methods for file operations or utility functions can be placed here.
}