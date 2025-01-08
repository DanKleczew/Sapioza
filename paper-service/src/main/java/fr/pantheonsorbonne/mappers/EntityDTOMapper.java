package fr.pantheonsorbonne.mappers;

public interface EntityDTOMapper<D, E> {
    public D mapEntityToDTO(E entity);
    public E mapDTOToEntity(D dto);
}
