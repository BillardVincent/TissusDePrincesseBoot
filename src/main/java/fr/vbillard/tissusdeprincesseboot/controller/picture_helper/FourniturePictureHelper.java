package fr.vbillard.tissusdeprincesseboot.controller.picture_helper;

import java.io.ByteArrayInputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.PreferenceService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Component
@Scope("prototype")
public class FourniturePictureHelper extends PictureHelper {

	private FournitureService fournitureService;

	private Fourniture fourniture;

	public FourniturePictureHelper(FournitureService fournitureService, PreferenceService preferenceService,
			StageInitializer initializer, ImageService imageService) {
		super(preferenceService, initializer, imageService);
		this.fournitureService = fournitureService;
	}

	public void setPane(ImageView imagePane, FournitureDto dto) {
		fourniture = fournitureService.convert(dto);
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

	public void addPictureWeb(FournitureDto dto) {
		fourniture = fournitureService.convert(dto);
		addPictureWeb();
	}

	public void addPictureLocal(FournitureDto dto) {
		fourniture = fournitureService.convert(dto);
		addPictureLocal();
	}

}
