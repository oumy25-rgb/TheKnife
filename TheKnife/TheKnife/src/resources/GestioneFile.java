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
import theknife.Preferiti;
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

    public String[] dividereCsv(String linea) {
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
    
	 /**
     * Verifica se esiste un utente con uno specifico username nel file.
     *
     * @param nomeFile file CSV degli utenti
     * @param username username da cercare
     * @return true se esiste, false altrimenti
     */
	
    //il metodo per controllare se l'utente esiste o no 
    public boolean utenteEsiste(String nomeFile, String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/dati/" + nomeFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] parti = dividereCsv(linea);
                // Assumendo che l'username sia alla posizione 3 (index 3)
                if (parti.length > 3 && parti[3].equalsIgnoreCase(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }
        return false;
    }
    
    // Metodo per la scrittura su file
	 /**
     * Scrive un insieme di righe su file.
     *
     * @param nomeFile percorso del file
     * @param dati dati da scrivere
     * @param method true per appendere, false per sovrascrivere
     */
    public void scriviSuFile(String nomeFile, ArrayList<String> dati, boolean method) {

        if (dati == null || dati.isEmpty()) {
            System.err.println("Nessun dato da scrivere sul file.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile,method))) {
            for (String linea : dati) {
                writer.write(linea);
                writer.newLine(); // Aggiungi una nuova riga dopo ogni scrittura
            }
            System.out.println("Scrittura completata con successo.");
        } catch (IOException e) {
            System.err.println("Si è verificato un errore durante la scrittura: " + e.getMessage());
        }
    }

    // Metodo per la lettura da un file che va bene per leggere tutte le recensioni
	 /**
     * Legge tutte le righe da un file di testo.
     *
     * @param nomeFile percorso del file
     * @return lista di righe lette
     */
    public ArrayList<String> leggiDaFile(String nomeFile) {
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
     * Cerca una stringa all’interno di un file CSV e restituisce la riga trovata.
     *
     * @param nomeFile percorso del file
     * @param ricerca valore da cercare
     * @return array con i campi della riga, oppure null se non trovato
     */
	
    public String[] cercaNelFile(String nomeFile, String ricerca) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] arr=dividereCsv(linea);// Passo la linea al metodo dividiCSV
                for(String el:arr){
                    if(el.equalsIgnoreCase(ricerca)){
                        return arr; //se uno degli elementi dell'array corrisponde a ciò che cerco ritorno l'array
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Si è verificato un errore durante la lettura: " + e.getMessage());
        }
        return null; // Se non c'è corrispondenza ritorno null
    }
 /** Scrive un ristorante su file CSV. */
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

    /** Scrive un preferito (utente, ristorante) su file CSV. */
    public void scriviPreferiti(String nomeFile, Preferiti f) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("src/dati/" + nomeFile, true);
        } catch (IOException ex) {
            System.err.println("Errore in apertura del file " + ex);
        }
        BufferedWriter writeF = new BufferedWriter(fw);
        try {
            //Cambiare i dati da scrivere
            writeF.write(f.getNomeUtente() + ",");
            writeF.write(f.getNomeRistorante() + "\n");
        } catch (IOException ex) {
            System.err.println("Errore in fase di scrittura: " + ex);
        }
        try {
            writeF.flush();
        } catch (IOException ex) {
            System.err.println("Errore durante lo svuotamento del buffer " + ex);
        }
    }
    
        public void scriviPiatto(String nomeFile, Piatto piatto) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("src/dati/" + nomeFile, true);
        } catch (IOException ex) {
            System.err.println("Errore in apertura del file " + ex);
        }
        BufferedWriter writeF = new BufferedWriter(fw);
        try {
            //Cambiare i dati da scrivere
            writeF.write(piatto + "\n");
        } catch (IOException ex) {
            System.err.println("Errore in fase di scrittura: " + ex);
        }
        try {
            writeF.flush();
        } catch (IOException ex) {
            System.err.println("Errore durante lo svuotamento del buffer " + ex);
        }
    }
    
        public void scriviUtente(String nomeFile, Utente u) {
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
        
        public void scriviRecensione(String nomeFile, Recensione r) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("src/dati/" + nomeFile, true);
        } catch (IOException ex) {
            System.err.println("Errore in apertura del file " + ex);
        }
        BufferedWriter writeF = new BufferedWriter(fw);
        try {
            //Cambiare i dati da scrivere
            writeF.write(r.getRistorante()+ ",");
            writeF.write(r.getCliente() + ",");
            writeF.write(r.getData() + ",");
            writeF.write(r.getTestoRecensione() + ",");
            writeF.write(r.getStelle() + "\n");
        } catch (IOException ex) {
            System.err.println("Errore in fase di scrittura: " + ex);
        }
        try {
            writeF.flush();
        } catch (IOException ex) {
            System.err.println("Errore durante lo svuotamento del buffer " + ex);
        }
    }
        
	/** Salva l’associazione tra un proprietario (codice fiscale) e il suo ristorante. */
    public void salvaAssociazioneProprietarioRistorante(String nomeFile, String proprietario, Ristorante ristorante) {
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
     /** Salva la segnalazione di un nuovo ristorante proposto da un utente. */
     public void salvaSegnalazioneRistorante(String nomeFile, String nome, String indirizzo, String locazione, String cucina) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile, true))) {
            String riga = String.format("%s,%s,%s,%s", nome, indirizzo, locazione, cucina);
            writer.write(riga);
            writer.newLine();
            System.out.println("Segnalazione ristorante salvata con successo.");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio della segnalazione: " + e.getMessage());
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

/** Trova il nome del ristorante dato il codice fiscale del proprietario. */
public static String cercaRistoranteDaProprietario(String filePath, String codFiscale) {
        GestioneFile gf = new GestioneFile();
        ArrayList<String> righe = gf.leggiDaFile(filePath);
        for (String riga : righe) {
            String[] tokens = gf.dividereCsv(riga);
            if (tokens.length >= 2 && tokens[0].equalsIgnoreCase(codFiscale)) {
                return tokens[1];
            }
        }
        return null;
    }
    /** Trova il nome di un utente dato il suo codice fiscale. */
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
     /** Aggiunge un’associazione tra codice fiscale e nome del ristorante. */
    public static void aggiungiRistoranteProprietario(String filePath, String codFiscale, String nomeRistorante) {
    try (FileWriter writer = new FileWriter(filePath, true);
         BufferedWriter bw = new BufferedWriter(writer);
         PrintWriter out = new PrintWriter(bw)) {
        out.println(codFiscale + "," + nomeRistorante);
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
    }
}
    /** Rimuove un ristorante da un file di associazioni. */
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
    /*** Rimuove un ristorante direttamente dal file dei ristoranti. */
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
    /** Elimina un menu (file) dalla cartella dei menu. */           
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


