package fr.vbillard.tissusdeprincesseboot.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.exception.NotFoundException;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Preference;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.services.PreferenceService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PictureUtils {

	PreferenceService preferenceService;
	StageInitializer initializer;
	ImageService imageService;

	public Optional<Photo> addPictureWeb(Optional<Photo> image) {

		URL url = initializer.displayModale(PathEnum.WEB_URL, null, "URL de l'image").getUrl();

		if (url != null) {
			try {
				String name = url.getHost();
				String path = url.getPath();
				String extension = path.substring(path.lastIndexOf('.') + 1);
				BufferedImage bufferedImage = ImageIO.read(url);
				return Optional.of(imageService.setImage(image, name, extension, bufferedImage));
			} catch (IOException e) {
				e.printStackTrace();
				throw new NotFoundException(url.toString());
			}
		}
		return Optional.empty();

	}

	public Optional<Photo> addPictureLocal(Optional<Photo> image) {
		Preference pref = preferenceService.getPreferences();
		File file = initializer.directoryChooser(pref);

		Optional<Photo> imageOpt = Optional.empty();

		if (file != null) {
			try {
				String name = file.getName();
				String extension = name.substring(name.lastIndexOf(".") + 1);
				BufferedImage bufferedImage = ImageIO.read(file);
				pref.setPictureLastUploadPath(file.getAbsolutePath());
				preferenceService.savePreferences(pref);
				imageOpt = Optional.of(imageService.setImage(image, name, extension, bufferedImage));

			} catch (IOException e) {
				e.printStackTrace();
				throw new NotFoundException(file.getName());

			}
		}
		return imageOpt;

	}

}
