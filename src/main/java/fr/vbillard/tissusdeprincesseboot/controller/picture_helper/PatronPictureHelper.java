package fr.vbillard.tissusdeprincesseboot.controller.picture_helper;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.PatronService;
import fr.vbillard.tissusdeprincesseboot.service.PreferenceService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class PatronPictureHelper extends PictureHelper {

	private PatronService patronService;

	private Patron patron;

	public PatronPictureHelper(PatronService patronService, PreferenceService preferenceService,
			StageInitializer initializer, ImageService imageService) {
		super(preferenceService, initializer, imageService);
		this.patronService = patronService;
	}

	public void setPane(ImageView imagePane, PatronDto dto) {
		patron = patronService.convert(dto);
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

	public void addPictureWeb(PatronDto dto) {
		patron = patronService.convert(dto);
		addPictureWeb();
	}

	public void addPictureLocal(PatronDto dto) {
		patron = patronService.convert(dto);
		addPictureLocal();
	}
	
	public void addPictureClipBoard(PatronDto dto) {
		patron = patronService.convert(dto);
		addPictureFromClipboard();
	}

}
