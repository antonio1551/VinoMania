package control;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.ProdottoDAO;
import model.Utente;

@WebServlet("/admin/elimina-prodotto")
public class EliminaProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ProdottoDAO prodottoDAO = new ProdottoDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Controllo di sicurezza: verifichiamo che la richiesta arrivi da un Admin
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        
        if (utente == null || !utente.isAdmin()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Accesso negato: Solo gli amministratori possono eliminare i prodotti.");
            return;
        }

        try {
            // 2. Leggiamo l'ID del prodotto da cancellare
            int idProdotto = Integer.parseInt(request.getParameter("id"));
            
            // 3. Eseguiamo la cancellazione tramite il DAO
            prodottoDAO.doDelete(idProdotto);
            
            // 4. Reindirizziamo l'admin alla tabella di gestione con un parametro di successo
            response.sendRedirect(request.getContextPath() + "/admin/gestione-prodotti?successo=cancellazione");
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/gestione-prodotti?errore=id_invalido");
        } catch (SQLException e) {
            e.printStackTrace();
            // In un sistema reale mostreremmo un messaggio, qui rimandiamo alla tabella con errore
            response.sendRedirect(request.getContextPath() + "/admin/gestione-prodotti?errore=database");
        }
    }
}