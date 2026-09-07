package model;

import java.io.Serializable;

/**
 * Java Bean che rappresenta il prodotto (Vino) all'interno dell'applicazione Vino Mania.
 * Implementazione di Serializable per consentire il passaggio dell'oggetto nella sessione HTTP.
 */
public class Prodotto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String descrizione;
    private double prezzo;
    private int quantita;
    private String categoria; // es. "Rosso", "Bianco", "Spumante", "Passito"
    private String immagine;  // Nome del file immagine (es. "chianti.jpg")

    // 1. Costruttore vuoto (obbligatorio per lo standard Java Bean)
    public Prodotto() {
    }

    // 2. Costruttore completo (comodo per la creazione rapida degli oggetti dal DAO)
    public Prodotto(int id, String nome, String descrizione, double prezzo, int quantita, String categoria, String immagine) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.categoria = categoria;
        this.immagine = immagine;
    }

    // --- Getter e Setter ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    @Override
    public String toString() {
        return "Prodotto [id=" + id + ", nome=" + nome + ", prezzo=" + prezzo + "€, quantita=" + quantita + "]";
    }
}