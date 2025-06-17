package controller;

import dto.UtenteDTO;
import dto.RicettaDTO;
import dto.ProfiloUtenteDTO;
import entity.Raccolta;
import entity.Utente;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*; // Importa tutti i metodi statici di Assert, così non dobbiamo scrivere "Assert." ogni volta
import java.sql.Date;
import java.util.List;

public class GestoreControllerTest {
    // Questa è la classe che stiamo testando
    private GestoreController controller;

    // Dati di un utente "vero" che esiste nel sistema, usato per i test
    private static final String VALID_USERNAME = "marco_verdi";
    private static final String VALID_NOME = "Marco";
    private static final String VALID_COGNOME = "Verdi";
    private static final String VALID_EMAIL = "marco_verdi@mail.ex";
    private static final String VALID_PASSWORD = "MarcoVerd5";
    private static final String VALID_BIOGRAFIA = "Cuoco amatoriale";
    private static final String VALID_IMMAGINE = "uploads/marco_verdi.jpg";

    // Dati per un utente di prova, creato solo per i test
    private static final String TEST_USERNAME = "TestPeppe";
    private static final String TEST_NOME = "Test";
    private static final String TEST_COGNOME = "Peppe";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "TestPass123";

    // ID di una ricetta di prova, usato per aggiungere commenti
    private static final int TEST_RICETTA_ID = 1;

    // Date per generare un report (rapporto)
    private static final Date DATA_INIZIO = Date.valueOf("2025-06-09");
    private static final Date DATA_FINE = Date.valueOf("2025-06-11");

    // Questo metodo viene eseguito prima di ogni singolo test
    @Before
    public void setUp() {
        // Otteniamo l'istanza del GestoreController. È un "singleton", quindi ne esiste solo una per tutta l'applicazione.
        controller = GestoreController.getInstance();
    }

    // Questo metodo viene eseguito dopo ogni singolo test
    @After
    public void tearDown() {
        // Puliamo l'utente corrente alla fine di ogni test per non sporcare i test successivi
        controller.clearUtenteCorrente();
    }

    // Test per verificare che otteniamo sempre la stessa istanza del controller
    @Test
    public void testGetInstance() {
        // Verifichiamo che la prima volta che lo chiediamo, il controller non sia vuoto
        GestoreController firstInstance = GestoreController.getInstance();
        assertNotNull("La prima istanza non dovrebbe essere vuota!", firstInstance);

        // Verifichiamo che la seconda volta che lo chiediamo, sia la stessa di prima (è un singleton)
        GestoreController secondInstance = GestoreController.getInstance();
        assertSame("La seconda istanza deve essere la stessa della prima, perché è un singleton!", firstInstance, secondInstance);
    }

    // Test per quando l'utente corrente viene impostato come "null" (vuoto)
    @Test
    public void testSetUtenteCorrente_Null() {
        // Impostiamo l'utente corrente a null
        controller.setUtenteCorrente(null);
        // Controlliamo che, se l'utente è null, le raccolte utente siano vuote
        assertTrue("Dovrebbe restituire una lista vuota quando l'utente è null",
                controller.getRaccolteUtente(VALID_USERNAME).isEmpty());
    }

    // Test per impostare un utente valido come utente corrente
    @Test
    public void testSetUtenteCorrente_ValidUser() {
        // Creiamo un oggetto UtenteDTO con dati di un utente valido
        UtenteDTO utenteDTO = new UtenteDTO(VALID_USERNAME, VALID_NOME, VALID_COGNOME, VALID_EMAIL, VALID_PASSWORD);
        // Impostiamo questo utente come utente corrente
        controller.setUtenteCorrente(utenteDTO);

        // Verifichiamo che l'utente sia stato impostato correttamente.
        // Se l'utente è valido, la lista delle raccolte non dovrebbe essere vuota.
        assertFalse("Non dovrebbe restituire una lista vuota se l'utente è valido",
                controller.getRaccolteUtente(VALID_USERNAME).isEmpty());
    }

