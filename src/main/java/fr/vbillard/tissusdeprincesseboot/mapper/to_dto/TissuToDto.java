package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dao.TissuDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TissuToDto extends TypeMapConfigurer<Tissu, TissuDto> {

	TissuDao tissuDao;

	@Override
	public void configure(TypeMap<Tissu, TissuDto> typeMap) {
		typeMap.addMappings(mapping -> mapping.using(new LongueurRestanteConverter()).map(src -> src,
				TissuDto::setLongueurRestante));
		typeMap.addMapping(src -> src.getMatiere().getValue(), TissuDto::setMatiere);
		typeMap.addMapping(src -> src.getTissage().getValue(), TissuDto::setTissage);
		typeMap.addMapping(Tissu::getTypeTissu, (TissuDto dest, TypeTissuEnum v) -> dest.setTypeTissu(v));

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
					e.printStackTrace();
				}
			}
			return longueurRestante;
		}
	}
}