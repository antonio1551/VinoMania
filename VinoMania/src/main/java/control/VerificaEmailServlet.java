package control;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.UtenteDAO;

@WebServlet("/verifica-email")
public class VerificaEmailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static UtenteDAO utenteDAO = new UtenteDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String email = request.getParameter("email");
        
        // Impostiamo il tipo di risposta per AJAX
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        if (email != null && !email.trim().isEmpty()) {
            try {
                boolean esiste = utenteDAO.checkEmailExists(email);
                
                if (esiste) {
                    response.getWriter().write("esiste");
                } else {
                    response.getWriter().write("libera");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write("errore");
            }
        }
    }
}