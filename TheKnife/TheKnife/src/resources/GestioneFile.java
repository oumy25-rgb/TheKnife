/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import theknife.Recensione;
import theknife.Ristorante;
import theknife.Utente;

/**
 * La classe <strong>GestioneFile</strong> fornisce metodi di supporto per la
 * lettura e scrittura di dati su file CSV e di testo. 
 * <p>
 * Gestisce in particolare:
 * <ul>
 *   <li>Utenti</li>
 *   <li>Ristoranti</li>
 *   <li>Recensioni</li>
 *   <li>Preferiti</li>
 *   <li>Menu e piatti</li>
 *   <li>Associazioni tra ristoratori e ristoranti</li>
 * </ul>
 * Tutti i dati sono memorizzati in file all’interno della cartella <code>src/dati/</code>.
 * </p>
 * * @author omema gharsellaoui
 * @author Giuseppina Salvati
 */

public class GestioneFile {
    
     /**
     * Divide una riga CSV in colonne.
     *
     * @param linea riga CSV da dividere
     * @return array di stringhe con i valori della riga
     */

    public static String[] dividereCsv(String linea) {
        //System.out.println("Nel metodo ricevo la linea: "+linea);
        // Definisci il delimitatore
        String delimitatore = ","; // Nuovo delimitatore (?=([^\"]*\"[^\"]*\")[^\"]*$)
	
        // Crea uno StringTokenizer
        StringTokenizer tokenizer = new StringTokenizer(linea, delimitatore);

        // Crea un array per memorizzare i token
        String[] colonne = new String[tokenizer.countTokens()+1];
        int index = 0;

        // Estrai i token
        while (tokenizer.hasMoreTokens()) {
            String parte=tokenizer.nextToken();
            colonne[index++] = parte; // Aggiungi il token, rimuovendo eventuali spazi
        }

        return colonne; // Restituisce l'array di stringhe
    }
    

    // Metodo per la lettura da un file che va bene per leggere tutte le recensioni
	 /**
     * Legge tutte le righe da un file di testo.
     *
     * @param nomeFile percorso del file
     * @return lista di righe lette
     */
    public static ArrayList<String> leggiDaFile(String nomeFile) {
        ArrayList<String> dati = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                dati.add(linea); // Aggiungi ogni linea letta alla lista
            }
        } catch (IOException e) {
            //System.err.println("Si è verificato un errore durante la lettura: " + e.getMessage());
        }
        return dati; // Restituisci la lista di dati letti
    }

    /**
     * Scrive i dati di un ristorante su un file CSV.
     * <p>
     * Se il file non esiste, viene creato e viene scritta anche l'intestazione delle colonne.
     * Se il file esiste già, i dati vengono aggiunti in coda.
     * </p>
     *
     * @param filePath il percorso del file CSV su cui scrivere i dati
     * @param ristorante l'oggetto {@link Ristorante} contenente i dati del ristorante da scrivere
     *
     * @throws NullPointerException se {@code filePath} o {@code ristorante} sono null
     */
    
public static void scriviRistorante(String filePath, Ristorante ristorante) {
    try {
        // Verifica se il file esiste già
        File file = new File(filePath);
        boolean fileEsiste = file.exists();
        
        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            // Scrivi l'intestazione solo se il file è nuovo
            if (!fileEsiste) {
                out.println("Name,Address,City,Nation,Price,Cuisine,Longitude,Latitude,Delivery,Reservation");
            }
            
            // Scrivi i dati del ristorante
            out.println(ristorante.toCSV()); // Scrivi i dati del ristorante
            out.flush(); // Assicurati che i dati vengano scritti immediatamente
        }
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura del file: " + e.getMessage());
    }
}
    
