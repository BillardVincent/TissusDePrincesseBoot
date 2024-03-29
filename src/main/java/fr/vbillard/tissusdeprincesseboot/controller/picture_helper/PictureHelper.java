package fr.vbillard.tissusdeprincesseboot.controller.picture_helper;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.exception.NotFoundException;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Preference;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.PreferenceService;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public abstract class PictureHelper {

  protected PreferenceService preferenceService;
  protected StageInitializer initializer;
  protected ImageService imageService;
  protected ImageView imagePane;

  protected Optional<Photo> picture;

  private static final Logger LOGGER = LogManager.getLogger(PictureHelper.class);


  protected PictureHelper(PreferenceService preferenceService, StageInitializer initializer,
      ImageService imageService) {
    this.preferenceService = preferenceService;
    this.initializer = initializer;
    this.imageService = imageService;
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
        LOGGER.error(e);
        throw new NotFoundException(url.toString());
      }
    }
    if (picture.isPresent()) {
      setImage();
    }
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
        LOGGER.error(e);
        throw new NotFoundException(file.getName());
      }
    }
    setImage();
  }

  protected void addPictureFromClipboard() {
    Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);


    if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
      try {
        Image img = (Image) transferable.getTransferData(DataFlavor.imageFlavor);

        int width = img.getWidth(null);
        int height = img.getHeight(null);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        picture = Optional.of(imageService.setImage(picture, "copie", "jpg", bi));
      } catch (IOException | UnsupportedFlavorException e) {
        LOGGER.error(e);
        throw new IllegalData("Les données du presse-papier ne correspondent pas à une image");
      }
    }
    if (picture.isPresent()) {
      setImage();
    }
  }

  protected abstract void setImage();

}
