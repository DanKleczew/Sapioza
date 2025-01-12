package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.StoredPaperDAO;
import fr.pantheonsorbonne.dto.StoredPaperInputDTO;
import fr.pantheonsorbonne.dto.StoredPaperOutputDTO;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.mappers.StoredPaperMapper;
import fr.pantheonsorbonne.model.StoredPaper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StoredPaperService {

    @Inject
    StoredPaperDAO storedPaperDAO;

    @Inject
    StoredPaperMapper storedPaperMapper;

    @Transactional
    public StoredPaper createStoredPaper(StoredPaperInputDTO dto) throws PaperDatabaseAccessException {
        try {
            // Mapper le DTO en entité
            StoredPaper storedPaper = storedPaperMapper.mapInputDTOToEntity(dto);
            // Sauvegarder l'entité via le DAO et renvoyer l'entité persistée
            return storedPaperDAO.saveStoredPaper(storedPaper);
        } catch (Exception e) {
            throw new PaperDatabaseAccessException("Failed to save StoredPaper", e);
        }
    }

    @Transactional
    public StoredPaper updateStoredPaper(Long id, StoredPaperInputDTO dto) throws PaperNotFoundException {
        // Vérifier si l'entité existe
        StoredPaper existingPaper = storedPaperDAO.findStoredPaperById(id);
        if (existingPaper == null) {
            throw new PaperNotFoundException("StoredPaper with ID " + id + " not found");
        }

        // Mettre à jour le contenu
        existingPaper.setBody(dto.getContent().getBytes());
        // Sauvegarder l'entité mise à jour
        return storedPaperDAO.saveStoredPaper(existingPaper);
    }

    public StoredPaperOutputDTO getStoredPaper(Long id) throws PaperNotFoundException {
        StoredPaper storedPaper = storedPaperDAO.findStoredPaperById(id);
        if (storedPaper == null) {
            throw new PaperNotFoundException("StoredPaper with ID " + id + " not found");
        }
        return storedPaperMapper.mapEntityToOutputDTO(storedPaper);
    }

    @Transactional
    public void deleteStoredPaper(Long id) throws PaperNotFoundException {
        // Vérifier si l'article avec cet ID existe
        StoredPaper existingPaper = storedPaperDAO.findStoredPaperById(id);
        if (existingPaper == null) {
            throw new PaperNotFoundException("StoredPaper with ID " + id + " not found");
        }

        // Si l'article existe, procéder à la suppression
        storedPaperDAO.deleteStoredPaper(existingPaper);
    }

}

