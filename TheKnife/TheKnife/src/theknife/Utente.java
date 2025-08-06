/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theknife;
/**
 *
 * @author HEW4K7Z2EA
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
    	
        if (Utente.campoNonVuoto(nominativo)) {
        	
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
        
       if (Utente.campoNonVuoto(s)) {
    	   if(!s.matches(regex)) {
        	   System.out.println("Formato non valido, riprova.");
        	   controllo = false;
           }
       }else
    	   controllo = false;
       
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
    
}




    
    
