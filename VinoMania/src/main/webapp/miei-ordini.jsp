<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Ordine" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>I Miei Ordini - Vino Mania</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

    <jsp:include page="header.jsp" />

    <div class="container">
        <h1>Lo Storico dei Tuoi Ordini</h1>

        <%
            List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
            DecimalFormat df = new DecimalFormat("0.00");

            if (ordini == null || ordini.isEmpty()) {
        %>
            <p style="margin-top: 20px; font-size: 1.1em;">Non hai ancora effettuato nessun ordine.</p>
            <a href="${pageContext.request.contextPath}/catalogo" class="btn" style="margin-top: 20px; display: inline-block;">Vai al Catalogo</a>
        <%
            } else {
        %>
            <table class="cart-table" style="margin-top: 30px;">
                <thead>
                    <tr>
                        <th>N° Ordine</th>
                        <th>Data</th>
                        <th>Indirizzo di Spedizione</th>
                        <th>Totale</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Ordine o : ordini) { %>
                        <tr>
                            <td><strong>#<%= o.getId() %></strong></td>
                            <td><%= o.getDataOrdine() %></td>
                            <td><%= o.getIndirizzo() %></td>
                            <td><strong>&euro; <%= df.format(o.getTotale()) %></strong></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <%
            }
        %>
    </div>

    <jsp:include page="footer.jsp" />

</body>
</html>