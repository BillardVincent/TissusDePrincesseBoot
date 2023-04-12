package fr.vbillard.tissusdeprincesseboot.controller.utils;

import java.util.Iterator;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.model.Inventaire;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.service.InventaireService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.utils.path.PathEnum;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DisplayInventaireService {

	TissuService tissuService;
	InventaireService inventaireService;

	public void displayTissuInventaire(StageInitializer initializer, Inventaire inventaire) {

		if (inventaire == null || CollectionUtils.isEmpty(inventaire.getTissus())) {
			return;
		}

		Iterator<TissuUsed> iterator = inventaire.getTissus().iterator();

		while (iterator.hasNext()) {
			TissuUsed tissuUsed = iterator.next();
			FxData d = new FxData();

			d.setTissuUsed(tissuUsed);

			FxData r = initializer.displayModale(PathEnum.CARROUSEL, d, "Inventaire");

			if (r == null || r.getTissuUsed() == null) {
				break;
			}

			tissuService.saveOrUpdate(r.getTissu());
			iterator.remove();
			inventaireService.saveOrUpdate(r.getInventaire());
		}

		if (inventaire.getTissus().isEmpty()) {
			inventaireService.delete(inventaire);
		}
		batchInventaire(initializer);
	}

	public void batchInventaire(StageInitializer initializer) {
		if (inventaireService.count() > 0) {

			FxData data = new FxData();
			data.setListInventaire(inventaireService.getAll());

			FxData result = initializer.displayModale(PathEnum.INVENTAIRE, data, "Inventaires incomplets");

			if (result == null) {
				return;
			}

			displayTissuInventaire(initializer, result.getInventaire());
		}

	}
}
