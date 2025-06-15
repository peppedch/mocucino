package entity;

import dto.*; // Importiamo tutti i "DTO" che ci servono (sono oggetti che portano dati)
import org.junit.After; // Serve per i metodi che vengono eseguiti dopo ogni test
import org.junit.Before; // Serve per i metodi che vengono eseguiti prima di ogni test
import org.junit.Test; // Segna un metodo come un test da eseguire

import java.util.ArrayList; // Ci serve per creare liste
import java.util.List; // Rappresenta una lista generica

import static org.junit.Assert.*; // Importiamo tutti i metodi per fare le verifiche (come assertEquals, assertTrue, ecc.)

public class UtenteTest {

    // L'utente che useremo per i nostri test
    private Utente utente;
    // La raccolta di ricette predefinita dell'utente
    private Raccolta raccoltaDefault;

    // Questo metodo viene eseguito prima di ogni singolo test per preparare l'ambiente
    @Before
    public void setUp() throws Exception {
        // Creiamo una ricetta di base che verrà messa nella raccolta predefinita
        Ricetta ricettaDefault = new Ricetta("Ricetta Default", "Descrizione Default", 30, true);
        // Creiamo la raccolta di default, dandole un nome, una descrizione e la ricetta creata
        raccoltaDefault = new Raccolta("Default", "Raccolta predefinita", ricettaDefault);
        // Creiamo un nuovo utente di test con i dati specificati e la sua raccolta predefinita
        utente = new Utente("testUser", "Test", "User", "test@example.com", "password", raccoltaDefault);
    }

    // Questo metodo viene eseguito dopo ogni test per pulire l'ambiente e evitare problemi con i test successivi
    @After
    public void tearDown() throws Exception {
        // Resettiamo l'utente e la raccolta a null per "pulire"
        utente = null;
        raccoltaDefault = null;
    }

    // Test per calcolare le statistiche di un utente quando ha delle ricette
    @Test
    public void testGetStatisticheUtente_ConRicette() {
        // --- Preparazione dei dati per il test ---
        List<RicettaDTO> ricette = new ArrayList<>(); // Creiamo una lista vuota per le ricette

        // Dati per la prima ricetta
        List<String> tag1 = new ArrayList<>(); // Lista dei tag per la ricetta 1
        tag1.add("test1"); // Aggiungiamo un tag
        List<IngredienteDTO> ingredienti1 = new ArrayList<>(); // Lista degli ingredienti per la ricetta 1
        ingredienti1.add(new IngredienteDTO("Ingrediente 1", "100", "g")); // Aggiungiamo un ingrediente
        // Creiamo la prima ricetta con titolo, descrizione, tempo, ingredienti e tag
        RicettaDTO ricetta1 = new RicettaDTO("Ricetta 1", "Descrizione della prima ricetta", 30, ingredienti1, tag1);
        ricetta1.setNumeroLike(10); // Le diamo 10 "mi piace"
        ricetta1.setNumCommenti(5); // E 5 commenti

        // Dati per la seconda ricetta
        List<String> tag2 = new ArrayList<>(); // Lista dei tag per la ricetta 2
        tag2.add("test2"); // Aggiungiamo un tag
        List<IngredienteDTO> ingredienti2 = new ArrayList<>(); // Lista degli ingredienti per la ricetta 2
        ingredienti2.add(new IngredienteDTO("Ingrediente 2", "200", "g")); // Aggiungiamo un ingrediente
        // Creiamo la seconda ricetta
        RicettaDTO ricetta2 = new RicettaDTO("Ricetta 2", "Descrizione della seconda ricetta", 45, ingredienti2, tag2);
        ricetta2.setNumeroLike(15); // Le diamo 15 "mi piace"
        ricetta2.setNumCommenti(8); // E 8 commenti

        ricette.add(ricetta1); // Aggiungiamo la prima ricetta alla lista
        ricette.add(ricetta2); // Aggiungiamo la seconda ricetta alla lista

        // --- Esecuzione del test ---
        // Chiediamo all'utente di calcolare le statistiche per le ricette che gli abbiamo dato
        StatisticheDTO stats = utente.calcolaStatistiche(ricette);

        // --- Verifica dei risultati ---
        // Controlliamo che l'oggetto delle statistiche non sia vuoto
        assertNotNull("Le statistiche non devono essere vuote!", stats);
        // Controlliamo che il totale dei "mi piace" sia quello che ci aspettiamo (10 + 15 = 25)
        assertEquals("Il totale dei 'mi piace' dovrebbe essere 25", 25, stats.getTotalLikes());
        // Controlliamo che il totale dei commenti sia quello che ci aspettiamo (5 + 8 = 13)
        assertEquals("Il totale dei commenti dovrebbe essere 13", 13, stats.getTotalComments());
        // Controlliamo che la ricetta con più "mi piace" sia la "Ricetta 2"
        assertEquals("La ricetta più apprezzata dovrebbe essere 'Ricetta 2'", "Ricetta 2", stats.getMostLikedRecipe());
    }

