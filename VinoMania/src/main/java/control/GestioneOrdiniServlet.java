package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Ordine;
import model.OrdineDAO;
import model.Utente;

// Mappiamo l'URL in base a come avevamo impostato il pulsante nella dashboard.jsp
@WebServlet("/admin/ordini")
public class GestioneOrdiniServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static OrdineDAO ordineDAO = new OrdineDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Controllo accessi rigoroso
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        
        if (utente == null || !utente.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Chiamiamo il nuovo metodo per recuperare l'elenco globale
            List<Ordine> tuttiOrdini = ordineDAO.doRetrieveAllOrdini();
            request.setAttribute("ordini", tuttiOrdini);
            
            request.getRequestDispatcher("/admin/ordini.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento degli ordini.");
        }
    }
}