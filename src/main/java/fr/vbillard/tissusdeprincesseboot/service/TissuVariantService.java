package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuVariantDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@Service
public class TissuVariantService extends AbstractService<TissuVariant> {
	private TissuVariantDao tissuVariantDao;
	private MatiereService matiereService;
	private TissageService tissageService;
	private TissusRequisDao tissuRequisDao;
	private ModelMapper mapper;

	public List<TissuVariantDto> getVariantByTissuRequis(TissuRequisDto tissu) {
		List<TissuVariant> listTv = tissuVariantDao.getAllByTissuRequisId(tissu.getId());
		return listTv.stream().map(v -> mapper.map(v, TissuVariantDto.class)).collect(Collectors.toList());
	}

	public List<TissuVariant> getVariantByTissuRequis(TissuRequis tissu) {
		return tissuVariantDao.getAllByTissuRequisId(tissu.getId());
	}

	public TissuVariantDto saveOrUpdate(TissuVariantDto variantSelected) {
		TissuVariant tv = map(variantSelected);
		tv = saveOrUpdate(tv);
		return mapper.map(tv, TissuVariantDto.class);
	}

	@Override
	protected void beforeSaveOrUpdate(TissuVariant entity) {

	}

	@Override
	protected Idao getDao() {
		return tissuVariantDao;
	}

	public List<TissuVariant> getVariantByTissuRequisId(int id) {
		return tissuVariantDao.getAllByTissuRequisId(id);
	}

	private TissuVariant map(TissuVariantDto dto) {
		TissuVariant tv = mapper.map(dto, TissuVariant.class);
		tv.setMatiere(matiereService.findMatiere(dto.getMatiere()));
		tv.setTissage(tissageService.findTissage(dto.getTissage()));
		tv.setTissuRequis(tissuRequisDao.getById(dto.getTissuRequisId()));

		return tv;
	}

}
