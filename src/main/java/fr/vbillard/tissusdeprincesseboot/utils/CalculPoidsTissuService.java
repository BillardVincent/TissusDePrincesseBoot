package fr.vbillard.tissusdeprincesseboot.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.Recommendation;

@Component
public class CalculPoidsTissuService {

	private ConstantesMetier constantesMetier;

	public CalculPoidsTissuService(ConstantesMetier constantesMetier) {
		this.constantesMetier = constantesMetier;
	}

	public int poidFromTissu(Tissu tissu) {
		switch (tissu.getUnitePoids()) {
		case GRAMME_M:
			return tissu.getPoids() / tissu.getLaize() * 100;
		case GRAMME_M_CARRE:
			return tissu.getPoids();
		default:
			return -1;
		}
	}

	public Map<GammePoids, Recommendation> setRecommendations(Tissu tissu) {
		Map<GammePoids, Recommendation> result = new HashMap<GammePoids, Recommendation>();
		for (GammePoids gp : GammePoids.values()) {
			result.put(gp, getRecommendation(gp, tissu));
		}

		return result;
	}

	private Recommendation getRecommendation(GammePoids gp, Tissu tissu) {
		int poidsGM2 = poidFromTissu(tissu);

		if (poidsGM2 == -1) {
			return Recommendation.INCONNU;
		}

		switch (gp) {
		case LOURD:
			if (poidsGM2 > constantesMetier.getMaxPoidsMoyen()) {
				return Recommendation.IDEAL;
			} else if (poidsGM2 > constantesMetier.getMaxPoidsMoyen()
					+ constantesMetier.getMaxPoidsMoyen() * constantesMetier.getMargePoidsErreur())
				return Recommendation.POSSIBLE;
			else
				return Recommendation.NON_RECOMMANDE;
		case MOYEN:
			if (poidsGM2 <= constantesMetier.getMaxPoidsMoyen() && poidsGM2 > constantesMetier.getMinPoidsMoyen()) {
				return Recommendation.IDEAL;
			} else if (poidsGM2 > constantesMetier.getMaxPoidsMoyen()
					+ constantesMetier.getMaxPoidsMoyen() * constantesMetier.getMargePoidsErreur())
				return Recommendation.POSSIBLE;
			else
				return Recommendation.NON_RECOMMANDE;

		case LEGER:
			if (poidsGM2 > constantesMetier.getMaxPoidsMoyen()) {
				return Recommendation.IDEAL;
			} else if (poidsGM2 > constantesMetier.getMaxPoidsMoyen()
					+ constantesMetier.getMaxPoidsMoyen() * constantesMetier.getMargePoidsErreur())
				return Recommendation.POSSIBLE;
			else
				return Recommendation.NON_RECOMMANDE;
		default:
			return Recommendation.INCONNU;
		}
	}
}
