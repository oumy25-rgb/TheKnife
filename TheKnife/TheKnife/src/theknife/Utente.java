
package theknife;
/**
 * la classe astratta <strong>Utente</strong> rappresenta un utente del sistema TheKnife.
 * <p>
 * Ogni utente possiede dati anagrafici, credenziali e informazioni di contatto.
 * La classe viene estesa da specifiche tipologie di utenti, come Cliente o Ristoratore.
 * </p>
 * 
 * @author Giuseppina Salvati
 * @author Omema Gharsellaoui
 */
public abstract class Utente {
    private String nome;
    private String cognome;
    private String codFiscale;
    String username;
    private String password;
    private String dataNascita;
    private String luogoDomicilio;
    private String ruolo;

    
    /**
     * Costruisce un nuovo utente con tutte le informazioni fornite.
     * <p>
     * Se <code>dataNascita</code> è vuota o nulla, viene impostata a "N/A".
     * </p>
     * 
     * @param nome <code>nome</code> dell'utente
     * @param cognome <code>cognome</code> dell'utente
     * @param codFiscale <code>codFiscale</code> dell'utente
     * @param username <code>username</code> scelto dall'utente
     * @param password <code>password</code> dell'utente
     * @param dataNascita <code>dataNascita</code> dell'utente, opzionale
     * @param luogoDomicilio <code>luogoDomicilio</code> dell'utente
     * @param ruolo <code>ruolo</code> dell'utente
     */
    
    public Utente(String nome, String cognome, String codFiscale, String username, String password,
                  String dataNascita, String luogoDomicilio, String ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.codFiscale = codFiscale;
        this.username = username;
        this.password = password;
        this.dataNascita = (dataNascita != null && !dataNascita.trim().isEmpty()) ? dataNascita : "N/A"; // Imposta un valore di default
        this.luogoDomicilio = luogoDomicilio;
        this.ruolo = ruolo;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public String getLuogoDomicilio() {
        return luogoDomicilio;
    }

    public String getRuolo() {
        return ruolo;
    }
    
   
    
}




    
    
