<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Utente" %>
<%
    // Sicurezza: Solo admin loggati
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
    <title>Aggiungi Nuovo Vino - Vino Mania</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

    <jsp:include page="/header.jsp" />

    <div class="container" style="max-width: 600px; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin-top: 40px;">
        <h2>Aggiungi un Nuovo Vino</h2>
        <a href="dashboard.jsp" style="color: #8B0000; text-decoration: underline; margin-bottom: 20px; display: inline-block;">&larr; Torna alla Dashboard</a>
        
        <form id="formProdotto" action="${pageContext.request.contextPath}/admin/salva-prodotto" method="post">
            
            <div class="form-group">
                <label for="nome">Nome del Vino:</label>
                <input type="text" id="nome" name="nome" required>
            </div>

            <div class="form-group">
                <label for="categoria">Categoria / Tipologia (es. Rosso, Bianco, Bollicine):</label>
                <input type="text" id="categoria" name="categoria" required>
            </div>

            <div class="form-group">
                <label for="descrizione">Descrizione:</label>
                <textarea id="descrizione" name="descrizione" rows="4" style="padding: 10px; border: 1px solid #ccc; border-radius: 4px;" required></textarea>
            </div>

            <div style="display: flex; gap: 15px;">
                <div class="form-group" style="flex: 1;">
                    <label for="prezzo">Prezzo (&euro;):</label>
                    <input type="number" id="prezzo" name="prezzo" step="0.01" min="0.1" required>
                    <span id="errorePrezzo" class="error-msg">Il prezzo deve essere maggiore di 0.</span>
                </div>
                
                <div class="form-group" style="flex: 1;">
                    <label for="quantita">Scorte in Magazzino:</label>
                    <input type="number" id="quantita" name="quantita" min="0" value="10" required>
                </div>
                
                <div class="form-group" style="flex: 2;">
                    <label for="immagine">Nome file immagine (es. vino1.jpg):</label>
                    <input type="text" id="immagine" name="immagine" required>
                </div>
            </div>

            <button type="submit" class="btn" style="width: 100%; background-color: #4CAF50; color: white; margin-top: 15px;">
                Salva nel Catalogo
            </button>
        </form>
    </div>

    <jsp:include page="/footer.jsp" />

    <!-- Validazione JavaScript client-side (no alert) -->
    <script>
        document.getElementById("formProdotto").addEventListener("submit", function(event) {
            let prezzoInput = document.getElementById("prezzo");
            let errorePrezzo = document.getElementById("errorePrezzo");
            
            if (parseFloat(prezzoInput.value) <= 0) {
                event.preventDefault(); // Blocca l'invio
                prezzoInput.classList.add("input-error");
                errorePrezzo.style.display = "block";
            } else {
                prezzoInput.classList.remove("input-error");
                errorePrezzo.style.display = "none";
            }
        });
    </script>
</body>
</html>