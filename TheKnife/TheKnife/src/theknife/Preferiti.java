
package theknife;

/**
 * La classe <strong>Preferiti</strong> rappresenta l'associazione
 * tra un utente registrato e un ristorante che egli ha salvato
 * nella propria lista dei preferiti sulla piattaforma <em>TheKnife</em>.
 * <p>
 * È una struttura dati semplice (DTO - Data Transfer Object)
 * utilizzata per memorizzare coppie <code>(nomeUtente, nomeRistorante)</code>.
 *</p>
 * @author omema gharsellaoui
 * @author Giuseppina Salvati
 */

public class Preferiti {
    /** Nome utente proprietario della lista dei preferiti. */
    String nomeUtente;
    /** Nome del ristorante salvato tra i preferiti dall'utente. */
    String nomeRistorante;
    /**
     * Costruttore della classe <code>Preferiti</code>.
     *
     * @param nomeUtente username del cliente
     * @param nomeRistorante nome del ristorante salvato nei preferiti
     */
    public Preferiti(String nomeUtente, String nomeRistorante) {
        this.nomeUtente = nomeUtente;
        this.nomeRistorante = nomeRistorante;
    }
     /**
     * Restituisce il nome dell'utente.
     *
     * @return username del cliente
     */
    public String getNomeUtente() {
        return nomeUtente;
    }
    /**
     * Imposta il nome dell'utente.
     *
     * @param nomeUtente nuovo username
     */
    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }
    /**
     * Restituisce il nome del ristorante.
     *
     * @return nome del ristorante nei preferiti
     */
    public String getNomeRistorante() {
        return nomeRistorante;
    }
     /**
     * Imposta il nome del ristorante.
     *
     * @param nomeRistorante nuovo nome del ristorante
     */
    public void setNomeRistorante(String nomeRistorante) {
        this.nomeRistorante = nomeRistorante;
    }
    
    
}