    // Test per calcolare le statistiche quando la lista di ricette è vuota
    @Test
    public void testGetStatisticheUtente_ListaVuota() {
        // --- Preparazione ---
        List<RicettaDTO> ricette = new ArrayList<>(); // Creiamo una lista di ricette vuota

        // --- Esecuzione ---
        // Calcoliamo le statistiche con una lista di ricette vuota
        StatisticheDTO stats = utente.calcolaStatistiche(ricette);

        // --- Verifica ---
        // Controlliamo che l'oggetto delle statistiche non sia vuoto
        assertNotNull("Le statistiche non devono essere vuote!", stats);
        // Se la lista è vuota, i like totali devono essere 0
        assertEquals("Il totale dei 'mi piace' dovrebbe essere 0", 0, stats.getTotalLikes());
        // Se la lista è vuota, i commenti totali devono essere 0
        assertEquals("Il totale dei commenti dovrebbe essere 0", 0, stats.getTotalComments());
        // E la ricetta più apprezzata dovrebbe dire "Nessuna ricetta"
        assertEquals("La ricetta più apprezzata dovrebbe essere 'Nessuna ricetta'", "Nessuna ricetta", stats.getMostLikedRecipe());
    }

    // Test per calcolare le statistiche con una sola ricetta nella lista
    @Test
    public void testGetStatisticheUtente_RicettaSingola() {
        // --- Preparazione ---
        List<RicettaDTO> ricette = new ArrayList<>(); // Creiamo una lista per le ricette

        // Dati per la singola ricetta
        List<String> tag = new ArrayList<>(); // Lista dei tag
        tag.add("test"); // Aggiungiamo un tag
        List<IngredienteDTO> ingredienti = new ArrayList<>(); // Lista degli ingredienti
        ingredienti.add(new IngredienteDTO("Ingrediente", "100", "g")); // Aggiungiamo un ingrediente
        // Creiamo la singola ricetta
        RicettaDTO ricetta = new RicettaDTO("Ricetta Semplice", "Descrizione semplice", 30, ingredienti, tag);
        ricetta.setNumeroLike(5); // Le diamo 5 "mi piace"
        ricetta.setNumCommenti(3); // E 3 commenti

        ricette.add(ricetta); // Aggiungiamo la ricetta alla lista

        // --- Esecuzione ---
        // Calcoliamo le statistiche per questa singola ricetta
        StatisticheDTO stats = utente.calcolaStatistiche(ricette);

        // --- Verifica ---
        // Controlliamo che l'oggetto delle statistiche non sia vuoto
        assertNotNull("Le statistiche non devono essere vuote!", stats);
        // Controlliamo che i like totali corrispondano a quelli della singola ricetta
        assertEquals("Il totale dei 'mi piace' dovrebbe essere 5", 5, stats.getTotalLikes());
        // Controlliamo che i commenti totali corrispondano a quelli della singola ricetta
        assertEquals("Il totale dei commenti dovrebbe essere 3", 3, stats.getTotalComments());
        // La ricetta più apprezzata deve essere quella che abbiamo appena aggiunto
        assertEquals("La ricetta più apprezzata dovrebbe essere 'Ricetta Semplice'", "Ricetta Semplice", stats.getMostLikedRecipe());
    }

    // Test per calcolare le statistiche quando ci sono ricette con lo stesso numero di "mi piace"
    @Test
    public void testGetStatisticheUtente_LikeUguali() {
        // --- Preparazione ---
        List<RicettaDTO> ricette = new ArrayList<>(); // Creiamo una lista per le ricette

        // Prima ricetta con 10 like
        List<String> tag1 = new ArrayList<>(); // Tag per la ricetta 1
        tag1.add("test1");
        List<IngredienteDTO> ingredienti1 = new ArrayList<>(); // Ingredienti per la ricetta 1
        ingredienti1.add(new IngredienteDTO("Ingrediente 1", "100", "g"));
        RicettaDTO ricetta1 = new RicettaDTO("Ricetta 1", "Descrizione ricetta 1", 30, ingredienti1, tag1);
        ricetta1.setNumeroLike(10); // 10 "mi piace"
        ricetta1.setNumCommenti(5);

        // Seconda ricetta con lo stesso numero di like
        List<String> tag2 = new ArrayList<>(); // Tag per la ricetta 2
        tag2.add("test2");
        List<IngredienteDTO> ingredienti2 = new ArrayList<>(); // Ingredienti per la ricetta 2
        ingredienti2.add(new IngredienteDTO("Ingrediente 2", "200", "g"));
        RicettaDTO ricetta2 = new RicettaDTO("Ricetta 2", "Descrizione ricetta 2", 45, ingredienti2, tag2);
        ricetta2.setNumeroLike(10); // Anche questa ha 10 "mi piace" (come la prima)
        ricetta2.setNumCommenti(8);

        ricette.add(ricetta1); // Aggiungiamo la prima ricetta
        ricette.add(ricetta2); // Aggiungiamo la seconda ricetta

        // --- Esecuzione ---
        // Calcoliamo le statistiche
        StatisticheDTO stats = utente.calcolaStatistiche(ricette);

        // --- Verifica ---
        // Controlliamo che l'oggetto delle statistiche non sia vuoto
        assertNotNull("Le statistiche non devono essere vuote!", stats);
        // Controlliamo il totale dei "mi piace" (10 + 10 = 20)
        assertEquals("Il totale dei 'mi piace' dovrebbe essere 20", 20, stats.getTotalLikes());
        // Controlliamo il totale dei commenti (5 + 8 = 13)
        assertEquals("Il totale dei commenti dovrebbe essere 13", 13, stats.getTotalComments());
        // Se due ricette hanno lo stesso numero di like, dovrebbe prendere la prima che trova ("Ricetta 1")
        assertEquals("La ricetta più apprezzata dovrebbe essere 'Ricetta 1' (la prima con 10 like)", "Ricetta 1", stats.getMostLikedRecipe());
    }
}
