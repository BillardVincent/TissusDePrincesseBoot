package fr.vbillard.tissusdeprincesseboot.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.PhotoDao;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;

@Service
public class ImageService extends AbstractService<Photo>{

	PhotoDao dao;
	
	public ImageService(PhotoDao dao) {
		this.dao = dao;
	}
	public Photo getImage(Tissu tissu) {
		return dao.getByTissu(tissu);
	}
	
	@Override
	protected JpaRepository getDao() {
		return dao;
	}
}
