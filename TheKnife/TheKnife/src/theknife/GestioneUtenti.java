/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theknife;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.opencsv.CSVReader;

import resources.GestioneFile;

/**
 *
 * @author HEW4K7Z2EA
 */
public class GestioneUtenti {
    private ArrayList<Utente> utenti;
    GestioneFile gf=new GestioneFile();
    StringTokenizer st;
    public GestioneUtenti() {
        utenti = new ArrayList<>();
    }

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

    
    public Utente login(String username, String password) {
    ArrayList<String> dati = gf.leggiDaFile("src/dati/utente.csv");

    for (String s : dati) {
        // Controlla se la riga è vuota
        if (s.trim().isEmpty()) {
            continue; // Salta le righe vuote
        }

        StringTokenizer st = new StringTokenizer(s, ",");
        
        // Assicurati che ci siano abbastanza token
        if (st.countTokens() < 8) { // Assumendo che ci siano 8 campi
            continue; // Salta questa riga se i dati sono incompleti
        }

        // Crea un nuovo oggetto Utente
        Utente u = new Utente(st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken()) {};

        // Controlla le credenziali
        if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
            return u; // Login riuscito
        }
    }
    return null; // Login fallito
}

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
    
    public static boolean formatoValido(String s,String regex) {
       
        boolean controllo = true;
        s = s.toUpperCase().trim();
       
    	   if(!s.matches(regex)) {
        	   System.out.println("Formato non valido, riprova.");
        	   controllo = false;
    	   }
    	   
       return controllo;
    }
    
    public static boolean campoNonVuoto(String s) {
    	
    	if(s.isEmpty() || s == null) {
    		System.out.println("Non puoi lasciare il campo vuoto, riprova.");
    		return false;
    	}
    	
    	return true; // non è vuoto
    }
    
    public static boolean isLongitudineValida(String longi) {
        try {
            double val = Double.parseDouble(longi.replace(",", ".").trim());
            return val >= -180 && val <= 180;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLatitudineValida(String lati) {
        try {
            double val = Double.parseDouble(lati.replace(",", ".").trim());
            return val >= -90 && val <= 90;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
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
