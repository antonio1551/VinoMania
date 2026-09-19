package control;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Utente;
import model.UtenteDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static UtenteDAO utenteDAO = new UtenteDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Se un utente fa una GET a /login, gli mostriamo semplicemente il form
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // 1. Chiediamo al DAO di verificare le credenziali
            Utente utente = utenteDAO.doRetrieveByEmailAndPassword(email, password);

            if (utente != null) {
                // 2. CREAZIONE DELLA SESSIONE 
                HttpSession session = request.getSession();
                session.setAttribute("utente", utente); // Questo è il nostro "token" di accesso

                // 3. Reindirizziamo l'utente alla Home Page
                response.sendRedirect(request.getContextPath() + "/");
            } else {
                // 4. Credenziali errate: rimandiamo al form con un messaggio di errore
                request.setAttribute("errore", "Email o password errati. Riprova.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore di connessione al database.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}