package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;

@Component
public class RequisToDto extends TypeMapConfigurer<TissuRequis, TissuRequisDto>{

	@Override
	public void configure(TypeMap<TissuRequis, TissuRequisDto> typeMap) {

		typeMap.addMapping(src -> src.getVersion().getId(), TissuRequisDto::setVersionId);
		typeMap.setPostConverter(context -> {
			if (!CollectionUtils.isEmpty(context.getSource().getTissages()))
					context.getDestination().setTissage(context.getSource().getTissages().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList()));
			if (!CollectionUtils.isEmpty(context.getSource().getMatieres()))
				context.getDestination().setMatiere(context.getSource().getMatieres().stream().map(AbstractSimpleValueEntity::getValue).collect(Collectors.toList()));
		return context.getDestination();
		});

	}

}
