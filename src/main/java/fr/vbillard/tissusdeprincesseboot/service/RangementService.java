package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.RangementDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.RangementDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TypeRangement;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.Rangement_;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RangementService extends AbstractService<Rangement> {

	private RangementDao dao;

	@Override
	public RangementDao getDao() {
		return dao;
	}

	public List<Rangement> getByParent(int id){
		return dao.getByConteneur_Id(id, Sort.by(Rangement_.RANG));
	}

	public List<Rangement> getByParentRoot(int id){
		return dao.getByConteneurRoot_Id(id, Sort.by(Rangement_.RANG));
	}

	@Transactional
	public String getRangementPath(int id){

		Rangement r = getById(id);
		StringBuilder result = new StringBuilder();
		result.append(r.getNom());
		while (r.getConteneur() != null){
			r = r.getConteneur();
			result.insert(0, r.getNom()+ "/");
		}
		result.insert(0, r.getConteneurRoot().getNom() + "/");

		return result.toString();
	}


	public RangementDto convert(Rangement source) {
		RangementDto dto = new RangementDto();
		dto.setId(source.getId());
		dto.setRang(source.getRang());
		dto.setNom(source.getNom());
		dto.setType(TypeRangement.RANGEMENT);
		return dto;
	}
}
