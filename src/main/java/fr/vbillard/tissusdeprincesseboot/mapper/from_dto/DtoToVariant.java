package fr.vbillard.tissusdeprincesseboot.mapper.from_dto;

import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dao.MatiereDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissageDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DtoToVariant extends TypeMapConfigurer<TissuVariantDto, TissuVariant> {
	MatiereDao ms;
	TissageDao ts;

	@Override
	public void configure(TypeMap<TissuVariantDto, TissuVariant> typeMap) {
		typeMap.addMappings(mapper -> mapper.using(new MatiereConverter()).map(src -> src, TissuVariant::setMatiere));
		typeMap.addMappings(mapper -> mapper.using(new TypeTissuConverter()).map(TissuVariantDto::getTypeTissu,
				TissuVariant::setTypeTissu));

		typeMap.setPostConverter(context -> {
			context.getDestination().setTissage(ts.getByValue(context.getSource().getTissage()));
			return context.getDestination();
		});
	}

	private class TypeTissuConverter extends AbstractConverter<String, TypeTissuEnum> {
		@Override
		protected TypeTissuEnum convert(String source) {
			return TypeTissuEnum.getEnum(source);
		}
	}

	private class MatiereConverter extends AbstractConverter<TissuDto, Matiere> {
		@Override
		protected Matiere convert(TissuDto source) {
			return ms.getByValue(source.getMatiere());
		}
	}

}
