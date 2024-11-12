package fr.vbillard.tissusdeprincesseboot.controller.picture_helper;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import fr.vbillard.tissusdeprincesseboot.service.PreferenceService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class TissuPictureHelper extends PictureHelper<Tissu, TissuDto> {


	public TissuPictureHelper(TissuService tissuService, PreferenceService preferenceService,
			StageInitializer initializer, ImageService imageService) {
		super(preferenceService, initializer, imageService, tissuService);
	}

	@Override
	Optional<Photo> getImageFromEntity() {
		return imageService.getImage(entity);
	}

	@Override
	protected void addEntityToImage() {
		picture.setTissu(entity);
	}

	public boolean hasImage(TissuDto dto){
		return imageService.hasImage(dto);
	}

}
