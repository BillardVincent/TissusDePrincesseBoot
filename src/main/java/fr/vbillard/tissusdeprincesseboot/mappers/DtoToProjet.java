package fr.vbillard.tissusdeprincesseboot.mappers;

import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;


@Component
public class DtoToProjet extends TypeMapConfigurer<ProjetDto, Projet> {
    @Override
    public void configure(TypeMap<ProjetDto, Projet> typeMap) {
        typeMap.addMappings(mapper -> mapper.using(new StatusConverter()).map(dto -> dto, Projet::setStatus));
        typeMap.addMappings(mapper -> mapper.using(new PatronConverter()).map(ProjetDto::getPatron, Projet::setPatron));
    }

    private class StatusConverter extends AbstractConverter<ProjetDto, ProjectStatus> {
        @Override
        protected ProjectStatus convert(ProjetDto dto) {
            return dto.getProjectStatusProperty() == null || dto.getProjectStatus() == "" ? ProjectStatus.BROUILLON : ProjectStatus.getEnum(dto.getProjectStatus());
        }
    }

    private class PatronConverter extends AbstractConverter<PatronDto, Patron> {
        @Override
        protected Patron convert(PatronDto dto) {
            return dto == null ? new Patron() : new ModelMapper().map(dto, Patron.class);
        }
    }
}
