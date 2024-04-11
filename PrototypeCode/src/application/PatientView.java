package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PatientView {
    private final Stage stage;
    private final phase3base app;

    public PatientView(Stage stage, phase3base app) {
        this.stage = stage;
        this.app = app;
    }

    public void viewPastVisits() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        TextField patientIDField = new TextField();
        patientIDField.setPromptText("Enter Your Patient ID");
        Button viewVisitsButton = new Button("View Past Visits");
        TextArea visitsArea = new TextArea();
        visitsArea.setEditable(false);

        viewVisitsButton.setOnAction(e -> {
            // Clearing the visitsArea content every time the button is clicked
            visitsArea.setText("");

            String patientID = patientIDField.getText().trim();
            File patientFile = new File(patientID + "_PatientVisits.txt");
            if (patientFile.exists()) {
                try {
                    List<String> lines = Files.readAllLines(Paths.get(patientFile.toURI()));
                    if (lines.isEmpty()) {
                        visitsArea.setText("No past visits found.");
                    } else {
                        // Displaying all lines from the file
                        String content = String.join("\n", lines);
                        visitsArea.setText(content);
                    }
                } catch (IOException ex) {
                    visitsArea.setText("Error reading file: " + ex.getMessage());
                }
            } else {
                visitsArea.setText("No past visits found.");
            }
        });

        Button backButton = new Button("Back to Patient Portal");
        backButton.setOnAction(e -> app.displayPatientPortal());

        layout.getChildren().addAll(new Label("View Past Visits"), patientIDField, viewVisitsButton, visitsArea, backButton);

        Scene scene = new Scene(layout, 820, 520);
        stage.setScene(scene);
    }

    // Additional helper methods or functionalities can be added here as needed
}

