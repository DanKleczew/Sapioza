package fr.pantheonsorbonne.global;

public interface EntityDTOMapper<D, E> {
    public D mapEntityToDTO(E entity);
    public E mapDTOToEntity(D dto);
}
