/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package theknife;

import java.util.ArrayList;
import java.util.Scanner;
import resources.GestioneFile;

/**
 *
 * @author HEW4K7Z2EA
 */
public class TheKnife {

    /**
     * @param args the command line arguments
     */
    // Dichiarazione globale
    static GestioneFile gestioneFile = new GestioneFile();// Creazione dell'istanza di GestioneFile
    static GestioneRistoranti gestioneRistoranti = new GestioneRistoranti();

    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scanner = new Scanner(System.in);
        GestioneRistoranti gestioneRistoranti = new GestioneRistoranti();
        GestioneUtenti gestioneUtenti = new GestioneUtenti();
        GestioneFile gf = new GestioneFile();
        Utente nuovoUtente = null;
        // Menu iniziale
        while (true) {
            try {
                System.out.println("1. Registrati");
                System.out.println("2. Login");
                System.out.println("3. Cerca Ristoranti");
                System.out.println("4. Accedi come Guest");
                System.out.println("5. Esci");

                System.out.print("Scelta: "); // <-- QUI l'input sarà su una nuova riga

                // Gestione dell'input dell'utente
                int scelta = Integer.parseInt(scanner.nextLine());

                switch (scelta) {
                    case 1:
                    	boolean controllo = false;
                        // Registrazione utente
                        System.out.println("Inserisci il tuo nome:");
                        String nome = scanner.nextLine();

                        System.out.println("Inserisci il tuo cognome:");
                        String cognome = scanner.nextLine();

                        System.out.println("Inserisci il tuo codice fiscale:");
                        String codFiscale = scanner.nextLine();

                        System.out.println("Inserisci un username:");
                        String username = scanner.nextLine();

                        System.out.println("Inserisci una password:");
                        String password = scanner.nextLine(); // In un'applicazione reale, cifrare la password

                        System.out.println("Inserisci la tua data di nascita (opzionale):");
                        String dataNascita = scanner.nextLine();

                        System.out.println("Inserisci il tuo luogo di domicilio:");
                        String luogoDomicilio = scanner.nextLine();

                        System.out.println("Inserisci il tuo ruolo (cliente/ristoratore):");
                        String ruolo = scanner.nextLine();

                        nuovoUtente = new Utente(nome, cognome, codFiscale, username, password, dataNascita, luogoDomicilio, ruolo) {};

                        // Se ruolo ristoratore far inserire i dati del ristorante
                        if (ruolo.equals("ristoratore")) {
                        	
                            System.out.println("Inserisci il nome del ristorante:");
                            String nomeRistorante = scanner.nextLine();
                            
                            System.out.println("Inserisci l'indirizzo del ristorante");
                            String indirizzoRistorante = scanner.nextLine();
                
                            System.out.println("Inserisci città del ristorante");
                            String citta = scanner.nextLine();
                            
                            System.out.println("Inserisci nazione del ristorante");
                            String nazione = scanner.nextLine();
                            
                            System.out.println("Inserisci il tipo di cucina del ristorante:");
                            String tipoCucina = scanner.nextLine();
                            
                            System.out.println("Inserisci il prezzo medio del ristorante (es. €25.25):");
                            String prezzoRistorante = scanner.nextLine().replace("€", "").trim(); // Rimuovi simboli e spazi
                            
                            System.out.println("Inserisci opzione di servizio delivery (true/false) : ");
                            boolean delivery = scanner.nextBoolean();
                            
                            System.out.println("Inserisci opzione di prenotazione online (true/false) : ");
                            boolean prenotazione = scanner.nextBoolean();
                            
                           
                            int stars;
                            
                            do {
                            	controllo=false;
	                            System.out.println("Inserisci numero di stelle Michelin (da 1 a 3) : ");
	                            stars  = scanner.nextInt();
	                            
	                            if(stars<=3 && stars>=1)
	                            	controllo=true;
	                            else
	                            	System.out.println("Hai inserito un valore non valido, riprova.");
	                            
                            }while(!controllo );
                            
                            Ristorante nuovoRistorante = new Ristorante(nomeRistorante, indirizzoRistorante, luogoDomicilio, prezzoRistorante, nazione,  tipoCucina, 157.25, 124.36, delivery, prenotazione, stars, new ArrayList<Recensione>());

                            gf.scriviRistorante("ristoranti.csv", nuovoRistorante);
                            Ristorante ristoranteAssociato = new Ristorante(nomeRistorante, "Via Roma 1", "Roma", "$$", "italia","Italiana", 12.345678, 98.765432,true,true,2,new ArrayList<>());

                            // Salvataggio dell'associazione
                            gf.salvaAssociazioneProprietarioRistorante("proprietari.csv", nuovoUtente.getCodFiscale(), ristoranteAssociato);
                            gf.scriviPreferiti("preferiti.csv", new Preferiti(nuovoUtente.getCodFiscale(), nomeRistorante));
                        }

                        // Scrittura dell'utente su file
                        gf.scriviUtente("utente.csv", nuovoUtente);
                        gestioneUtenti.registraUtente(nuovoUtente);
                        System.out.println("Complimenti " + ruolo + "! Ti sei registrato con successo!");
                        break;

                    case 2:
                    
                        System.out.print("Username: ");
                        String user = scanner.nextLine();
                        System.out.print("Password: ");
                        String pass = scanner.nextLine();
                        GestioneRecensioni gestioneRecensioni = new GestioneRecensioni();

                        ArrayList<String> utenti = gf.leggiDaFile("src/dati/utente.csv");
                        Utente loggedUser = null;

                        for (String line : utenti) {
                            String[] dati = gf.dividereCsv(line);
                            if (dati.length >= 8 && dati[3].equals(user) && dati[4].equals(pass)) {
                                String ruoloUtente = dati[7];
                                if (ruoloUtente.equals("cliente")) {
                                    loggedUser = new Cliente(dati[0], dati[1], dati[2], dati[3], dati[4], dati[5], dati[6]);
                                } else if (ruoloUtente.equals("ristoratore")) {
                                    loggedUser = new Ristoratore(dati[0], dati[1], dati[2], dati[3], dati[4], dati[5], dati[6], null);
                                }
                                break;
                            }
                        }

                        if (loggedUser == null) {
                            System.out.println("Credenziali errate.");
                        } else if (loggedUser instanceof Cliente) {
                            ((Cliente) loggedUser).mostraMenu(gestioneRistoranti, gestioneRecensioni, scanner);
                        } else if (loggedUser instanceof Ristoratore) {
                            ((Ristoratore) loggedUser).caricaRistoranteAssociato(gestioneRistoranti);
                            ((Ristoratore) loggedUser).mostraMenu(gestioneRistoranti, gestioneRecensioni, scanner);
                        }
                        break;


                    case 3:
                        // Funzione di ricerca ristoranti
                        System.out.println("Inserisci il tipo di cucina (lascia vuoto per ignorare):");
                        String tipoCucina = scanner.nextLine();
                        System.out.println("Inserisci la città (lascia vuoto per ignorare):");
                        String locazione = scanner.nextLine();
                        double fasciaPrezzoMax = Double.MAX_VALUE;
                        double fasciaPrezzoMin = 0.0;
                        boolean delivery = false;
                        boolean prenotazioneOnline = false;
                        int stelleMin = 0;
                        System.out.println("Inserisci fascia di prezzo minima:");
                        try {
                            fasciaPrezzoMin = Double.parseDouble(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            fasciaPrezzoMin = 0.0;
                        }
                        System.out.println("Inserisci fascia di prezzo massima:");
                        try{
                        fasciaPrezzoMax = Double.parseDouble(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            fasciaPrezzoMax = Double.MAX_VALUE;
                        }
                        System.out.println("Richiedi servizio di delivery? (true/false):");
                        try{
                        delivery = Boolean.parseBoolean(scanner.nextLine());
                        } catch (Exception e) {
                            delivery=true;
                        }
                        System.out.println("Richiedi prenotazione online? (true/false):");
                        try{
                        prenotazioneOnline = Boolean.parseBoolean(scanner.nextLine());
                        } catch (Exception e) {
                            prenotazioneOnline=true;
                        }
                        System.out.println("Inserisci il numero minimo di stelle:");
                        try{
                        stelleMin = Integer.parseInt(scanner.nextLine());
                        } catch (Exception e) {
                            stelleMin=1;
                        }
                        ArrayList<Ristorante> risultati = gestioneRistoranti.cercaRistoranti(tipoCucina, locazione, fasciaPrezzoMin, fasciaPrezzoMax, delivery, prenotazioneOnline, stelleMin);
                        System.out.println("FINE RICERCA...");
                        if (risultati.isEmpty()) {
                            System.out.println("Nessun ristorante trovato con i criteri indicati.");
                        } else {
                            System.out.println("Ristoranti trovati:");
                            for (Ristorante r : risultati) {
                                System.out.println("- " + r.getName() + ", Cucina: " + r.getCuisine() + ", Prezzo: " + r.getPrice() + ", Stelle: " + String.format("%.2f", r.getMediaStelle()));
                            }
                        }
                        break;

                    case 4:
                        // Accesso come guest
                        System.out.println("Inserisci il nome di un luogo (lascia vuoto per ignorare):");
                        String luogo = scanner.nextLine();
                        if (!luogo.isEmpty()) {
                            System.out.println("Hai scelto di cercare ristoranti a: " + luogo);
                            ArrayList<Ristorante> risultatiGuest = gestioneRistoranti.cercaRistoranti("", luogo, 0.0, Double.MAX_VALUE, false, false, 0);
                            if (risultatiGuest.isEmpty()) {
                                System.out.println("Nessun ristorante trovato nella località: " + luogo);
                            } else {
                                System.out.println("Ristoranti trovati a " + luogo + ":");
                                for (Ristorante r : risultatiGuest) {
                                    System.out.printf("- %s | Cucina: %s | Prezzo: %s | Stelle: %.2f%n",
                                            r.getName(), r.getCuisine(), r.getPrice(), r.getMediaStelle());
                                }
                            }
                        } else {
                            System.out.println("Nessun luogo inserito.");
                        }
                        break;

                    case 5:
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Scelta non valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Errore: inserisci un numero valido." + e + " " +e.getMessage());
            } catch (Exception e) {
                System.out.println("Si è verificato un errore: " + e.getMessage());
            }
        }
    }

}
    

   