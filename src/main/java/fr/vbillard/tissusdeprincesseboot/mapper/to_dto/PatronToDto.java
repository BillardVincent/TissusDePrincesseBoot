package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dao.FournitureRequiseDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.DestinationSetter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

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
		typeMap.addMapping(Patron::getSupportType,
        (DestinationSetter<PatronDto, SupportTypeEnum>) PatronDto::setTypeSupport);

    }

}
