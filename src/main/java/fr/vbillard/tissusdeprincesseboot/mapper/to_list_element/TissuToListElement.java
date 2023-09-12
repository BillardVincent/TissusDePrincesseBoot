package fr.vbillard.tissusdeprincesseboot.mapper.to_list_element;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ListElement;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;


@Component
public class TissuToListElement extends TypeMapConfigurer<Tissu, ListElement> {
    @Override
    public void configure(TypeMap<Tissu, ListElement> typeMap) {
        typeMap.addMappings(mapper -> mapper.using(new DimensionsConverter()).map(src -> src , ListElement::setDimensions));
        typeMap.addMappings(mapper -> mapper.using(new TitreConverter()).map(src -> src , ListElement::setTitre));
    }
    
    private class DimensionsConverter extends AbstractConverter<Tissu, String> {
        @Override
        protected String convert(Tissu t) {
        	String dimension;
        	if (t.getLongueur() == 0 && t.getLaize() == 0) {
        		dimension = "non communiqué";
        	} else {
        		dimension = t.getLongueur() + " cm x " +t.getLaize() + " cm";
        	}
            return dimension;
        }
    }
    
    private class TitreConverter extends AbstractConverter<Tissu, String> {
        @Override
        protected String convert(Tissu t) {
        	return t.getTypeTissu() +" "+t.getMatiere();
        }
    }
}
