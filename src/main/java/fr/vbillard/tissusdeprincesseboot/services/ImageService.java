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

	public Optional<Photo> getImage(Projet projet) {
		if (projet.getId() == 0){
			return Optional.empty();
		}
		return dao.getByProjet(projet.getId());
	}

	public Optional<Photo> getImage(Tissu tissu) {
		if (tissu.getId() == 0){
			return Optional.empty();
		}
		return dao.getByTissu(tissu);
	}

	public Image imageOrDefault(Optional<Photo> photo) {
		if (photo.isPresent()) {
			return new Image(new ByteArrayInputStream(photo.get().getData()));
		}
			try {
				return new Image(pathProperties.getImageDefault().getURL().toString());
			} catch (IOException e) {
				e.printStackTrace();
				throw new PersistanceException("Erreur de chargement de l'image");
			}

	}

	@Override
	protected JpaRepository getDao() {
		return dao;
	}
}
