package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dao.FournitureDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.utils.color.ColorUtils;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FournitureToDto extends TypeMapConfigurer<Fourniture, FournitureDto> {

	FournitureDao fournitureDao;

	private static final Logger LOGGER = LogManager.getLogger(FournitureToDto.class);


	@Override
	public void configure(TypeMap<Fourniture, FournitureDto> typeMap) {
		typeMap.addMappings(mapping -> mapping.using(new LongueurRestanteConverter()).map(src -> src,
				FournitureDto::setQuantiteDisponible));
		typeMap.addMapping(src -> src.getType().getIntitulePrincipale(), FournitureDto::setIntituleDimension);
		typeMap.addMapping(src -> src.getType().getIntituleSecondaire(), FournitureDto::setIntituleSecondaire);
		
		typeMap.setPostConverter(context -> {
			if (context.getSource().getId() != 0) {

				if (context.getSource().getQuantitePrincipale() != null) {

					if (context.getSource().getQuantitePrincipale().getUnite() != null) {
						context.getDestination().setUnite(context.getSource().getQuantitePrincipale().getUnite());

					}
					if (context.getDestination().getUnite() == null && context.getSource().getType() != null
							&& context.getSource().getType().getUnitePrincipaleConseillee() != null) {
						context.getDestination().setUnite(context.getSource().getType().getUnitePrincipaleConseillee());
					}
					float qte = Unite.convertir(context.getSource().getQuantitePrincipale().getQuantite(),
							context.getSource().getType().getDimensionPrincipale().getDefault(),
							Unite.getEnum(context.getDestination().getUnite()));
					context.getDestination().setQuantite(qte);
				}
					if (context.getSource().getQuantiteSecondaire() != null && !Float.isNaN(context.getSource().getQuantiteSecondaire().getQuantite()) ) {

						if (context.getSource().getQuantiteSecondaire().getUnite() != null) {
							context.getDestination().setUniteSecondaire(context.getSource().getQuantiteSecondaire().getUnite());
						}

						if (context.getDestination().getUniteSecondaire() == null && context.getSource().getType() != null
								&& context.getSource().getType().getUnitePrincipaleConseillee() != null) {
							context.getDestination().setUniteSecondaire(context.getSource().getType().getUnitePrincipaleConseillee());
						}
						float qte = Unite.convertir(context.getSource().getQuantiteSecondaire().getQuantite(),
								context.getSource().getType().getDimensionSecondaire().getDefault(), Unite.getEnum(context.getDestination().getUniteSecondaire()));
						context.getDestination().setQuantiteSec(qte);
					}
				}

			if (context.getSource().getColor() != null){
				context.getDestination().setColor(ColorUtils.entityToColor(context.getSource().getColor()));
				context.getDestination().colorId = context.getSource().getColor().getId();
			}

			return context.getDestination();
		});
	}
	
	private class LongueurRestanteConverter extends AbstractConverter<Fourniture, Float> {
		@Override
		protected Float convert(Fourniture source) {
			if (source == null ||  source.getQuantitePrincipale() == null || source.getQuantitePrincipale().getQuantite() == null) {
				return 0f;
			}
			float longueurRestante = source.getQuantitePrincipale().getQuantite();
			if (source.getId() != 0) {
				try {
					Float utilise = fournitureDao.quantiteUtilisee(source.getId());
					if (utilise != null) {
						longueurRestante -= utilise;
					}
				} catch (Exception e) {
					LOGGER.error(e);
				}
			}
			return longueurRestante;
		}
	}
}
