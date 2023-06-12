package fr.vbillard.tissusdeprincesseboot.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.MatiereDao;
import fr.vbillard.tissusdeprincesseboot.dao.PatronVersionDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuVariantDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.exception.CantBeDeletedException;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractSimpleValueEntity;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	public PatronVersion convert(PatronVersionDto dto) {
		return null;
	}

	@Override
	public PatronVersionDto convert(PatronVersion entity) {
		return null;
	}

	public List<PatronVersion> getByPatronId(int id) {
		return dao.getByPatronId(id);
	}
}
