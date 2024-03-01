package fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element;

import fr.vbillard.tissusdeprincesseboot.config.PathIconsProperties;
import fr.vbillard.tissusdeprincesseboot.exception.UnexpectedException;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomIcon {

    private static final Logger LOGGER = LogManager.getLogger(CustomIcon.class);

    private final PathIconsProperties pathProperties;

    private final Map<Resource, Map<Integer, ImageView>> iconsPool = new HashMap<>();

    public CustomIcon(PathIconsProperties pathProperties) {
        this.pathProperties = pathProperties;
    }

    public ImageView getIcon(Resource path, int size) {

        ImageView returnedImage = new ImageView();
        ImageView imageFromPool = iconsPool.computeIfAbsent(path, p -> new HashMap<>())
                .computeIfAbsent(size, s -> {
                    WritableImage writableImage = buildImageFromPath(path, size);
                    return buildImageView(writableImage, size);
                });

        BeanUtils.copyProperties(imageFromPool, returnedImage);

        return returnedImage;
    }

    @PostConstruct
    private void loadAllSVGs() {
        washingMachinIcon(20);
        noWashingMachinIcon(20);
        textBoxRemove(40);
        textBoxCheck(40);

        for (TypeTissuEnum type : TypeTissuEnum.values())
            for (int size : new int[] { 20, 40 }) {
                typeTissu(type, size);
            }
    }

    public ImageView washingMachinIcon(int size) {
        return getIcon(pathProperties.getWashingMachine(), size);
    }

    public ImageView noWashingMachinIcon(int size) {
        return getIcon(pathProperties.getNoWashingMachine(), size);
    }

    public ImageView textBoxCheck(int size) {
        return getIcon(pathProperties.getTextBoxCheck(), size);
    }

    public ImageView textBoxRemove(int size) {
        return getIcon(pathProperties.getTextBoxRemove(), size);
    }

    public ImageView typeTissu(TypeTissuEnum typeTissu, int size) {
        if (typeTissu == null) {
            return new ImageView();
        }
        Resource path;
        switch (typeTissu) {
        case CHAINE_ET_TRAME:
            path = pathProperties.getChaineEtTrame();
            break;
        case MAILLE:
            path = pathProperties.getMaille();
            break;
        case MIXILIGNE:
            path = pathProperties.getMultiligne();
            break;
        case NON_TISSE:
            path = pathProperties.getNonTisse();
            break;
        default:
            return new ImageView();
        }
        return getIcon(path, size);

    }

    public ImageView buildImageView(WritableImage image, int size) {
        ImageView imgView = new ImageView();
        imgView.setImage(image);
        imgView.setFitHeight(size);
        imgView.setFitWidth(size);
        return imgView;
    }

    private WritableImage buildImageFromPath(Resource resource, int size) {
        BufferedImageTranscoder trans = new BufferedImageTranscoder();
        try (InputStream file = resource.getInputStream()) {
            TranscoderInput transIn = new TranscoderInput(file);
            WritableImage img = new WritableImage(size, size);
            trans.transcode(transIn, null);
            return SwingFXUtils.toFXImage(trans.getBufferedImage(), img);

        } catch (TranscoderException | IOException ex) {
            LOGGER.error(ex.getMessage());
            throw new UnexpectedException();
        }
    }

}
