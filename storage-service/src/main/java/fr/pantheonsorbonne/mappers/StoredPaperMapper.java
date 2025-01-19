package fr.pantheonsorbonne.mappers;

import fr.pantheonsorbonne.global.EntityDTOMapper;
import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.model.StoredPaper;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StoredPaperMapper implements EntityDTOMapper<PaperContentDTO, StoredPaper> {


    @Override
    public PaperContentDTO mapEntityToDTO(StoredPaper entity) {
        return new PaperContentDTO(entity.getPaperUuid(), entity.getBody());
    }

    @Override
    public StoredPaper mapDTOToEntity(PaperContentDTO dto) {
        StoredPaper storedPaper = new StoredPaper();
        storedPaper.setPaperUuid(dto.paperUuid());
        storedPaper.setBody(dto.pdf());
        return storedPaper;
    }
}

