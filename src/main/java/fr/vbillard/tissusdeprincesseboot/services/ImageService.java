package fr.vbillard.tissusdeprincesseboot.services;

import java.util.List;

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
	public List<Photo> getImages(Tissu tissu) {
		return dao.getAllByTissu(tissu);
	}
	
	@Override
	protected JpaRepository getDao() {
		return dao;
	}
}
