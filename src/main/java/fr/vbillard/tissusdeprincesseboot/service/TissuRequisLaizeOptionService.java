package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.TissuRequisLaizeOptionDao;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequisLaizeOption;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TissuRequisLaizeOptionService extends AbstractService<TissuRequisLaizeOption> {
	private TissuRequisLaizeOptionDao dao;


	@Override
	protected void beforeSaveOrUpdate(TissuRequisLaizeOption entity) {

	}

	@Override
	protected TissuRequisLaizeOptionDao getDao() {
		return dao;
	}

	public List<TissuRequisLaizeOption> getTissuRequisLaizeOptionByRequisId(int id){
		return dao.getTissuRequisLaizeOptionByRequisId(id);
	}

}
