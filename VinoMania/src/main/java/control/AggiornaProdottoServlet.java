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

@WebServlet("/admin/aggiorna-prodotto")
public class AggiornaProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ProdottoDAO prodottoDAO = new ProdottoDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Controllo di sicurezza: solo gli admin loggati
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        
        if (utente == null || !utente.isAdmin()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Accesso negato.");
            return;
        }

        // 2. Recupero dei parametri inviati dal form (incluso l'ID nascosto)
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String categoria = request.getParameter("categoria");
            String descrizione = request.getParameter("descrizione");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));

            // 3. Popolamento dell'oggetto Prodotto
            Prodotto prodottoAggiornato = new Prodotto();
            prodottoAggiornato.setId(id);
            prodottoAggiornato.setNome(nome);
            prodottoAggiornato.setCategoria(categoria);
            prodottoAggiornato.setDescrizione(descrizione);
            prodottoAggiornato.setPrezzo(prezzo);
            prodottoAggiornato.setQuantita(quantita);

            // 4. Esecuzione dell'aggiornamento tramite il DAO
            prodottoDAO.doUpdate(prodottoAggiornato);

            // 5. Reindirizzamento alla dashboard con un messaggio di successo
            response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp?successo=modifica");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp?errore=formato_dati");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel database durante l'aggiornamento.");
        }
    }
}