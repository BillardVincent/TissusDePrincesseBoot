package fr.vbillard.tissusdeprincesseboot.controller.utils;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.model.Inventaire;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.service.InventaireService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;

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
			int beforeEditLongueur = tissuUsed.getLongueur();
			FxData d = new FxData();

			d.setTissuUsed(tissuUsed);

			FxData r = initializer.displayModale(PathEnum.CARROUSEL, d, "Inventaire");

			if (r == null || r.getTissuUsed() == null) {
				break;
			}

			int diff = beforeEditLongueur - r.getTissuUsed().getLongueur();

			Tissu tissu = r.getTissuUsed().getTissu();
			tissu.setLongueurDisponible(tissu.getLongueurDisponible() + diff);
			tissu.setLongueur(tissu.getLongueur() - r.getTissuUsed().getLongueur());
			tissuService.saveOrUpdate(tissu);

			iterator.remove();
			inventaireService.saveOrUpdate(inventaire);
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