    // Test per impostare un utente di test come utente corrente
    @Test
    public void testSetUtenteCorrente_TestUser() {
        // Creiamo un oggetto UtenteDTO con dati di un utente di test
        UtenteDTO utenteDTO = new UtenteDTO(TEST_USERNAME, TEST_NOME, TEST_COGNOME, TEST_EMAIL, TEST_PASSWORD);
        // Impostiamo questo utente come utente corrente
        controller.setUtenteCorrente(utenteDTO);

        // Controlliamo che l'utente sia stato impostato bene. Anche l'utente di test dovrebbe avere delle raccolte.
        assertFalse("Non dovrebbe restituire una lista vuota anche per l'utente di test",
                controller.getRaccolteUtente(TEST_USERNAME).isEmpty());
    }

    // Test per pulire (azzerare) l'utente corrente
    @Test
    public void testClearUtenteCorrente() {
        // Prima impostiamo un utente (uno vero)
        UtenteDTO utenteDTO = new UtenteDTO(VALID_USERNAME, VALID_NOME, VALID_COGNOME, VALID_EMAIL, VALID_PASSWORD);
        controller.setUtenteCorrente(utenteDTO);

        // Poi lo puliamo
        controller.clearUtenteCorrente();

        // Verifichiamo che sia stato pulito, controllando che le raccolte siano di nuovo vuote
        assertTrue("Dovrebbe restituire una lista vuota dopo aver pulito l'utente",
                controller.getRaccolteUtente(VALID_USERNAME).isEmpty());
    }

    // Test per ottenere le raccolte quando l'utente corrente è null
    @Test
    public void testGetRaccolteUtente_NullUtente() {
        // Assicuriamoci che l'utente corrente sia null
        controller.clearUtenteCorrente();
        assertTrue("Dovrebbe dare una lista vuota se l'utente non è loggato",
                controller.getRaccolteUtente(VALID_USERNAME).isEmpty());
    }

    // Test per ottenere l'ID della raccolta predefinita quando l'utente è null
    @Test
    public void testGetIdRaccoltaDefault_NullUtente() {
        // Assicuriamoci che l'utente corrente sia null
        controller.clearUtenteCorrente();
        assertEquals("Dovrebbe restituire -1 se non c'è un utente loggato", -1, controller.getIdRaccoltaDefault());
    }

    // Test per ottenere l'ID della raccolta predefinita con un utente valido
    @Test
    public void testGetIdRaccoltaDefault_ValidUser() {
        // Impostiamo un utente valido
        UtenteDTO utenteDTO = new UtenteDTO(VALID_USERNAME, VALID_NOME, VALID_COGNOME, VALID_EMAIL, VALID_PASSWORD);
        controller.setUtenteCorrente(utenteDTO);

        // Verifichiamo che l'ID della raccolta predefinita non sia -1 (che indica un errore o nessun utente)
        assertNotEquals("Dovrebbe restituire un ID valido per un utente esistente", -1, controller.getIdRaccoltaDefault());
    }

    // Test per ottenere le ricette recenti
    @Test
    public void testGetRicetteRecenti() {
        // Chiediamo le ricette recenti per l'utente di test
        List<RicettaDTO> ricette = controller.getRicetteRecenti(TEST_USERNAME);

        // Controlliamo che la lista delle ricette non sia proprio vuota (cioè null)
        assertNotNull("La lista delle ricette non dovrebbe essere vuota del tutto (nulla)", ricette);

        // Controlliamo che la lista non sia vuota, se l'utente ha pubblicato ricette
        assertFalse("La lista delle ricette non dovrebbe essere vuota se l'utente ha ricette pubbliche",
                ricette.isEmpty());

        // Per ogni ricetta nella lista, controlliamo che i dati importanti ci siano
        for (RicettaDTO ricetta : ricette) {
            assertNotNull("Il titolo della ricetta non dovrebbe essere vuoto", ricetta.getTitolo());
            assertNotNull("La descrizione della ricetta non dovrebbe essere vuota", ricetta.getDescrizione());
            assertTrue("Il tempo di preparazione deve essere un numero positivo", ricetta.getTempoPreparazione() > 0);
        }
    }

    // Test per aggiungere un commento
    @Test
    public void testAggiungiCommento() {
        // Creiamo un commento di prova, con un timestamp per renderlo unico
        String commento = "Commento di prova " + System.currentTimeMillis();
        // Tentiamo di aggiungere il commento alla ricetta di test
        boolean success = controller.aggiungiCommento(TEST_USERNAME, TEST_RICETTA_ID, commento);
        // Verifichiamo che l'operazione sia andata a buon fine
        assertTrue("L'aggiunta del commento dovrebbe funzionare senza problemi", success);
    }

