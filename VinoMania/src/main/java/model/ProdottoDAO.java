package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Data Access Object per la gestione dei prodotti (vini) nel database.
 */
public class ProdottoDAO {

    private static final String TABLE_NAME = "prodotto";

    /**
     * Salva un nuovo prodotto all'interno del database.
     */
    public synchronized void doSave(Prodotto prodotto) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (nome, descrizione, prezzo, quantita, categoria, immagine) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, prodotto.getNome());
            preparedStatement.setString(2, prodotto.getDescrizione());
            preparedStatement.setDouble(3, prodotto.getPrezzo());
            preparedStatement.setInt(4, prodotto.getQuantita());
            preparedStatement.setString(5, prodotto.getCategoria());
            preparedStatement.setString(6, prodotto.getImmagine());

            preparedStatement.executeUpdate();

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    /**
     * Recupera un singolo prodotto tramite il suo ID identificativo.
     */
    public synchronized Prodotto doRetrieveByKey(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Prodotto bean = new Prodotto();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                bean.setId(rs.getInt("id"));
                bean.setNome(rs.getString("nome"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setQuantita(rs.getInt("quantita"));
                bean.setCategoria(rs.getString("categoria"));
                bean.setImmagine(rs.getString("immagine"));
            }

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return bean;
    }
    
    /**
     * Aggiorna i dati di un prodotto esistente nel database (Funzione Admin)
     */
    public synchronized void doUpdate(Prodotto prodotto) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // Query di UPDATE: aggiorniamo tutti i campi basandoci sull'ID
        String updateSQL = "UPDATE prodotto SET nome = ?, categoria = ?, descrizione = ?, prezzo = ?, quantita = ? WHERE id = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, prodotto.getNome());
            preparedStatement.setString(2, prodotto.getCategoria());
            preparedStatement.setString(3, prodotto.getDescrizione());
            preparedStatement.setDouble(4, prodotto.getPrezzo());
            preparedStatement.setInt(5, prodotto.getQuantita());
            preparedStatement.setInt(6, prodotto.getId()); // Il parametro WHERE

            preparedStatement.executeUpdate();

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    /**
     * Elimina un prodotto dal database in base all'ID.
     */
    public synchronized boolean doDelete(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, id);

            result = preparedStatement.executeUpdate();

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return (result != 0);
    }

    /**
     * Recupera l'elenco completo dei prodotti, con possibilità di ordinamento.
     */
    public synchronized Collection<Prodotto> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<Prodotto> prodotti = new LinkedList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.trim().isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Prodotto bean = new Prodotto();

                bean.setId(rs.getInt("id"));
                bean.setNome(rs.getString("nome"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setQuantita(rs.getInt("quantita"));
                bean.setCategoria(rs.getString("categoria"));
                bean.setImmagine(rs.getString("immagine"));

                prodotti.add(bean);
            }

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return prodotti;
    }
}
