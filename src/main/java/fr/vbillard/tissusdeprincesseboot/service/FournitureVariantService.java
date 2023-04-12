package fr.vbillard.tissusdeprincesseboot.service;

import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureRequiseDao;
import fr.vbillard.tissusdeprincesseboot.dao.FournitureVariantDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.AbstractRequis;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureVariant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FournitureVariantService extends AbstractVariantService<FournitureVariant, Fourniture, FournitureVariantDto> {
	private FournitureVariantDao fournitureVariantDao;
	private FournitureRequiseDao fournitureRequiseDao;
	private TypeFournitureService typeFournitureService;
	private ModelMapper mapper;


	public List<FournitureVariantDto> getVariantByRequis(FournitureRequiseDto fourniture) {
		List<FournitureVariant> listTv = fournitureVariantDao.getAllByRequisId(fourniture.getId());
		return convertToDto(listTv);
	}


	@Override
	public FournitureVariant convert(FournitureVariantDto dto) {
		FournitureVariant tv = mapper.map(dto, FournitureVariant.class);
		tv.setRequis(fournitureRequiseDao.getById(dto.getFournitureRequiseId()));
		tv.setTypeFourniture(dto.getType());

		return tv;
	}

	@Override
	public FournitureVariantDto convert(FournitureVariant entity) {
		return mapper.map(entity, FournitureVariantDto.class);
	}

	@Override
	public FournitureVariantDto saveOrUpdate(FournitureVariantDto variantSelected) {
		FournitureVariant tv = convert(variantSelected);
		tv = saveOrUpdate(tv);
		return convert(tv);
	}

	@Override
	protected void beforeSaveOrUpdate(FournitureVariant entity) {

	}

	@Override
	protected FournitureVariantDao getDao() {
		return fournitureVariantDao;
	}

	public List<FournitureVariant> getVariantByFournitureRequisId(int id) {
		return fournitureVariantDao.getAllByRequisId(id);
	}

	@Override
	public List<FournitureVariant> getVariantByRequis(AbstractRequis<Fourniture> requis) {
			if (requis == null){
				return Collections.emptyList();
			}
			return getVariantByFournitureRequisId(requis.getId());
		}
}
