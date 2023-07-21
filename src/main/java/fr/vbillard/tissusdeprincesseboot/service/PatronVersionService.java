package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import fr.vbillard.tissusdeprincesseboot.controller.components.LaizeLongueurOptionCell;
import fr.vbillard.tissusdeprincesseboot.dao.PatronVersionDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequisLaizeOption;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatronVersionService extends AbstractDtoService<PatronVersion, PatronVersionDto> {
	private PatronVersionDao dao;
	private MapperService mapper;

	@Override
	protected PatronVersionDao getDao() {
		return dao;
	}

	@Override
	protected void beforeSaveOrUpdate(PatronVersion patronVersion) {
		if (patronVersion.getId() != 0) {
			dao.findById(patronVersion.getId()).ifPresent(pv -> patronVersion.setPatron(pv.getPatron()));
		}
	}

	@Override
	public PatronVersion convert(PatronVersionDto dto) {
		return mapper.map(dto);
	}

	@Override
	public PatronVersionDto convert(PatronVersion entity) {
		return mapper.map(entity);
	}

	public List<PatronVersion> getByPatronId(int id) {
		return dao.getByPatronId(id);
	}

	@Transactional
	public List<PatronVersionDto> getDtoByPatronId(int id) {
		return convertToDto(getByPatronId(id));
	}

	public void duplicate(int id) {
		// TODO Auto-generated method stub
		
	}
}
