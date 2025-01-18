package fr.pantheonsorbonne;

import fr.pantheonsorbonne.dao.StoredPaperDAO;
import fr.pantheonsorbonne.dto.StoredPaperInputDTO;
import fr.pantheonsorbonne.dto.StoredPaperOutputDTO;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.model.StoredPaper;
import fr.pantheonsorbonne.service.StoredPaperService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class StoredPaperServiceTest {

    @Inject
    StoredPaperService storedPaperService;

    @Inject
    StoredPaperDAO storedPaperDAO;

    private StoredPaperInputDTO inputDTO;

    @BeforeEach
    void setUp() {
        inputDTO = new StoredPaperInputDTO("uuid123", "content123".getBytes());
    }

    // Test de la création d'un papier
    @Test
    void testCreateStoredPaper() throws PaperDatabaseAccessException {
        // Act
        StoredPaperOutputDTO result = storedPaperService.createStoredPaper(inputDTO);

        // Assert
        assertNotNull(result);
        assertEquals("uuid123", result.getId());
        assertArrayEquals("content123".getBytes(), result.getContent());

        // Vérifier que l'entité a bien été persistée dans la base de données
        StoredPaper storedPaper = storedPaperDAO.findStoredPaperByUuid("uuid123");
        assertNotNull(storedPaper);
        assertArrayEquals("content123".getBytes(), storedPaper.getBody());
    }

    // Test de la mise à jour d'un papier
    @Test
    void testUpdateStoredPaper() throws PaperNotFoundException {
        // Arrange
        StoredPaper createdPaper = new StoredPaper();
        createdPaper.setPaperUuid("uuid123");
        createdPaper.setBody("content123".getBytes());
        storedPaperDAO.saveStoredPaper(createdPaper);

        StoredPaperInputDTO updatedDTO = new StoredPaperInputDTO("uuid123", "updatedContent".getBytes());

        // Act
        StoredPaperOutputDTO result = storedPaperService.updateStoredPaper("uuid123", updatedDTO);

        // Assert
        assertNotNull(result);
        assertArrayEquals("updatedContent".getBytes(), result.getContent());

        // Vérifier que l'entité a bien été mise à jour dans la base de données
        StoredPaper updatedPaper = storedPaperDAO.findStoredPaperByUuid("uuid123");
        assertNotNull(updatedPaper);
        assertArrayEquals("updatedContent".getBytes(), updatedPaper.getBody());
    }

    // Test de la récupération d'un papier
    @Test
    void testGetStoredPaper() throws PaperNotFoundException {
        // Arrange
        StoredPaper createdPaper = new StoredPaper();
        createdPaper.setPaperUuid("uuid123");
        createdPaper.setBody("content123".getBytes());
        storedPaperDAO.saveStoredPaper(createdPaper);

        // Act
        StoredPaperOutputDTO result = storedPaperService.getStoredPaper("uuid123");

        // Assert
        assertNotNull(result);
        assertEquals("uuid123", result.getId());
        assertArrayEquals("content123".getBytes(), result.getContent());
    }

    // Test de la suppression d'un papier
    @Test
    void testDeleteStoredPaper() throws PaperNotFoundException {
        // Arrange
        StoredPaper createdPaper = new StoredPaper();
        createdPaper.setPaperUuid("uuid123");
        createdPaper.setBody("content".getBytes());
        storedPaperDAO.saveStoredPaper(createdPaper);

        // Act
        storedPaperService.deleteStoredPaper("uuid123");

        // Assert
        assertThrows(PaperNotFoundException.class, () -> storedPaperService.getStoredPaper("uuid123"));
    }

    // Test de la gestion de l'exception "PaperNotFoundException" pour la mise à jour
    @Test
    void testUpdateStoredPaper_PaperNotFound() {
        // Act & Assert
        assertThrows(PaperNotFoundException.class, () -> storedPaperService.updateStoredPaper("uuid123", inputDTO));
    }

    // Test de la gestion de l'exception "PaperNotFoundException" pour la récupération
    @Test
    void testGetStoredPaper_PaperNotFound() {
        // Act & Assert
        assertThrows(PaperNotFoundException.class, () -> storedPaperService.getStoredPaper("uuid123"));
    }

    // Test de la gestion de l'exception "PaperNotFoundException" pour la suppression
    @Test
    void testDeleteStoredPaper_PaperNotFound() {
        // Act & Assert
        assertThrows(PaperNotFoundException.class, () -> storedPaperService.deleteStoredPaper("uuid123"));
    }
}