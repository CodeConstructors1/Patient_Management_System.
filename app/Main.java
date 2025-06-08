package app;



import dao.PatientDAO;
import dao.PatientDAOImpl;
import gui.PatientManagementGUI;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.connect();
            PatientDAO dao = new PatientDAOImpl(conn);
            new PatientManagementGUI(dao);
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

