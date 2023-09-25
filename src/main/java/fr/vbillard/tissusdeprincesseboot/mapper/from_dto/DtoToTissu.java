package fr.vbillard.tissusdeprincesseboot.mapper.from_dto;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dao.MatiereDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissageDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.ColorEntity;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.utils.color.ColorUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DtoToTissu extends TypeMapConfigurer<TissuDto, Tissu> {
	MatiereDao ms;
	TissageDao ts;

	@Override
	public void configure(TypeMap<TissuDto, Tissu> typeMap) {
		typeMap.addMappings(mapper -> mapper.using(new IdConverter()).map(src -> src, Tissu::setId));
		typeMap.addMappings(mapper -> mapper.using(new ReferenceConverter()).map(src -> src, Tissu::setReference));
		typeMap.addMappings(
				mapper -> mapper.using(new UnitePoidsConverter()).map(TissuDto::getUnitePoids, Tissu::setUnitePoids));
		typeMap.addMappings(mapper -> mapper.using(new MatiereConverter()).map(src -> src, Tissu::setMatiere));
		typeMap.addMappings(
				mapper -> mapper.using(new TypeTissuConverter()).map(TissuDto::getTypeTissu, Tissu::setTypeTissu));

		typeMap.addMappings(
				mapper -> mapper.using(new ColorConverter()).map(src -> src, Tissu::setColor));

		typeMap.setPostConverter(context -> {
			context.getDestination().setTissage(ts.getByValue(context.getSource().getTissage()));
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

	private class ColorConverter extends AbstractConverter<TissuDto, ColorEntity> {
		@Override
		protected ColorEntity convert(TissuDto source) {
			if (source.getColor() == null) {
				return null;
			}

			ColorEntity color = ColorUtils.colorToEntity(source.getColor());
			color.setId(source.colorId);
			return color;
		}
	}
}
