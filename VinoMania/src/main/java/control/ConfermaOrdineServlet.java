package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Carrello;
import model.Ordine;
import model.OrdineDAO;
import model.Utente;

@WebServlet("/confermaOrdine")
public class ConfermaOrdineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static OrdineDAO ordineDAO = new OrdineDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        // 1. Controllo di sicurezza: se utente non loggato o carrello vuoto, rimandiamo indietro
        if (utente == null || carrello == null || carrello.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        // 2. Recupero i dati inseriti nel form (checkout.jsp)
        String indirizzo = request.getParameter("indirizzo");
        String citta = request.getParameter("citta");
        String cap = request.getParameter("cap");
        String carta = request.getParameter("carta");

        // Assemblo l'indirizzo completo
        String indirizzoCompleto = indirizzo + ", " + citta + " - " + cap;

        // 3. Costruisco l'oggetto Ordine
        Ordine ordine = new Ordine();
        ordine.setIdUtente(utente.getId());
        ordine.setTotale(carrello.getTotale());
        ordine.setIndirizzo(indirizzoCompleto);
        ordine.setNumeroCarta(carta);
        
        // Passo gli articoli dal carrello all'ordine (creando una copia della lista)
        ordine.setProdottiAcquistati(new ArrayList<>(carrello.getItems()));

        try {
            // 4. Salvo tutto nel Database
            ordineDAO.doSave(ordine);

            // 5. Svuotiamo il carrello dopo l'acquisto
            carrello.Svuota();

            // 6. Rimandiamo a una pagina di successo
            request.getRequestDispatcher("/successo.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            // In caso di errore SQL, rimandiamo al checkout con un messaggio
            request.setAttribute("errore", "Errore di sistema durante il salvataggio dell'ordine. Riprova più tardi.");
            request.getRequestDispatcher("/checkout.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Se qualcuno prova ad accedere tramite URL diretto, lo rimandiamo al carrello
        response.sendRedirect(request.getContextPath() + "/carrello");
    }
}