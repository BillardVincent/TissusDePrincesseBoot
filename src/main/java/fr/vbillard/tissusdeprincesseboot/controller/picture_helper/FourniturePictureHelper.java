package fr.vbillard.tissusdeprincesseboot.controller.picture_helper;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.PreferenceService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class FourniturePictureHelper extends PictureHelper {

	private FournitureService fournitureService;

	private Fourniture fourniture;

	public FourniturePictureHelper(FournitureService fournitureService, PreferenceService preferenceService,
			StageInitializer initializer, ImageService imageService) {
		super(preferenceService, initializer, imageService);
		this.fournitureService = fournitureService;
	}

	public void setPane(ImageView imagePane, FournitureDto dto) {
		setPane(imagePane, fournitureService.convert(dto));
	}
	public void setPane(ImageView imagePane, Fourniture fourniture) {
		this.fourniture = fourniture;
		picture = imageService.getImage(fourniture);
		this.imagePane = imagePane;
		imagePane.setImage(imageService.imageOrDefault(picture));
	}

	protected void setImage() {
		fournitureService.saveOrUpdate(fourniture);
		picture.get().setFourniture(fourniture);
		imageService.saveOrUpdate(picture.get());
		imagePane.setImage(new Image(new ByteArrayInputStream(picture.get().getData())));
	}

	public boolean hasImage(FournitureDto dto){
		return imageService.hasImage(dto);
	}

	public void addPictureWeb(FournitureDto dto) {
		fourniture = fournitureService.convert(dto);
		addPictureWeb();
	}

	public void addPictureLocal(FournitureDto dto) {
		fourniture = fournitureService.convert(dto);
		addPictureLocal();
	}

	public void addPictureClipBoard(FournitureDto dto){
		fourniture = fournitureService.convert(dto);
		addPictureFromClipboard();
	}

}
