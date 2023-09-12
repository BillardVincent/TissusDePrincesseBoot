package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.DestinationSetter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjetToDto extends TypeMapConfigurer<Projet, ProjetDto> {


    @Override
    public void configure(TypeMap<Projet, ProjetDto> typeMap) {
        typeMap.addMapping(Projet::getStatus, (DestinationSetter<ProjetDto, ProjectStatus>) ProjetDto::setProjectStatus);
    }

}
