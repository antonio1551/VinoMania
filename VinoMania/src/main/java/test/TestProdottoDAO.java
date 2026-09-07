package test;

import model.Prodotto;
import model.ProdottoDAO;
import java.util.Collection;

public class TestProdottoDAO {

    public static void main(String[] args) {
        ProdottoDAO dao = new ProdottoDAO();

        try {
            // 1. Creiamo un nuovo vino di test
            Prodotto nuovoVino = new Prodotto();
            nuovoVino.setNome("Chianti Classico DOCG");
            nuovoVino.setDescrizione("Vino rosso toscano corposo, dal profumo intenso.");
            nuovoVino.setPrezzo(15.50);
            nuovoVino.setQuantita(24);
            nuovoVino.setCategoria("Rosso");
            nuovoVino.setImmagine("chianti.jpg");

            System.out.println("1. Tentativo di salvataggio nel database...");
            dao.doSave(nuovoVino);
            System.out.println("   Salvataggio completato con successo!\n");

            // 2. Leggiamo i dati per verificare che siano stati inseriti
            System.out.println("2. Lettura dei prodotti dal database:");
            Collection<Prodotto> catalogo = dao.doRetrieveAll("");

            for (Prodotto p : catalogo) {
                // Sfrutta il metodo toString() che abbiamo generato nel Java Bean
                System.out.println("   -> " + p.toString());
            }

        } catch (Exception e) {
            System.err.println("ERRORE DURANTE IL TEST:");
            e.printStackTrace();
        }
    }
}