package fr.vbillard.tissusdeprincesseboot.controller.picture_helper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.util.StringUtils;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.exception.NotFoundException;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Preference;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.PreferenceService;
import fr.vbillard.tissusdeprincesseboot.utils.path.PathEnum;
import javafx.scene.image.ImageView;

public abstract class PictureHelper {

	protected PreferenceService preferenceService;
	protected StageInitializer initializer;
	protected ImageService imageService;
	protected MapperService mapper;
	protected ImageView imagePane;

	protected Optional<Photo> picture;

	protected PictureHelper(MapperService mapper, PreferenceService preferenceService, StageInitializer initializer,
			ImageService imageService) {
		this.preferenceService = preferenceService;
		this.initializer = initializer;
		this.imageService = imageService;
		this.mapper = mapper;
	}

	protected void addPictureWeb() {

		URL url = initializer.displayModale(PathEnum.WEB_URL, null, "URL de l'image").getUrl();

		if (url != null && StringUtils.hasLength(url.toString())) {
			try {
				String name = url.getHost();
				String path = url.getPath();
				String extension = path.substring(path.lastIndexOf('.') + 1);
				BufferedImage bufferedImage = ImageIO.read(url);
				picture = Optional.of(imageService.setImage(picture, name, extension, bufferedImage));
			} catch (IOException e) {
				e.printStackTrace();
				throw new NotFoundException(url.toString());
			}
		}
		setImage();
	}

	protected void addPictureLocal() {
		Preference pref = preferenceService.getPreferences();
		File file = initializer.directoryChooser(pref);

		if (file != null) {
			try {
				String name = file.getName();
				String extension = name.substring(name.lastIndexOf(".") + 1);
				BufferedImage bufferedImage = ImageIO.read(file);
				pref.setPictureLastUploadPath(file.getAbsolutePath());
				preferenceService.savePreferences(pref);
				picture = Optional.of(imageService.setImage(picture, name, extension, bufferedImage));

			} catch (IOException e) {
				e.printStackTrace();
				throw new NotFoundException(file.getName());
			}
		}
		setImage();
	}

	protected abstract void setImage();

}
