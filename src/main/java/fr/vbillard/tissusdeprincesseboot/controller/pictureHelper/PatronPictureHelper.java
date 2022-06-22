package fr.vbillard.tissusdeprincesseboot.controller.pictureHelper;

import java.io.ByteArrayInputStream;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.PatronService;
import fr.vbillard.tissusdeprincesseboot.service.PreferenceService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Component
@Scope("prototype")
public class PatronPictureHelper extends PictureHelper {

	private PatronService patronService;

	private Patron patron;

	public PatronPictureHelper(PatronService patronService, ModelMapper mapper, PreferenceService preferenceService,
			StageInitializer initializer, ImageService imageService) {
		super(mapper, preferenceService, initializer, imageService);
		this.patronService = patronService;
	}

	public void setPane(ImageView imagePane, PatronDto dto) {
		patron = mapper.map(dto, Patron.class);
		picture = imageService.getImage(patron);
		this.imagePane = imagePane;
		imagePane.setImage(imageService.imageOrDefault(picture));
	}

	protected void setImage() {
		patronService.saveOrUpdate(patron);
		picture.get().setPatron(patron);
		imageService.saveOrUpdate(picture.get());
		imagePane.setImage(new Image(new ByteArrayInputStream(picture.get().getData())));
	}

}
