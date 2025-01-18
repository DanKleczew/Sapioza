package fr.pantheonsorbonne.mappers;

import fr.pantheonsorbonne.dto.StoredPaperInputDTO;
import fr.pantheonsorbonne.dto.StoredPaperOutputDTO;
import fr.pantheonsorbonne.model.StoredPaper;
import jakarta.enterprise.context.ApplicationScoped;

import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class StoredPaperMapper {

    public StoredPaper mapInputDTOToEntity(StoredPaperInputDTO dto) {
        StoredPaper storedPaper = new StoredPaper();
        storedPaper.setPaperUuid(dto.getId());
        storedPaper.setBody(dto.getContent());
        return storedPaper;
    }

    public StoredPaperOutputDTO mapEntityToOutputDTO(StoredPaper entity) {
        byte[] content = entity.getBody();
        return new StoredPaperOutputDTO(entity.getPaperUuid(), content);
    }
}

