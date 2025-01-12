package fr.pantheonsorbonne;

import fr.pantheonsorbonne.dao.StoredPaperDAO;
import fr.pantheonsorbonne.model.StoredPaper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class StoredPaperDAOTest {

    @Inject
    StoredPaperDAO storedPaperDAO;

    @Test
    void testSaveAndFind() {
        // Arrange
        StoredPaper storedPaper = new StoredPaper();
        storedPaper.setBody("Sample Content".getBytes());

        // Act
        StoredPaper savedPaper = storedPaperDAO.saveStoredPaper(storedPaper);
        StoredPaper foundPaper = storedPaperDAO.findStoredPaperById(savedPaper.getId());

        // Assert
        assertNotNull(savedPaper.getId());
        assertNotNull(foundPaper);
        assertArrayEquals("Sample Content".getBytes(), foundPaper.getBody());
    }


}