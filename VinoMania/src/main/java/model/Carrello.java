package model;

import java.util.ArrayList;
import java.util.List;

public class Carrello {
    private List<ItemCarrello> items;

    public Carrello() {
        items = new ArrayList<>();
    }

    public void AggiungiProdotto(Prodotto prodotto, int quantita) {
        for (ItemCarrello item : items) {
            if (item.getProdotto().getId() == prodotto.getId()) {
                item.setQuantita(item.getQuantita() + quantita);
                return;
            }
        }
        items.add(new ItemCarrello(prodotto, quantita));
    }

    public void RimuoviProdotto(int idProdotto) {
        items.removeIf(item -> item.getProdotto().getId() == idProdotto);
    }

    public void AggiornaQuantita(int idProdotto, int quantita) {
        for (ItemCarrello item : items) {
            if (item.getProdotto().getId() == idProdotto) {
                if (quantita > 0) {
                    item.setQuantita(quantita);
                } else {
                    RimuoviProdotto(idProdotto);
                }
                break;
            }
        }
    }

    public void Svuota() {
        items.clear();
    }

    public List<ItemCarrello> getItems() {
        return items;
    }
    
    public double getTotale() {
        double totale = 0;
        for (ItemCarrello item : items) {
            totale += item.getProdotto().getPrezzo() * item.getQuantita();
        }
        return totale;
    }
}