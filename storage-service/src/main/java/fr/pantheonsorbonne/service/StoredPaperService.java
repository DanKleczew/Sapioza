package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.StoredPaperDAO;
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
    public void updateStoredPaper(PaperContentDTO dto) throws PaperNotFoundException {
        // Find the existing paper by paperUuid
        StoredPaper existingPaper = storedPaperDAO.findStoredPaperByUuid(dto.paperUuid());
        if (existingPaper == null) {
            throw new PaperNotFoundException("StoredPaper with paperUuid " + dto.paperUuid() + " not found");
        }

        // Update the paper's body with the new content
        existingPaper.setBody(dto.pdf());
        // Save the updated paper
        StoredPaper updatedPaper = storedPaperDAO.saveStoredPaper(existingPaper);
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

    public void savePaper(PaperContentDTO paperContentDTO) throws PaperDatabaseAccessException {
        try {
            StoredPaper storedPaper = new StoredPaper();
            storedPaper.setPaperUuid(paperContentDTO.paperUuid());
            storedPaper.setBody(paperContentDTO.pdf());
            storedPaperDAO.saveStoredPaper(storedPaper);
        } catch (Exception e) {
            throw new PaperDatabaseAccessException("Failed to save paper in database");
        }
    }

    public byte[] getPaperContent(String paperUuid) throws PaperDatabaseAccessException {
        byte[] storedPaper = storedPaperDAO.findBodyByUuid(paperUuid);
        if (storedPaper == null) {
            throw new PaperDatabaseAccessException("No paper found for uuid " + paperUuid);
        }
        return storedPaper;
    }
}
