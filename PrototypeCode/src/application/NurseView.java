package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
        backButton.setOnAction(e -> app.displayNursePortal());

        layout.getChildren().addAll(new Label("Check-In Patient"), patientIDField, checkInButton, feedbackText, backButton);

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
        DatePicker visitDate = new DatePicker(); // DatePicker for recording the visit date
        visitDate.setPromptText("Visit Date");
        TextField temperatureField = new TextField();
        temperatureField.setPromptText("Temperature");
        TextField heartRateField = new TextField();
        heartRateField.setPromptText("Heart Rate");
        TextField breathingRateField = new TextField();
        breathingRateField.setPromptText("Breathing Rate");
        TextField bloodPressureField = new TextField();
        bloodPressureField.setPromptText("Blood Pressure");
        Button saveButton = new Button("Save Vitals");
        Text feedbackText = new Text();

        saveButton.setOnAction(e -> {
            String patientID = patientIDField.getText().trim();
            if (visitDate.getValue() == null) {
                feedbackText.setText("Please select a date for the visit.");
                return;
            }
            try {
                FileWriter fw = new FileWriter(patientID + "_PatientVisits.txt", true); // Append to the patient's visit file
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);

                // Writing visit date and vitals to the file
                out.println("Visit Date: " + visitDate.getValue().toString());
                out.println("Temperature: " + temperatureField.getText());
                out.println("Heart Rate: " + heartRateField.getText());
                out.println("Breathing Rate: " + breathingRateField.getText());
                out.println("Blood Pressure: " + bloodPressureField.getText());
                out.println(""); // Adding a blank line for readability
                out.close();

                feedbackText.setText("Vitals for Patient ID: " + patientID + " saved successfully on " + visitDate.getValue());
            } catch (IOException ex) {
                feedbackText.setText("Error saving vitals: " + ex.getMessage());
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> app.displayNursePortal());

        layout.add(new Label("Patient ID"), 0, 0);
        layout.add(patientIDField, 1, 0);
        layout.add(new Label("Visit Date"), 0, 1); // Label for the visit date
        layout.add(visitDate, 1, 1); // DatePicker for the visit date
        layout.add(new Label("Temperature"), 0, 2);
        layout.add(temperatureField, 1, 2);
        layout.add(new Label("Heart Rate"), 0, 3);
        layout.add(heartRateField, 1, 3);
        layout.add(new Label("Breathing Rate"), 0, 4);
        layout.add(breathingRateField, 1, 4);
        layout.add(new Label("Blood Pressure"), 0, 5);
        layout.add(bloodPressureField, 1, 5);
        layout.add(saveButton, 1, 6);
        layout.add(feedbackText, 1, 7);
        layout.add(backButton, 1, 8);

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
        TextArea historyArea = new TextArea();
        historyArea.setEditable(false);

        viewHistoryButton.setOnAction(e -> {
            String patientID = patientIDField.getText().trim();
            File patientFile = new File(patientID + "_PatientVisits.txt");
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
        backButton.setOnAction(e -> app.displayNursePortal());

        layout.getChildren().addAll(new Label("View Patient History"), patientIDField, viewHistoryButton, historyArea, backButton);

        Scene scene = new Scene(layout, 820, 520);
        stage.setScene(scene);
    }
   

    // Additional helper methods for file operations or utility functions can be placed here.
}
