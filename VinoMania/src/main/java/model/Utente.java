package model;

import java.io.Serializable;

/**
 * Java Bean che rappresenta un utente (Cliente o Amministratore).
 */
public class Utente implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private boolean admin; // true se è amministratore, false se è cliente

    public Utente() {
    }

    public Utente(int id, String nome, String cognome, String email, String password, boolean admin) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }

    @Override
    public String toString() {
        return "Utente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", admin=" + admin + "]";
    }
}