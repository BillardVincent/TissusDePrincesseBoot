package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.UserPrefDao;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserPrefService extends AbstractService<UserPref> {

	private UserPrefDao dao;

	public UserPref getUser() {
		Optional<UserPref> opt = dao.findById(1);
		return opt.orElse(buildNewPref());
	}

	private UserPref buildNewPref() {
		UserPref userPref = new UserPref();
		userPref.setId(1);
		userPref.setLongueurMargePercent(0.1f);
		userPref.setPoidsMargePercent(0.2f);
		userPref.setMaxPoidsMoyen(340);
		userPref.setMinPoidsMoyen(200);
		return saveOrUpdate(userPref);
	}

	@Override
	protected void beforeSaveOrUpdate(UserPref entity) {
		if (entity.getId() > 1) {
			throw new IllegalData();
		}
		if (entity.getMaxPoidsMoyen() == 0) {
			entity.setMaxPoidsMoyen(340);
		}
		if (entity.getMinPoidsMoyen() == 0) {
			entity.setMinPoidsMoyen(200);
		}
		if (entity.getMaxPoidsMoyen() < entity.getMinPoidsMoyen()) {
			throw new IllegalData("La limite moyen/lourd doit être strictement supérieurs à la limite léger/moyen");
		}
	}

	@Override
	protected UserPrefDao getDao() {
		return dao;
	}
}
