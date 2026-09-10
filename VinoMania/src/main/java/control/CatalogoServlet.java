package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

// Attenzione: su Tomcat 10+ si usa "jakarta" e non "javax"
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Prodotto;
import model.ProdottoDAO;

/**
 * Servlet per la gestione del catalogo prodotti.
 */
@WebServlet("/catalogo") 
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
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel recupero dei prodotti dal database");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Gestiamo le richieste POST allo stesso modo delle GET
        doGet(request, response);
    }
}