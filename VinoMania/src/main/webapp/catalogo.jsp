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

// Inclusione dell'header

<jsp:include page="header.jsp" />

    <div class="container">
        <h1>I Nostri Vini</h1>
        
        <div class="grid">

    <h1>I Nostri Vini</h1>
    
    <div class="grid">
        <%
            // 1. Recuperiamo l'attributo inserito dalla Servlet nella request
            Collection<Prodotto> prodotti = (Collection<Prodotto>) request.getAttribute("prodotti");
            
            // 2. Controlliamo se ci sono prodotti
            if (prodotti != null && !prodotti.isEmpty()) {
                
                // 3. Ciclo for per generare una card HTML per ogni singolo vino
            	
                for (Prodotto p : prodotti) {
            %>
                <div class="card">
                    <%-- Aggiungiamo il tag img che punta alla cartella images unita al nome file del database --%>
                    <img src="images/<%= p.getImmagine() %>" alt="<%= p.getNome() %>" style="width: 100%; height: 200px; object-fit: cover; border-radius: 4px;">
                    
                    <h3><%= p.getNome() %></h3>
                    <p><em><%= p.getCategoria() %></em></p>
                    <p><%= p.getDescrizione() %></p>
                    <p class="prezzo"><%= String.format("%.2f", p.getPrezzo()) %> €</p>
                </div>
            <%
                } // Chiude il ciclo for per i prodotti
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