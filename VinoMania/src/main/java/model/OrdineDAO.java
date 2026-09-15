package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class OrdineDAO {

    // Nomi delle due tabelle fondamentali
    private static final String TABLE_ORDINE = "ordine";
    private static final String TABLE_COMPOSIZIONE = "composizione_ordine";

    /**
     * Salva l'ordine e i suoi dettagli nel database utilizzando una transazione.
     */
    public synchronized void doSave(Ordine ordine) throws SQLException {
        Connection connection = null;
        PreparedStatement psOrdine = null;
        PreparedStatement psComposizione = null;
        ResultSet rs = null;

        // Query per l'ordine principale (la data viene gestita automaticamente dal DB usando NOW() o CURDATE())
        String insertOrdine = "INSERT INTO " + TABLE_ORDINE + " (id_utente, totale, indirizzo, data_ordine, numero_carta) VALUES (?, ?, ?, CURDATE(), ?)";
        
        // Query per i dettagli dell'ordine (CONGELIAMO IL PREZZO QUI)
        String insertComposizione = "INSERT INTO " + TABLE_COMPOSIZIONE + " (id_ordine, id_prodotto, quantita, prezzo_acquisto) VALUES (?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            
            // INIZIO TRANSAZIONE: Disabilitiamo l'autocommit per garantire che entrambe le INSERT vadano a buon fine
            connection.setAutoCommit(false);

            // 1. Inseriamo l'ordine generale e chiediamo a MySQL di restituirci l'ID autogenerato
            psOrdine = connection.prepareStatement(insertOrdine, Statement.RETURN_GENERATED_KEYS);
            psOrdine.setInt(1, ordine.getIdUtente());
            psOrdine.setDouble(2, ordine.getTotale());
            psOrdine.setString(3, ordine.getIndirizzo());
            psOrdine.setString(4, ordine.getNumeroCarta());
            psOrdine.executeUpdate();

            // 2. Recuperiamo l'ID generato per questo nuovo ordine
            rs = psOrdine.getGeneratedKeys();
            int idOrdineGenerato = 0;
            if (rs.next()) {
                idOrdineGenerato = rs.getInt(1);
                ordine.setId(idOrdineGenerato); // Aggiorniamo l'oggetto Java
            }

            // 3. Inseriamo i singoli prodotti nella tabella di associazione
            psComposizione = connection.prepareStatement(insertComposizione);
            for (ItemCarrello item : ordine.getProdottiAcquistati()) {
                psComposizione.setInt(1, idOrdineGenerato);
                psComposizione.setInt(2, item.getProdotto().getId()); // ID del prodotto
                psComposizione.setInt(3, item.getQuantita());
                
                // REQUISITO CHIAVE: Salviamo il prezzo attuale, così l'ordine rimane storicizzato correttamente!
                psComposizione.setDouble(4, item.getProdotto().getPrezzo()); 
                
                psComposizione.executeUpdate();
            }

            // 4. Se siamo arrivati fin qui senza eccezioni, CONFERMIAMO tutto (Commit)
            connection.commit();

        } catch (SQLException e) {
            // Se qualcosa va storto, ANNULLIAMO tutte le modifiche (Rollback)
            if (connection != null) {
                connection.rollback();
            }
            throw e; // Rilanciamo l'errore per gestirlo nella Servlet
        } finally {
            // 5. Pulizia delle risorse e ripristino dell'autocommit
            if (connection != null) connection.setAutoCommit(true);
            try {
                if (rs != null) rs.close();
                if (psOrdine != null) psOrdine.close();
                if (psComposizione != null) psComposizione.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }
    
    /**
     * Recupera lo storico degli ordini di un singolo utente (dal più recente)
     */
    public synchronized List<Ordine> doRetrieveByUser(int idUtente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Ordine> ordini = new ArrayList<>();

        // Selezioniamo gli ordini ordinandoli per data decrescente
        String selectSQL = "SELECT * FROM " + TABLE_ORDINE + " WHERE id_utente = ? ORDER BY data_ordine DESC, id DESC";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUtente);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Ordine ordine = new Ordine();
                ordine.setId(rs.getInt("id"));
                ordine.setIdUtente(rs.getInt("id_utente"));
                ordine.setTotale(rs.getDouble("totale"));
                ordine.setIndirizzo(rs.getString("indirizzo"));
                ordine.setDataOrdine(rs.getString("data_ordine"));
                ordine.setNumeroCarta(rs.getString("numero_carta"));
                ordini.add(ordine);
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return ordini;
    }
}