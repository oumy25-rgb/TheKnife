
package resources;
/**
 * La classe <strong>Piatto</strong> rappresenta un singolo piatto
 * all’interno del menu di un ristorante.
 * <p>
 * Ogni piatto è caratterizzato da:
 * <ul>
 *   <li>Nome</li>
 *   <li>Descrizione</li>
 *   <li>Prezzo</li>
 * </ul>
 * </p>
 * 
 * Fornisce metodi di accesso, modifica e conversione in stringa
 * sia per la visualizzazione che per la memorizzazione in formato CSV.
 * 
 * @author omema gharsellaoui
 * @author Giuseppina Salvati
 */

public class Piatto {
    private String nome;
    private String descrizione;
    private double prezzo;
    /**
     * Costruttore completo.
     *
     * @param nome nome del piatto
     * @param descrizione breve descrizione del piatto
     * @param prezzo prezzo del piatto in euro
     */
    public Piatto(String nome, String descrizione, double prezzo) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
    }
    /** @return nome del piatto */
    public String getNome() {
        return nome;
    }
    /** @return descrizione del piatto */
    public String getDescrizione() {
        return descrizione;
    }
    /** @return prezzo del piatto */
    public double getPrezzo() {
        return prezzo;
    }
    /** @param nome nuovo nome del piatto */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /** @param descrizione nuova descrizione del piatto */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    /** @param prezzo nuovo prezzo del piatto */
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
    
    
    /**
     * Restituisce una rappresentazione testuale del piatto,
     * adatta per la visualizzazione all’utente.
     *
     * @return stringa formattata con nome, descrizione e prezzo in euro
     */
    @Override
    public String toString() {
        return nome + " - " + descrizione + " (" + prezzo + "€)";
    }

      /**
     * Converte il piatto in una riga CSV.
     * <p>
     * Formato: <code>nome,descrizione,prezzo</code>
     * </p>
     *
     * @return rappresentazione del piatto in formato CSV
     */
    public String toCSV() {
        return nome + "," + descrizione + "," + prezzo;
    }
    
}

