package fr.vbillard.tissusdeprincesseboot.mapper.from_dto;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DtoToFournitureRequise extends TypeMapConfigurer<FournitureRequiseDto, FournitureRequise>{

	@Override
	public void configure(TypeMap<FournitureRequiseDto, FournitureRequise> typeMap) {
		typeMap.addMapping(FournitureRequiseDto::getQuantiteSecondaireMin, FournitureRequise::setQuantiteSecMin);
		typeMap.addMapping(FournitureRequiseDto::getQuantiteSecondaireMax, FournitureRequise::setQuantiteSecMax);

	}

}
