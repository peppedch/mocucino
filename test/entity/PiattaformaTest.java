package entity;

import dto.UtenteDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*; // Importa tutti i metodi statici di Assert per comodità

public class PiattaformaTest {
    // La nostra piattaforma che stiamo testando
    private Piattaforma piattaforma;
    // Un utente di prova per i test
    private Utente utenteTest;
    // Una ricetta di prova per i test
    private Ricetta ricettaTest;

    // Dati per un nuovo utente da registrare
    private static final String NEW_USERNAME = "t3st_junit";
    private static final String NEW_NOME = "Test";
    private static final String NEW_COGNOME = "Test";
    private static final String NEW_EMAIL = "t3st_junit4@mail.ex";
    private static final String NEW_PASSWORD = "TestPass2";

    // Dati di un utente che sappiamo già esistere nel sistema
    private static final String EXISTING_USERNAME = "marco_verdi";
    private static final String EXISTING_NOME = "Marco";
    private static final String EXISTING_COGNOME = "Verdi";
    private static final String EXISTING_EMAIL = "marco_verdi@mail.ex";
    private static final String EXISTING_PASSWORD = "MarcoVerd5";

    // Questo metodo viene eseguito prima di ogni singolo test
    @Before
    public void setUp() {
        // Creiamo un utente e una ricetta di test per preparare la piattaforma
        utenteTest = new Utente("test_user", "Test", "User", "test@mail.ex", "TestPass1",
                new Raccolta("Default", "Raccolta di default", null));
        ricettaTest = new Ricetta("Test Recipe", "Test Description", 30, true);
        // Otteniamo l'istanza della Piattaforma (è un singleton, ne esiste una sola)
        piattaforma = Piattaforma.getInstance(utenteTest, ricettaTest);
    }

    // Questo metodo viene eseguito dopo ogni singolo test
    @After
    public void tearDown() {
        // Puliamo l'oggetto piattaforma alla fine di ogni test
        piattaforma = null;
    }

    // Test per verificare che otteniamo sempre la stessa istanza della piattaforma
    @Test
    public void testGetInstance() {
        // Verifichiamo che la prima volta che chiediamo l'istanza, non sia vuota
        Piattaforma firstInstance = Piattaforma.getInstance(utenteTest, ricettaTest);
        assertNotNull("La prima istanza non dovrebbe essere vuota!", firstInstance);

        // Verifichiamo che la seconda volta che la chiediamo, sia la stessa della prima (singleton)
        Piattaforma secondInstance = Piattaforma.getInstance(utenteTest, ricettaTest);
        assertSame("La seconda istanza deve essere la stessa della prima, perché è un singleton!", firstInstance, secondInstance);
    }

    // Test per registrare un nuovo utente che non esiste ancora
    //TODO: ATTENZIONE ogni volta che si esegue il test si devono cambiare i dati. (sopra)
    @Test
    public void testRegistraUtente_NewUser() {
        // Creiamo i dati per un utente tutto nuovo
        UtenteDTO newUser = new UtenteDTO(
                NEW_USERNAME,
                NEW_NOME,
                NEW_COGNOME,
                NEW_EMAIL,
                NEW_PASSWORD
        );

        // Proviamo a registrare questo nuovo utente
        boolean success = piattaforma.registraUtente(newUser);

        // Controlliamo che la registrazione sia andata a buon fine
        assertTrue("La registrazione di un nuovo utente dovrebbe riuscire!", success);
    }

    // Test per provare a registrare un utente che esiste già
    @Test
    public void testRegistraUtente_ExistingUser() {
        // Creiamo i dati di un utente che sappiamo già esistere
        UtenteDTO existingUser = new UtenteDTO(
                EXISTING_USERNAME,
                EXISTING_NOME,
                EXISTING_COGNOME,
                EXISTING_EMAIL,
                EXISTING_PASSWORD
        );

        // Proviamo a registrare questo utente che esiste già
        boolean success = piattaforma.registraUtente(existingUser);

        // Controlliamo che la registrazione non sia andata a buon fine (perché l'utente esiste già)
        assertFalse("La registrazione di un utente che esiste già dovrebbe fallire!", success);
    }
}
