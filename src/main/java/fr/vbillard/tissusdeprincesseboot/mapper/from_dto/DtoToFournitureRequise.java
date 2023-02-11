package fr.vbillard.tissusdeprincesseboot.mapper.from_dto;

import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dao.PatronDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
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
