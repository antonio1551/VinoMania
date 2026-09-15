<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Utente" %>
<%@ page import="model.Carrello" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout - Vino Mania</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

    <jsp:include page="header.jsp" />

    <%
        Utente utente = (Utente) session.getAttribute("utente");
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        DecimalFormat df = new DecimalFormat("0.00");
    %>

    <div class="container" style="max-width: 600px; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin-top: 40px;">
        <h2>Conferma Ordine</h2>
        <p>Stai per acquistare prodotti per un totale di <strong>&euro; <%= df.format(carrello.getTotale()) %></strong>.</p>
        <hr style="margin: 20px 0; border: 0; border-top: 1px solid #eee;">

        <!-- Form che invierà i dati finali per salvare l'ordine nel DB -->
        <form id="formCheckout" action="${pageContext.request.contextPath}/confermaOrdine" method="post">
            
            <h3>Dati di Spedizione</h3>
            <div class="form-group">
                <label for="indirizzo">Indirizzo di Consegna (Via e civico):</label>
                <input type="text" id="indirizzo" name="indirizzo" required>
            </div>
            
            <div style="display: flex; gap: 15px;">
                <div class="form-group" style="flex: 2;">
                    <label for="citta">Città:</label>
                    <input type="text" id="citta" name="citta" required>
                </div>
                <div class="form-group" style="flex: 1;">
                    <label for="cap">CAP:</label>
                    <input type="text" id="cap" name="cap" required maxlength="5">
                </div>
            </div>

            <h3 style="margin-top: 20px;">Dati di Pagamento</h3>
            <div class="form-group">
                <label for="carta">Numero Carta di Credito:</label>
                <input type="text" id="carta" name="carta" placeholder="1234 5678 1234 5678" required maxlength="16">
            </div>

            <button type="submit" class="btn" style="width: 100%; background-color: #4CAF50; color: white; margin-top: 20px; font-size: 1.1em;">
                Paga e Completa Ordine
            </button>
        </form>
    </div>

    <jsp:include page="footer.jsp" />

</body>
</html>