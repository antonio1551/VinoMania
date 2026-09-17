package control;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Prodotto;
import model.ProdottoDAO;
import model.Utente;

@WebServlet("/admin/modifica-prodotto")
public class ModificaProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ProdottoDAO prodottoDAO = new ProdottoDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Controllo accessi (sempre obbligatorio nelle sezioni Admin)
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        if (utente == null || !utente.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Recupero l'ID del prodotto passato nell'URL (es: ?id=3)
            int idProdotto = Integer.parseInt(request.getParameter("id"));
            
            // Richiamo un metodo del tuo DAO per ottenere il singolo prodotto
            Prodotto prodottoDaModificare = prodottoDAO.doRetrieveByKey(idProdotto);
            
            if (prodottoDaModificare != null) {
                // Metto il prodotto nella request e inoltro alla JSP
                request.setAttribute("prodotto", prodottoDaModificare);
                request.getRequestDispatcher("/admin/modifica-prodotto.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp?errore=notfound");
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento del prodotto.");
        }
    }
}