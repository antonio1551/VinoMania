package model;

import java.util.ArrayList;
import java.util.List;

public class Ordine {
    private int id;
    private int idUtente;
    private double totale;
    private String indirizzo;
    private String dataOrdine;
    private String numeroCarta;
    
    // Lista che conterrà i prodotti esatti con la quantità acquistata
    private List<ItemCarrello> prodottiAcquistati; 

    public Ordine() {
        this.prodottiAcquistati = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public double getTotale() { return totale; }
    public void setTotale(double totale) { this.totale = totale; }

    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    public String getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(String dataOrdine) { this.dataOrdine = dataOrdine; }
    
    public String getNumeroCarta() { return numeroCarta; }
    public void setNumeroCarta(String numeroCarta) { this.numeroCarta = numeroCarta; }

    public List<ItemCarrello> getProdottiAcquistati() { return prodottiAcquistati; }
    public void setProdottiAcquistati(List<ItemCarrello> prodottiAcquistati) { 
        this.prodottiAcquistati = prodottiAcquistati; 
    }
}