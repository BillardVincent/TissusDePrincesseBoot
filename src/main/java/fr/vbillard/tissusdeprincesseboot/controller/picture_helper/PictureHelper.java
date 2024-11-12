package fr.vbillard.tissusdeprincesseboot.controller.picture_helper;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.color.ColorComponent;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FxDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.exception.NotFoundException;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Preference;
import fr.vbillard.tissusdeprincesseboot.service.AbstractDtoService;
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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * Gere les methodes relatives aux images.
 * On peut y lier un ColorComponent via setColorComponent() pour que celui-ci charge l'image plus facilement
 * @param <T> AbstractEntity
 * @param <U> FxDto relatif à l'AbstractEntity
 */
public abstract class PictureHelper<T extends AbstractEntity, U extends FxDto<T>> {

    protected PreferenceService preferenceService;
    protected StageInitializer initializer;
    protected ImageService imageService;
    protected ImageView imagePane;
    protected AbstractDtoService<T, U> service;
    protected T entity;
    protected Photo picture;
    protected ColorComponent colorComponent;

    private static final Logger LOGGER = LogManager.getLogger(PictureHelper.class);

    protected PictureHelper(PreferenceService preferenceService, StageInitializer initializer,
            ImageService imageService, AbstractDtoService<T, U> service) {
        this.preferenceService = preferenceService;
        this.initializer = initializer;
        this.imageService = imageService;
        this.service = service;

    }

    public void setColorComponent(ColorComponent colorComponent) {
        this.colorComponent = colorComponent;
    }

    public void setPane(ImageView imagePane, U dto) {
        setPane(imagePane, service.convert(dto));
    }

    public void setPane(ImageView imagePane, T entity) {
        this.entity = entity;
        picture = getImageFromEntity().orElse(null);
        this.imagePane = imagePane;
        imagePane.setImage(imageService.imageOrDefault(picture));
    }

    abstract Optional<Photo> getImageFromEntity();

    public void addPictureWeb(U dto) {
        entity = service.convert(dto);
        addPictureWeb();
    }

    public void addPictureLocal(U dto) {
        entity = service.convert(dto);
        addPictureLocal();
    }

    public void addPictureClipBoard(U dto) {
        entity = service.convert(dto);
        addPictureFromClipboard();
    }

    protected void setImage() {
        service.saveOrUpdate(entity);
        addEntityToImage();
        imageService.saveOrUpdate(picture);
        imagePane.setImage(new javafx.scene.image.Image(new ByteArrayInputStream(picture.getData())));
        if (colorComponent != null) {
            colorComponent.setImage(imagePane.getImage());
        }
    }

    protected void addPictureWeb() {

        URL url = initializer.displayModale(PathEnum.WEB_URL, null, "URL de l'image").getUrl();

        if (url != null && StringUtils.hasLength(url.toString())) {
            try {
                String name = url.getHost();
                String path = url.getPath();
                String extension = path.substring(path.lastIndexOf('.') + 1);
                BufferedImage bufferedImage = ImageIO.read(url);
                picture = imageService.setImage(picture, name, extension, bufferedImage);
            } catch (IOException e) {
                LOGGER.error(e);
                throw new NotFoundException(url.toString());
            }
        }
        if (picture != null) {
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
                picture = imageService.setImage(picture, name, extension, bufferedImage);

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
                picture = imageService.setImage(picture, "copie", "jpg", bi);
            } catch (IOException | UnsupportedFlavorException e) {
                LOGGER.error(e);
                throw new IllegalData("Les données du presse-papier ne correspondent pas à une image");
            }
        }
        if (picture != null) {
            setImage();
        }
    }

    protected abstract void addEntityToImage();

}
