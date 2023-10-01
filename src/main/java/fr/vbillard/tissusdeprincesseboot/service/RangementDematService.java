package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.RangementDematDao;
import fr.vbillard.tissusdeprincesseboot.model.RangementDemat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RangementDematService extends AbstractService<RangementDemat> {

	private RangementDematDao dao;

	@Override
	protected RangementDematDao getDao() {
		return dao;
	}



}