/**
 * Scrive i dati di un utente su un file CSV.
 * <p>
 * I dati dell'utente vengono aggiunti in coda al file. 
 * Il metodo gestisce internamente eventuali errori di apertura o scrittura del file, 
 * stampando messaggi di errore sulla console.
 * </p>
 *
 * @param nomeFile il nome del file CSV all'interno della cartella "src/dati/" in cui scrivere i dati
 * @param u l'oggetto {@link Utente} contenente i dati dell'utente da scrivere
 */ 
        public static void scriviUtente(String nomeFile, Utente u) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("src/dati/" + nomeFile, true);
        } catch (IOException ex) {
            System.err.println("Errore in apertura del file " + ex);
        }
        BufferedWriter writeF = new BufferedWriter(fw);
        try {
            //Cambiare i dati da scrivere
            writeF.write(u.getNome()+ ",");
            writeF.write(u.getCognome() + ",");
            writeF.write(u.getCodFiscale() + ",");
            writeF.write(u.getUsername() + ",");
            writeF.write(u.getPassword() + ",");
            writeF.write(u.getDataNascita() + ",");
            writeF.write(u.getLuogoDomicilio() + ",");
            writeF.write(u.getRuolo() + "\n");
        } catch (IOException ex) {
            System.err.println("Errore in fase di scrittura: " + ex);
        }
        try {
            writeF.flush();
        } catch (IOException ex) {
            System.err.println("Errore durante lo svuotamento del buffer " + ex);
        }
    }
        
        
        /**
         * Salva l'associazione tra un proprietario e il suo ristorante su un file CSV.
         * <p>
         * L'associazione viene salvata nel formato "codiceFiscaleProprietario,NomeRistorante" 
         * e aggiunta in coda al file specificato nella cartella "src/dati/". 
         * Se il ristorante passato è nullo, il metodo stampa un messaggio di errore e non scrive nulla.
         * Le eccezioni durante l'apertura o la scrittura del file vengono gestite internamente 
         * e riportate sulla console.
         * </p>
         *
         * @param nomeFile il nome del file CSV in cui salvare l'associazione
         * @param proprietario il codice fiscale del proprietario del ristorante
         * @param ristorante l'oggetto {@link Ristorante} associato al proprietario; se nullo, non viene salvata alcuna associazione
         */
        
    public static void salvaAssociazioneProprietarioRistorante(String nomeFile, String proprietario, Ristorante ristorante) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("src/dati/" + nomeFile, true);
        } catch (IOException ex) {
            System.err.println("Errore in apertura del file " + ex);
        }
        BufferedWriter writeF = new BufferedWriter(fw);
        try {
            if (ristorante != null) {
                // Scrivi l'associazione nel formato CSV
                writeF.write(proprietario + "," + ristorante.getName() + "\n");
            } else {
                System.out.println("Errore: il ristorante è nullo. Impossibile salvare l'associazione.");
            }
        } catch (IOException ex) {
            System.err.println("Errore in fase di scrittura: " + ex);
        }
        try {
            writeF.flush();
        } catch (IOException ex) {
            System.err.println("Errore durante lo svuotamento del buffer " + ex);
        }
    }
     
 /**
     * Legge tutte le recensioni dal file delle recensioni.
     *
     * @return lista di oggetti {@link Recensione}
     */
  public static ArrayList<Recensione> leggiTutteLeRecensioni() {
    ArrayList<Recensione> recensioni = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader("src/dati/recensioni.csv"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // Usa split con limite per mantenere campi vuoti
            String[] tokens = line.split(",", -1);

            if (tokens.length >= 5) {
                String nomeRistorante = tokens[0];
                String codiceFiscale = tokens[1];
                String testoRecensione = tokens[2];
                double stelle = Double.parseDouble(tokens[3].replace(',', '.'));
                String data = tokens[4];

                // Campo risposta opzionale
                String risposta = (tokens.length >= 6 && tokens[5] != null && !tokens[5].trim().isEmpty())
                        ? tokens[5]
                        : null;

                recensioni.add(new Recensione(nomeRistorante, codiceFiscale, testoRecensione, stelle, data, risposta));
            }
        }
    } catch (Exception e) {
        System.err.println("Errore lettura recensioni: " + e.getMessage());
    }

    return recensioni;
}

  /**
   * Cerca il nome del ristorante associato a un proprietario, dato il suo codice fiscale.
   * <p>
   * Legge il file CSV specificato da {@code filePath}, dove ogni riga rappresenta un'associazione
   * nel formato "codiceFiscaleProprietario,NomeRistorante". 
   * Se trova una riga con codice fiscale corrispondente a {@code codFiscale}, 
   * restituisce il nome del ristorante associato.
   * </p>
   *
   * @param filePath il percorso del file CSV contenente le associazioni proprietario-ristorante
   * @param codFiscale il codice fiscale del proprietario da cercare
   * @return il nome del ristorante associato al proprietario se trovato; {@code null} se non trovato
   */
  
