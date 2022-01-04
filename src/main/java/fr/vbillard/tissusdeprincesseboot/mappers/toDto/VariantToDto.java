package fr.vbillard.tissusdeprincesseboot.mappers.toDto;

import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.TypeTissu;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.TypeMap;

@Component
public class VariantToDto extends TypeMapConfigurer<TissuVariant, TissuVariantDto> {
    @Override
    public void configure(TypeMap<TissuVariant, TissuVariantDto> typeMap) {
        typeMap.addMapping(TissuVariant::getMatiere, (TissuVariantDto dest, Matiere v) -> dest.setMatiere(v == null ? Strings.EMPTY : v.getValue()));
        typeMap.addMapping(TissuVariant::getTypeTissu, (TissuVariantDto dest, TypeTissu v) -> dest.setType(v == null ? Strings.EMPTY : v.getValue()));
        typeMap.addMapping(TissuVariant::getTissage, (TissuVariantDto dest, Tissage v) -> dest.setTissage(v == null ? Strings.EMPTY : v.getValue()));
    }
}
