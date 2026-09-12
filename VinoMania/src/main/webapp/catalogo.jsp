<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Collection" %>
<%@ page import="model.Prodotto" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogo - Vino Mania</title>
    <style>
        /* [QUI RIMANE IL TUO CSS PRECEDENTE] */
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 0; margin: 0; }
        .container { padding: 20px; }
        .grid { display: flex; flex-wrap: wrap; gap: 20px; }
        .card { background: white; border: 1px solid #ddd; padding: 15px; border-radius: 8px; width: 250px; text-align: center; }
        .prezzo { color: #8B0000; font-weight: bold; font-size: 1.2em; }
    </style>
</head>
<body>

<!-- /* Inclusione dell'header */ -->

<jsp:include page="header.jsp" />

    <div class="container">
        <h1>I Nostri Vini</h1>
        
        <div class="grid">
            <%
                // Recuperiamo la lista dei prodotti passata dalla Servlet
                Collection<Prodotto> catalogo = (Collection<Prodotto>) request.getAttribute("prodotti");
                
                if (catalogo != null && !catalogo.isEmpty()) {
                    for (Prodotto p : catalogo) {
            %>
                <div class="card">
                    <img src="${pageContext.request.contextPath}/images/<%= p.getImmagine() %>" alt="<%= p.getNome() %>" style="max-width: 100%; height: 200px; object-fit: contain;">
                    
                    <h3><%= p.getNome() %></h3>
                    <p><em><%= p.getCategoria() %></em></p>
                    <p><%= p.getDescrizione() %></p>
                    <p class="prezzo">&euro; <%= String.format("%.2f", p.getPrezzo()) %></p>
                    
                    <!-- FORM AGGIUNGI AL CARRELLO -->
                    <form action="${pageContext.request.contextPath}/carrello" method="post" style="margin-top: 15px;">
                        <!-- Campi nascosti per dire alla Servlet cosa fare e per quale prodotto -->
                        <input type="hidden" name="azione" value="aggiungi">
                        <input type="hidden" name="id" value="<%= p.getId() %>">
                        
                        <!-- Selezione quantità e pulsante -->
                        <input type="number" name="quantita" value="1" min="1" style="width: 50px; padding: 6px; border: 1px solid #ccc; border-radius: 4px;">
                        <button type="submit" class="btn" style="background-color: #8B0000; color: white; padding: 8px 12px; border: none; margin-left: 5px;">
                            🛒 Aggiungi
                        </button>
                    </form>
                </div>
            <%
                    } // Chiude il ciclo for
                } else { 
            %>
                <p>Nessun prodotto disponibile al momento. Ritorna più tardi!</p>
            <%
                } // Chiude l'else
            %>
        </div>

</body>
</html>
    </div>

</body>
</html>