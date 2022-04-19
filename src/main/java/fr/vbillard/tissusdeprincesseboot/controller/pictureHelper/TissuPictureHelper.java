package fr.vbillard.tissusdeprincesseboot.controller.pictureHelper;

import java.io.ByteArrayInputStream;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.services.ImageService;
import fr.vbillard.tissusdeprincesseboot.services.PreferenceService;
import fr.vbillard.tissusdeprincesseboot.services.TissuService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Component
@Scope("prototype")
public class TissuPictureHelper extends PictureHelper {

	private TissuService tissuService;

	private Tissu tissu;

	public TissuPictureHelper(TissuService tissuService, ModelMapper mapper, PreferenceService preferenceService,
			StageInitializer initializer, ImageService imageService) {
		super(mapper, preferenceService, initializer, imageService);
		this.tissuService = tissuService;
	}

	public void setPane(ImageView imagePane, TissuDto dto) {
		tissu = mapper.map(dto, Tissu.class);
		picture = imageService.getImage(tissu);
		this.imagePane = imagePane;
		imagePane.setImage(imageService.imageOrDefault(picture));
	}

	protected void setImage() {
		tissuService.saveOrUpdate(tissu);
		picture.get().setTissu(tissu);
		imageService.saveOrUpdate(picture.get());
		imagePane.setImage(new Image(new ByteArrayInputStream(picture.get().getData())));
	}

}
