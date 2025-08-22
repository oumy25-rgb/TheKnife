
package theknife;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.opencsv.CSVReader;

import resources.GestioneFile;

/**
 * La classe <strong>GestioneUtenti</strong> fornisce i metodi
 * per gestire gli utenti della piattaforma <em>TheKnife</em>.
 * <p>
 * Le principali funzionalità includono:
 * <ul>
 *   <li>Registrazione di un nuovo utente</li>
 *   <li>Login con credenziali (username e password)</li>
 *   <li>Verifica della validità di campi e formati inseriti</li>
 *   <li>Cifratura della password</li>
 *   <li>Controllo dell’esistenza di codice fiscale o username già registrati</li>
 * </ul>
 * Gli utenti vengono memorizzati in un file CSV (<code>src/dati/utente.csv</code>).
 *
 * @author Giuseppina Salvati
 * @author omema Gharsellaoui
 */

public class GestioneUtenti {
    /** Lista di utenti registrati nella piattaforma. */
    private ArrayList<Utente> utenti;
    /** Gestore dei file per la lettura/scrittura da CSV. */
    GestioneFile gf=new GestioneFile();
    /** Tokenizer usato per suddividere i dati letti da file. */
    StringTokenizer st;
    /**
     * Costruttore della classe <code>GestioneUtenti</code>.
     * Inizializza la lista degli utenti vuota.
     */
    public GestioneUtenti() {
        utenti = new ArrayList<>();
    }
     /**
     * Registra un nuovo utente nella piattaforma, verificando che
     * l'username non sia già stato utilizzato.
     *
     * @param utente utente da registrare
     */
    public void registraUtente(Utente utente) {
        // Controllo se l'username è già in uso
        for (Utente u : utenti) {
            if (u.getUsername().equalsIgnoreCase(utente.getUsername())) {
                System.out.println("Username già in uso. Scegli un altro username.");
                return;
            }
        }
        utenti.add(utente);
        System.out.println("Registrazione avvenuta con successo!");
    }

    
    /**
     * Cifra una password utilizzando una semplice cifratura a scorrimento
     * con chiave fissa (+5 posizioni ASCII).
     *
     * @param input password in chiaro
     * @return password cifrata
     */
    public static String cifraPassword(String input) {
        int chiave = 5; // Chiave di cifratura fissa: ogni carattere verrà spostato di 5 posizioni
        String risultato = ""; // Stringa che conterrà il risultato finale

        // Ciclo per ogni carattere della stringa input
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i); // Prende il carattere corrente

            // Verifica se è un carattere stampabile (tra ' ' e '~', cioè ASCII 32–126)
            if (c >= 32 && c <= 126) {
                int range = 95; // Numero totale di caratteri stampabili

                // Applica lo spostamento ciclico
                char ch = (char)(((c - 32 + chiave) % range + range) % range + 32);

                risultato = risultato + ch; // Aggiunge il carattere cifrato alla stringa
            } else {
                risultato = risultato + c; // Se non è stampabile, lo lascia invariato
            }
        }

        return risultato; // Restituisce la stringa cifrata
    }

    /**
     * Verifica se un nominativo è valido:
     * non vuoto e composto solo da lettere o spazi.
     *
     * @param nominativo stringa da verificare
     * @return true se valido, false altrimenti
     */
    
    public static boolean nominativoValido(String nominativo) {
    	
    	boolean controllo = true;
    	
        if (GestioneUtenti.campoNonVuoto(nominativo)) {
        	
        	for (int i = 0; i < nominativo.length(); i++) {
                if (!Character.isLetter(nominativo.charAt(i)) && nominativo.charAt(i) != ' ') {
                	System.out.println("Non puoi inserire numeri o simboli, riprova.");
                    controllo = false;
                    break;
                }
        	}     
        }else {
        	System.out.println("Non puoi lasciare il campo vuoto, riprova.");
        	controllo = false;
        }
    
        
        return controllo; 
    }
     /**
     * Verifica se una stringa rispetta un determinato formato (regex).
     *
     * @param s stringa da verificare
     * @param regex espressione regolare da rispettare
     * @return true se valido, false altrimenti
     */
    public static boolean formatoValido(String s,String regex) {
       
        boolean controllo = true;
        s = s.toUpperCase().trim();
       
    	   if(!s.matches(regex)) {
        	   System.out.println("Formato non valido, riprova.");
        	   controllo = false;
    	   }
    	   
       return controllo;
    }

    /**
     * Verifica che una stringa non sia vuota o nulla.
     *
     * @param s stringa da controllare
     * @return true se non vuota, false altrimenti
     */
    
    public static boolean campoNonVuoto(String s) {
    	
    	if(s.isEmpty() || s == null) {
    		System.out.println("Non puoi lasciare il campo vuoto, riprova.");
    		return false;
    	}
    	
    	return true; // non è vuoto
    }
     /**
     * Controlla se una stringa rappresenta una longitudine valida (-180 ≤ x ≤ 180).
     *
     * @param longi stringa con il valore di longitudine
     * @return true se valida, false altrimenti
     */
    public static boolean isLongitudineValida(String longi) {
        try {
            double val = Double.parseDouble(longi.replace(",", ".").trim());
            return val >= -180 && val <= 180;
        } catch (NumberFormatException e) {
            return false;
        }
    }
     /**
     * Controlla se una stringa rappresenta una latitudine valida (-90 ≤ x ≤ 90).
     *
     * @param lati stringa con il valore di latitudine
     * @return true se valida, false altrimenti
     */
    
    public static boolean isLatitudineValida(String lati) {
        try {
            double val = Double.parseDouble(lati.replace(",", ".").trim());
            return val >= -90 && val <= 90;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * Verifica se un codice fiscale è già registrato nel file degli utenti.
     *
     * @param cf codice fiscale da controllare
     * @return true se esiste già, false altrimenti
     */
    public static boolean cfEsiste(String cf) {
    	
    	try (CSVReader reader = new CSVReader(new FileReader("src/dati/utente.csv"))) {
            String[] riga;
            while ((riga = reader.readNext()) != null) {
                
            	if(riga[2].equals(cf)) {
            		System.out.println("Questo codice fiscale è già presente!, riprova.");
            		return true; //true se esiste già
            	}
            }
        } catch (Exception e) {
            System.err.println("Error searching : " + e.getMessage());
        }
        return false;
    	
    }
    
     /**
     * Verifica se uno username è già registrato nel file degli utenti.
     *
     * @param user username da controllare
     * @return true se esiste già, false altrimenti
     */
public static boolean userEsiste(String user) {
    	
    	try (CSVReader reader = new CSVReader(new FileReader("src/dati/utente.csv"))) {
            String[] riga;
            while ((riga = reader.readNext()) != null) {
                
            	if(riga[3].equals(user)) {
            		System.out.println("Questo user è già presente!, riprova.");
            		return true; //true se esiste già
            	}
            }
        } catch (Exception e) {
            System.err.println("Error searching : " + e.getMessage());
        }
        return false;
    	
    }
}

