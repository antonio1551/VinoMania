<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrazione - Vino Mania</title>
    <!-- CSS esterno -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

    <!-- Inclusione Header -->
    <jsp:include page="header.jsp" />

    <div class="container" style="max-width: 500px; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin-top: 40px;">
        <h2>Crea un Account</h2>
        <p>Unisciti a Vino Mania per effettuare i tuoi ordini.</p>
        
        <!-- Il form punta alla futura RegistrazioneServlet -->
        <form id="formRegistrazione" action="${pageContext.request.contextPath}/registrazione" method="post">
            
            <div class="form-group">
                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" required>
                <span id="erroreNome" class="error-msg">Inserisci un nome valido (solo lettere).</span>
            </div>

            <div class="form-group">
                <label for="cognome">Cognome:</label>
                <input type="text" id="cognome" name="cognome" required>
                <span id="erroreCognome" class="error-msg">Inserisci un cognome valido (solo lettere).</span>
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
                <span id="erroreEmail" class="error-msg">Inserisci un indirizzo email valido.</span>
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                <span id="errorePassword" class="error-msg">La password deve contenere almeno 8 caratteri.</span>
            </div>

            <button type="submit" class="btn" style="width: 100%; background-color: #8B0000; color: white;">Registrati</button>
        </form>
        
        <p style="margin-top: 20px;">Hai già un account? <a href="${pageContext.request.contextPath}/login.jsp">Accedi qui</a>.</p>
    </div>

    <!-- Inclusione Footer -->
    <jsp:include page="footer.jsp" />

    <!-- JS esterno inserito in fondo al body per ottimizzare il caricamento -->
    <script src="${pageContext.request.contextPath}/scripts/validazione.js"></script>
</body>
</html>