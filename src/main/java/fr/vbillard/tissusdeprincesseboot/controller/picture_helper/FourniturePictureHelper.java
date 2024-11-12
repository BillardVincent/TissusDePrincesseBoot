package fr.vbillard.tissusdeprincesseboot.controller.picture_helper;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.PreferenceService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class FourniturePictureHelper extends PictureHelper<Fourniture, FournitureDto> {

	public FourniturePictureHelper(FournitureService service, PreferenceService preferenceService,
			StageInitializer initializer, ImageService imageService) {
		super(preferenceService, initializer, imageService, service);
	}

	@Override
	Optional<Photo> getImageFromEntity() {
		return imageService.getImage(entity);
	}

	@Override
	protected void addEntityToImage() {
		picture.setFourniture(entity);
	}

	public boolean hasImage(FournitureDto dto){
		return imageService.hasImage(dto);
	}

}
