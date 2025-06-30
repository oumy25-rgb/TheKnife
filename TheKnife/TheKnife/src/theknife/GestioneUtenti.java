/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theknife;

import java.util.ArrayList;
import java.util.StringTokenizer;
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
        ArrayList<String> dati=gf.leggiDaFile("src/dati/utente.csv");
        
        for (String s:dati) {
        st=new StringTokenizer(s,",");
        //public Utente(String nome, String cognome,String codFiscale ,String username, String password, String dataNascita, String luogoDomicilio, String ruolo)
        Utente u=new Utente(st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken());
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null; // Login fallito
    }
    
}
