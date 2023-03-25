package fr.vbillard.tissusdeprincesseboot.mapper.to_list_element;

import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ListElement;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class FournitureRequiseToListElement extends TypeMapConfigurer<FournitureRequiseDto, ListElement> {

	private static final String NON_RENSEIGNE = "non renseigné";

	@Override
	public void configure(TypeMap<FournitureRequiseDto, ListElement> typeMap) {
		typeMap.addMappings(
				mapper -> mapper.using(new DimensionsConverter()).map(src -> src, ListElement::setDimensions));
		typeMap.addMappings(mapper -> mapper.using(new TitreConverter()).map(src -> src, ListElement::setTitre));
		// typeMap.addMappings(mapper -> mapper.using(new DescConverter()).map(src ->
		// src, ListElement::setDescription));
		typeMap.addMappings(mapper -> mapper.using(new RefConverter()).map(src -> src, ListElement::setReference));
	}

	private class DimensionsConverter extends AbstractConverter<FournitureRequiseDto, String> {
		@Override
		protected String convert(FournitureRequiseDto f) {
			String dimension;
			if (f.getQuantite() == 0) {
				dimension = NON_RENSEIGNE;
			} else {
				dimension = f.getQuantite() + f.getUnite().getAbbreviation();
			}
			return dimension;
		}
	}

	private class TitreConverter extends AbstractConverter<FournitureRequiseDto, String> {
		@Override
		protected String convert(FournitureRequiseDto f) {
			return f.getTypeName();
		}
	}

	private class RefConverter extends AbstractConverter<FournitureRequiseDto, String> {
		@Override
		protected String convert(FournitureRequiseDto f) {
			return f.getQuantite() + f.getUnite().getAbbreviation();
		}
	}

	/*
	 * private class DescConverter extends AbstractConverter<TissuRequisDto, String>
	 * {
	 * 
	 * @Override protected String convert(TissuRequisDto t) { StringBuilder
	 * description = new StringBuilder();
	 * 
	 * List<TissuVariant> variants = tvs.getVariantByTissuRequisId(t.getId());
	 * 
	 * for (int i = 0; i > variants.size() || i > MAX_VARIANT_DISPLAYED;) {
	 * TissuVariant tv = variants.get(i++);
	 * description.append("(").append(i).append(") ").append(tv.getMatiere()).
	 * append(" ") .append(tv.getTypeTissu()); if (i != variants.size()) {
	 * description.append(Strings.LINE_SEPARATOR); } } if (variants.size() >
	 * MAX_VARIANT_DISPLAYED) { description.append(" [... +").append(variants.size()
	 * - MAX_VARIANT_DISPLAYED).append("]"); } return description.toString(); } }
	 */

}