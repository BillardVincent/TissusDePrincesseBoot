package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.RangementRootDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.RangementDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TypeRangement;
import fr.vbillard.tissusdeprincesseboot.model.RangementRoot;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RangementRootService extends AbstractService<RangementRoot> {

	private RangementRootDao dao;

	@Override
	public RangementRootDao getDao() {
		return dao;
	}

	public RangementDto convert(RangementRoot source){
		RangementDto dto = new RangementDto();
		dto.setId(source.getId());
		dto.setRang(source.getRang());
		dto.setNom(source.getNom());
		dto.setType(TypeRangement.ROOT);
		return dto;
	}


}
