package entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import dto.UtenteDTO;
import static org.junit.Assert.*;

public class PiattaformaTest {
    private Utente testUtente;
    private Ricetta testRicetta;
    private Piattaforma piattaforma;

    @Before
    public void setUp() throws Exception {
        // Create test data
        testUtente = new Utente("testUser", "Test", "User", "test@example.com", "password123", 
            new Raccolta("Default", "Raccolta di default", null));
        testRicetta = new Ricetta("Test Recipe", "Test Description", 30, true);
    }

    @After
    public void tearDown() throws Exception {
        // Reset singleton instance
        piattaforma = null;
    }

    @Test
    public void getInstance() {
        // Test first instance creation
        Piattaforma firstInstance = Piattaforma.getInstance(testUtente, testRicetta);
        assertNotNull("First instance should not be null", firstInstance);
        
        // Test second instance is the same as first (singleton pattern)
        Piattaforma secondInstance = Piattaforma.getInstance(testUtente, testRicetta);
        assertSame("Second instance should be the same as first", firstInstance, secondInstance);
        
        // Test with different parameters (should still return same instance)
        Utente differentUtente = new Utente("diffUser", "Diff", "User", "diff@example.com", "pass123", 
            new Raccolta("Default", "Raccolta di default", null));
        Ricetta differentRicetta = new Ricetta("Diff Recipe", "Diff Description", 45, false);
        Piattaforma thirdInstance = Piattaforma.getInstance(differentUtente, differentRicetta);
        assertSame("Third instance should be the same as first", firstInstance, thirdInstance);
    }

    @Test
    public void registraUtente() {
        // Get platform instance
        Piattaforma piattaforma = Piattaforma.getInstance(testUtente, testRicetta);
        
        // Test successful registration
        UtenteDTO newUser = new UtenteDTO("newUser", "New", "User", "new@example.com", "newpass123");
        boolean success = piattaforma.registraUtente(newUser);
        assertTrue("Registration should succeed with valid data", success);
        
        // Test registration with existing username (should fail)
        UtenteDTO duplicateUser = new UtenteDTO("newUser", "Duplicate", "User", "duplicate@example.com", "pass123");
        boolean duplicateSuccess = piattaforma.registraUtente(duplicateUser);
        assertFalse("Registration should fail with duplicate username", duplicateSuccess);
        
        // Test registration with null data
        boolean nullSuccess = piattaforma.registraUtente(null);
        assertFalse("Registration should fail with null data", nullSuccess);
    }
}