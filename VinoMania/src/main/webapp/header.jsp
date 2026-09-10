<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    /* Stili per la barra di navigazione */
    .navbar {
        background-color: #8B0000; /* Rosso scuro */
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
        background-color: #5A0000; /* Rosso ancora più scuro al passaggio del mouse */
    }
</style>

<div class="navbar">
    <!-- Il contextPath assicura che il link funzioni sempre, a prescindere da dove ci troviamo -->
    <a href="${pageContext.request.contextPath}/" class="logo">🍷 Vino Mania</a>
    
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/">Home</a>
        <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
        <a href="${pageContext.request.contextPath}/carrello">Carrello</a>
        <a href="${pageContext.request.contextPath}/login">Area Riservata</a>
    </div>
</div>