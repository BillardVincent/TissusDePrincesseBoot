package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureRequiseDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

@Component
@AllArgsConstructor
public class PatronToDto extends TypeMapConfigurer<Patron, PatronDto> {

    @Lazy
    TissusRequisDao trs;
    @Lazy
    FournitureRequiseDao frs;
    
    @Lazy
    ModelMapper mapper;

    @Override
    public void configure(TypeMap<Patron, PatronDto> typeMap) {
		typeMap.addMapping(Patron::getSupportType, (PatronDto d, SupportTypeEnum v) -> d.setTypeSupport(v));
        typeMap.setPostConverter(context -> {
        	List<TissuRequisDto> trLst = null;
        	if (context.getSource().getId() != 0){
                trLst = trs.getAllByPatronId(
                		context.getSource().getId())
                		.stream().map(tr-> mapper.map(tr, TissuRequisDto.class ))
                		.collect(Collectors.toList());
            }
            context.getDestination().setTissusRequis(trLst);

        	List<FournitureRequiseDto> frLst = null;
        	if (context.getSource().getId() != 0){
        		frLst = frs.getAllByPatronId(
                		context.getSource().getId())
                		.stream().map(fr-> mapper.map(fr, FournitureRequiseDto.class ))
                		.collect(Collectors.toList());
            }
            context.getDestination().setFournituresRequises(frLst);
            
            return context.getDestination();
        });
    }

}
