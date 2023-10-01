package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.RangementRootDematDao;
import fr.vbillard.tissusdeprincesseboot.model.RangementRootDemat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RangementRootDematService extends AbstractService<RangementRootDemat> {

	private RangementRootDematDao dao;

	@Override
	protected RangementRootDematDao getDao() {
		return dao;
	}





}
