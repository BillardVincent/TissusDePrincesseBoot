package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dao.TissuDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.utils.color.ColorUtils;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.DestinationSetter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TissuToDto extends TypeMapConfigurer<Tissu, TissuDto> {

	TissuDao tissuDao;

	private static final Logger LOGGER = LogManager.getLogger(TissuToDto.class);

	@Override
	public void configure(TypeMap<Tissu, TissuDto> typeMap) {
		typeMap.addMappings(mapping -> mapping.using(new LongueurRestanteConverter()).map(src -> src,
				TissuDto::setLongueurRestante));
		typeMap.addMapping(src -> src.getMatiere().getValue(), TissuDto::setMatiere);
		typeMap.addMapping(src -> src.getTissage().getValue(), TissuDto::setTissage);

		typeMap.setPostConverter(context -> {
			if (context.getSource().getColor() != null){
				context.getDestination().setColor(ColorUtils.entityToColor(context.getSource().getColor()));
				context.getDestination().colorId = context.getSource().getColor().getId();
			}
			return context.getDestination();
		});

		typeMap.addMapping(Tissu::getTypeTissu, (DestinationSetter<TissuDto, TypeTissuEnum>) TissuDto::setTypeTissu);

	}

	private class LongueurRestanteConverter extends AbstractConverter<Tissu, Integer> {
		@Override
		protected Integer convert(Tissu source) {
			int longueurRestante = source.getLongueur();
			if (source.getId() != 0) {
				try {
					Integer utilise = tissuDao.longueurUtilisee(source.getId());
					if (utilise != null) {
						longueurRestante -= utilise;
					}
				} catch (Exception e) {
					LOGGER.error(e);
				}
			}
			return longueurRestante;
		}
	}
}
