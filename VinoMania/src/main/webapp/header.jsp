<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Utente" %>

<style>
    /* Stili per la barra di navigazione */
    .navbar {
        background-color: #8B0000;
        padding: 15px 30px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        margin-bottom: 20px;
    }
    .navbar .logo {
        color: white;
        font-size: 24px;
        font-weight: bold;
        text-decoration: none;
    }
    .nav-links {
        display: flex;
        gap: 20px;
        align-items: center; /* Allinea verticalmente gli elementi */
    }
    .nav-links a {
        color: white;
        text-decoration: none;
        font-size: 16px;
        padding: 8px 12px;
        border-radius: 4px;
        transition: background-color 0.3s;
    }
    .nav-links a:hover {
        background-color: #5A0000;
    }
    .user-greeting {
        color: #FFD700; /* Color oro per far risaltare il nome */
        font-weight: bold;
        margin-right: 10px;
        font-size: 16px;
    }
</style>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/" class="logo">🍷 Vino Mania</a>
    
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/">Home</a>
        <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
        <a href="${pageContext.request.contextPath}/carrello">Carrello</a>
        
        <%
            // Controlliamo se esiste un utente loggato nella sessione
            Utente utenteLoggato = (Utente) session.getAttribute("utente");
            
            if (utenteLoggato != null) {
        %>
            <!-- UTENTE LOGGATO -->
            <span class="user-greeting">Ciao, <%= utenteLoggato.getNome() %>!</span>
            
            <% if (utenteLoggato.isAdmin()) { %>
                <!-- Link visibile SOLO all'amministratore -->
                <a href="${pageContext.request.contextPath}/admin/dashboard.jsp">Pannello Admin</a>
            <% } else { %>
                <!-- Link visibile SOLO ai clienti normali -->
                <a href="${pageContext.request.contextPath}/miei-ordini">I Miei Ordini</a>
            <% } %>
            
            <a href="${pageContext.request.contextPath}/logout" style="background-color: #333;">Esci</a>
            
        <%
            } else {
        %>
            <!-- UTENTE OSPITE (NON LOGGATO) -->
            <a href="${pageContext.request.contextPath}/login.jsp">Area Riservata</a>
        <%
            }
        %>
    </div>
</div>