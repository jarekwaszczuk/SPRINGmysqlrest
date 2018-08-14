package pl.jarek.mysqlrest;

public interface Converter<ENTITY, DTO> {

    ENTITY toEntity(DTO dto);

    DTO toDTO(ENTITY entity);

}
