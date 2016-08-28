package com.readthisstuff.rts.web.rest.mapper;

import com.readthisstuff.rts.domain.*;
import com.readthisstuff.rts.web.rest.dto.StuffDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Stuff and its DTO StuffDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StuffMapper {

    StuffDTO stuffToStuffDTO(Stuff stuff);

    List<StuffDTO> stuffsToStuffDTOs(List<Stuff> stuffs);

    Stuff stuffDTOToStuff(StuffDTO stuffDTO);

    List<Stuff> stuffDTOsToStuffs(List<StuffDTO> stuffDTOs);
}
