/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theknife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import resources.GestioneFile;

/**
 *
 * @author HEW4K7Z2EA
 */
public class Utente {
    private String nome;
    private String cognome;
    private String codFiscale;
    private String username;
    private String password; // Cifrata
    private String dataNascita; // Facoltativa
    private String luogoDomicilio;
    private String ruolo; // cliente/ristoratore
    private ArrayList<String> preferiti; // Lista dei ristoranti preferiti
    private ArrayList<Recensione> recensioni; // Lista delle recensioni scritte
    
    private Ristorante ristorante; // Ristorante associato all'utente (solo per ristoratori)
    GestioneFile gf=new GestioneFile();
    StringTokenizer st,st2;
    public Utente(String nome, String cognome,String codFiscale ,String username, String password, String dataNascita, String luogoDomicilio, String ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.codFiscale=codFiscale;
        this.username = username;
        this.password = password;
        this.dataNascita = dataNascita;
        this.luogoDomicilio = luogoDomicilio;
        this.ruolo = ruolo;
        this.preferiti = new ArrayList<>();
        this.recensioni = new ArrayList<>();
        this.ristorante=setRistorante();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Ristorante getRistorante() {
        return ristorante;
    }    
    
    public ArrayList<String> getPreferiti() {
        return preferiti;
    }
    
    
    
    public Ristorante setRistorante() {
        ArrayList<String> dati=gf.leggiDaFile("src/dati/proprietari.csv");
        Ristorante r=null;
        for (String s:dati) {
        st=new StringTokenizer(s,",");
        //public Utente(String nome, String cognome,String codFiscale ,String username, String password, String dataNascita, String luogoDomicilio, String ruolo)
        String proprietario=st.nextToken();
        String nomeR=st.nextToken();
        if (proprietario.equals(this.getCodFiscale())) {
                ArrayList<String> datiRistorante=gf.leggiDaFile("src/dati/ristoranti.csv");
                for (String t:datiRistorante) {
                    st2=new StringTokenizer(t,",");
                    if(nomeR.equalsIgnoreCase(st2.nextToken())){
                    //public Ristorante(String name, String address, String location, String price, String cuisine, double longitude, double latitude, String phoneNumber, String url, String websiteUrl, String award, boolean greenStar, String facilitiesAndServices, String description, ArrayList<Recensione> recensioni) {
                    System.out.println("Ho trovato il ristorante "+nomeR);    
                    r=new Ristorante(nomeR,st2.nextToken(),st2.nextToken(),st2.nextToken(),st2.nextToken(),Double.parseDouble(st2.nextToken()),Double.parseDouble(st2.nextToken()),st2.nextToken(),st2.nextToken(),st2.nextToken(),st2.nextToken(),new Boolean("TRUE"),st2.nextToken(),st2.nextToken(),null);
                
                    }
                }
            }
        }
        return r;
    }
    
    
     public void aggiungiPreferito(Ristorante ristorante) {
        if (!preferiti.contains(ristorante)) {
            GestioneFile gestioneFile = new GestioneFile();
            gestioneFile.scriviPreferiti("preferiti.csv", new Preferiti(codFiscale, ristorante.getName()));
            System.out.println(ristorante.getName() + " è stato aggiunto ai preferiti.");
        } else {
            System.out.println(ristorante.getName() + " è già nei tuoi preferiti.");
        }
    }

   /*public void rimuoviPreferito(Ristorante ristorante) {
        if (preferiti.remove(ristorante)) {
            System.out.println(ristorante.getName() + " è stato rimosso dai preferiti.");
        } else {
            System.out.println(ristorante.getName() + " non è presente nei tuoi preferiti.");
        }
}*/


    public void visualizzaPreferiti() {
        Boolean stampato=false;
        GestioneFile gf=new GestioneFile();
        preferiti=gf.leggiDaFile("src/dati/preferiti.csv");
        for(String p:preferiti){
            String[] arr=gf.dividereCsv(p);
            if(arr[0].equalsIgnoreCase(codFiscale)){
                System.out.println(arr[1]);
                stampato=true;
            }
        }
        if (!stampato) {
            System.out.println("Non hai ristoranti preferiti.");
        }
    }

   public void aggiungiRecensione(Ristorante ristorante, String codFiscale, String data, 
                             String testo, int stelle, String risposta) {
    Recensione rec = new Recensione(ristorante.getName(), codFiscale, data, testo, stelle, risposta);
    rec.scriviSuFile(); // Ora usa il metodo corretto
    System.out.println("Recensione per " + ristorante.getName() + " salvata!");
}


public void visualizzaRecensioniPerCodiceFiscale(String codiceFiscale) {
    // Ottieni le recensioni dal metodo esistente in Recensione
    ArrayList<Recensione> recensioni = Recensione.cercaPerCliente(codiceFiscale);
    
    if (recensioni.isEmpty()) {
        System.out.println("Non hai ancora scritto recensioni.");
    } else {
        System.out.println("--- LE TUE RECENSIONI (" + recensioni.size() + ") ---");
        for (Recensione r : recensioni) {
            System.out.println("Ristorante: " + r.getRistorante());
            System.out.println("Data: " + r.getData());
            System.out.println("Voto: " + r.getStelle() + " stelle");
            System.out.println("Testo: " + r.getTestoRecensione());
            if (r.getRisposta() != null) {
                System.out.println("Risposta: " + r.getRisposta());
            }
            System.out.println("-------------------");
        }
    }
}



   
   
public ArrayList<Recensione> getRecensioniPerCliente(String codFiscale) {
        ArrayList<Recensione> tutteRecensioni = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/dati/recensioni.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 5 && tokens[1].equalsIgnoreCase(codFiscale)) {
                    tutteRecensioni.add(new Recensione(tokens[0], tokens[1], tokens[2], 
                        tokens[3], Integer.parseInt(tokens[4]), tokens.length > 5 ? tokens[5] : null));
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }
        return tutteRecensioni;
    }   




