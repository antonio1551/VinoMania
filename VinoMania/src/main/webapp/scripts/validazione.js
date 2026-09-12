// Aspettiamo che il DOM sia completamente caricato
document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("formRegistrazione");
    const nome = document.getElementById("nome");
    const cognome = document.getElementById("cognome");
    const email = document.getElementById("email");
    const password = document.getElementById("password");

    // Espressioni regolari per la validazione
    const regexNome = /^[A-Za-z\s]+$/; // Solo lettere e spazi
    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Formato email standard
    const regexPassword = /^.{8,}$/; // Almeno 8 caratteri

    // Funzione generica per mostrare/nascondere l'errore modificando il DOM (Niente alert!)
    function validaCampo(input, regex, idErrore) {
        const errorSpan = document.getElementById(idErrore);
        if (!regex.test(input.value.trim())) {
            input.classList.add("input-error");
            errorSpan.style.display = "block";
            return false;
        } else {
            input.classList.remove("input-error");
            errorSpan.style.display = "none";
            return true;
        }
    }

    // Gestione dell'evento 'change' (si attiva quando l'utente esce dal campo dopo aver digitato)
    nome.addEventListener("change", function() { validaCampo(nome, regexNome, "erroreNome"); });
    cognome.addEventListener("change", function() { validaCampo(cognome, regexNome, "erroreCognome"); });
    email.addEventListener("change", function() { validaCampo(email, regexEmail, "erroreEmail"); });
    password.addEventListener("change", function() { validaCampo(password, regexPassword, "errorePassword"); });

    // Gestione dell'evento 'submit' (si attiva alla pressione del pulsante di invio)
    form.addEventListener("submit", function(event) {
        let isNomeValido = validaCampo(nome, regexNome, "erroreNome");
        let isCognomeValido = validaCampo(cognome, regexNome, "erroreCognome");
        let isEmailValida = validaCampo(email, regexEmail, "erroreEmail");
        let isPasswordValida = validaCampo(password, regexPassword, "errorePassword");

        // I dati vengono inviati al server solo se tutti i controlli sono superati
        if (!isNomeValido || !isCognomeValido || !isEmailValida || !isPasswordValida) {
            event.preventDefault(); // Blocca l'invio del form
        }
    });
});