public static String cercaRistoranteDaProprietario(String filePath, String codFiscale) {
        ArrayList<String> righe = GestioneFile.leggiDaFile(filePath);
        for (String riga : righe) {
            String[] tokens = GestioneFile.dividereCsv(riga);
            if (tokens.length >= 2 && tokens[0].equalsIgnoreCase(codFiscale)) {
                return tokens[1];
            }
        }
        return null;
    }
  
/**
 * Trova il nome di un utente dato il suo codice fiscale leggendo un file CSV.
 * <p>
 * Il file CSV specificato da {@code filePath} deve avere ogni riga nel formato:
 * "Nome,Cognome,CodiceFiscale,...". Il metodo confronta il terzo campo (codice fiscale)
 * con il valore passato in {@code codFiscale}. Se trova una corrispondenza, restituisce il nome dell'utente.
 * </p>
 *
 * <p>
 * Se il codice fiscale non viene trovato, viene restituito lo stesso valore di {@code codFiscale}.
 * Eventuali errori di lettura del file vengono gestiti internamente stampando un messaggio sulla console.
 * </p>
 *
 * @param filePath il percorso del file CSV contenente i dati degli utenti
 * @param codFiscale il codice fiscale dell'utente da cercare
 * @return il nome dell'utente se trovato; altrimenti restituisce il codice fiscale passato
 */
    public static String getNomeDaCodFiscale(String filePath, String codFiscale) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens.length >= 3 && tokens[2].equalsIgnoreCase(codFiscale)) {
                return tokens[0]; // Restituisce il nome
            }
        }
    } catch (IOException e) {
        System.err.println("Errore nella lettura del file: " + e.getMessage());
    }
    return codFiscale; // Se non trovato ritorna comunque il codice fiscale
}
    /**
     * Aggiunge un'associazione tra il codice fiscale di un proprietario e il nome del suo ristorante
     * su un file CSV.
     * <p>
     * La nuova associazione viene aggiunta in coda al file specificato da {@code filePath} 
     * nel formato "codiceFiscale,NomeRistorante". Eventuali errori durante la scrittura
     * vengono gestiti internamente e stampati sulla console.
     * </p>
     *
     * @param filePath il percorso del file CSV in cui aggiungere l'associazione
     * @param codFiscale il codice fiscale del proprietario del ristorante
     * @param nomeRistorante il nome del ristorante da associare al proprietario
     */
    public static void aggiungiRistoranteProprietario(String filePath, String codFiscale, String nomeRistorante) {
    try (FileWriter writer = new FileWriter(filePath, true);
         BufferedWriter bw = new BufferedWriter(writer);
         PrintWriter out = new PrintWriter(bw)) {
        out.println(codFiscale + "," + nomeRistorante);
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
    }
}
    /**
     * Rimuove un'associazione tra un proprietario e un ristorante da un file CSV.
     * <p>
     * Legge tutte le righe del file specificato da {@code percorso} e rimuove la riga
     * che corrisponde esattamente all'associazione "{@code codFiscale},{@code nomeRistorante}".
     * Il file viene poi sovrascritto con le righe filtrate. 
     * Eventuali errori di I/O vengono gestiti internamente e stampati sulla console.
     * </p>
     *
     * @param percorso il percorso del file CSV contenente le associazioni
     * @param codFiscale il codice fiscale del proprietario del ristorante
     * @param nomeRistorante il nome del ristorante da rimuovere
     * @return {@code true} se l'operazione di rimozione e riscrittura del file è andata a buon fine, {@code false} in caso di errore
     */
    public static boolean rimuoviRistorante(String percorso, String codFiscale, String nomeRistorante) {
        try {
            File file = new File(percorso);
            List<String> righe = Files.readAllLines(file.toPath());

            List<String> righeFiltrate = new ArrayList<>();
            for (String linea : righe) {
                if (!linea.trim().equals(codFiscale + "," + nomeRistorante)) {
                    righeFiltrate.add(linea);
                }
            }

            // Sovrascrivo direttamente il file
            Files.write(file.toPath(), righeFiltrate);

        } catch (IOException e) {
            System.err.println("Errore durante la modifica del file: " + e.getMessage());
            return false;
        }
        return true;
    }
    /**
     * Rimuove un ristorante dal file CSV dei ristoranti.
     * <p>
     * Legge tutte le righe del file specificato da {@code percorso} e rimuove 
     * la riga corrispondente ai dati del ristorante passato tramite {@code ristorante.toCSV()}.
     * Il file viene poi sovrascritto con le righe filtrate. 
     * Eventuali errori di I/O vengono gestiti internamente e stampati sulla console.
     * </p>
     *
     * @param percorso il percorso del file CSV contenente i dati dei ristoranti
     * @param ristorante l'oggetto {@link Ristorante} da rimuovere dal file
     * @return {@code true} se l'operazione di rimozione e riscrittura del file è andata a buon fine, {@code false} in caso di errore
     */
    public static boolean rimuoviRistorante(String percorso, Ristorante ristorante) {
        try {
            File file = new File(percorso);
            List<String> righe = Files.readAllLines(file.toPath());

            List<String> righeFiltrate = new ArrayList<>();
            String rigaDaRimuovere = ristorante.toCSV();

            for (String linea : righe) {
                if (!linea.trim().equals(rigaDaRimuovere)) {
                    righeFiltrate.add(linea);
                }
            }

            // Sovrascrivo direttamente il file
            Files.write(file.toPath(), righeFiltrate);
            return true;

        } catch (IOException e) {
            System.err.println("Errore durante la modifica del file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina un menu (file) dalla cartella dei menu.
     * <p>
     * Controlla se la cartella specificata da {@code percorsoCartella} esiste e se è valida.
     * Se il file con nome {@code nomeMenu} esiste all'interno della cartella, tenta di eliminarlo.
     * Eventuali errori o impossibilità di eliminazione vengono stampati sulla console.
     * </p>
     *
     * @param percorsoCartella il percorso della cartella contenente i file dei menu
     * @param nomeMenu il nome del file del menu da eliminare
     * @return {@code true} se il file è stato eliminato correttamente o se il file non esiste, 
     *         {@code false} se la cartella non esiste o se non è stato possibile eliminare il file
     */      
    public static boolean eliminaMenu(String percorsoCartella, String nomeMenu) {
        File cartella = new File(percorsoCartella);
        if (!cartella.exists() || !cartella.isDirectory()) {
            System.err.println("La cartella specificata non esiste o non è valida.");
            return false;
        }

        File fileDaEliminare = new File(cartella, nomeMenu);
        
        if(fileDaEliminare.exists()) {
	
	        if (fileDaEliminare.delete()) {
	            return true;
	        } else {
	            System.err.println("Impossibile eliminare il menu " + nomeMenu);
	            return false;
	        }
        }
        
        return true;
    }     
}


