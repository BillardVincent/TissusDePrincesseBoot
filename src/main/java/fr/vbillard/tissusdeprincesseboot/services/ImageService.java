package fr.vbillard.tissusdeprincesseboot.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import fr.vbillard.tissusdeprincesseboot.exception.PersistanceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.config.PathImgProperties;
import fr.vbillard.tissusdeprincesseboot.dao.PhotoDao;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageService extends AbstractService<Photo> {

	PhotoDao dao;
	PathImgProperties pathProperties;

	public Photo getImage(Projet projet) {
		Optional<Photo> photo = dao.getByProjet(projet.getId());
		return photo.isPresent() ? photo.get() : null;
	}

	public Photo getImage(Tissu tissu) {
		return dao.getByTissu(tissu);
	}

	public Image imageOrDefault(Photo photo) {
		if (photo == null) {
			try {
				return new Image(pathProperties.getImageDefault().getURL().toString());
			} catch (IOException e) {
				e.printStackTrace();
				throw new PersistanceException("Erreur de chargement de l'image");
			}
		}
		return new Image(new ByteArrayInputStream(photo.getData()));
	}

	@Override
	protected JpaRepository getDao() {
		return dao;
	}
}
