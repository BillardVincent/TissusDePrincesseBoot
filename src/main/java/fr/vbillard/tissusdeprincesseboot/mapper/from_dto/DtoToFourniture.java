package fr.vbillard.tissusdeprincesseboot.mapper.from_dto;

import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DtoToFourniture extends TypeMapConfigurer<FournitureDto, Fourniture> {
	TypeFournitureService tfs;

	@Override
	public void configure(TypeMap<FournitureDto, Fourniture> typeMap) {
		typeMap.addMappings(mapper -> mapper.using(new IdConverter()).map(src -> src, Fourniture::setId));
		typeMap.addMappings(mapper -> mapper.using(new ReferenceConverter()).map(src -> src, Fourniture::setReference));
		typeMap.addMappings(
				mapper -> mapper.using(new UniteConverter()).map(FournitureDto::getUnite, Fourniture::setUnite));
		typeMap.addMappings(mapper -> mapper.using(new TypeConverter()).map(src -> src, Fourniture::setType));
		
	}

	private class IdConverter extends AbstractConverter<FournitureDto, Integer> {
		@Override
		protected Integer convert(FournitureDto dto) {
			return dto.getIdProperty() == null ? 0 : dto.getId();
		}
	}

	private class ReferenceConverter extends AbstractConverter<FournitureDto, String> {
		@Override
		protected String convert(FournitureDto dto) {
			return dto.getReferenceProperty() == null ? "" : dto.getReference();
		}
	}

	private class UniteConverter extends AbstractConverter<String, Unite> {
		@Override
		protected Unite convert(String source) {
			return Unite.getEnum(source);
		}
	}

	private class TypeConverter extends AbstractConverter<FournitureDto, TypeFourniture> {
		@Override
		protected TypeFourniture convert(FournitureDto source) {
			return tfs.findTypeFourniture(source.getType());
		}
	}
}