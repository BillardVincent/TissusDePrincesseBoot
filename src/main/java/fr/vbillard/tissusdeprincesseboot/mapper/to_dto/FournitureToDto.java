package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FournitureToDto extends TypeMapConfigurer<Fourniture, FournitureDto> {

	FournitureDao fournitureDao;

	@Override
	public void configure(TypeMap<Fourniture, FournitureDto> typeMap) {
		typeMap.addMappings(mapping -> mapping.using(new LongueurRestanteConverter()).map(src -> src,
				FournitureDto::setQuantiteDisponible));
		typeMap.addMapping(src -> src.getType().getIntitulePrincipale(), FournitureDto::setIntituleDimension);
		typeMap.addMapping(src -> src.getType().getIntituleSecondaire(), FournitureDto::setIntituleSecondaire);
		
		typeMap.setPostConverter(context -> {
			if (context.getSource().getId() != 0){

				if (Utils.isNotEmpty(context.getSource().getQuantitePrincipale(),
						context.getSource().getQuantitePrincipale().getUnite())){
					context.getDestination().setUnite(context.getSource().getQuantitePrincipale().getUnite());
				} else if (Utils.isNotEmpty(context.getSource().getType(),
						context.getSource().getType().getUnitePrincipaleConseillee())){
					context.getDestination().setUnite(context.getSource().getType().getUnitePrincipaleConseillee());
				}

				if (Utils.isNotEmpty(context.getSource().getType(),
						context.getSource().getType().getUniteSecondaireConseillee())){
					context.getDestination().setUniteSecondaire(context.getSource().getQuantiteSecondaire().getUnite());
				} else if (Utils.isNotEmpty(context.getSource().getType(),
						context.getSource().getType().getUnitePrincipaleConseillee())){
					context.getDestination().setUniteSecondaire(context.getSource().getType().getUnitePrincipaleConseillee());
				}
			}
            return context.getDestination();
		});
	}
	
	private class LongueurRestanteConverter extends AbstractConverter<Fourniture, Float> {
		@Override
		protected Float convert(Fourniture source) {
			if (Utils.isEmpty(source, source.getQuantitePrincipale(), source.getQuantitePrincipale().getQuantite())) {
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
					e.printStackTrace();
				}
			}
			return longueurRestante;
		}
	}
	
}
