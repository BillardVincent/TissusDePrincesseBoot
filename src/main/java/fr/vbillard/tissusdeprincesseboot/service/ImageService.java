package fr.vbillard.tissusdeprincesseboot.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.config.PathImgProperties;
import fr.vbillard.tissusdeprincesseboot.dao.PhotoDao;
import fr.vbillard.tissusdeprincesseboot.exception.PersistanceException;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.ImageFormat;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageService extends AbstractService<Photo> {

	PhotoDao dao;
	PathImgProperties pathProperties;

	public Optional<Photo> getImage(Projet projet) {
		if (projet.getId() == 0) {
			return Optional.empty();
		}
		return dao.getByProjet(projet.getId());
	}

	public Optional<Photo> getImage(Patron patron) {
		if (patron.getId() == 0) {
			return Optional.empty();
		}
		return dao.getByPatron(patron);
	}

	public Optional<Photo> getImage(Tissu tissu) {
		if (tissu.getId() == 0) {
			return Optional.empty();
		}
		return dao.getByTissu(tissu);
	}
	
	public Optional<Photo> getImage(Fourniture fourniture) {
		if (fourniture.getId() == 0) {
			return Optional.empty();
		}
		return dao.getByFourniture(fourniture);
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
	protected void beforeSaveOrUpdate(Photo entity) {

	}

	@Override
	protected PhotoDao getDao() {
		return dao;
	}

	public Photo setImage(Optional<Photo> imageOpt, String name, String extension, BufferedImage bufferedImage)
			throws IOException {
		bufferedImage = Scalr.resize(bufferedImage, 900);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, extension, baos);
		byte[] data = baos.toByteArray();
		baos.close();

		Photo image = imageOpt.orElseGet(Photo::new);

		image.setData(data);
		image.setNom(name);
		image.setFormat(ImageFormat.valueOf(extension.toUpperCase()));

		return image;
	}

}
