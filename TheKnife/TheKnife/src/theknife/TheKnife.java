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

                        nuovoUtente = new Utente(nome, cognome, codFiscale, username, password, dataNascita, luogoDomicilio, ruolo);

                        // Se ruolo ristoratore far inserire i dati del ristorante
                        if (ruolo.equals("ristoratore")) {
                            System.out.println("Inserisci il nome del ristorante:");
                            String nomeRistorante = scanner.nextLine();
                            System.out.println("Inserisci l'indirizzo del ristorante");
                            String indirizzoRistorante = scanner.nextLine();
                            System.out.println("Inserisci il numero di telefono del ristorante:");
                            String telefonoRistorante = scanner.nextLine();
                            System.out.println("Inserisci la descrizione del ristorante:");
                            String descrizioneRistorante = scanner.nextLine();

                            Ristorante nuovoRistorante = new Ristorante(nomeRistorante, indirizzoRistorante, luogoDomicilio, "25.25", "napoletana", 157.25, 124.36, telefonoRistorante, "www.sgfag.com", "www.sgfag.com", "gambero rosso", true, "accesso disabili", descrizioneRistorante, new ArrayList<Recensione>());

                            gf.scriviRistorante("ristoranti.csv", nuovoRistorante);
                            Ristorante ristoranteAssociato = new Ristorante(nomeRistorante, "Via Roma 1", "Roma", "$$", "Italiana", 12.345678, 98.765432, "0123456789", "http://ristoranteitaliano.com", "http://ristoranteitaliano.com", "Miglior Ristorante 2023", true, "WiFi, Parcheggio", "Ristorante con piatti tipici italiani.", new ArrayList<>());

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
                        // Login utente
                        System.out.println("Inserisci username:");
                        String loginUsername = scanner.nextLine();
                        System.out.println("Inserisci password:");
                        String loginPassword = scanner.nextLine();

                        Utente utenteLoggato = gestioneUtenti.login(loginUsername, loginPassword);
                        if (utenteLoggato != null) {
                            System.out.println("Login effettuato con successo!");
                            if (utenteLoggato.getRuolo().equalsIgnoreCase("ristoratore")) {
                                System.out.println("Benvenuto proprietario del ristorante " + utenteLoggato.getRistorante().getName());
                                menuRistoratore(gestioneRistoranti, utenteLoggato, scanner);
                            } else {
                                menuCliente(utenteLoggato, scanner);
                            }
                        } else {
                            System.out.println("Credenziali errate.");
                        }
                        break;

                    case 3:
                        // Funzione di ricerca ristoranti
                        System.out.println("Inserisci il tipo di cucina (lascia vuoto per ignorare):");
                        String tipoCucina = scanner.nextLine();
                        System.out.println("Inserisci la locazione (città) (lascia vuoto per ignorare):");
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

    private static void menuRistoratore(GestioneRistoranti gestioneRistoranti, Utente ristoratore, Scanner scanner) {
        int scelta = 0;
        do {
            System.out.println("===== MENU RISTORATORE =====");
            System.out.println("1. Crea e gestisci il menu del ristorante");
            System.out.println("2-Aggiungi piatti al menu");
            System.out.println("2-Aggiungi promozione");
            System.out.println("4. Visualizza piatti del menu");
            System.out.println("5. Visualizza feedback dei clienti");
            //sono esempi di voci che può fare solo il ristoratore
            System.out.println("0-Esci");
            scelta = Integer.parseInt(scanner.nextLine());
            switch (scelta) {
                case 0:
                    System.out.println("Logging out.....");
                    break;
                case 1:
                    gestioneRistoranti.creaEMenuRistorante(ristoratore.getRistorante().getName());
                    break;
                case 2:
                    aggiungiPiatto(ristoratore);
                    break;
                case 3:
                    aggiungiPromozione(ristoratore);
                    break;
                case 4:
                    visualizzaMenu(ristoratore);
                    break;
                case 5:
                    visualizzaFeedback(ristoratore);
                    break;
            }

        } while (scelta != 0);
    }

    private static void aggiungiPiatto(Utente ristoratore) {
        System.out.println("Aggiungi nome del piatto:");
        Scanner scanner = new Scanner(System.in);
        String nomePiatto = scanner.nextLine();

        Ristorante ristorante = ristoratore.getRistorante();
        ristorante.aggiungiPiatto(nomePiatto);
        System.out.println("Piatto aggiunto: " + nomePiatto);
    }

    private static void aggiungiPromozione(Utente ristoratore) {

        System.out.println("Aggiungi il nome della promozione:");
        Scanner scanner = new Scanner(System.in);
        String nomePromozione = scanner.nextLine();

        Ristorante ristorante = ristoratore.getRistorante();
        ristorante.aggiungiPromozione(nomePromozione, ristoratore.getUsername());
        System.out.println("Promozione aggiunta: " + nomePromozione);

    }

    private static void visualizzaMenu(Utente ristoratore) {
        Ristorante ristorante = ristoratore.getRistorante();
        System.out.println("Ecco i piatti del tuo menu:");
        for (String piatto : ristorante.getMenu()) {
            System.out.println("- " + piatto);
        }
    }

    private static void visualizzaFeedback(Utente ristoratore) {
        Ristorante ristorante = ristoratore.getRistorante();
        ristorante.visualizzaRecensioni();

    }

    private static void menuCliente(Utente cliente, Scanner scanner) {
        int scelta = 0;

        System.out.println("\nBenvenuto nel menu Cliente, " + cliente.getNome() + "!\n");

        do {
            System.out.println("===== MENU CLIENTE =====");
            System.out.println("1. Visualizza i tuoi ristoranti preferiti");
            System.out.println("2. Aggiungi un ristorante ai preferiti");
            System.out.println("3. Rimuovi un ristorante dai preferiti");
            System.out.println("4. Visualizza le tue recensioni");
            System.out.println("5. Aggiungi una recensione a un ristorante");
            System.out.println("0. Logout");
            System.out.print("Seleziona un'opzione: ");

            try {
                scelta = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Per favore, inserisci un numero valido.\n");
                continue;
            }

            switch (scelta) {
                case 0:
                    System.out.println("Logout eseguito. A presto!");
                    break;

                case 1:
                    cliente.visualizzaPreferiti();
                    break;

                case 2:
                    System.out.print("Inserisci il nome del ristorante da aggiungere ai preferiti: ");
                    String nomeAdd = scanner.nextLine();
                    Ristorante rAdd = cercaRistorantePerNome(nomeAdd);
                    if (rAdd != null) {
                        cliente.aggiungiPreferito(rAdd);
                    } else {
                        System.out.println("Ristorante non trovato.\n");
                    }
                    break;

                case 3:
                    System.out.print("Inserisci il nome del ristorante da rimuovere dai preferiti: ");
                    String nomeRemove = scanner.nextLine();
                    Ristorante rRemove = cercaRistorantePerNome(nomeRemove);
                    if (rRemove != null) {
                        cliente.rimuoviPreferito(rRemove);
                    } else {
                        System.out.println("Ristorante non trovato.\n");
                    }
                    break;

                case 4:
                    // Visualizza le recensioni dell'utente
                    ArrayList<Recensione> recensioni = cliente.getRecensioni();
                    if (recensioni.isEmpty()) {
                        System.out.println("Non hai ancora scritto recensioni.\n");
                    } else {
                        System.out.println("Le tue recensioni:");
                        for (Recensione rec : recensioni) {
                            System.out.println("- \"" + rec.getTestoRecensione() + "\" (" + rec.getStelle() + " stelle) su " + rec.getRistorante());
                        }
                        System.out.println();
                    }
                    break;

                case 5:
                    // Aggiungi una recensione a un ristorante
                    System.out.print("Inserisci il nome del ristorante per aggiungere una recensione: ");
                    String nomeRec = scanner.nextLine();
                    Ristorante rRec = cercaRistorantePerNome(nomeRec);
                    if (rRec != null) {
                        System.out.print("Inserisci il testo della recensione: ");
                        String testo = scanner.nextLine();
                        int stelle = 0;
                        do {
                            System.out.print("Inserisci il voto in stelle (1-5): ");
                            try {
                                stelle = Integer.parseInt(scanner.nextLine());
                                if (stelle < 1 || stelle > 5) {
                                    System.out.println("Inserisci un numero tra 1 e 5.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Per favore inserisci un numero valido.");
                            }
                        } while (stelle < 1 || stelle > 5);

                        // Aggiungi la recensione
                        cliente.aggiungiRecensione(rRec, cliente.getCodFiscale(), java.time.LocalDate.now().toString(), testo, stelle);
                        System.out.println("Recensione aggiunta con successo!\n");
                    } else {
                        System.out.println("Ristorante non trovato.\n");
                    }
                    break;

                default:
                    System.out.println("Scelta non valida. Riprova.\n");
            }

        } while (scelta != 0);
    }

    // Metodo di ricerca già definito
    private static Ristorante cercaRistorantePerNome(String nome) {
        GestioneFile gf = new GestioneFile();
        ArrayList<String> datiRistoranti = gf.leggiDaFile("src/dati/ristoranti.csv");
        for (String line : datiRistoranti) {
            String[] tokens = line.split(",");
            if (tokens.length > 0 && tokens[0].equalsIgnoreCase(nome)) {
                return new Ristorante(tokens[0], tokens.length > 1 ? tokens[1] : "", "", "", "", 0, 0, "", "", "", "", false, "", "", new ArrayList<>());
            }
        }
        return null;
    }

}
