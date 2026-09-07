package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Prodotto;
import model.ProdottoDAO;

/**
 * Servlet per la gestione del catalogo prodotti.
 */
@WebServlet("/catalogo") // Questo è l'URL per richiamare la Servlet dal browser
public class CatalogoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Inizializziamo il DAO che ci servirà per parlare col database
    private static ProdottoDAO prodottoDAO = new ProdottoDAO();

    public CatalogoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            // 1. Chiediamo al Model (DAO) la lista di tutti i prodotti
            Collection<Prodotto> catalogo = prodottoDAO.doRetrieveAll("");
            
            // 2. Inseriamo la lista nella "request" per passarla alla JSP (View)
            request.setAttribute("prodotti", catalogo);
            
            // 3. Passiamo il controllo alla pagina JSP che mostrerà i dati
            RequestDispatcher dispatcher = request.getRequestDispatcher("/catalogo.jsp");
            dispatcher.forward(request, response);
            
        } catch (SQLException e) {
            // In caso di errore col database, stampiamo l'errore nella console di Eclipse
            e.printStackTrace();
            // E reindirizziamo l'utente a una pagina di errore generica (opzionale)
            response.sendRedirect("error.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Per ora facciamo in modo che le richieste POST vengano gestite come le GET
        doGet(request, response);
    }
}