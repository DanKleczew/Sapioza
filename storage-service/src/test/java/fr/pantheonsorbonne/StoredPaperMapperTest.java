package fr.pantheonsorbonne;

import fr.pantheonsorbonne.dto.StoredPaperInputDTO;
import fr.pantheonsorbonne.dto.StoredPaperOutputDTO;
import fr.pantheonsorbonne.mappers.StoredPaperMapper;
import fr.pantheonsorbonne.model.StoredPaper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoredPaperMapperTest {

    private final StoredPaperMapper mapper = new StoredPaperMapper();

    @Test
    void testMapInputDTOToEntity() {
        StoredPaperInputDTO inputDTO = new StoredPaperInputDTO();
        inputDTO.setId(1L);
        inputDTO.setContent("Sample Content");

        StoredPaper entity = mapper.mapInputDTOToEntity(inputDTO);

        assertEquals(1L, entity.getId());
        assertArrayEquals("Sample Content".getBytes(), entity.getBody());
    }

    @Test
    void testMapEntityToOutputDTO() {
        StoredPaper entity = new StoredPaper();
        entity.setId(1L);
        entity.setBody("Sample Content".getBytes());

        StoredPaperOutputDTO outputDTO = mapper.mapEntityToOutputDTO(entity);

        assertEquals(1L, outputDTO.getId());
        assertEquals("Sample Content", outputDTO.getContent());
    }
}