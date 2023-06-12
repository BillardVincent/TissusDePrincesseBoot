package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;

@Component
public class RequisToDto extends TypeMapConfigurer<TissuRequis, TissuRequisDto>{

	@Override
	public void configure(TypeMap<TissuRequis, TissuRequisDto> typeMap) {

		typeMap.addMapping(src -> src.getVersion().getPatron().getId(), TissuRequisDto::setPatronId);
	}

}
