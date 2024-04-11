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
import java.util.stream.Collectors;

public class MessagingCode{
    private Stage stage;
    private phase3base app; // Reference to phase3base

    public MessagingCode(Stage stage, phase3base app) {
        this.stage = stage;
        this.app = app; // Initialize the app reference
    }
    public void displayMessageView(String actor) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        Label messageLabel = new Label("Who Would you Like to message?");
        Button patientButton = new Button("Patient");
        Button doctorButton = new Button("Doctor");
        Button nurseButton = new Button("Nurse");
        // Example patient list for demonstration purposes
        // In a real application, you'd dynamically generate this based on patient data
        Button backButton;
        switch (actor) {
        //Doctors can only message nurses and patients
    	case "Doctor":
    		nurseButton.setOnAction(e -> displayMessages("Doctor", "Nurse"));
    		patientButton.setOnAction(e -> displayMessages("Doctor", "Patient"));
    		backButton = createBackMainButton("Doctor");
    		layout.getChildren().addAll(messageLabel, patientButton, nurseButton, backButton);
    		break;
    	case "Nurse":
    	//Nurses can only message patients and doctors
    		patientButton.setOnAction(e -> displayMessages("Nurse", "Patient"));
    		doctorButton.setOnAction(e -> displayMessages("Nurse", "Doctor"));
    		backButton = createBackMainButton("Nurse");
    		layout.getChildren().addAll(messageLabel, patientButton, doctorButton, backButton);
    		break;
    	case "Patient":
    	//Patients can only message nurses and doctors
    		nurseButton.setOnAction(e -> displayMessages("Patient", "Nurse"));
    		doctorButton.setOnAction(e -> displayMessages("Patient", "Doctor"));
    		backButton = createBackMainButton("Patient");
    		layout.getChildren().addAll(messageLabel, doctorButton, nurseButton, backButton);
    		break;
    	default:
    		System.out.println("Incorrect Actor Given");
    	}
        Scene scene = new Scene(layout, 820, 520);
        stage.setScene(scene);
    }
    
    private void displayMessages(String fromActor, String toActor) {
    	//Switch out vbox later
    	GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(25));
        Label previousMessageLabel = new Label("Previous Messages");
        TextField pastMessages = new TextField();
        pastMessages.setEditable(false);
        Label currentMessageLabel = new Label("Message to Send");
        TextField currentMessage = new TextField();
        currentMessage.setPromptText("Enter Message");
        Button sendMessageButton = new Button("Send");
        Button backButton = createBackMessageButton(fromActor);
        Label errorMessage= new Label(); 
        if(fromActor.equals("Patient") || toActor.equals("Patient")) {
        	Label enterPatientIDLabel = new Label("Please Enter Patient ID");
        	TextField enterPatientID = new TextField();
        	enterPatientID.setPromptText("Enter Patient ID");
        	sendMessageButton.setOnAction(e -> {
        		String patientID = enterPatientID.getText();
        		if (patientID.length() != 5 || !patientID.matches("\\d+")) {
                    errorMessage.setText("Error: Patient ID is invalid");
                }
        		errorMessage.setText("");
        		writeMessage(currentMessage.getText(), patientID, fromActor, toActor);
        		pastMessages.setText(retrievePastMessages(patientID, fromActor, toActor));});
        	// adds the components to the layout
            layout.add(enterPatientIDLabel, 0, 0);
            layout.add(enterPatientID, 1, 0);
            layout.add(previousMessageLabel, 0, 1);
            layout.add(pastMessages, 1, 1);
            layout.add(currentMessageLabel, 0, 2);
            layout.add(currentMessage, 1, 2);
            layout.add(sendMessageButton, 0, 3);
//            layout.add(bloodPressureField, 1, 3);
//            layout.add(saveButton, 1, 4);
            layout.add(backButton, 0, 4);
            layout.add(errorMessage, 0, 4);
        }
        else {
        	sendMessageButton.setOnAction(e -> {
        		writeMessage(currentMessage.getText(), fromActor, toActor);
        		pastMessages.setText(retrievePastMessages(fromActor, toActor));
                });
        	layout.add(previousMessageLabel, 0, 1);
        	layout.add(pastMessages, 0, 1);
            layout.add(currentMessageLabel, 0, 2);
            layout.add(currentMessage, 1, 2);
            layout.add(sendMessageButton, 0, 3);
//            layout.add(bloodPressureField, 1, 3);
//            layout.add(saveButton, 1, 4);
            layout.add(backButton, 0, 4);
        	pastMessages.setText(retrievePastMessages(fromActor, toActor));
        }
        Scene scene = new Scene(layout, 820, 520);
        stage.setScene(scene);
    }
    
    private String retrievePastMessages(String fromActor, String toActor) {
    	String nurseDoctorConvo = "DoctorNurseMessage.txt";
        try {
        	BufferedReader br = new BufferedReader(new FileReader(nurseDoctorConvo));
            String messages = "";
            String currentLine;
            while((currentLine = br.readLine()) != null) {
            	System.out.println(currentLine);
            	messages += currentLine + "\n";
            }
            br.close();
            System.out.println("Successfully retrieved messages from DoctorNurseMessage.txt");
            return messages;
        } catch (IOException e) {
            File file = new File(nurseDoctorConvo);
            return "";
        }
    }
    private String retrievePastMessages(String patientID, String fromActor, String toActor) {
    	String convoFile = patientID+"_";
    	switch (fromActor) {
    	case "Doctor":
    	case "Nurse":
    		convoFile += fromActor + ".txt";
    		break;
    	case "Patient":
    		convoFile += toActor + ".txt";
    		break;
    	default:
    		System.out.println("Incorrect Actor Given");
    	}
    	try {
        	BufferedReader br = new BufferedReader(new FileReader(convoFile));
            String messages = "";
            String currentLine;
            while((currentLine = br.readLine()) != null) {
            	System.out.println(currentLine);
            	messages += currentLine + "\n";
            }
            br.close();
            System.out.println("Successfully retrieved messages from DoctorNurseMessage.txt");
            return messages;
        } catch (IOException e) {
            File file = new File(convoFile);
            return "";
        }
    	
    }
    private void writeMessage(String message, String fromActor, String toActor) {
        try {
            // makes the FileWriter where it than reads the _PatientInfo.txt file and is then
        	// able to be edited
            FileWriter fw = new FileWriter("DoctorNurseMessage.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);

            // writes the new vital signs to the file
            bw.write(fromActor+ ": " + message);
            bw.newLine(); // Add a new line for readability
            // closes the file reader/writer and buffered reader/writer
            bw.close();
            fw.close();
            System.out.println("Successfully written message between Nurse and Doctor into DoctorNurseMessage.txt");
        } catch (IOException e) {
            System.out.println("Error writing to DoctorNurseMessage.txt: " + e.getMessage());
        }
    }
    private void writeMessage(String message, String patientID, String fromActor, String toActor) {
    	String fileName = patientID+"_";
    	try {
    		FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
    		switch (fromActor) {
        	case "Doctor":
        	case "Nurse":
        		fileName += fromActor;
        		// writes the new vital signs to the file
                bw.write(fromActor+ ": " + message);
                bw.newLine(); // Add a new line for readability
                // closes the file reader/writer and buffered reader/writer
                bw.close();
                fw.close();
        		break;
        	case "Patient":
        		BufferedReader br = new BufferedReader(new FileReader(patientID+"_PatientInfo.txt"));
        		br.readLine();
        		br.readLine();
        		String patientName = br.readLine(); 
        		br.close();
        		fileName += toActor;
        		bw.write(patientName+ ": " + message);
                bw.newLine(); // Add a new line for readability
                // closes the file reader/writer and buffered reader/writer
                bw.close();
                fw.close();
        		break;
        	default:
        		System.out.println("Incorrect Actor Given");
        	}
            System.out.println("Successfully written message to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to "+ fileName +": " + e.getMessage());
        }
    }
    
    private Button createBackMainButton(String actor) {
        Button backButton = new Button("Back to Previous Page");
        switch (actor) {
    	case "Doctor":
    		backButton.setOnAction(e -> app.displayDoctorPortal()); // Use app reference here
    		break;
    	case "Nurse":
    		backButton.setOnAction(e -> app.displayNursePortal()); // Use app reference here
    		break;
    	case "Patient":
    		backButton.setOnAction(e -> app.displayPatientPortal()); // Use app reference here
    		break;
    	default:
    		System.out.println("Incorrect Actor Given");
    	}
        return backButton;
    }
    private Button createBackMessageButton(String actor) {
        Button backButton = new Button("Back to Previous Page");
        backButton.setOnAction(e -> displayMessageView(actor));
        return backButton;
    }
}
