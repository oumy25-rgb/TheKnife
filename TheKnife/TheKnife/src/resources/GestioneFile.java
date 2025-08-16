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
 *
 * @author HEW4K7Z2EA
 */
public class GestioneFile {
    


    public String[] dividereCsv(String linea) {
        //System.out.println("Nel metodo ricevo la linea: "+linea);
        // Definisci il delimitatore
        String delimitatore = ","; // Nuovo delimitatore (?=([^\"]*\"[^\"]*\")[^\"]*$)
        
        //// Creo uno StringTokenizer
        //String[] colonne = linea.split(delimitatore, -1); // Utilizza split con il nuovo delimitatore
        // Rimuovo eventuali spazi
        //for (int i = 0; i < colonne.length; i++) {
       // colonne[i] = colonne[i].trim();
       // }

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
        
    // Metodo per salvare l'associazione tra proprietario e ristorante in PROPRIETARI.CSV
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

    public static void aggiungiRistoranteProprietario(String filePath, String codFiscale, String nomeRistorante) {
    try (FileWriter writer = new FileWriter(filePath, true);
         BufferedWriter bw = new BufferedWriter(writer);
         PrintWriter out = new PrintWriter(bw)) {
        out.println(codFiscale + "," + nomeRistorante);
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
    }
}

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

