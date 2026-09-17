<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Ordine" %>
<%@ page import="model.Utente" %>
<%@ page import="java.text.DecimalFormat" %>
<%
    // Sicurezza: Solo admin
    Utente adminUser = (Utente) session.getAttribute("utente");
    if (adminUser == null || !adminUser.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestione Vendite - Vino Mania</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

    <jsp:include page="/header.jsp" />

    <div class="container">
        <h2>Storico Completo Vendite</h2>
        <a href="${pageContext.request.contextPath}/admin/dashboard.jsp" style="color: #8B0000; text-decoration: underline; margin-bottom: 20px; display: inline-block;">&larr; Torna alla Dashboard</a>

        <%
            List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
            DecimalFormat df = new DecimalFormat("0.00");

            if (ordini != null && !ordini.isEmpty()) {
        %>
            <table class="cart-table" style="margin-top: 20px;">
                <thead>
                    <tr>
                        <th>N° Ordine</th>
                        <th>Data</th>
                        <th>ID Cliente</th>
                        <th>Indirizzo Spedizione</th>
                        <th>Totale</th>
                        <th>Azioni</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Ordine o : ordini) { %>
                        <tr>
                            <td><strong>#<%= o.getId() %></strong></td>
                            <td><%= o.getDataOrdine() %></td>
                            <!-- Mostriamo l'ID utente (e volendo il suo nome se facessimo una query più complessa) -->
                            <td style="text-align: center;"><span style="background: #f1f1f1; padding: 3px 8px; border-radius: 4px;">User <%= o.getIdUtente() %></span></td>
                            <td><%= o.getIndirizzo() %></td>
                            <td><strong>&euro; <%= df.format(o.getTotale()) %></strong></td>
                            <td>
                                <!-- Ricicliamo la Servlet DettaglioOrdine! L'amministratore può usare la stessa pagina del cliente per vedere il dettaglio! -->
                                <a href="${pageContext.request.contextPath}/dettaglio-ordine?id=<%= o.getId() %>" class="btn" style="padding: 5px 10px; font-size: 0.9em; background-color: #555;">Dettaglio</a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <%
            } else {
        %>
            <p>Nessun ordine presente nel database.</p>
        <%
            }
        %>
    </div>

    <jsp:include page="/footer.jsp" />

</body>
</html>