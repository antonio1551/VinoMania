package control;

import java.io.IOException;
import java.sql.SQLException;

// Import corretti per Tomcat 10.1+
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Utente;
import model.UtenteDAO;

@WebServlet("/registrazione")
public class RegistrazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static UtenteDAO utenteDAO = new UtenteDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Se qualcuno prova ad accedere a questo URL digitandolo, lo rimandiamo al form
        response.sendRedirect("registrazione.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Recuperiamo i parametri inviati dal form HTML
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2. Controllo basilare lato server (sicurezza extra in caso aggirino il JS)
        if (nome != null && cognome != null && email != null && password != null && password.length() >= 8) {
            try {
                // 3. Creiamo l'oggetto Utente (di default, admin è false per le nuove registrazioni)
                Utente nuovoUtente = new Utente();
                nuovoUtente.setNome(nome);
                nuovoUtente.setCognome(cognome);
                nuovoUtente.setEmail(email);
                nuovoUtente.setPassword(password);
                nuovoUtente.setAdmin(false);

                // 4. Salviamo l'utente nel database tramite il DAO
                utenteDAO.doSave(nuovoUtente);

                // 5. Se va tutto bene, rimandiamo alla pagina di Login con un messaggio
                request.setAttribute("messaggioSuccesso", "Registrazione completata con successo! Ora puoi accedere.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
                // 6. In caso di errore (es. email duplicata), rimandiamo alla registrazione col messaggio
                request.setAttribute("errore", "Errore durante la registrazione. L'email potrebbe essere già in uso.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/registrazione.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            // Dati non validi
            request.setAttribute("errore", "Compila tutti i campi correttamente.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/registrazione.jsp");
            dispatcher.forward(request, response);
        }
    }
}