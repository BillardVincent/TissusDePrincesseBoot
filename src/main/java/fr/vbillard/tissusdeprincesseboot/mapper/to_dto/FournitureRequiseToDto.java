package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FournitureRequiseToDto extends TypeMapConfigurer<FournitureRequise, FournitureRequiseDto> {

	@Override
	public void configure(TypeMap<FournitureRequise, FournitureRequiseDto> typeMap) {
		//typeMap.addMappings(mapping -> mapping.using(new LongueurRestanteConverter()).map(src -> src,
		//		FournitureDto::setQuantiteDisponible));
		typeMap.addMapping(src -> src.getType().getIntitulePrincipale(), FournitureDto::setIntituleDimension);
		typeMap.addMapping(src -> src.getType().getIntituleSecondaire(), FournitureDto::setIntituleSecondaire);
		
		typeMap.setPostConverter(context -> {
			if (context.getSource().getId() != 0) {

				if (context.getSource().getQuantitePrincipale() != null) {
					Float qte = context.getSource().getQuantitePrincipale().getQuantite();
					context.getDestination().setQuantite(qte == null ? 0 : qte);
					if (context.getSource().getQuantitePrincipale().getUnite() != null) {
						context.getDestination().setUnite(context.getSource().getQuantitePrincipale().getUnite());

					}
					if (context.getDestination().getUnite() == null && context.getSource().getType() != null
							&& context.getSource().getType().getUnitePrincipaleConseillee() != null) {
						context.getDestination().setUnite(context.getSource().getType().getUnitePrincipaleConseillee());
					}
				}
					if (context.getSource().getQuantiteSecondaire() != null){
						Float qte = context.getSource().getQuantiteSecondaire().getQuantite();
						context.getDestination().setQuantiteSec(qte == null ? 0 : qte);

						if (context.getSource().getQuantiteSecondaire().getUnite() != null) {
							context.getDestination().setUniteSecondaire(context.getSource().getQuantiteSecondaire().getUnite());

						}
							}
					if (context.getDestination().getUniteSecondaire() == null && context.getSource().getType() != null
							&& context.getSource().getType().getUnitePrincipaleConseillee() != null) {
						context.getDestination().setUniteSecondaire(context.getSource().getType().getUnitePrincipaleConseillee());
					}
				}

			return context.getDestination();
		});
	}
	
}
