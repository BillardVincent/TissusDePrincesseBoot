package fr.vbillard.tissusdeprincesseboot.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureRequiseDao;
import fr.vbillard.tissusdeprincesseboot.dao.FournitureVariantDao;
import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureVariant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FournitureVariantService extends AbstractService<FournitureVariant> {
	private FournitureVariantDao fournitureVariantDao;
	private FournitureRequiseDao fournitureRequiseDao;
	private TypeFournitureService typeFournitureService;
	private ModelMapper mapper;

	public List<FournitureVariantDto> getVariantByFournitureRequis(FournitureRequiseDto fourniture) {
		List<FournitureVariant> listTv = fournitureVariantDao.getAllByFournitureRequiseId(fourniture.getId());
		return listTv.stream().map(v -> mapper.map(v, FournitureVariantDto.class)).collect(Collectors.toList());
	}

	public List<FournitureVariant> getVariantByFournitureRequis(FournitureRequise fourniture) {
		return fournitureVariantDao.getAllByFournitureRequiseId(fourniture.getId());
	}

	public FournitureVariantDto saveOrUpdate(FournitureVariantDto variantSelected) {
		FournitureVariant tv = map(variantSelected);
		tv = saveOrUpdate(tv);
		return mapper.map(tv, FournitureVariantDto.class);
	}

	@Override
	protected void beforeSaveOrUpdate(FournitureVariant entity) {

	}

	@Override
	protected Idao getDao() {
		return fournitureVariantDao;
	}

	public List<FournitureVariant> getVariantByFournitureRequisId(int id) {
		return fournitureVariantDao.getAllByFournitureRequiseId(id);
	}

	private FournitureVariant map(FournitureVariantDto dto) {
		FournitureVariant tv = mapper.map(dto, FournitureVariant.class);
		tv.setFournitureRequise(fournitureRequiseDao.getById(dto.getFournitureRequiseId()));
		tv.setTypeFourniture(dto.getType());

		return tv;
	}

	public List<FournitureVariant> getVariantByFournitureRequise(FournitureRequise fourniture) {
		if (fourniture == null){
			return Collections.emptyList();
		}
		return getVariantByFournitureRequisId(fourniture.getId());
	}
}
