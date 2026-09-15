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

@WebServlet("/miei-ordini")
public class MieiOrdiniServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static OrdineDAO ordineDAO = new OrdineDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        // Controllo accessi: solo i loggati possono vedere i propri ordini
        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Recupero la lista dal DAO passando l'ID dell'utente in sessione
            List<Ordine> ordini = ordineDAO.doRetrieveByUser(utente.getId());
            request.setAttribute("ordini", ordini);
            
            // Inoltro alla pagina JSP per la visualizzazione
            request.getRequestDispatcher("/miei-ordini.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel recupero degli ordini.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}