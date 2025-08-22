
package theknife;

import java.io.FileReader;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import resources.GestioneFile;
import resources.GestioneMenu;
import resources.Piatto;
/*
 * La classe <strong>Ristorante</strong> rappresenta un ristorante registrato
 * nella piattaforma <em>TheKnife</em>.
 * <p>
 * Ogni ristorante contiene informazioni anagrafiche (nome, indirizzo, città, nazione),
 * caratteristiche (tipo di cucina, fascia di prezzo, coordinate geografiche),
 * servizi disponibili (delivery e prenotazione online) e dati dinamici
 * come il menu, le promozioni e le recensioni degli utenti.
 * <p>
 * I metodi offrono funzionalità di consultazione, gestione del menu,
 * calcolo della valutazione media e riepilogo delle recensioni.
 *
 * @author omema gharsellaoui
	@author Giuseppina salvati
 */

public class Ristorante {
    //Name,Address,city,nation,price,Cuisine,Longitude,Latitude,delivery,reservation,stars
//modificare gli attributi e i metodi
    
    private String name;
    private String address;
    private String city;
    private String nation;
    private String price;
    private String cuisine;
    private double longitude;
    private double latitude;
    private boolean delivery; //possibilità di delivery
    private boolean reservation; //possibilità di prenotazione online
   
    
    private ArrayList<Recensione> recensioni; // Lista delle recensioni
    // Nuovi attributi per gestire menu e promozioni
    private ArrayList<String> promozioni; // Lista delle promozioni
    private ArrayList<Piatto> menu; // Lista di piatti
    /*Costruttore principale della classe <code>Ristorante</code>.*/
	/** @param name nome del ristorante
     * @param address indirizzo del ristorante
     * @param city città del ristorante
     * @param price fascia di prezzo
     * @param nation nazione in cui si trova
     * @param cuisine tipologia di cucina
     * @param longitude coordinata longitudinale
     * @param latitude coordinata latitudinale
     * @param delivery disponibilità del servizio delivery
     * @param reservation disponibilità del servizio di prenotazione
     * @param recensioni lista di recensioni iniziali*/

