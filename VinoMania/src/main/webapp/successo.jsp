<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ordine Completato - Vino Mania</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

    <jsp:include page="header.jsp" />

    <div class="container" style="text-align: center; margin-top: 50px; background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); max-width: 600px;">
        <h1 style="color: #4CAF50; font-size: 3em; margin-bottom: 10px;">&#10004;</h1>
        <h2 style="color: #333;">Ordine Completato con Successo!</h2>
        <p style="font-size: 1.1em; color: #555; margin-bottom: 30px;">
            Grazie per aver acquistato su Vino Mania. Il tuo ordine è stato registrato nei nostri sistemi e verrà elaborato al più presto.
        </p>
        
        <div>
            <a href="${pageContext.request.contextPath}/" class="btn" style="background-color: #8B0000; color: white;">Torna alla Home</a>
            <a href="${pageContext.request.contextPath}/miei-ordini" class="btn" style="background-color: #555; color: white; margin-left: 10px;">Visualizza i tuoi ordini</a>
        </div>
    </div>

    <jsp:include page="footer.jsp" />

</body>
</html>