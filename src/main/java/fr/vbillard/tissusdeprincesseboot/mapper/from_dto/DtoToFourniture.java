package fr.vbillard.tissusdeprincesseboot.mapper.from_dto;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Quantite;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
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
		typeMap.addMappings(mapper -> mapper.using(new QuantitePrimConverter()).map(src -> src,
				Fourniture::setQuantitePrincipale));
		typeMap.addMappings(mapper -> mapper.using(new QuantiteSecConverter()).map(src -> src,
				Fourniture::setQuantiteSecondaire));

	}

	private static class IdConverter extends AbstractConverter<FournitureDto, Integer> {
		@Override
		protected Integer convert(FournitureDto dto) {
			return dto.getIdProperty() == null ? 0 : dto.getId();
		}
	}

	private static class ReferenceConverter extends AbstractConverter<FournitureDto, String> {
		@Override
		protected String convert(FournitureDto dto) {
			return dto.getReferenceProperty() == null ? Strings.EMPTY : dto.getReference();
		}
	}

	private static class UniteConverter extends AbstractConverter<String, Unite> {
		@Override
		protected Unite convert(String source) {
			return Unite.getEnum(source);
		}
	}
	
	private static class QuantitePrimConverter extends AbstractConverter<FournitureDto, Quantite> {
		@Override
		protected Quantite convert(FournitureDto source) {
			if (Strings.isEmpty(source.getUnite()) || source.getUnite().equals(Unite.NON_RENSEIGNE.getLabel()) ) {

				Quantite quantite = new Quantite();
				//if ()
				//return 0f;
				return quantite;
			}
			//return source.getQuantite() / Unite.getEnum(source.getUnite()).getFacteur();
			return null;
		}
	}
	
	private static class QuantiteSecConverter extends AbstractConverter<FournitureDto, Quantite> {
		@Override
		protected Quantite convert(FournitureDto source) {
			return null;
			/*
			if (Strings.isEmpty(source.getUniteSecondaire()) || source.getUniteSecondaire().equals(Unite.NON_RENSEIGNE.getLabel()) ) {
				return 0f;
			}
			return source.getQuantiteSecondaire() / Unite.getEnum(source.getUniteSecondaire()).getFacteur();
			
			 */
		}
	}

}