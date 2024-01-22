package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.InventaireDao;
import fr.vbillard.tissusdeprincesseboot.model.Inventaire;
import org.springframework.stereotype.Service;

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
	public InventaireDao getDao() {
		return dao;
	}

	public boolean hasInventairesIncomplets() {
		return dao.count() > 0;
	}
	
}
