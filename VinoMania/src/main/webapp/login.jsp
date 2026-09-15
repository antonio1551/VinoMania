<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Vino Mania</title>
    <!-- Collegamento al CSS esterno -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

    <!-- Inclusione Header -->
    <jsp:include page="header.jsp" />

    <div class="container" style="max-width: 400px; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin-top: 40px;">
        <h2>Accedi</h2>
        
        <!-- Gestione dinamica dei messaggi dalla Servlet -->
        <% if (request.getAttribute("messaggioSuccesso") != null) { %>
            <p style="color: green; font-weight: bold; background: #e8f5e9; padding: 10px; border-radius: 4px;">
                <%= request.getAttribute("messaggioSuccesso") %>
            </p>
        <% } %>
        
        <% if (request.getAttribute("errore") != null) { %>
            <p style="color: red; font-weight: bold; background: #ffebee; padding: 10px; border-radius: 4px;">
                <%= request.getAttribute("errore") %>
            </p>
        <% } %>

        <form action="${pageContext.request.contextPath}/login" method="post" style="margin-top: 20px;">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>

            <button type="submit" class="btn" style="width: 100%; background-color: #8B0000; color: white;">Entra</button>
        </form>
        
        <p style="margin-top: 20px;">Non hai ancora un account? <a href="${pageContext.request.contextPath}/registrazione.jsp">Registrati qui</a>.</p>
    </div>

    <!-- Inclusione Footer -->
    <jsp:include page="footer.jsp" />

</body>
</html>