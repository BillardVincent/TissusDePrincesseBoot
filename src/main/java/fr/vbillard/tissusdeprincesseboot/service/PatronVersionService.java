package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.PatronVersionDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class PatronVersionService extends AbstractDtoService<PatronVersion, PatronVersionDto> {
	private final PatronVersionDao dao;
	private final MapperService mapper;
	private final TissuRequisService tissuRequisService;
	private final FournitureRequiseService fournitureRequiseService;

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
	protected void beforeDelete(PatronVersion entity) {
		List<TissuRequis> trLst = tissuRequisService.getAllByVersionId(entity.getId());
		if (!CollectionUtils.isEmpty(trLst)) {
			for (TissuRequis tr : trLst) {
				tissuRequisService.delete(tr);
			}
		}
		
		List<FournitureRequise> frLst = fournitureRequiseService.getAllByVersionId(entity.getId());
		if (!CollectionUtils.isEmpty(frLst)) {
			for (FournitureRequise fr : frLst) {
				fournitureRequiseService.delete(fr);
			}
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

	@Transactional
	public void duplicate(int id) {
		PatronVersion source = getById(id);
		PatronVersion clone = new PatronVersion();
		clone.setNom(source.getNom() + " (copie)");
		clone.setPatron(source.getPatron());
		
		
		clone = saveOrUpdate(clone);
		
		List<TissuRequis> trLst = tissuRequisService.getAllByVersionId(id);
		if (!CollectionUtils.isEmpty(trLst)) {
			for (TissuRequis tr : trLst) {
				tissuRequisService.duplicate(tr.getId(), clone);
			}
		}
		
		List<FournitureRequise> frLst = fournitureRequiseService.getAllByVersionId(id);
		if (!CollectionUtils.isEmpty(frLst)) {
			for (FournitureRequise fr : frLst) {
				fournitureRequiseService.duplicate(fr.getId(), clone);
			}
		}
		
	}
}
