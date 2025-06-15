package entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import dto.RicettaDTO;
import dto.IngredienteDTO;
import dto.StatisticheDTO;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class UtenteTest {
    private Utente utente;
    private Raccolta raccoltaDefault;
    private List<IngredienteDTO> ingredienti;
    private List<String> tag;

    @Before
    public void setUp() throws Exception {
        // Creo una raccolta di default per l'utente
        raccoltaDefault = new Raccolta("Default", "Raccolta di default", null);
        raccoltaDefault.setId(1); // ID fittizio per i test
        
        // Creo l'utente di test
        utente = new Utente("testUser", "Test", "User", "test@example.com", "password123", raccoltaDefault);
        
        // Preparo ingredienti e tag di test
        ingredienti = new ArrayList<>();
        ingredienti.add(new IngredienteDTO("Farina", "200", "g"));
        ingredienti.add(new IngredienteDTO("Zucchero", "100", "g"));
        
        tag = new ArrayList<>();
        tag.add("Dolce");
        tag.add("Vegetariano");
    }

    @After
    public void tearDown() throws Exception {
        utente = null;
        raccoltaDefault = null;
        ingredienti = null;
        tag = null;
    }

    @Test
    public void creaRicetta() {
        // Test caso di successo
        RicettaDTO ricettaDTO = new RicettaDTO(
            "Torta al cioccolato",
            "Procedimento test",
            60,
            ingredienti,
            tag
        );
        ricettaDTO.setIdRaccolta(1); // ID della raccolta di default
        ricettaDTO.setAutoreUsername("testUser");
        ricettaDTO.setVisibilita(true);
        
        boolean success = utente.creaRicetta(ricettaDTO);
        assertTrue("La creazione della ricetta dovrebbe avere successo", success);
        
        // Test con ID raccolta non valido
        RicettaDTO ricettaDTOInvalid = new RicettaDTO(
            "Torta invalida",
            "Procedimento test",
            60,
            ingredienti,
            tag
        );
        ricettaDTOInvalid.setIdRaccolta(-1);
        ricettaDTOInvalid.setAutoreUsername("testUser");
        
        boolean fail = utente.creaRicetta(ricettaDTOInvalid);
        assertFalse("La creazione della ricetta dovrebbe fallire con ID raccolta non valido", fail);
        
        // Test con DTO null
        boolean nullFail = utente.creaRicetta(null);
        assertFalse("La creazione della ricetta dovrebbe fallire con DTO null", nullFail);

        // Test con lista tag vuota
        RicettaDTO ricettaNoTag = new RicettaDTO(
            "Torta senza tag",
            "Procedimento test",
            60,
            ingredienti,
            new ArrayList<>()
        );
        ricettaNoTag.setIdRaccolta(1);
        ricettaNoTag.setAutoreUsername("testUser");
        boolean noTagSuccess = utente.creaRicetta(ricettaNoTag);
        assertTrue("La creazione della ricetta dovrebbe avere successo anche senza tag", noTagSuccess);

        // Test con lista ingredienti vuota
        RicettaDTO ricettaNoIng = new RicettaDTO(
            "Torta senza ingredienti",
            "Procedimento test",
            60,
            new ArrayList<>(),
            tag
        );
        ricettaNoIng.setIdRaccolta(1);
        ricettaNoIng.setAutoreUsername("testUser");
        boolean noIngSuccess = utente.creaRicetta(ricettaNoIng);
        assertTrue("La creazione della ricetta dovrebbe avere successo anche senza ingredienti", noIngSuccess);
    }

    @Test
    public void getStatisticheUtente() {
        // Test con lista vuota di ricette
        StatisticheDTO statsEmpty = utente.getStatisticheUtente("testUser");
        assertEquals("I like totali dovrebbero essere 0 con lista vuota", 0, statsEmpty.getTotalLikes());
        assertEquals("I commenti totali dovrebbero essere 0 con lista vuota", 0, statsEmpty.getTotalComments());
        assertEquals("La ricetta più apprezzata dovrebbe essere 'Nessuna ricetta' con lista vuota", 
            "Nessuna ricetta", statsEmpty.getMostLikedRecipe());
        
        // Creiamo una lista di ricette per testare il ciclo for
        List<RicettaDTO> ricette = new ArrayList<>();
        
        RicettaDTO ricetta1 = new RicettaDTO("Torta", "Descrizione", 60, ingredienti, tag);
        ricetta1.setNumeroLike(5);
        ricetta1.setNumCommenti(3);
        ricette.add(ricetta1);
        
        RicettaDTO ricetta2 = new RicettaDTO("Pizza", "Descrizione", 45, ingredienti, tag);
        ricetta2.setNumeroLike(10);
        ricetta2.setNumCommenti(5);
        ricette.add(ricetta2);
        
        RicettaDTO ricetta3 = new RicettaDTO("Pasta", "Descrizione", 30, ingredienti, tag);
        ricetta3.setNumeroLike(8);
        ricetta3.setNumCommenti(2);
        ricette.add(ricetta3);
        
        // Test con le ricette create
        StatisticheDTO stats = utente.getStatisticheUtente("testUser");
        assertEquals("I like totali dovrebbero essere 23", 23, stats.getTotalLikes());
        assertEquals("I commenti totali dovrebbero essere 10", 10, stats.getTotalComments());
        assertEquals("La ricetta più apprezzata dovrebbe essere 'Pizza'", "Pizza", stats.getMostLikedRecipe());

        // Test con username null
        StatisticheDTO statsNullUsername = utente.getStatisticheUtente(null);
        assertEquals("I like totali dovrebbero essere 0 con username null", 0, statsNullUsername.getTotalLikes());
        assertEquals("I commenti totali dovrebbero essere 0 con username null", 0, statsNullUsername.getTotalComments());
        assertEquals("La ricetta più apprezzata dovrebbe essere 'Nessuna ricetta' con username null", 
            "Nessuna ricetta", statsNullUsername.getMostLikedRecipe());
    }
}