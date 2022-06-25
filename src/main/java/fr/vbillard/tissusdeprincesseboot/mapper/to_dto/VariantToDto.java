package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;

@Component
public class VariantToDto extends TypeMapConfigurer<TissuVariant, TissuVariantDto> {
	@Override
	public void configure(TypeMap<TissuVariant, TissuVariantDto> typeMap) {
		typeMap.addMapping(TissuVariant::getMatiere,
				(TissuVariantDto dest, Matiere v) -> dest.setMatiere(v == null ? Strings.EMPTY : v.getValue()));
		typeMap.addMapping(TissuVariant::getTissage,
				(TissuVariantDto dest, Tissage v) -> dest.setTissage(v == null ? Strings.EMPTY : v.getValue()));
	}
}
