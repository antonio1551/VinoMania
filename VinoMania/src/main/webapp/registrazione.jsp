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
                <input type="email" id="email" name="email" onblur="verificaEmailAJAX()" required>
                <span id="emailError" class="error-msg" style="display: none; color: #f44336; font-size: 0.9em; margin-top: 5px;">
                    Attenzione: Questa email è già registrata!
                </span>
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
    
    <script>
        function verificaEmailAJAX() {
            var emailInput = document.getElementById("email");
            var emailError = document.getElementById("emailError");
            var submitBtn = document.getElementById("btnRegistrati"); // Sostituisci con l'ID del tuo bottone Submit
            
            var email = emailInput.value;

            // Chiamata AJAX solo se il campo non è vuoto
            if (email.length > 0) {
                var xhr = new XMLHttpRequest();
                
                xhr.onreadystatechange = function() {
                    // Quando la risposta è pronta e lo stato HTTP è 200 (OK)
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        var risposta = xhr.responseText;
                        
                        if (risposta === "esiste") {
                            // Modifica del DOM per mostrare l'errore
                            emailError.style.display = "block";
                            emailInput.style.border = "2px solid #f44336"; // Bordo rosso
                            if(submitBtn) submitBtn.disabled = true; // Blocca il form
                        } else {
                            // L'email è libera, rimuoviamo gli errori
                            emailError.style.display = "none";
                            emailInput.style.border = "1px solid #ccc";
                            if(submitBtn) submitBtn.disabled = false; // Sblocca il form
                        }
                    }
                };
                
                // Prepariamo e inviamo la richiesta GET alla nostra nuova Servlet
                xhr.open("GET", "${pageContext.request.contextPath}/verifica-email?email=" + encodeURIComponent(email), true);
                xhr.send();
            }
        }
    </script>
</body>
</html>