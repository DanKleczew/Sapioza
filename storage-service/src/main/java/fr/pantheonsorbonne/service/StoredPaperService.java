package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.StoredPaperDAO;
import fr.pantheonsorbonne.dto.StoredPaperInputDTO;
import fr.pantheonsorbonne.dto.StoredPaperOutputDTO;
import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.mappers.StoredPaperMapper;
import fr.pantheonsorbonne.model.StoredPaper;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
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
    public StoredPaperOutputDTO createStoredPaper(StoredPaperInputDTO dto) throws PaperDatabaseAccessException {
        try {
            // Mapper the DTO to entity
            StoredPaper storedPaper = storedPaperMapper.mapInputDTOToEntity(dto);
            // Save the entity using the DAO
            StoredPaper savedPaper = storedPaperDAO.saveStoredPaper(storedPaper);
            // Return the saved paper as a DTO
            return storedPaperMapper.mapEntityToOutputDTO(savedPaper);
        } catch (Exception e) {
            throw new PaperDatabaseAccessException("Failed to save StoredPaper");
        }
    }

    @Transactional
    public StoredPaperOutputDTO updateStoredPaper(String paperUuid, StoredPaperInputDTO dto) throws PaperNotFoundException {
        // Find the existing paper by paperUuid
        StoredPaper existingPaper = storedPaperDAO.findStoredPaperByUuid(paperUuid);
        if (existingPaper == null) {
            throw new PaperNotFoundException("StoredPaper with paperUuid " + paperUuid + " not found");
        }

        // Update the paper's body with the new content
        existingPaper.setBody(dto.getContent());
        // Save the updated paper
        StoredPaper updatedPaper = storedPaperDAO.saveStoredPaper(existingPaper);
        // Return the updated paper as a DTO
        return storedPaperMapper.mapEntityToOutputDTO(updatedPaper);
    }

    public StoredPaperOutputDTO getStoredPaper(String paperUuid) throws PaperNotFoundException {
        // Find the paper by its paperUuid
        StoredPaper storedPaper = storedPaperDAO.findStoredPaperByUuid(paperUuid);
        if (storedPaper == null) {
            throw new PaperNotFoundException("StoredPaper with paperUuid " + paperUuid + " not found");
        }
        // Return the paper as a DTO
        return storedPaperMapper.mapEntityToOutputDTO(storedPaper);
    }

    @Transactional
    public void deleteStoredPaper(String paperUuid) throws PaperNotFoundException {
        // Find the paper by its paperUuid
        StoredPaper existingPaper = storedPaperDAO.findStoredPaperByUuid(paperUuid);
        if (existingPaper == null) {
            throw new PaperNotFoundException("StoredPaper with paperUuid " + paperUuid + " not found");
        }

        // Delete the paper
        storedPaperDAO.deleteStoredPaper(existingPaper);
    }

    @Transactional
    public StoredPaperOutputDTO createStoredPaperFromDTO(PaperContentDTO dto) throws PaperDatabaseAccessException {
        try {
            StoredPaper storedPaper = new StoredPaper();
            storedPaper.setPaperUuid(dto.paperUuid());
            storedPaper.setBody(dto.body().getBytes());
            storedPaperDAO.saveStoredPaper(storedPaper);
            return storedPaperMapper.mapEntityToOutputDTO(storedPaper);
        } catch (Exception e) {
            throw new PaperDatabaseAccessException("Failed to save StoredPaper from DTO");
        }
    }

    public void savePaper(PaperContentDTO paperContentDTO) throws PaperDatabaseAccessException {
        try {
            StoredPaper storedPaper = new StoredPaper();
            storedPaper.setPaperUuid(paperContentDTO.paperUuid());
            storedPaper.setBody(paperContentDTO.body().getBytes());
            storedPaperDAO.saveStoredPaper(storedPaper);
        } catch (Exception e) {
            throw new PaperDatabaseAccessException("Failed to save paper in database");
        }
    }

    public String getPaperContent(String paperUuid) throws PaperDatabaseAccessException {
        StoredPaper storedPaper = storedPaperDAO.findStoredPaperByUuid(paperUuid);
        if (storedPaper == null) {
            throw new PaperDatabaseAccessException("No paper found for uuid " + paperUuid);
        }
        return new String(storedPaper.getBody());
    }
}
