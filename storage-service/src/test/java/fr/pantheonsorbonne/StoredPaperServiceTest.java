package fr.pantheonsorbonne;

import fr.pantheonsorbonne.dto.StoredPaperInputDTO;
import fr.pantheonsorbonne.dto.StoredPaperOutputDTO;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.model.StoredPaper;
import fr.pantheonsorbonne.service.StoredPaperService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class StoredPaperServiceTest {

    @Inject
    StoredPaperService storedPaperService;  // Injection du service

    @Test
    void testSaveAndFindWithGeneratedId() throws PaperNotFoundException {
        // Créer un DTO d'entrée avec le contenu
        StoredPaperInputDTO inputDTO = new StoredPaperInputDTO();
        inputDTO.setContent("Custom Content");

        // Utiliser le service pour créer l'entité
        StoredPaper createdPaper = storedPaperService.createStoredPaper(inputDTO);  // Utiliser le service pour enregistrer l'entité

        // Récupérer l'entité par ID en utilisant le service (l'ID est généré automatiquement)
        StoredPaperOutputDTO foundPaper = storedPaperService.getStoredPaper(createdPaper.getId());  // Utiliser le service pour récupérer l'entité

        // Vérifier que l'entité a été sauvegardée et correspond aux données
        assertNotNull(foundPaper);  // Vérifier que l'entité existe
        assertArrayEquals("Custom Content".getBytes(), foundPaper.getContent().getBytes());  // Vérifier que le contenu est correct
    }

    @Test
    void testUpdateStoredPaper() throws PaperNotFoundException, PaperDatabaseAccessException {
        // Arrange : Créer un DTO avec un contenu initial
        StoredPaperInputDTO inputDTO = new StoredPaperInputDTO();
        inputDTO.setContent("Original Content");

        // Act : Utiliser le service pour créer l'entité
        StoredPaper createdPaper = storedPaperService.createStoredPaper(inputDTO); // Crée une entité avec un ID

        // Vérifier que l'entité a été correctement sauvegardée
        assertNotNull(createdPaper);
        assertNotNull(createdPaper.getId()); // Vérifier que l'ID a été généré
        assertArrayEquals("Original Content".getBytes(), createdPaper.getBody());

        // Maintenant, on met à jour le contenu de l'entité
        inputDTO.setContent("Updated Content");
        StoredPaper updatedPaper = storedPaperService.updateStoredPaper(createdPaper.getId(), inputDTO); // Met à jour l'entité

        // Assert : Vérifier que le contenu a été mis à jour
        assertNotNull(updatedPaper);
        assertArrayEquals("Updated Content".getBytes(), updatedPaper.getBody());
    }

    @Test
    void testDeleteStoredPaper() throws PaperDatabaseAccessException, PaperNotFoundException {
        // Arrange : Créer un DTO et sauvegarder l'article
        StoredPaperInputDTO inputDTO = new StoredPaperInputDTO();
        inputDTO.setContent("Content to be deleted");
        StoredPaper createdPaper = storedPaperService.createStoredPaper(inputDTO); // Crée une entité avec un ID

        // Vérifier que l'entité a été correctement sauvegardée
        assertNotNull(createdPaper);
        assertNotNull(createdPaper.getId()); // Vérifier que l'ID a été généré

        // Act : Supprimer l'article
        storedPaperService.deleteStoredPaper(createdPaper.getId()); // Effacer l'entité par son ID

        // Assert : Vérifier que l'article a bien été supprimé
        assertThrows(PaperNotFoundException.class, () -> storedPaperService.getStoredPaper(createdPaper.getId())); // Lancer une exception si l'article n'est pas trouvé
    }

    @Test
    void testDeleteStoredPaper1() throws PaperDatabaseAccessException, PaperNotFoundException {

        // Act : Supprimer l'article
        storedPaperService.deleteStoredPaper(9L); // Effacer l'entité par son ID

        // Assert : Vérifier que l'article a bien été supprimé
        assertThrows(PaperNotFoundException.class, () -> storedPaperService.getStoredPaper(9L)); // Lancer une exception si l'article n'est pas trouvé
    }


}


