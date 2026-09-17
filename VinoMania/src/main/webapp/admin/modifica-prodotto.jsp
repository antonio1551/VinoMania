<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Utente" %>
<%@ page import="model.Prodotto" %>
<%
    // Sicurezza: Solo admin
    Utente adminUser = (Utente) session.getAttribute("utente");
    if (adminUser == null || !adminUser.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    
    // Recupero il prodotto pre-caricato dalla Servlet
    Prodotto p = (Prodotto) request.getAttribute("prodotto");
    if (p == null) {
        // Se si tenta di accedere direttamente alla JSP senza passare dalla Servlet
        response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifica Vino - Vino Mania</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

    <jsp:include page="/header.jsp" />

    <div class="container" style="max-width: 600px; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin-top: 40px;">
        <h2>Modifica: <%= p.getNome() %></h2>
        <a href="dashboard.jsp" style="color: #8B0000; text-decoration: underline; margin-bottom: 20px; display: inline-block;">&larr; Torna alla Dashboard</a>
        
        <!-- Il form punta alla Servlet che si occuperà dell'UPDATE -->
        <form id="formModifica" action="${pageContext.request.contextPath}/admin/aggiorna-prodotto" method="post">
            
            <!-- FONDAMENTALE: inviamo l'ID del prodotto in modo invisibile -->
            <input type="hidden" name="id" value="<%= p.getId() %>">

            <div class="form-group">
                <label for="nome">Nome del Vino:</label>
                <input type="text" id="nome" name="nome" value="<%= p.getNome() %>" required>
            </div>

            <div class="form-group">
                <label for="categoria">Categoria / Tipologia:</label>
                <input type="text" id="categoria" name="categoria" value="<%= p.getCategoria() %>" required>
            </div>

            <div class="form-group">
                <label for="descrizione">Descrizione:</label>
                <textarea id="descrizione" name="descrizione" rows="4" style="padding: 10px; border: 1px solid #ccc; border-radius: 4px;" required><%= p.getDescrizione() %></textarea>
            </div>

            <div style="display: flex; gap: 15px;">
                <div class="form-group" style="flex: 1;">
                    <label for="prezzo" style="color: #8B0000; font-weight: bold;">Nuovo Prezzo (&euro;):</label>
                    <input type="number" id="prezzo" name="prezzo" step="0.01" min="0.1" value="<%= p.getPrezzo() %>" required style="border: 2px solid #8B0000;">
                </div>
                
                <div class="form-group" style="flex: 1;">
                    <label for="quantita">Scorte:</label>
                    <input type="number" id="quantita" name="quantita" min="0" value="<%= p.getQuantita() %>" required>
                </div>
            </div>

            <button type="submit" class="btn" style="width: 100%; background-color: #ff9800; color: white; margin-top: 15px;">
                Conferma Modifiche
            </button>
        </form>
    </div>

    <jsp:include page="/footer.jsp" />

</body>
</html>