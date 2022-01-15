package fr.vbillard.tissusdeprincesseboot.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.dao.PhotoDao;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.utils.PathProperties;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageService extends AbstractService<Photo> {

	PhotoDao dao;
	PathProperties pathProperties;

	public Photo getImage(Projet projet) {
		return dao.getByProjet(projet.getId()).get();
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
			}
		}
		return new Image(new ByteArrayInputStream(photo.getData()));
	}

	@Override
	protected JpaRepository getDao() {
		return dao;
	}
}
