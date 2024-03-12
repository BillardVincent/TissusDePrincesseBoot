package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.DestinationSetter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatronToDto extends TypeMapConfigurer<Patron, PatronDto> {

    @Override
    public void configure(TypeMap<Patron, PatronDto> typeMap) {
		typeMap.addMapping(Patron::getSupportType,
        (DestinationSetter<PatronDto, SupportTypeEnum>) PatronDto::setTypeSupport);

    }

}
