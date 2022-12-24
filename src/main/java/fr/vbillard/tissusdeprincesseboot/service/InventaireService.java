package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dao.InventaireDao;
import fr.vbillard.tissusdeprincesseboot.model.Inventaire;

@Service
public class InventaireService extends AbstractService<Inventaire>{

	
	private InventaireDao dao;
	
	public InventaireService(InventaireDao dao) {
		this.dao = dao;
	}
	
	@Override
	protected void beforeSaveOrUpdate(Inventaire entity) {
		
	}

	@Override
	protected InventaireDao getDao() {
		return dao;
	}

	public boolean hasInventairesIncomplets() {
		return dao.count() > 0;
	}
	
}
