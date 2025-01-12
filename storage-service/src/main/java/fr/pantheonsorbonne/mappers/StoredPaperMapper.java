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
        storedPaper.setId(dto.getId());
        storedPaper.setBody(dto.getContent().getBytes(StandardCharsets.UTF_8)); // String vers byte[]
        return storedPaper;
    }

    public StoredPaperOutputDTO mapEntityToOutputDTO(StoredPaper entity) {
        String content = new String(entity.getBody(), StandardCharsets.UTF_8); // byte[] vers String
        return new StoredPaperOutputDTO(entity.getId(), content);
    }
}

