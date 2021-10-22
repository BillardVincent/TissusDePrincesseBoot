package fr.vbillard.tissusdeprincesseboot.factory;

import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;

import static fr.vbillard.tissusdeprincesseboot.utils.RandomUtils.FAKER;

public class TissuVariantDtoFactory {

    public static TissuVariantDto newDto() {
        return newDto(new FactoryContext());
    }

    public static TissuVariantDto newDto(FactoryContext context) {
        TissuVariantDto dto = new TissuVariantDto();
        context.setVariantDto(dto);
        dto.setId(FAKER.random().nextInt(1, 100));
        dto.setTissuRequisId(context.getTissuRequis().getId());
        dto.setMatiere(FAKER.harryPotter().spell());
        dto.setType(FAKER.harryPotter().book());
        dto.setTissage(FAKER.harryPotter().house());

        return dto;
    }

}
