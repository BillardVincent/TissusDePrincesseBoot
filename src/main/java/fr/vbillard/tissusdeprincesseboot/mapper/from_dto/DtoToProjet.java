package fr.vbillard.tissusdeprincesseboot.mapper.from_dto;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class DtoToProjet extends TypeMapConfigurer<ProjetDto, Projet> {
    @Override
    public void configure(TypeMap<ProjetDto, Projet> typeMap) {
        typeMap.addMappings(mapper -> mapper.using(new StatusConverter()).map(dto -> dto, Projet::setStatus));
        typeMap.addMappings(mapper -> mapper.using(new PatronConverter()).map(ProjetDto::getPatronVersion,
            Projet::setPatronVersion));
    }

    private static class StatusConverter extends AbstractConverter<ProjetDto, ProjectStatus> {
        @Override
        protected ProjectStatus convert(ProjetDto dto) {
            return dto.getProjectStatusProperty() == null || dto.getProjectStatus().equals(Strings.EMPTY) ?
                    ProjectStatus.BROUILLON : ProjectStatus.getEnum(dto.getProjectStatus());
        }
    }

    private static class PatronConverter extends AbstractConverter<PatronVersionDto, PatronVersion> {
        @Override
        protected PatronVersion convert(PatronVersionDto dto) {
            return dto == null ? new PatronVersion() : new ModelMapper().map(dto, PatronVersion.class);
        }
    }
}
