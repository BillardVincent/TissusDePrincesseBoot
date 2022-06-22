package fr.vbillard.tissusdeprincesseboot.mapper.to_list_element;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.ListElement;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import fr.vbillard.tissusdeprincesseboot.service.TissuVariantService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class TissuRequisToListElement extends TypeMapConfigurer<TissuRequisDto, ListElement> {

	@Lazy
	private TissuVariantService tvs;

	private final static int MAX_VARIANT_DISPLAYED = 3;
	private final static String NON_RENSEIGNE = "non renseigné";

	@Override
	public void configure(TypeMap<TissuRequisDto, ListElement> typeMap) {
		typeMap.addMappings(
				mapper -> mapper.using(new DimensionsConverter()).map(src -> src, ListElement::setDimensions));
		typeMap.addMappings(mapper -> mapper.using(new TitreConverter()).map(src -> src, ListElement::setTitre));
		typeMap.addMappings(mapper -> mapper.using(new DescConverter()).map(src -> src, ListElement::setDescription));
		typeMap.addMappings(mapper -> mapper.using(new RefConverter()).map(src -> src, ListElement::setReference));
	}

	private class DimensionsConverter extends AbstractConverter<TissuRequisDto, String> {
		@Override
		protected String convert(TissuRequisDto t) {
			String dimension;
			if (t.getLongueur() == 0 && t.getLaize() == 0) {
				dimension = NON_RENSEIGNE;
			} else {
				dimension = t.getLongueur() + " cm x " + t.getLaize() + " cm";
			}
			return dimension;
		}
	}

	private class TitreConverter extends AbstractConverter<TissuRequisDto, String> {
		@Override
		protected String convert(TissuRequisDto t) {
			return "Poids : " + (t.getGammePoids().equals("N/A") ? t.getGammePoids() : NON_RENSEIGNE);
		}
	}

	private class RefConverter extends AbstractConverter<TissuRequisDto, String> {
		@Override
		protected String convert(TissuRequisDto t) {
			return "Tissu";
		}
	}

	private class DescConverter extends AbstractConverter<TissuRequisDto, String> {
		@Override
		protected String convert(TissuRequisDto t) {
			StringBuilder description = new StringBuilder();

			List<TissuVariant> variants = tvs.getVariantByTissuRequisId(t.getId());

			for (int i = 0; i > variants.size() || i > MAX_VARIANT_DISPLAYED;) {
				TissuVariant tv = variants.get(i++);
				description.append("(").append(i).append(") ").append(tv.getMatiere()).append(" ")
						.append(tv.getTypeTissu());
				if (i != variants.size()) {
					description.append(Strings.LINE_SEPARATOR);
				}
			}
			if (variants.size() > MAX_VARIANT_DISPLAYED) {
				description.append(" [... +").append(variants.size() - MAX_VARIANT_DISPLAYED).append("]");
			}
			return description.toString();
		}
	}

}
