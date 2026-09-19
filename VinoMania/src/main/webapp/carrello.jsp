<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Carrello" %>
<%@ page import="model.ItemCarrello" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Il tuo Carrello - Vino Mania</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

    <!-- Inclusione Header -->
    <jsp:include page="header.jsp" />

    <div class="container">
        <h1>Il tuo Carrello</h1>

        <%
            // Recuperiamo il carrello dalla sessione
            Carrello carrello = (Carrello) session.getAttribute("carrello");
            
            // Verifichiamo se il carrello è vuoto o non esiste
            if (carrello == null || carrello.getItems().isEmpty()) {
        %>
            <p>Il tuo carrello è attualmente vuoto.</p>
            <br>
            <a href="${pageContext.request.contextPath}/catalogo" class="btn" style="background-color: #8B0000; color: white;">Torna agli acquisti</a>
        <%
            } else {
                // Formattatore per mostrare i prezzi con due decimali
                DecimalFormat df = new DecimalFormat("0.00");
        %>
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>Prodotto</th>
                        <th>Prezzo Unitario</th>
                        <th>Quantità</th>
                        <th>Totale</th>
                        <th>Azioni</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Cicliamo su tutti i prodotti nel carrello
                        for (ItemCarrello item : carrello.getItems()) {
                    %>
                    <tr>
                        <td><strong><%= item.getProdotto().getNome() %></strong></td>
                        <td>&euro; <%= df.format(item.getProdotto().getPrezzo()) %></td>
                        <td>
                            <!-- Form per aggiornare la quantità -->
                            <form action="${pageContext.request.contextPath}/carrello" method="post" style="display:inline;">
                                <input type="hidden" name="azione" value="aggiorna">
                                <input type="hidden" name="id" value="<%= item.getProdotto().getId() %>">
                                <input type="number" name="quantita" value="<%= item.getQuantita() %>" min="1" class="qta-input">
                                <button type="submit" class="btn-small">Aggiorna</button>
                            </form>
                        </td>
                        <td><strong>&euro; <%= df.format(item.getProdotto().getPrezzo() * item.getQuantita()) %></strong></td>
                        <td>
                            <!-- Form per rimuovere il singolo prodotto -->
                            <form action="${pageContext.request.contextPath}/carrello" method="post" style="display:inline;">
                                <input type="hidden" name="azione" value="rimuovi">
                                <input type="hidden" name="id" value="<%= item.getProdotto().getId() %>">
                                <button type="submit" class="btn-remove">X Rimuovi</button>
                            </form>
                        </td>
                    </tr>
                    <%
                        } // Fine ciclo for
                    %>
                </tbody>
            </table>

            <div class="cart-summary">
                <h2>Totale Ordine: &euro; <%= df.format(carrello.getTotale()) %></h2>
                <div class="cart-actions">
                    <!-- Form per svuotare l'intero carrello -->
                    <form action="${pageContext.request.contextPath}/carrello" method="post">
                        <input type="hidden" name="azione" value="svuota">
                        <button type="submit" class="btn-remove" style="background-color: #555;">Svuota Carrello</button>
                    </form>
                    
                    <!-- Link al Checkout (Controllo degli accessi) -->
                    <a href="${pageContext.request.contextPath}/checkout" class="btn" style="background-color: #8B0000; color: white;">Procedi all'Acquisto</a>
                </div>
            </div>
        <%
            } 
        %>
    </div>

    <!-- Inclusione Footer -->
    <jsp:include page="footer.jsp" />

</body>
</html>