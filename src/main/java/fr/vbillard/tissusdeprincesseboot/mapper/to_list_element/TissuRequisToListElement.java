package fr.vbillard.tissusdeprincesseboot.mapper.to_list_element;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ListElement;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import lombok.AllArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TissuRequisToListElement extends TypeMapConfigurer<TissuRequisDto, ListElement> {


	private static final int MAX_VARIANT_DISPLAYED = 3;
	private static final String NON_RENSEIGNE = "non renseigné";

	@Override
	public void configure(TypeMap<TissuRequisDto, ListElement> typeMap) {
		typeMap.addMappings(
				mapper -> mapper.using(new DimensionsConverter()).map(src -> src, ListElement::setDimensions));
		//typeMap.addMappings(mapper -> mapper.using(new TitreConverter()).map(src -> src, ListElement::setTitre));
		typeMap.addMappings(mapper -> mapper.using(new DescConverter()).map(src -> src, ListElement::setDescription));
		typeMap.addMappings(mapper -> mapper.using(new RefConverter()).map(src -> src, ListElement::setReference));
	}

	private class DimensionsConverter extends AbstractConverter<TissuRequisDto, String> {
		@Override
		protected String convert(TissuRequisDto t) {
			// TODO patron version

			String dimension;
			//if (t.getLongueur() == 0 && t.getLaize() == 0) {
				dimension = NON_RENSEIGNE;
			/*} else {
				dimension = t.getLongueur() + " cm x " + t.getLaize() + " cm";
			}

			 */
			return dimension;
		}
	}
/*
	private class TitreConverter extends AbstractConverter<TissuRequisDto, String> {
		@Override
		protected String convert(TissuRequisDto t) {
			return "Poids : " + (t.getGammePoids().equals("N/A") ? t.getGammePoids() : NON_RENSEIGNE);
		}
	}

 */

	private class RefConverter extends AbstractConverter<TissuRequisDto, String> {
		@Override
		protected String convert(TissuRequisDto t) {
			return "Tissu";
		}
	}

	private class DescConverter extends AbstractConverter<TissuRequisDto, String> {
		@Override
		protected String convert(TissuRequisDto t) {
			// TODO ?
			return null;
		}
	}

}
