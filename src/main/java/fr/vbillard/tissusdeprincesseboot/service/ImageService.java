package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.config.PathImgProperties;
import fr.vbillard.tissusdeprincesseboot.dao.PhotoDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.PersistanceException;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.ImageFormat;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService extends AbstractService<Photo> {

	private PhotoDao dao;
	private PathImgProperties pathProperties;

	private static final Logger LOGGER = LogManager.getLogger(ImageService.class);

	public Optional<Photo> getImage(Projet projet) {
		if (projet.getId() == 0) {
			return Optional.empty();
		}
		return dao.getByProjet(projet.getId());
	}

	public boolean hasImage(TissuDto tissu) {
		return dao.existsByTissuId(tissu.getId());
	}

	public boolean hasImage(FournitureDto fourniture) {
		return dao.existsByFournitureId(fourniture.getId());
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

	public Image imageOrDefault(Photo photo) {
		if (photo != null) {
			return new Image(new ByteArrayInputStream(photo.getData()));
		}
		try {
			return new Image(pathProperties.getImageDefault().getURL().toString());
		} catch (IOException e) {
			LOGGER.error(e);
			throw new PersistanceException("Erreur de chargement de l'image");
		}

	}

	@Override
	protected void beforeSaveOrUpdate(Photo entity) {
		// Nothing To Do
	}

	@Override
	public PhotoDao getDao() {
		return dao;
	}

	public Photo setImage(Photo imageOpt, String name, String extension, BufferedImage bufferedImage)
			throws IOException {
		bufferedImage = Scalr.resize(bufferedImage, 900);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, extension, baos);
		byte[] data = baos.toByteArray();
		baos.close();

		Photo image = imageOpt == null ? new Photo() : imageOpt;

		image.setData(data);
		image.setNom(name);
		image.setFormat(ImageFormat.valueOf(extension.toUpperCase()));

		return image;
	}

}