    public Ristorante(String name, String address, String city, String price,String nation, String cuisine, double longitude, double latitude, boolean delivery,boolean reservation,ArrayList<Recensione> recensioni) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.price = price;
        this.cuisine = cuisine;
        this.longitude = longitude;
        this.latitude = latitude;
        this.delivery = delivery;
        this.reservation = reservation;
        this.nation = nation;
        this.recensioni = recensioni;
        this.promozioni = new ArrayList<>(); // Inizializza l'ArrayList per le promozioni   
        this.menu = new ArrayList<>();

    }

	/**
     * Costruttore alternativo che inizializza il menu del ristorante da un gestore file.
     *
     * @param gf gestore file (non usato direttamente in questo costruttore)
     * @param menu lista dei piatti che compongono il menu
     */

    public Ristorante(GestioneFile gf, ArrayList<Piatto> menu) {
        this.menu = menu;
    }
       
    public String getNation() {
    	return nation;
    }
    
    public void setNation(String nation) {
    	this.nation=nation;
    }
    
    public boolean getDelivery() {
    	return delivery;
    }
    
    public boolean getReservation() {
    	return reservation;
    }
    
    public void setDelivery(boolean delivery) {
    	this.delivery = delivery;
    }

    public void setReservation(boolean reservation) {
    	this.reservation = reservation;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setLocation(String location) {
        this.city = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public ArrayList<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(ArrayList<Recensione> recensioni) {
        this.recensioni = recensioni;
    }

    public ArrayList<Piatto> getMenu() {
        return menu;
    }
    
    public ArrayList<String> getPromozioni() {
        return promozioni;
    }

    public void setPromozioni(ArrayList<String> promozioni) {
        this.promozioni = promozioni;
    }
	
    /**
     * Calcola la media delle stelle ricevute dalle recensioni.
     *
     * @return media delle stelle, 0.0 se non ci sono recensioni
     */
    
     public double getMediaStelle() {
        if (recensioni.isEmpty()||recensioni == null ) return 0.0;
        double somma = 0.0;
        for (Recensione r : recensioni) {
            somma += r.getStelle();
        }
        return somma / recensioni.size();
    }
      /**
     * Visualizza a terminale le informazioni anagrafiche e i servizi del ristorante.
     */
    public void visualizzaRistorante() {
	    System.out.println("Nome Ristorante: " + name);
	    System.out.println("Indirizzo: " + address);
	    System.out.println("Città: " + city);
	    System.out.println("Nazione: " + nation);
	    System.out.println("Prezzo: " + price);
	    System.out.println("Cucina: " + cuisine);
	    System.out.println("Coordinate: Latitudine " + latitude + ", Longitudine " + longitude);
	    System.out.println("Servizio Delivery: " + delivery);
	    System.out.println("Prenotazione Online: " + reservation);
    
    }

	 /**
     * Mostra a terminale tutte le recensioni relative a un ristorante.
     *
     * @param ristorante nome del ristorante
     */

    public static void visualizzaRecensioni(String ristorante) {
    ArrayList<Recensione> recs = Recensione.cercaPerRistorante(ristorante);
    
    if (recs.isEmpty()) {
        System.out.println("Nessuna recensione presente per " + ristorante);
    } else {
        System.out.println("Recensioni per " + ristorante + ":");
        for (Recensione r : recs) {
            System.out.println("- " + r.getTestoRecensione() + " (" + r.getStelle() + " stelle)");
            // Nessuna stampa della risposta
        }
    }
}

	/**
     * Aggiunge una recensione alla lista del ristorante.
     *
     * @param recensione recensione da aggiungere
     */
    
    public void aggiungiRecensione(Recensione recensione) {
        recensioni.add(recensione);
    }

	
     /**
     * Mostra un riepilogo delle recensioni per un ristorante:
     * numero totale e media delle stelle.
     *
     * @param ristorante nome del ristorante
     */
    
    public static void visualizzaRiepilogo(String ristorante) {
    ArrayList<Recensione> recensioni = Recensione.cercaPerRistorante(ristorante);
    if (recensioni.isEmpty()) {
        System.out.println("Nessuna recensione disponibile per " + ristorante);
        return;
    }

    double sommaStelle = 0;
    for (Recensione recensione : recensioni) {
        sommaStelle += recensione.getStelle();
    }

    double mediaStelle = sommaStelle / recensioni.size();

    System.out.println("Riepilogo per " + ristorante + ":");
    System.out.println("Numero di recensioni: " + recensioni.size());
    System.out.println("Media stelle: " + mediaStelle);
}

     /**
     * Carica il menu del ristorante leggendo da file CSV dedicato.
     */
public void caricaMenuRistorante() {
    this.menu = new GestioneMenu().leggiMenu(name + "Menu.csv");
}

     /**
     * Rimuove un piatto dal menu e aggiorna il file CSV.
     *
     * @param nomePiatto nome del piatto da rimuovere
     */     
public void rimuoviPiatto(String nomePiatto) {
    boolean rimosso = false;
    for (int i = 0; i < menu.size(); i++) {
        if (menu.get(i).getNome().equalsIgnoreCase(nomePiatto)) {
            menu.remove(i);
            rimosso = true;
            break;
        }
    }

    if (rimosso) {
        new GestioneMenu().scriviMenu(name + "Menu.csv", menu);
        System.out.println("Piatto rimosso correttamente.");
    } else {
        System.out.println("Piatto non trovato nel menu.");
    }
}

	/**
     * Calcola la media delle stelle leggendo direttamente dal file CSV delle recensioni.
     *
     * @param nome nome del ristorante
     * @param citta città del ristorante
     * @param indirizzo indirizzo del ristorante
     * @return media delle stelle trovate, oppure 0.0 se nessuna recensione valida
     */
	
    public double calcoloMediaStelle(String nome, String citta, String indirizzo) {
    double somma = 0.0;
    int cont = 0;

    try (CSVReader reader = new CSVReader(new FileReader("src/dati/recensioni.csv"))) {
        String[] riga;
        while ((riga = reader.readNext()) != null) {
            if (riga.length > 6 && // Assicurati che ci siano abbastanza colonne
                nome.equalsIgnoreCase(riga[0]) && 
                indirizzo.equalsIgnoreCase(riga[2]) && 
                citta.equalsIgnoreCase(riga[3])) {
                
                try {
                    somma += Double.parseDouble(riga[4]); // Assicurati che il campo stelle sia corretto
                    cont++;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid star rating format: " + riga[4]);
                }
            }
        }
    } catch (Exception e) {
        System.err.println("Error reading reviews: " + e.getMessage());
    }

    return cont > 0 ? somma / cont : 0.0; // Evita divisione per zero
}

/**
     * Esporta le informazioni principali del ristorante in formato CSV.
     *
     * @return stringa CSV con i dati del ristorante
     */
	
    // In Ristorante.java
public String toCSV() {
    return String.join(",",
        this.name,
        this.address,
        this.city,
        this.nation,
        this.price,
        this.cuisine,
        String.valueOf(this.longitude),
        String.valueOf(this.latitude),
        String.valueOf(this.delivery),
        String.valueOf(this.reservation)
    );
}


     
} 


