package fr.vbillard.tissusdeprincesseboot.mappers.fromDto;

import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dao.PatronDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DtoToRequis extends TypeMapConfigurer<TissuRequisDto, TissuRequis>{

	private PatronDao patronDao;
	
	@Override
	public void configure(TypeMap<TissuRequisDto, TissuRequis> typeMap) {
		
		typeMap.addMappings(mapper -> mapper.using(new IdConverter()).map(src -> src, TissuRequis::setId));
        typeMap.addMappings(mapper -> mapper.using(new GammePoidsConverter()).map(TissuRequisDto::getGammePoids, TissuRequis::setGammePoids));


        typeMap.setPostConverter(context -> {
    		Patron patron = patronDao.getById(context.getSource().getPatronId());
            return context.getDestination();
        });
    }

    private class IdConverter extends AbstractConverter<TissuRequisDto, Integer> {
        @Override
        protected Integer convert(TissuRequisDto dto) {
            return dto.getIdProperty() == null ? 0 : dto.getId();
        }
    }


    private class GammePoidsConverter extends AbstractConverter<String, GammePoids> {
        @Override
        protected GammePoids convert(String source) {
            return GammePoids.getEnum(source);
        }
    }


}
