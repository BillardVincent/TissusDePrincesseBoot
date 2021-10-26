package fr.vbillard.tissusdeprincesseboot.mappers;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.services.TissuVariantService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TissuRequisMapper implements IMapper<TissuRequis, TissuRequisDto>{
	@Lazy
	private TissuVariantService tvs ;

	public static TissuRequis map(TissuRequisDto dto, Patron p) {
		TissuRequis tr = new TissuRequis();
		tr.setId(dto.getId());
		tr.setLaize(dto.getLaize());
		tr.setLongueur(dto.getLongueur());
		tr.setGammePoids(GammePoids.getEnum(dto.getGammePoids()));
		tr.setPatron(p);
		return tr;
	}

	public TissuRequis map(TissuRequisDto dto) {
		TissuRequis tr = new TissuRequis();
		tr.setId(dto.getId());
		tr.setLaize(dto.getLaize());
		tr.setLongueur(dto.getLongueur());
		tr.setGammePoids(GammePoids.getEnum(dto.getGammePoids()));
		return tr;
	}

	public TissuRequisDto map(TissuRequis tr) {
		TissuRequisDto dto = new TissuRequisDto();
		dto.setId(tr.getId());
		dto.setLaize(tr.getLaize());
		dto.setLongueur(tr.getLongueur());
		dto.setGammePoids(tr.getGammePoids().label);
		List<TissuVariantDto> variants = tvs.getVariantByTissuRequis(dto);
		dto.setVariant(variants);
		return dto;
	}

}
