package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerConnectionPool {

    // Configura con i tuoi dati
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/vinomania?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            // Caricamento dinamico del driver JDBC
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC non trovato: " + e.getMessage());
        }
    }

    // Metodo per ottenere una nuova connessione
    public static synchronized Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Metodo utility per chiudere/rilasciare la connessione
    public static synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            Connection con = DriverManagerConnectionPool.getConnection();
            if (con != null && !con.isClosed()) {
                System.out.println("Connessione al database di Vino Mania avvenuta con SUCCESSO!");
                DriverManagerConnectionPool.releaseConnection(con);
            }
        } catch (SQLException e) {
            System.err.println("Connessione FALLITA:");
            e.printStackTrace();
        }
    }
}