    // Test per ottenere il profilo di un utente
    @Test
    public void testGetProfiloUtente() {
        // Prima, aggiorniamo il profilo con i dati "originali" per essere sicuri che siano a posto per il test
        ProfiloUtenteDTO profiloOriginale = new ProfiloUtenteDTO(
                VALID_USERNAME,
                VALID_NOME,
                VALID_COGNOME,
                VALID_EMAIL,
                VALID_PASSWORD,
                VALID_BIOGRAFIA,
                VALID_IMMAGINE
        );
        controller.aggiornaProfiloUtente(profiloOriginale);

        // Ora proviamo a recuperare il profilo dell'utente valido
        ProfiloUtenteDTO profilo = controller.getProfiloUtente(VALID_USERNAME);

        // Verifichiamo che il profilo non sia vuoto
        assertNotNull("Il profilo non dovrebbe essere vuoto", profilo);
        // E controlliamo che ogni singolo campo corrisponda ai dati che ci aspettiamo
        assertEquals("L'username non è quello che ci aspettavamo", VALID_USERNAME, profilo.getUsername());
        assertEquals("Il nome non è quello che ci aspettavamo", VALID_NOME, profilo.getNome());
        assertEquals("Il cognome non è quello che ci aspettavamo", VALID_COGNOME, profilo.getCognome());
        assertEquals("L'email non è quella che ci aspettavamo", VALID_EMAIL, profilo.getEmail());
        assertEquals("La biografia non è quella che ci aspettavamo", VALID_BIOGRAFIA, profilo.getBiografia());
        assertEquals("L'immagine non è quella che ci aspettavamo", VALID_IMMAGINE, profilo.getImmagine());
    }

    // Test per aggiornare il profilo di un utente
    @Test
    public void testAggiornaProfiloUtente()  {
        // Salviamo la biografia originale dell'utente per poterla ripristinare dopo il test
        ProfiloUtenteDTO profiloOriginale = controller.getProfiloUtente(VALID_USERNAME);
        String biografiaOriginale = profiloOriginale.getBiografia();

        // Utilizziamo un blocco try-finally per assicurarci che la biografia venga sempre ripristinata, anche se il test fallisce
        try {
            // Creiamo un nuovo oggetto ProfiloUtenteDTO con la biografia modificata
            ProfiloUtenteDTO profiloAggiornato = new ProfiloUtenteDTO(
                    VALID_USERNAME,
                    VALID_NOME,
                    VALID_COGNOME,
                    VALID_EMAIL,
                    VALID_PASSWORD,
                    "Test biografia", // Biografia più corta per rispettare il limite del database
                    VALID_IMMAGINE
            );

            // Tentiamo di aggiornare il profilo con i nuovi dati
            boolean success = controller.aggiornaProfiloUtente(profiloAggiornato);
            // Verifichiamo che l'aggiornamento sia andato a buon fine
            assertTrue("L'aggiornamento del profilo dovrebbe funzionare correttamente!", success);

            // Ora, recuperiamo nuovamente il profilo e controlliamo che la biografia sia stata modificata
            ProfiloUtenteDTO profiloVerificato = controller.getProfiloUtente(VALID_USERNAME);
            assertEquals("La biografia non è stata aggiornata correttamente, qualcosa è andato storto!",
                    "Test biografia", profiloVerificato.getBiografia());
        } finally {
            // In questo blocco, ripristiniamo la biografia originale
            ProfiloUtenteDTO profiloRipristino = new ProfiloUtenteDTO(
                    VALID_USERNAME,
                    VALID_NOME,
                    VALID_COGNOME,
                    VALID_EMAIL,
                    VALID_PASSWORD,
                    biografiaOriginale, // Ripristiniamo la biografia originale
                    VALID_IMMAGINE
            );
            // E eseguiamo l'aggiornamento per ripristinare i dati originali
            controller.aggiornaProfiloUtente(profiloRipristino);
        }
    }

    // Test per generare un report sul numero di ricette in un certo periodo
    @Test
    public void testGeneraReportNumRicette() {
        // Generiamo il report per l'intervallo di date specificato
        int numRicette = controller.generaReportNumRicette(DATA_INIZIO, DATA_FINE);

        // Controlliamo che il numero di ricette sia almeno zero (non può essere negativo!)
        assertTrue("Il numero di ricette nel report deve essere zero o più", numRicette >= 0);
    }


} 