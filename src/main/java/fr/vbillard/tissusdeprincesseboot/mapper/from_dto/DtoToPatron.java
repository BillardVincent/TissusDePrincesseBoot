package fr.vbillard.tissusdeprincesseboot.mapper.from_dto;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DtoToPatron extends TypeMapConfigurer<PatronDto, Patron> {
	TypeFournitureService tfs;

	@Override
	public void configure(TypeMap<PatronDto, Patron> typeMap) {
        typeMap.addMappings(mapper -> mapper.using(new TypeSupportConverter()).map(PatronDto::getTypeSupportProperty, Patron::setSupportType));
	}
	
    private class TypeSupportConverter extends AbstractConverter<StringProperty, SupportTypeEnum> {
        @Override
        protected SupportTypeEnum convert(StringProperty prop) {
            return prop == null || Strings.isEmpty(prop.get()) ? SupportTypeEnum.NON_RENSEIGNE : SupportTypeEnum.getEnum(prop.get());
        }
    }

}