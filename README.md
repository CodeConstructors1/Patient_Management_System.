Patient Management System

A simple Java Swing GUI application for managing patient records, including adding, updating, deleting, searching, and listing patients stored in a MySQL database.

Features

  Add new patients with name, age, gender, and disease.

  Update existing patient records by ID.

  Delete patients by ID.

  Search patients by ID or name (partial matching supported).

  List all patients in a scrollable text area.

  Input validation to ensure data integrity.

  User-friendly GUI with clear messages and confirmation dialogs.

  Modular architecture separating GUI, DAO, and model layers.

Technologies Used

  Java 8+

  Swing for GUI

  JDBC for database connectivity

  MySQL as the backend database

Prerequisites

  Java JDK installed (version 8 or higher recommended)

  MySQL Server installed and running

  MySQL Connector/J (JDBC driver) added to your project libraries

	
  Project Structure

  src/
  
 ├── app/
 
 │     └── Main.java     # Entry point: initializes DB connection and GUI
 
 ├── dao/
 
 │     ├── PatientDAO.java           # Interface defining DAO methods
 
 │     └── PatientDAOImpl.java       # Implementation of PatientDAO with JDBC
 
 ├── gui/
 
 │     └── PatientManagementGUI.java    # Swing GUI with all event handling and validation
 
 ├── model/
 
 │     └── Patient.java              # Patient model with Gender enum and fields
 
 └── util/
 └── DatabaseConnection.java     # Utility for establishing MySQL connection
       


Database Setup

  1.Create a MySQL database named HospitalDB.
  CREATE DATABASE HospitalDB;
  USE HospitalDB;

  2.Create the patients table:

  CREATE TABLE patients (
  
      id INT AUTO_INCREMENT PRIMARY KEY,
      
      name VARCHAR(100) NOT NULL,
      
      age INT NOT NULL,
      
      gender VARCHAR(10) NOT NULL,
      
      disease VARCHAR(255) NOT NULL
      
  );

  3.Update the database connection credentials in util/DatabaseConnection.java if necessary (username, password, URL).

Running the Application

  1.Clone the repository.

  2.Ensure MySQL is running and the database is set up as described above.

  3. Build and run the project using your favorite IDE (e.g., IntelliJ IDEA, Eclipse) or command line.

  4.The GUI window will open allowing you to manage patient records easily.

  Which File to Run
  
  To start the application, run the Main class located in the app package:


