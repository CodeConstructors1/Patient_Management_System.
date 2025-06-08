package gui;

import dao.PatientDAO;
import model.Patient;
import model.Patient.Gender;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PatientManagementGUI {
    private PatientDAO patientDAO;

    public PatientManagementGUI(PatientDAO dao) {
        this.patientDAO = dao;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Patient Management System");
        frame.setSize(700, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // --- Input Fields ---
        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(20, 20, 80, 25);
        frame.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(100, 20, 250, 25);
        frame.add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 60, 80, 25);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(100, 60, 250, 25);
        frame.add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(20, 100, 80, 25);
        frame.add(ageLabel);

        JTextField ageField = new JTextField();
        ageField.setBounds(100, 100, 250, 25);
        frame.add(ageField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(20, 140, 80, 25);
        frame.add(genderLabel);

        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderBox = new JComboBox<>(genders);
        genderBox.setBounds(100, 140, 250, 25);
        frame.add(genderBox);

        JLabel diseaseLabel = new JLabel("Disease:");
        diseaseLabel.setBounds(20, 180, 80, 25);
        frame.add(diseaseLabel);

        JTextField diseaseField = new JTextField();
        diseaseField.setBounds(100, 180, 250, 25);
        frame.add(diseaseField);

        // --- Buttons ---
        JButton submitButton = new JButton("Add Patient");
        submitButton.setBounds(20, 230, 150, 30);
        frame.add(submitButton);

        JButton listButton = new JButton("List Patients");
        listButton.setBounds(200, 230, 150, 30);
        frame.add(listButton);

        JButton deleteButton = new JButton("Delete by ID");
        deleteButton.setBounds(20, 270, 150, 30);
        frame.add(deleteButton);

        JButton updateButton = new JButton("Update Patient");
        updateButton.setBounds(200, 270, 150, 30);
        frame.add(updateButton);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(20, 310, 150, 30);
        frame.add(searchButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(200, 310, 150, 30);
        frame.add(exitButton);

        // --- Scrollable Text Area ---
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBounds(20, 360, 640, 330);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane);

        // --- Event Handlers ---

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to exit?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                System.exit(0);
            }
        });

        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String gender = (String) genderBox.getSelectedItem();
            String disease = diseaseField.getText().trim();

            // Validate all fields except ID
            if (name.isEmpty() || ageText.isEmpty() || gender.isEmpty() || disease.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields except ID are required.");
                return;
            }

            if (!isValidName(name)) {
                JOptionPane.showMessageDialog(frame, "Invalid name. Only letters, spaces, apostrophes, hyphens, and dots allowed.");
                return;
            }

            if (!isValidAge(ageText)) {
                JOptionPane.showMessageDialog(frame, "Age must be a number between 0 and 150.");
                return;
            }

            if (!isValidDisease(disease)) {
                JOptionPane.showMessageDialog(frame, "Invalid disease. Only letters, digits, spaces, apostrophes, hyphens, and dots allowed.");
                return;
            }

            try {
                int age = Integer.parseInt(ageText);
                Patient patient = new Patient(name, age, Gender.fromString(gender), disease);
                patientDAO.addPatient(patient);
                JOptionPane.showMessageDialog(frame, "Patient added successfully.");
                // Clear fields after add
                nameField.setText("");
                ageField.setText("");
                diseaseField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        listButton.addActionListener(e -> {
            try {
                List<Patient> patients = patientDAO.getAllPatients();
                outputArea.setText("");
                if (patients.isEmpty()) {
                    outputArea.setText("No patients found.");
                } else {
                    for (Patient p : patients) {
                        outputArea.append(p.toString() + "\n");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error fetching patients: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            String idText = idField.getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter Patient ID to delete.");
                return;
            }

            if (!isValidId(idText)) {
                JOptionPane.showMessageDialog(frame, "ID must be a positive number.");
                return;
            }

            try {
                int id = Integer.parseInt(idText);
                patientDAO.deletePatientById(id);
                JOptionPane.showMessageDialog(frame, "Patient deleted successfully.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error deleting patient: " + ex.getMessage());
            }
        });

        updateButton.addActionListener(e -> {
            String idText = idField.getText().trim();
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String gender = (String) genderBox.getSelectedItem();
            String disease = diseaseField.getText().trim();

            if (idText.isEmpty() || name.isEmpty() || ageText.isEmpty() || gender.isEmpty() || disease.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields including ID are required for update.");
                return;
            }

            if (!isValidId(idText)) {
                JOptionPane.showMessageDialog(frame, "ID must be a positive number.");
                return;
            }

            if (!isValidName(name)) {
                JOptionPane.showMessageDialog(frame, "Invalid name. Only letters, spaces, apostrophes, hyphens, and dots allowed.");
                return;
            }

            if (!isValidAge(ageText)) {
                JOptionPane.showMessageDialog(frame, "Age must be a number between 0 and 150.");
                return;
            }

            if (!isValidDisease(disease)) {
                JOptionPane.showMessageDialog(frame, "Invalid disease. Only letters, digits, spaces, apostrophes, hyphens, and dots allowed.");
                return;
            }

            try {
                int id = Integer.parseInt(idText);
                int age = Integer.parseInt(ageText);
                Patient patient = new Patient(id, name, age, Gender.fromString(gender), disease);
                patientDAO.updatePatient(patient);
                JOptionPane.showMessageDialog(frame, "Patient updated successfully.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error updating patient: " + ex.getMessage());
            }
        });

        searchButton.addActionListener(e -> {
            String idText = idField.getText().trim();
            String name = nameField.getText().trim();
            outputArea.setText("");

            try {
                if (!idText.isEmpty()) {
                    if (!isValidId(idText)) {
                        JOptionPane.showMessageDialog(frame, "ID must be a positive number.");
                        return;
                    }
                    int id = Integer.parseInt(idText);
                    Patient p = patientDAO.getPatientById(id);
                    if (p != null) {
                        outputArea.append(p.toString());
                    } else {
                        outputArea.setText("No patient found with ID: " + id);
                    }
                } else if (!name.isEmpty()) {
                    if (!isValidName(name)) {
                        JOptionPane.showMessageDialog(frame, "Invalid name. Only letters, spaces, apostrophes, hyphens, and dots allowed.");
                        return;
                    }
                    List<Patient> patients = patientDAO.getPatientsByName(name);
                    if (patients.isEmpty()) {
                        outputArea.setText("No patients found with name: " + name);
                    } else {
                        for (Patient p : patients) {
                            outputArea.append(p.toString() + "\n");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Enter ID or Name to search.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error searching patients: " + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }

    // Validation methods moved outside of createAndShowGUI:

    private static boolean isValidName(String name) {
        return name.matches("[a-zA-Z .'-]+");
    }

    private static boolean isValidDisease(String disease) {
        return disease.matches("[a-zA-Z0-9 .'-]+");
    }

    private static boolean isValidAge(String ageText) {
        try {
            int age = Integer.parseInt(ageText);
            return age >= 0 && age <= 150;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidId(String idText) {
        try {
            int id = Integer.parseInt(idText);
            return id > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
