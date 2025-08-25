
package theknife;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import static theknife.GestioneFile.leggiTutteLeRecensioni;

/**
 * La classe <strong>Recensione</strong> rappresenta una recensione scritta da un cliente
 * su un ristorante all’interno della piattaforma <em>TheKnife</em>.
 * 
 * Ogni recensione contiene:
 * <ul>
 *   <li>Il nome del ristorante recensito</li>
 *   <li>Il codice fiscale del cliente autore</li>
 *   <li>Un testo opzionale</li>
 *   <li>Un voto in stelle (1–5)</li>
 *   <li>La data della recensione</li>
 *   <li>Un’eventuale risposta del ristoratore</li>
 * </ul>
 * Le recensioni sono persistenti e vengono salvate/lette da file CSV.
 *
 * @author omema gharsellaoui
   @author Giuseppina Salvati
 */

public class Recensione {
    private String nomeRistorante;
    private String codiceFiscale;
    private double stelle;
    private String testoRecensione;
    private String data;
    private String risposta; // NUOVO CAMPO

    // Costruttore completo
    /**
     * Costruttore completo della classe <code>Recensione</code>.
     *
     * @param nomeRistorante nome del ristorante recensito
     * @param codiceFiscale codice fiscale del cliente autore
     * @param testoRecensione testo della recensione
     * @param stelle voto in stelle
     * @param data data della recensione
     * @param risposta eventuale risposta del ristoratore
     */
    public Recensione(String nomeRistorante, String codiceFiscale, String testoRecensione, 
                     double stelle, String data, String risposta) {
        this.nomeRistorante = nomeRistorante;
        this.codiceFiscale = codiceFiscale;
        this.testoRecensione = testoRecensione;
        this.stelle = stelle;
        this.data = data;
        this.risposta = risposta;
    }

    // Getters e Setters
    public String getNomeRistorante() { return nomeRistorante; }
    public void setNomeRistorante(String nomeRistorante) { this.nomeRistorante = nomeRistorante; }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public double getStelle() { return stelle; }
    public void setStelle(double stelle) { this.stelle = stelle; }

    public String getTestoRecensione() { return testoRecensione; }
    public void setTestoRecensione(String testoRecensione) { this.testoRecensione = testoRecensione; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getRisposta() { return risposta; } // NUOVO GETTER
    public void setRisposta(String risposta) { this.risposta = risposta; } // NUOVO SETTER

    /**
     * Restituisce il cliente autore della recensione (codice fiscale).
     * @return codice fiscale del cliente
     */
    public String getCliente() {
    return getCodiceFiscale(); // già presente
}

    public String getRistorante() {
        return getNomeRistorante(); // già presente
    }

    /**
     * Converte la recensione in formato CSV.
     * <p>
     * Campi salvati: ristorante, codice fiscale, testo recensione, stelle, data, risposta.
     *</p>
     * @return stringa CSV rappresentante la recensione
     * 
     */
    public String toCSV() {
    return String.format(Locale.US, "%s,%s,%s,%.1f,%s,%s",
        nomeRistorante != null ? nomeRistorante : "",
        codiceFiscale != null ? codiceFiscale : "",
        testoRecensione != null ? testoRecensione.replace(",", ";") : "",
        stelle,   // qui stelle è double, formato con 1 cifra decimale
        data != null ? data : "",
        (risposta != null && !risposta.trim().isEmpty()) ? risposta.replace(",", ";") : ""
    );
}

     /**
     * Cerca tutte le recensioni associate a un determinato ristorante.
     *
     * @param nomeRistorante nome del ristorante
     * @return lista di recensioni per quel ristorante
     */

    public static ArrayList<Recensione> cercaPerRistorante(String nomeRistorante) {
        ArrayList<Recensione> tutte = leggiTutteLeRecensioni();
        ArrayList<Recensione> filtrate = new ArrayList<>();

        for (Recensione r : tutte) {
            if (r.getNomeRistorante().equalsIgnoreCase(nomeRistorante)) {
                filtrate.add(r);
            }
        }
        return filtrate;
    }

     /**
     * Cerca tutte le recensioni scritte da un determinato cliente.
     *
     * @param codFiscale codice fiscale del cliente
     * @return lista di recensioni del cliente
     */

    public static ArrayList<Recensione> cercaPerCliente(String codFiscale) {
        ArrayList<Recensione> tutte = leggiTutteLeRecensioni();
        ArrayList<Recensione> filtrate = new ArrayList<>();

        for (Recensione r : tutte) {
            if (r.getCodiceFiscale().equalsIgnoreCase(codFiscale)) {
                filtrate.add(r);
            }
        }
        return filtrate;
    }

    /**
     * Sovrascrive il file CSV con la lista di recensioni fornita.
     *
     * @param recensioni lista di recensioni da salvare
     */

    public static void riscriviRecensioni(ArrayList<Recensione> recensioni) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/dati/recensioni.csv"))) {
            for (Recensione r : recensioni) {
                writer.write(r.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Errore nella riscrittura delle recensioni: " + e.getMessage());
        }
    }

    /**
     * Restituisce una rappresentazione leggibile della recensione
     * per la visualizzazione lato cliente.
     *
     * @return stringa con dettagli della recensione e risposta (se presente)
     */

    // Visualizzazione per cliente
      private String visualizzaPerCliente() {
        String base = "Ristorante: " + nomeRistorante +
                      "\nVoto: " + stelle + " stelle" +
                      "\nRecensione: \"" + testoRecensione + "\"" +
                      "\nData: " + data;
        if (risposta != null && !risposta.isEmpty()) {
            base += "\nRisposta del ristoratore: \"" + risposta + "\"";
        }
        return base;
    }

    /**
     * Override di <code>toString()</code> per restituire la visualizzazione lato cliente.
     * @return stringa rappresentativa della recensione
     */

    @Override
    public String toString() {
        return visualizzaPerCliente();
    }
}




