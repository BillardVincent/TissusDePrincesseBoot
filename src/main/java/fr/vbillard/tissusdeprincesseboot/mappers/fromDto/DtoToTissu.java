package fr.vbillard.tissusdeprincesseboot.mappers.fromDto;

import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dao.MatiereDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissageDao;
import fr.vbillard.tissusdeprincesseboot.dao.TypeTissuDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;

import lombok.AllArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;

@Component
@AllArgsConstructor
public class DtoToTissu extends TypeMapConfigurer<TissuDto, Tissu> {
    TypeTissuDao tts ;
    MatiereDao ms ;
    TissageDao ts ;

    @Override
    public void configure(TypeMap<TissuDto, Tissu> typeMap) {
        typeMap.addMappings(mapper -> mapper.using(new IdConverter()).map(src -> src, Tissu::setId));
        typeMap.addMappings(mapper -> mapper.using(new ReferenceConverter()).map(src -> src, Tissu::setReference));
        typeMap.addMappings(mapper -> mapper.using(new UnitePoidsConverter()).map(TissuDto::getUnitePoids, Tissu::setUnitePoids));
        typeMap.addMappings(mapper -> mapper.using(new MatiereConverter()).map(src -> src, Tissu::setMatiere));


        typeMap.setPostConverter(context -> {
            context.getDestination().setTissage(
                    ts.getByValue(context.getSource().getTissage()));
            context.getDestination().setTypeTissu(
                    tts.getByValue(context.getSource().getType()));
            return context.getDestination();
        });
    }

    private class IdConverter extends AbstractConverter<TissuDto, Integer> {
        @Override
        protected Integer convert(TissuDto dto) {
            return dto.getIdProperty() == null ? 0 : dto.getId();
        }
    }

    private class ReferenceConverter extends AbstractConverter<TissuDto, String> {
        @Override
        protected String convert(TissuDto dto) {
            return dto.getReferenceProperty() == null ? "" : dto.getReference();
        }
    }

    private class UnitePoidsConverter extends AbstractConverter<String, UnitePoids> {
        @Override
        protected UnitePoids convert(String source) {
            return UnitePoids.getEnum(source);
        }
    }

    private class MatiereConverter extends AbstractConverter<TissuDto, Matiere>{
        @Override
        protected Matiere convert(TissuDto source) {
            return ms.getByValue(source.getMatiere());
        }
    }
}