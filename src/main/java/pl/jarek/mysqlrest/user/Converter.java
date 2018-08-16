package pl.jarek.mysqlrest.user;

public interface Converter<ENTITY, DTO> {

    ENTITY toEntity(DTO dto);

    DTO toDTO(ENTITY entity);

}
