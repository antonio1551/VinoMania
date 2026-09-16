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

// La mappiamo dentro /admin/ per convenzione, anche se la protezione vera è nella logica
@WebServlet("/admin/salva-prodotto")
public class SalvaProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ProdottoDAO prodottoDAO = new ProdottoDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Controllo di sicurezza: verifichiamo che la richiesta arrivi da un Admin
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        
        if (utente == null || !utente.isAdmin()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Accesso negato: Solo gli amministratori possono aggiungere prodotti.");
            return;
        }

        // 2. Lettura dei parametri dal form
        String nome = request.getParameter("nome");
        String categoria = request.getParameter("categoria");
        String descrizione = request.getParameter("descrizione");
        String immagine = request.getParameter("immagine");
        
        int quantita = 0;
        try {
            quantita = Integer.parseInt(request.getParameter("quantita"));
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Se l'input è vuoto o non numerico, resta 0
        }
        
        
        // Convertiamo la stringa del prezzo in Double
        double prezzo = 0.0;
        try {
            prezzo = Double.parseDouble(request.getParameter("prezzo"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // 3. Creazione e salvataggio del prodotto
        Prodotto nuovoProdotto = new Prodotto();
        nuovoProdotto.setNome(nome);
        nuovoProdotto.setCategoria(categoria);
        nuovoProdotto.setDescrizione(descrizione);
        nuovoProdotto.setPrezzo(prezzo);
        nuovoProdotto.setImmagine(immagine);
        nuovoProdotto.setQuantita(quantita);

        try {
            prodottoDAO.doSave(nuovoProdotto);
            // 4. Reindirizzamento alla dashboard con un parametro di successo nell'URL
            response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp?successo=aggiunta");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante il salvataggio nel database.");
        }
    }
}