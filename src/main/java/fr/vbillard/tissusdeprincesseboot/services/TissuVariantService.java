package fr.vbillard.tissusdeprincesseboot.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.TissuVariantDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@Service
public class TissuVariantService extends AbstractService<TissuVariant>{
	TissuVariantDao tissuVariantDao;
	ModelMapper mapper;


	public List<TissuVariantDto> getVariantByTissuRequis(TissuRequisDto tissu) {
		List<TissuVariant> listTv = tissuVariantDao.getAllByTissuRequisId(tissu.getId());
		return listTv.stream().map(v -> mapper.map(v, TissuVariantDto.class)).collect(Collectors.toList());
	}
	
	public List<TissuVariant> getVariantByTissuRequis(TissuRequis tissu) {
		return tissuVariantDao.getAllByTissuRequisId(tissu.getId());
	}
	
	

	public TissuVariantDto saveOrUpdate(TissuVariantDto variantSelected) {
		
		return mapper.map(saveOrUpdate(mapper.map(variantSelected, TissuVariant.class)), TissuVariantDto.class);
	}

	@Override
	protected JpaRepository getDao() {
		return tissuVariantDao;
	}

}
