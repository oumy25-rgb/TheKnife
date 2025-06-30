/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theknife;

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

    public void rimuoviPreferito(Ristorante ristorante) {
        if (preferiti.remove(ristorante)) {
            System.out.println(ristorante.getName() + " è stato rimosso dai preferiti.");
        } else {
            System.out.println(ristorante.getName() + " non è presente nei tuoi preferiti.");
        }
    }

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

    public void aggiungiRecensione(Ristorante ristorante, String codFiscale,String data, String testoRecensione,int stelle) {
        Recensione nuovaRecensione = new Recensione(ristorante.getName(),codFiscale,data,testoRecensione,stelle);
        ristorante.aggiungiRecensione(nuovaRecensione);
        recensioni.add(nuovaRecensione);
        System.out.println("Recensione aggiunta per " + ristorante.getName());
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

    private static void menuUtente(Utente utente, Scanner scanner) {
    int scelta = 0;

    do {
        System.out.println("\n===== MENU UTENTE =====");
        System.out.println("1. Visualizza elenco ristoranti preferiti");
        System.out.println("2. Modifica dati profilo");
        System.out.println("3. Consiglia un nuovo ristorante");
        System.out.println("0. Logout");
        System.out.print("Scegli un'opzione: ");

        try {
            scelta = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Inserisci un numero valido.");
            continue;
        }

        switch (scelta) {
            case 0:
                System.out.println("Logout effettuato.");
                break;
            case 1:
                utente.visualizzaPreferiti();
                break;
            case 2:
                modificaProfilo(utente, scanner);
                break;
            case 3:
                consigliaRistorante(scanner);
                break;
            default:
                System.out.println("Scelta non valida. Riprova.");
        }

    } while (scelta != 0);
}

private static void modificaProfilo(Utente utente, Scanner scanner) {
    System.out.println("Modifica dati profilo (lascia vuoto per non modificare):");

    System.out.print("Nuovo nome [" + utente.getNome() + "]: ");
    String nome = scanner.nextLine();
    if (!nome.isEmpty()) {
        utente.setNome(nome);
        System.out.println("Nome aggiornato.");
    }

    System.out.print("Nuovo cognome [" + utente.getCognome() + "]: ");
    String cognome = scanner.nextLine();
    if (!cognome.isEmpty()) {
        utente.setCognome(cognome);
        System.out.println("Cognome aggiornato.");
    }

    System.out.print("Nuovo luogo di domicilio [" + utente.getLuogoDomicilio() + "]: ");
    String domicilio = scanner.nextLine();
    if (!domicilio.isEmpty()) {
        utente.setLuogoDomicilio(domicilio);
        System.out.println("Luogo di domicilio aggiornato.");
    }
}

private static void consigliaRistorante(Scanner scanner) {
    System.out.println("\n===== Consiglia un nuovo ristorante =====");
    System.out.print("Nome ristorante: ");
    String nome = scanner.nextLine();
    System.out.print("Indirizzo: ");
    String indirizzo = scanner.nextLine();
    System.out.print("Città/Località: ");
    String locazione = scanner.nextLine();
    System.out.print("Tipo di cucina: ");
    String cucina = scanner.nextLine();
    System.out.print("Numero di telefono: ");
    String telefono = scanner.nextLine();
    
    GestioneFile gf = new GestioneFile();
    gf.salvaSegnalazioneRistorante("src/dati/ristoranti.csv", nome, indirizzo, locazione, cucina, telefono);
   
    
    
}   
    
    
    
}
