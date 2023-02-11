package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FournitureRequiseToDto extends TypeMapConfigurer<FournitureRequise, FournitureRequiseDto> {

	@Override
	public void configure(TypeMap<FournitureRequise, FournitureRequiseDto> typeMap) {
		typeMap.addMapping(FournitureRequise::getQuantiteSecMin, FournitureRequiseDto::setQuantiteSecondaireMin);
		typeMap.addMapping(FournitureRequise::getQuantiteSecMax, FournitureRequiseDto::setQuantiteSecondaireMax);

	}
	
}
