package controller;

import dto.UtenteDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccessoControllerTest {
    private AccessoController controller;

    // Dati utente esistente valido
    private static final String VALID_EMAIL = "marco_verdi@mail.ex";
    private static final String VALID_PASSWORD = "MarcoVerd5";

    // Dati admin validi
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    // Dati per un nuovo utente
    private static final String NEW_USERNAME = "test_junit4";
    private static final String NEW_NOME = "Test";
    private static final String NEW_COGNOME = "Testing";
    private static final String NEW_EMAIL = "test_jUnit4@mail.ex";
    private static final String NEW_PASSWORD = "Test9";
    private static final String NEW_BIOGRAFIA = "Cuoca professional";
    private static final String NEW_IMMAGINE = "uploads/marco_verdi.jpg";

    @Before
    public void setUp() {
        // Prendo l'oggetto del controller
        controller = AccessoController.getInstance();
    }

    @After
    public void tearDown() {
        // Lo rimetto a null dopo i test
        controller = null;
    }

    @Test
    public void testGetInstance() {
        // Verifico che il controller non sia nullo
        AccessoController prima = AccessoController.getInstance();
        assertNotNull("Il controller non deve essere nullo", prima);

        // Verifico che sia sempre lo stesso oggetto (singleton)
        AccessoController seconda = AccessoController.getInstance();
        assertSame("Deve restituire sempre lo stesso oggetto", prima, seconda);
    }

    @Test
    public void testGetUtenteAutenticato_ValidCredentials() {
        // Provo a fare login con credenziali giuste
        UtenteDTO utente = controller.getUtenteAutenticato(VALID_EMAIL, VALID_PASSWORD);
        assertNotNull("Con email e password giuste, l'utente non deve essere nullo", utente);
        assertEquals("L'email dell'utente deve essere quella usata per il login", VALID_EMAIL, utente.getEmail());
    }

    @Test
    public void testGetUtenteAutenticato_InvalidCredentials() {
        // Provo a fare login con credenziali sbagliate
        UtenteDTO utente = controller.getUtenteAutenticato("invalid@email.com", "wrongpass");
        assertNull("Con dati sbagliati, l'utente deve essere nullo", utente);
    }


    //TODO: ATTENZIONE ogni volta che si esegue il test si devono cambiare i dati. (sopra)
    @Test
    public void testRegistraUtente_NewUser() {
        // Provo a registrare un nuovo utente
        UtenteDTO nuovoUtente = new UtenteDTO(
                NEW_USERNAME,
                NEW_NOME,
                NEW_COGNOME,
                NEW_EMAIL,
                NEW_PASSWORD
        );

        boolean registrato = controller.registraUtente(nuovoUtente);
        assertTrue("Il nuovo utente dovrebbe essere registrato con successo", registrato);
    }

    @Test
    public void testRegistraUtente_DuplicateUser() {
        // Provo a registrare un utente già esistente
        UtenteDTO utenteEsistente = new UtenteDTO(
                "marco_verdi",
                "Marco",
                "Verdi",
                "marco_verdi@mail.ex",
                "MarcoVerd5"
        );

        boolean registrato = controller.registraUtente(utenteEsistente);
        assertFalse("Non deve essere possibile registrare un utente già presente", registrato);
    }

    @Test
    public void testAutenticaAdmin_ValidCredentials() {
        // Provo login admin con dati giusti
        boolean ok = controller.autenticaAdmin(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertTrue("Il login admin deve riuscire con i dati giusti", ok);
    }

    @Test
    public void testAutenticaAdmin_InvalidCredentials() {
        // Provo login admin con dati sbagliati
        boolean ok = controller.autenticaAdmin("admin_sbagliato", "pass_sbagliata");
        assertFalse("Il login admin deve fallire con dati sbagliati", ok);
    }
}
