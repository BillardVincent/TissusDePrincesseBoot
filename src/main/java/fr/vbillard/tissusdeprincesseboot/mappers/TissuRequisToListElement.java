package fr.vbillard.tissusdeprincesseboot.mappers;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dtosFx.ListElement;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import fr.vbillard.tissusdeprincesseboot.services.TissuVariantService;


@Component
public class TissuRequisToListElement extends TypeMapConfigurer<TissuRequis, ListElement> {
	
	@Lazy
	private TissuVariantService tvs;
	
	private final int MAX_VARIANT_DISPLAYED = 3;
	
    @Override
    public void configure(TypeMap<TissuRequis, ListElement> typeMap) {
        typeMap.addMappings(mapper -> mapper.using(new DimensionsConverter()).map(src -> src , ListElement::setDimensions));
        typeMap.addMappings(mapper -> mapper.using(new TitreConverter()).map(src -> src , ListElement::setTitre));
        typeMap.addMappings(mapper -> mapper.using(new DescConverter()).map(src -> src , ListElement::setDescription));
    }
    
    private class DimensionsConverter extends AbstractConverter<TissuRequis, String> {
        @Override
        protected String convert(TissuRequis t) {
        	String dimension;
        	if (t.getLongueur() == 0 && t.getLaize() == 0) {
        		dimension = "non communiqué";
        	} else {
        		dimension = t.getLongueur() + " cm x " +t.getLaize() + " cm";
        	}
            return dimension;
        }
    }
    
    private class TitreConverter extends AbstractConverter<TissuRequis, String> {
        @Override
        protected String convert(TissuRequis t) {
        	return "Tissu " + t.getGammePoids().label;
        }
    }
    
    private class DescConverter extends AbstractConverter<TissuRequis, String> {
        @Override
        protected String convert(TissuRequis t) {
        	StringBuilder description = new StringBuilder();
        	
        	List<TissuVariant> variants = tvs.getVariantByTissuRequis(t);

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
