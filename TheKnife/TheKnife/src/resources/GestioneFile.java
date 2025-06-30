/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

    public void scriviRistorante(String nomeFile, Ristorante r) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("src/dati/" + nomeFile, true);
        } catch (IOException ex) {
            System.err.println("Errore in apertura del file " + ex);
        }
        BufferedWriter writeF = new BufferedWriter(fw);
        try {
            //Cambiare i dati da scrivere
            writeF.write(r.getName() + ",");
            writeF.write(r.getAddress() + ",");
            writeF.write(r.getLocation() + ",");
            writeF.write(r.getPrice() + ",");
            writeF.write(r.getCuisine() + ",");
            writeF.write(r.getLongitude() + ",");
            writeF.write(r.getLatitude() + ",");
            writeF.write(r.getPhoneNumber() + ",");
            writeF.write(r.getUrl() + ",");
            writeF.write(r.getWebsiteUrl() + ",");
            writeF.write(r.getAward() + ",");
            writeF.write(r.isGreenStar() ? "Si" : "No" + ",");
            writeF.write(r.getFacilitiesAndServices() + ",");
            writeF.write(r.getDescription() + "\n");
        } catch (IOException ex) {
            System.err.println("Errore in fase di scrittura: " + ex);
        }
        try {
            writeF.flush();
        } catch (IOException ex) {
            System.err.println("Errore durante lo svuotamento del buffer " + ex);
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
    
        public void scriviPiatto(String nomeFile, String piatto) {
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
        
     private static void salvaRistoranteSuFile(String nomeFile, String nome, String indirizzo, String locazione, String cucina, String telefono) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile, true))) {
        String riga = String.format("%s,%s,%s,%s,%s", nome, indirizzo, locazione, cucina, telefono);
        writer.write(riga);
        writer.newLine();
        System.out.println("Segnalazione salvata correttamente.");
    } catch (IOException e) {
        System.out.println("Errore durante il salvataggio della segnalazione: " + e.getMessage());
    }    
    
    }
    
      public void salvaSegnalazioneRistorante(String nomeFile, String nome, String indirizzo, String locazione, String cucina, String telefono) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile, true))) {
            String riga = String.format("%s,%s,%s,%s,%s", nome, indirizzo, locazione, cucina, telefono);
            writer.write(riga);
            writer.newLine();
            System.out.println("Segnalazione ristorante salvata con successo.");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio della segnalazione: " + e.getMessage());
        }
    }
               
        
}
