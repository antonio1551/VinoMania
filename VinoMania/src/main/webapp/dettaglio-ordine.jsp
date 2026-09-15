<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.ItemCarrello" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettaglio Ordine - Vino Mania</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

    <jsp:include page="header.jsp" />

    <div class="container">
        <h1>Dettaglio Ordine #<%= request.getAttribute("idOrdine") %></h1>
        <a href="${pageContext.request.contextPath}/miei-ordini" style="color: #8B0000; text-decoration: underline; margin-bottom: 20px; display: inline-block;">&larr; Torna ai tuoi ordini</a>

        <%
            List<ItemCarrello> dettagli = (List<ItemCarrello>) request.getAttribute("dettagli");
            DecimalFormat df = new DecimalFormat("0.00");

            if (dettagli != null && !dettagli.isEmpty()) {
        %>
            <table class="cart-table" style="margin-top: 20px;">
                <thead>
                    <tr>
                        <th>Prodotto</th>
                        <th>Quantità</th>
                        <th>Prezzo Storico (cad.)</th>
                        <th>Subtotale</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (ItemCarrello item : dettagli) { 
                           double subtotale = item.getProdotto().getPrezzo() * item.getQuantita();
                    %>
                        <tr>
                            <td>
                                <strong><%= item.getProdotto().getNome() %></strong><br>
                                <span style="font-size: 0.9em; color: #666;"><%= item.getProdotto().getCategoria() %></span>
                            </td>
                            <td><%= item.getQuantita() %></td>
                            <td>&euro; <%= df.format(item.getProdotto().getPrezzo()) %></td>
                            <td><strong>&euro; <%= df.format(subtotale) %></strong></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <%
            } else {
        %>
            <p>Impossibile recuperare i dettagli dell'ordine.</p>
        <%
            }
        %>
    </div>

    <jsp:include page="footer.jsp" />

</body>
</html>