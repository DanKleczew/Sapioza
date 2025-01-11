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

@ApplicationScoped
public class StoredPaperService {

    @Inject
    StoredPaperDAO storedPaperDAO;

    @Inject
    StoredPaperMapper storedPaperMapper;

    public void createStoredPaper(StoredPaperInputDTO dto) throws PaperDatabaseAccessException {
        try {
            StoredPaper storedPaper = storedPaperMapper.mapInputDTOToEntity(dto);
            storedPaperDAO.saveStoredPaper(storedPaper);
        } catch (Exception e) {
            throw new PaperDatabaseAccessException("Failed to save StoredPaper", e);
        }
    }

    public StoredPaperOutputDTO getStoredPaper(Long id) throws PaperNotFoundException {
        StoredPaper storedPaper = storedPaperDAO.findStoredPaperById(id);
        if (storedPaper == null) {
            throw new PaperNotFoundException("StoredPaper with ID " + id + " not found");
        }
        return storedPaperMapper.mapEntityToOutputDTO(storedPaper);
    }
}

