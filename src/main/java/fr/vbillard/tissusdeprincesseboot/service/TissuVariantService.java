package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuVariantDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TissuVariantService extends AbstractVariantService<TissuVariant, Tissu, TissuVariantDto> {
	private TissuVariantDao tissuVariantDao;
	private MatiereService matiereService;
	private TissageService tissageService;
	private TissusRequisDao tissuRequisDao;
	private ModelMapper mapper;

	public List<TissuVariantDto> getVariantByRequis(TissuRequisDto tissu) {
		List<TissuVariant> listTv = tissuVariantDao.getAllByRequisId(tissu.getId());
		return convertToDto(listTv);
	}

	public List<TissuVariant> getVariantByRequis(TissuRequis tissu) {
		return tissuVariantDao.getAllByRequisId(tissu.getId());
	}

	public TissuVariantDto saveOrUpdate(TissuVariantDto variantSelected) {
		TissuVariant tv = convert(variantSelected);
		tv = saveOrUpdate(tv);
		return mapper.map(tv, TissuVariantDto.class);
	}

	@Override
	protected void beforeSaveOrUpdate(TissuVariant entity) {

	}

	@Override
	protected TissuVariantDao getDao() {
		return tissuVariantDao;
	}

	public List<TissuVariant> getVariantByTissuRequisId(int id) {
		return tissuVariantDao.getAllByRequisId(id);
	}

	@Override
	public TissuVariant convert(TissuVariantDto dto) {
		TissuVariant tv = mapper.map(dto, TissuVariant.class);
		tv.setMatiere(matiereService.findMatiere(dto.getMatiere()));
		tv.setTissage(tissageService.findTissage(dto.getTissage()));
		tv.setRequis(tissuRequisDao.getById(dto.getTissuRequisId()));
		tv.setTypeTissu(TypeTissuEnum.getEnum(dto.getTypeTissu()));

		return tv;	}

	@Override
	public TissuVariantDto convert(TissuVariant entity) {
		return mapper.map (entity, TissuVariantDto.class);
	}

	@Override
	public List<TissuVariant> getVariantByRequis(AbstractRequis<Tissu> requis) {
		return getVariantByTissuRequisId(requis.getId());
	}
}
