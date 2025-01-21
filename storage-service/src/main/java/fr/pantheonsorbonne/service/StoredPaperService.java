package fr.pantheonsorbonne.service;

import fr.pantheonsorbonne.dao.StoredPaperDAO;
import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.mappers.StoredPaperMapper;
import fr.pantheonsorbonne.model.StoredPaper;
import fr.pantheonsorbonne.exception.PaperNotFoundException;
import fr.pantheonsorbonne.exception.PaperDatabaseAccessException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StoredPaperService {

    @Inject
    StoredPaperDAO storedPaperDAO;

    @Inject
    StoredPaperMapper storedPaperMapper;

    public void updateStoredPaper(PaperContentDTO dto) throws PaperNotFoundException {
        StoredPaper existingPaper = storedPaperDAO.findStoredPaperByUuid(dto.paperUuid());
        if (existingPaper == null) {
            throw new PaperNotFoundException("StoredPaper with paperUuid " + dto.paperUuid() + " not found");
        }

        existingPaper.setBody(dto.pdf());
        storedPaperDAO.saveStoredPaper(existingPaper);
    }

    public void deleteStoredPaper(String paperUuid) throws PaperNotFoundException {
        StoredPaper existingPaper = storedPaperDAO.findStoredPaperByUuid(paperUuid);
        if (existingPaper == null) {
            throw new PaperNotFoundException("StoredPaper with paperUuid " + paperUuid + " not found");
        }
        storedPaperDAO.deleteStoredPaper(existingPaper);
    }

    public void savePaper(PaperContentDTO paperContentDTO) {
        try {
            StoredPaper storedPaper = this.storedPaperMapper.mapDTOToEntity(paperContentDTO);
            storedPaperDAO.saveStoredPaper(storedPaper);
        } catch (Exception e) {
            throw new PaperDatabaseAccessException("Failed to save paper in database");
        }
    }

    public byte[] getPaperContent(String paperUuid) throws PaperNotFoundException{
        byte[] storedPaper = storedPaperDAO.findBodyByUuid(paperUuid);
        if (storedPaper == null) {
            throw new PaperNotFoundException("No paper found for uuid " + paperUuid);
        }
        return storedPaper;
    }
}
