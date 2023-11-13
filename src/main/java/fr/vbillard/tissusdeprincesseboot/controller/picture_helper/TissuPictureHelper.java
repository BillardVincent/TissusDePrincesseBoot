package fr.vbillard.tissusdeprincesseboot.controller.picture_helper;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.PreferenceService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@Scope("prototype")
public class TissuPictureHelper extends PictureHelper {

	private final TissuService tissuService;

	private Tissu tissu;

	public TissuPictureHelper(TissuService tissuService, PreferenceService preferenceService,
			StageInitializer initializer, ImageService imageService) {
		super(preferenceService, initializer, imageService);
		this.tissuService = tissuService;
	}

	public void setPane(ImageView imagePane, TissuDto dto) {
		tissu = tissuService.convert(dto);
		picture = imageService.getImage(tissu);
		this.imagePane = imagePane;
		imagePane.setImage(imageService.imageOrDefault(picture));
	}

	public boolean hasImage(TissuDto dto){
		return imageService.hasImage(dto);
	}

	protected void setImage() {
		tissuService.saveOrUpdate(tissu);
		picture.orElseThrow(RuntimeException::new).setTissu(tissu);
		imageService.saveOrUpdate(picture.get());
		imagePane.setImage(new Image(new ByteArrayInputStream(picture.get().getData())));
	}

	public void addPictureWeb(TissuDto dto) {
		tissu = tissuService.convert(dto);
		addPictureWeb();
	}

	public void addPictureLocal(TissuDto dto) {
		tissu = tissuService.convert(dto);
		addPictureLocal();
	}

	public void addPictureClipBoard(TissuDto dto) {
		tissu = tissuService.convert(dto);
		addPictureFromClipboard();
	}
}
