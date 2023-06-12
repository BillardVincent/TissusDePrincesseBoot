package fr.vbillard.tissusdeprincesseboot.mapper.from_dto;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dao.PatronDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import lombok.AllArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DtoToRequis extends TypeMapConfigurer<TissuRequisDto, TissuRequis>{

	private PatronDao patronDao;
	
	@Override
	public void configure(TypeMap<TissuRequisDto, TissuRequis> typeMap) {

    typeMap.addMappings(mapper -> mapper.using(new IdConverter()).map(src -> src, TissuRequis::setId));

        //TODO ??!!
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
