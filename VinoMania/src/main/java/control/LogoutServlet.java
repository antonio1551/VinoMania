package control;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Recuperiamo la sessione corrente. 
        // Passando "false", diciamo a Tomcat di NON creare una nuova sessione se questa non esiste.
        HttpSession session = request.getSession(false);
        
        // 2. Se la sessione esiste (ovvero l'utente era loggato), la distruggiamo
        if (session != null) {
            session.invalidate();
        }
        
        // 3. Reindirizziamo l'utente alla Home Page
        response.sendRedirect(request.getContextPath() + "/");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Indirizziamo eventuali richieste POST allo stesso comportamento della GET
        doGet(request, response);
    }
}