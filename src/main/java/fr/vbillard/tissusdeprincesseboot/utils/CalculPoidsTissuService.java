package fr.vbillard.tissusdeprincesseboot.utils;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.model.enums.Recommendation;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;

@Component
public class CalculPoidsTissuService {

	private UserPrefService userPrefService;

	public CalculPoidsTissuService(UserPrefService userPrefService) {
		this.userPrefService = userPrefService;
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
		Map<GammePoids, Recommendation> result = new EnumMap<GammePoids, Recommendation>(GammePoids.class);
		for (GammePoids gp : GammePoids.values()) {
			result.put(gp, getRecommendation(gp, tissu));
		}

		return result;
	}

	private Recommendation getRecommendation(GammePoids gp, Tissu tissu) {
		UserPref pref = userPrefService.getUser();
		int poidsGM2 = poidFromTissu(tissu);

		if (poidsGM2 == -1) {
			return Recommendation.INCONNU;
		}

		switch (gp) {
		case LOURD:
			if (poidsGM2 > pref.getMaxPoidsMoyen()) {
				return Recommendation.IDEAL;
			} else if (poidsGM2 > pref.getMaxPoidsMoyen() + pref.getMaxPoidsMoyen() * pref.getPoidsMargePercent())
				return Recommendation.POSSIBLE;
			else
				return Recommendation.NON_RECOMMANDE;
		case MOYEN:
			if (poidsGM2 <= pref.getMaxPoidsMoyen() && poidsGM2 > pref.getMinPoidsMoyen()) {
				return Recommendation.IDEAL;
			} else if (poidsGM2 > pref.getMaxPoidsMoyen() + pref.getMaxPoidsMoyen() * pref.getPoidsMargePercent())
				return Recommendation.POSSIBLE;
			else
				return Recommendation.NON_RECOMMANDE;

		case LEGER:
			if (poidsGM2 > pref.getMaxPoidsMoyen()) {
				return Recommendation.IDEAL;
			} else if (poidsGM2 > pref.getMaxPoidsMoyen() + pref.getMaxPoidsMoyen() * pref.getPoidsMargePercent())
				return Recommendation.POSSIBLE;
			else
				return Recommendation.NON_RECOMMANDE;
		default:
			return Recommendation.INCONNU;
		}
	}
}
