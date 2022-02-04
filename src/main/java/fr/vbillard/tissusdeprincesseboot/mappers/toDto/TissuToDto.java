package fr.vbillard.tissusdeprincesseboot.mappers.toDto;

import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dao.TissuUsedDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TissuToDto extends TypeMapConfigurer<Tissu, TissuDto> {

    @Lazy
    TissuUsedDao tUsedService ;

    @Override
    public void configure(TypeMap<Tissu, TissuDto> typeMap) {
        typeMap.addMappings(mapping -> mapping.using(new LongueurRestanteConverter()).map(src -> src, TissuDto::setLongueurRestante));
        typeMap.addMapping(src -> src.getMatiere().getValue(), TissuDto::setMatiere);
        typeMap.addMapping(src -> src.getTypeTissu().getValue(), TissuDto::setType);
        typeMap.addMapping(src -> src.getTissage().getValue(), TissuDto::setTissage);
    }

    private class LongueurRestanteConverter extends AbstractConverter<Tissu, Integer> {
        @Override
        protected Integer convert(Tissu source) {

            int longueurRestante = source.getLongueur();
            for (
            		// todo only if project is in particular state
                TissuUsed tu : tUsedService.getAllByTissu(source)) {
                longueurRestante -= tu.getLongueur();

            }
            return longueurRestante;
        }
    }
}
