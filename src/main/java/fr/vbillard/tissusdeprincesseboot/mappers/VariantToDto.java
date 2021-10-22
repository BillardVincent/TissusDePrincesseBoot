package fr.vbillard.tissusdeprincesseboot.mappers;

import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import org.modelmapper.TypeMap;

@Component
public class VariantToDto extends TypeMapConfigurer<TissuVariant, TissuVariantDto> {
    @Override
    public void configure(TypeMap<TissuVariant, TissuVariantDto> typeMap) {
        typeMap.addMapping(TissuVariant::getTissuRequis, (dest, v) -> dest.setTissuRequisId((int)v));
        typeMap.setPostConverter(context -> {
            context.getDestination().setMatiere(context.getSource().getMatiere() == null ? "" : context.getSource().getMatiere().getValue());
            context.getDestination().setTissage(context.getSource().getTissage() == null ? "" :  context.getSource().getTissage().getValue());
            context.getDestination().setType(context.getSource().getTypeTissu() == null ? "" :  context.getSource().getTypeTissu().getValue());
            return context.getDestination();
        });
    }
}
