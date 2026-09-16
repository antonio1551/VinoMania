<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Utente" %>
<%
    // CONTROLLO DI SICUREZZA: Solo gli Admin possono stare in questa pagina!
    Utente adminUser = (Utente) session.getAttribute("utente");
    if (adminUser == null || !adminUser.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return; // Blocca immediatamente il caricamento del resto della pagina
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pannello Amministratore - Vino Mania</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
    <style>
        .admin-menu {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .admin-card {
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            text-align: center;
            transition: transform 0.2s;
        }
        .admin-card:hover {
            transform: translateY(-5px);
        }
        .admin-card h3 {
            color: #8B0000;
            margin-bottom: 15px;
        }
        .admin-card p {
            color: #666;
            margin-bottom: 20px;
            font-size: 0.9em;
        }
    </style>
</head>
<body>

    <!-- Nota il "/" prima di header.jsp: serve per dire a Tomcat di cercarlo nella cartella principale e non in /admin/ -->
    <jsp:include page="/header.jsp" />

    <div class="container">
        <h1>Pannello di Controllo</h1>
        <p>Benvenuto, <strong><%= adminUser.getNome() %></strong>. Da qui puoi gestire il tuo e-commerce.</p>

        <div class="admin-menu">
            <!-- Card 1: Gestione Catalogo -->
            <div class="admin-card">
                <h3>Gestione Prodotti</h3>
                <p>Aggiungi nuovi vini, modifica i prezzi, aggiorna le descrizioni o elimina prodotti dal catalogo.</p>
                <!-- Il link punterà a una futura Servlet per estrarre tutti i prodotti -->
                <a href="${pageContext.request.contextPath}/admin/gestione-prodotti" class="btn" style="background-color: #8B0000; color: white; display: block;">Vai al Catalogo Admin</a>
            </div>

            <!-- Card 2: Aggiunta Rapida -->
            <div class="admin-card">
                <h3>Nuovo Prodotto</h3>
                <p>Inserisci rapidamente una nuova bottiglia nel database con foto, prezzo e dettagli.</p>
                <a href="${pageContext.request.contextPath}/admin/aggiungi-prodotto.jsp" class="btn" style="background-color: #4CAF50; color: white; display: block;">+ Aggiungi Vino</a>
            </div>

            <!-- Card 3: Visualizza tutti gli ordini (Opzionale ma molto utile) -->
            <div class="admin-card">
                <h3>Tutti gli Ordini</h3>
                <p>Visualizza lo storico completo degli ordini effettuati da tutti i clienti del sito.</p>
                <a href="${pageContext.request.contextPath}/admin/ordini" class="btn" style="background-color: #555; color: white; display: block;">Vedi Ordini Clienti</a>
            </div>
        </div>
    </div>

    <jsp:include page="/footer.jsp" />

</body>
</html>