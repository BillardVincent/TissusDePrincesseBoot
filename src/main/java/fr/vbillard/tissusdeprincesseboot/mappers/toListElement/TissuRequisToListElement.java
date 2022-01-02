package fr.vbillard.tissusdeprincesseboot.mappers.toListElement;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dtosFx.ListElement;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import fr.vbillard.tissusdeprincesseboot.services.TissuVariantService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class TissuRequisToListElement extends TypeMapConfigurer<TissuRequisDto, ListElement> {
	
	@Lazy
	private TissuVariantService tvs;
	
	private final int MAX_VARIANT_DISPLAYED = 3;
	
    @Override
    public void configure(TypeMap<TissuRequisDto, ListElement> typeMap) {
        typeMap.addMappings(mapper -> mapper.using(new DimensionsConverter()).map(src -> src , ListElement::setDimensions));
        typeMap.addMappings(mapper -> mapper.using(new TitreConverter()).map(src -> src , ListElement::setTitre));
        typeMap.addMappings(mapper -> mapper.using(new DescConverter()).map(src -> src , ListElement::setDescription));
    }
    
    private class DimensionsConverter extends AbstractConverter<TissuRequisDto, String> {
        @Override
        protected String convert(TissuRequisDto t) {
        	String dimension;
        	if (t.getLongueur() == 0 && t.getLaize() == 0) {
        		dimension = "non communiqué";
        	} else {
        		dimension = t.getLongueur() + " cm x " +t.getLaize() + " cm";
        	}
            return dimension;
        }
    }
    
    private class TitreConverter extends AbstractConverter<TissuRequisDto, String> {
        @Override
        protected String convert(TissuRequisDto t) {
        	return "Tissu " + t.getGammePoids();
        }
    }
    
    private class DescConverter extends AbstractConverter<TissuRequisDto, String> {
        @Override
        protected String convert(TissuRequisDto t) {
        	StringBuilder description = new StringBuilder();
        	
        	List<TissuVariant> variants = tvs.getVariantByTissuRequisId(t.getId());

        	for (int i=0; i > variants.size() || i > MAX_VARIANT_DISPLAYED;) {
        		TissuVariant tv = variants.get(i++);
        		description.append("(").append(i).append(") ")
        			.append(tv.getMatiere()).append(" ").append(tv.getTypeTissu());
        		if(i != variants.size()) {
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
