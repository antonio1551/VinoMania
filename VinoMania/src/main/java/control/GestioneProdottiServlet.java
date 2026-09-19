package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Prodotto;
import model.ProdottoDAO;
import model.Utente;

@WebServlet("/admin/gestione-prodotti")
public class GestioneProdottiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ProdottoDAO prodottoDAO = new ProdottoDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Sicurezza: Blocco accessi non autorizzati
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        
        if (utente == null || !utente.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // 2. Recupero tutti i prodotti dal database
        	List<Prodotto> catalogo = new ArrayList<>(prodottoDAO.doRetrieveAll(""));
            
            // 3. Passo la lista alla JSP
            request.setAttribute("catalogo", catalogo);
            request.getRequestDispatcher("/admin/gestione-prodotti.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel recupero dei prodotti.");
        }
    }
}