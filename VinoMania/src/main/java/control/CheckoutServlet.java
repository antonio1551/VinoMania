package control;

import java.io.IOException;

// Import per Tomcat 10.1+
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Utente;
import model.Carrello;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        // 1. Controllo di sicurezza: se non sei loggato, ti rimando al login
        if (utente == null) {
            request.setAttribute("errore", "Devi effettuare il login per poter procedere all'acquisto.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return; // Interrompe l'esecuzione del codice successivo
        }

        // 2. Controllo carrello: se è vuoto, ti rimando al carrello
        if (carrello == null || carrello.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        // 3. Se tutto è in regola, mostro la pagina con il form di spedizione/pagamento
        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}