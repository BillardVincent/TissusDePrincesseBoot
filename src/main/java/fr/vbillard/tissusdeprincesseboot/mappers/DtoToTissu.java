package fr.vbillard.tissusdeprincesseboot.mappers;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dao.MatiereDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissageDao;
import fr.vbillard.tissusdeprincesseboot.dao.TypeTissuDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.services.MatiereService;
import fr.vbillard.tissusdeprincesseboot.services.TissageService;
import fr.vbillard.tissusdeprincesseboot.services.TypeTissuService;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;

@Component
public class DtoToTissu extends TypeMapConfigurer<TissuDto, Tissu> {
    @Lazy
    TypeTissuDao tts ;
    @Lazy
    MatiereDao ms ;
    @Lazy
    TissageDao ts ;

    @Override
    public void configure(TypeMap<TissuDto, Tissu> typeMap) {
        typeMap.addMappings(mapper -> mapper.using(new IdConverter()).map(src -> src, Tissu::setId));
        typeMap.addMappings(mapper -> mapper.using(new ReferenceConverter()).map(src -> src, Tissu::setReference));
        typeMap.addMappings(mapper -> mapper.using(new UnitePoidsConverter()).map(src -> src, Tissu::setUnitePoids));
        typeMap.addMappings(mapper -> mapper.using(new MatiereConverter()).map(src -> src, Tissu::setMatiere));

        typeMap.setPostConverter(context -> {
            context.getDestination().setTypeTissu(
                    tts.getByValue(context.getSource().getType()));
            return context.getDestination();
        });
        typeMap.setPostConverter(context -> {
            context.getDestination().setTissage(
                    ts.getByValue(context.getSource().getTissage()));
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
            Matiere m = ms.getByValue(source.getMatiere());
            return m;
        }
    }
}