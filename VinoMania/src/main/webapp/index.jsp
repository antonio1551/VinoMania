<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - Vino Mania</title>
    <!-- Collegamento al foglio di stile esterno come da requisiti -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

    <!-- Inclusione dell'Header di navigazione -->
    <jsp:include page="header.jsp" />

    <!-- Sezione principale (Hero) -->
    <div class="hero">
        <h1>Benvenuti su Vino Mania</h1>
        <p>Esplora la nostra cantina digitale: le migliori etichette selezionate per te.</p>
        <a href="${pageContext.request.contextPath}/catalogo" class="btn">Vai al Catalogo</a>
    </div>

    <div class="container">
        <h2>Tradizione e Passione</h2>
        <p>Offriamo una curata selezione di vini rossi, bianchi, spumanti e passiti provenienti dalle migliori tradizioni italiane.</p>
    </div>

    <!-- Inclusione del Footer -->
    <jsp:include page="footer.jsp" />

</body>
</html>