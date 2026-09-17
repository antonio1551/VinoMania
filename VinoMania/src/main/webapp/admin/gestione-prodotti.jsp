<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Prodotto" %>
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
    <title>Gestione Prodotti - Vino Mania</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
    <style>
        /* Stili specifici per i pulsanti di azione */
        .btn-action {
            padding: 6px 12px;
            font-size: 0.9em;
            margin-right: 5px;
            text-decoration: none;
            display: inline-block;
            border-radius: 4px;
        }
        .btn-edit {
            background-color: #ff9800; /* Arancione per la modifica */
            color: white;
        }
        .btn-delete {
            background-color: #f44336; /* Rosso acceso per l'eliminazione */
            color: white;
        }
    </style>
</head>
<body>

    <jsp:include page="/header.jsp" />

    <div class="container">
        <h2>Gestione Catalogo Prodotti</h2>
        <a href="dashboard.jsp" style="color: #8B0000; text-decoration: underline; display: inline-block; margin-bottom: 20px;">&larr; Torna alla Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/aggiungi-prodotto.jsp" class="btn" style="float: right; background-color: #4CAF50; color: white;">+ Nuovo Vino</a>
        
        <div style="clear: both; margin-bottom: 20px;"></div>

        <%
            List<Prodotto> catalogo = (List<Prodotto>) request.getAttribute("catalogo");
            DecimalFormat df = new DecimalFormat("0.00");

            if (catalogo != null && !catalogo.isEmpty()) {
        %>
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Immagine</th>
                        <th>Nome</th>
                        <th>Categoria</th>
                        <th>Prezzo</th>
                        <th>Scorte</th>
                        <th>Azioni</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Prodotto p : catalogo) { %>
                        <tr>
                            <td><strong>#<%= p.getId() %></strong></td>
                            <td>
                                <!-- Se hai le immagini, mostriamo una miniatura. Altrimenti mostra il nome del file -->
                                <%= p.getImmagine() %>
                            </td>
                            <td><strong><%= p.getNome() %></strong></td>
                            <td><%= p.getCategoria() %></td>
                            <td>&euro; <%= df.format(p.getPrezzo()) %></td>
                            <td>
                                <% if (p.getQuantita() <= 5) { %>
                                    <span style="color: red; font-weight: bold;"><%= p.getQuantita() %> (In esaurimento)</span>
                                <% } else { %>
                                    <%= p.getQuantita() %>
                                <% } %>
                            </td>
                            <td>
                                <!-- Pulsante Modifica (punta alla Servlet creata prima) -->
                                <a href="${pageContext.request.contextPath}/admin/modifica-prodotto?id=<%= p.getId() %>" class="btn-action btn-edit">Modifica</a>
                                
                                <!-- Pulsante Elimina (punta a una futura Servlet con JS di conferma) -->
                                <a href="${pageContext.request.contextPath}/admin/elimina-prodotto?id=<%= p.getId() %>" class="btn-action btn-delete" onclick="return confirm('Sei sicuro di voler eliminare definitivamente il vino <%= p.getNome().replace("'", "\\'") %> dal catalogo?');">Elimina</a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <%
            } else {
        %>
            <p>Nessun prodotto presente nel catalogo.</p>
        <%
            }
        %>
    </div>

    <jsp:include page="/footer.jsp" />

</body>
</html>