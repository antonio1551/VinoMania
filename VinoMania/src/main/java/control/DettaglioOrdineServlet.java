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

import model.ItemCarrello;
import model.OrdineDAO;
import model.Utente;

@WebServlet("/dettaglio-ordine")
public class DettaglioOrdineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static OrdineDAO ordineDAO = new OrdineDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        // Controllo accessi
        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Leggiamo l'ID dell'ordine dalla query string
            int idOrdine = Integer.parseInt(request.getParameter("id"));
            
            // Chiamiamo il nuovo metodo del DAO
            List<ItemCarrello> dettagli = ordineDAO.doRetrieveDettagliByOrdine(idOrdine);
            
            // Passiamo i dati alla JSP
            request.setAttribute("dettagli", dettagli);
            request.setAttribute("idOrdine", idOrdine);
            
            request.getRequestDispatcher("/dettaglio-ordine.jsp").forward(request, response);
            
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/miei-ordini");
        }
    }
}