    public void eliminaRecensione(Ristorante ristorante, Recensione recensione) {
        if (recensioni.remove(recensione)) {
            ristorante.getRecensioni().remove(recensione);
            System.out.println("Recensione eliminata per " + ristorante.getName());
        } else {
            System.out.println("Recensione non trovata.");
        }
    }
    
    public String getRuolo() {
        return ruolo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLuogoDomicilio() {
        return luogoDomicilio;
    }

    public void setLuogoDomicilio(String luogoDomicilio) {
        this.luogoDomicilio = luogoDomicilio;
    }

    public ArrayList<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(ArrayList<Recensione> recensioni) {
        this.recensioni = recensioni;
    }
    
     public void visualizzaPromozioni(GestioneRistoranti gestioneRistoranti) {
       if (preferiti.isEmpty()) {
           System.out.println("Non hai ristoranti preferiti.");
           return;
       }

       boolean promozioniTrovate = false; // Variabile per controllare se ci sono promozioni

       System.out.println("Promozioni dai tuoi ristoranti preferiti:");
       for (String nomeRistorante : preferiti) {
           System.out.println("Cercando ristorante: " + nomeRistorante); // Debug
           Ristorante ristorante = gestioneRistoranti.cercaRistorantePerNome(nomeRistorante);
           if (ristorante != null) {
               System.out.println("Ristorante trovato: " + ristorante.getName()); // Debug
               if (!ristorante.getPromozioni().isEmpty()) { // Assicurati che ci siano promozioni
                   ristorante.visualizzaPromozioni();
                   promozioniTrovate = true; // Imposta a true se ci sono promozioni
               }
           } else {
               System.out.println("Ristorante " + nomeRistorante + " non trovato."); // Debug
           }
       }

       // Se non sono state trovate promozioni, stampa un messaggio
       if (!promozioniTrovate) {
           System.out.println("Non ci sono promozioni disponibili dai tuoi ristoranti preferiti.");
       }
   }
   
     
public boolean rimuoviPreferito(String nomeRistorante) {
    for (int i = 0; i < preferiti.size(); i++) {
        String preferito = preferiti.get(i).trim(); // Rimuovi eventuali spazi
        if (preferito.equalsIgnoreCase(nomeRistorante.trim())) { // Confronto case-insensitive
            preferiti.remove(i);
            return true; // Ristorante rimosso con successo
        }
    }
    return false; // Ristorante non trovato nei preferiti
}



public void eliminaRecensione(Ristorante ristorante) {
    Recensione recensioneDaEliminare = null;
    for (Recensione rec : recensioni) {
        if (rec.getRistorante().equalsIgnoreCase(ristorante.getName())) {
            recensioneDaEliminare = rec;
            break;
        }
    }
    if (recensioneDaEliminare != null) {
        recensioni.remove(recensioneDaEliminare);
        ristorante.getRecensioni().remove(recensioneDaEliminare);
        System.out.println("Recensione eliminata per " + ristorante.getName());
    } else {
        System.out.println("Nessuna recensione trovata per " + ristorante.getName());
    }
}


public void modificaRecensione(Ristorante ristorante, String testoNuovo, int stelle) {
    for (Recensione rec : recensioni) {
        if (rec.getRistorante().equalsIgnoreCase(ristorante.getName())) {
            rec.setRecensione(testoNuovo); // Assicurati di avere anche il metodo setRecensione()
            rec.setStelle(stelle); // Ora questo metodo esiste
            System.out.println("Recensione modificata per " + ristorante.getName());
            return;
        }
    }
    System.out.println("Nessuna recensione trovata per " + ristorante.getName());
}


public void caricaRecensioni() {
    try (BufferedReader reader = new BufferedReader(new FileReader("src/dati/recensioni.csv"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens.length >= 5) {
                String ristorante = tokens[0];
                String cliente = tokens[1];
                String data = tokens[2];
                String testoRecensione = tokens[3];
                int stelle = Integer.parseInt(tokens[4]);
                String risposta = tokens.length > 5 ? tokens[5] : null;

                Recensione recensione = new Recensione(ristorante, cliente, data, testoRecensione, stelle, risposta);
                this.recensioni.add(recensione);
            }
        }
    } catch (IOException e) {
        System.out.println("Errore durante il caricamento delle recensioni: " + e.getMessage());
    }
}




    
    
}
