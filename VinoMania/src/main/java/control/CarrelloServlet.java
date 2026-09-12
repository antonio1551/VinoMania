package control;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Carrello;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet("/carrello")
public class CarrelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ProdottoDAO prodottoDAO = new ProdottoDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        
        if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }

        String azione = request.getParameter("azione");
        
        try {
            if (azione != null) {
                if (azione.equals("aggiungi")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    int qta = Integer.parseInt(request.getParameter("quantita"));
                    Prodotto p = prodottoDAO.doRetrieveByKey(id);
                    if (p != null) {
                        carrello.AggiungiProdotto(p, qta);
                    }
                } else if (azione.equals("rimuovi")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    carrello.RimuoviProdotto(id);
                } else if (azione.equals("aggiorna")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    int qta = Integer.parseInt(request.getParameter("quantita"));
                    carrello.AggiornaQuantita(id, qta);
                } else if (azione.equals("svuota")) {
                    carrello.Svuota();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Reindirizziamo alla pagina JSP del carrello che visualizzerà i prodotti
        request.getRequestDispatcher("/carrello.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}