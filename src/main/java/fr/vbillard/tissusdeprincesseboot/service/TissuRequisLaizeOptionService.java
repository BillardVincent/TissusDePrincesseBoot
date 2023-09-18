package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.TissuRequisLaizeOptionDao;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequisLaizeOption;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TissuRequisLaizeOptionService extends AbstractService<TissuRequisLaizeOption> {
	private final TissuRequisLaizeOptionDao dao;
	private final TissuRequisService tissuRequisService;


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

	@Transactional
  public void createForThisRequis(int id) {
		TissuRequisLaizeOption tissuRequisLaizeOption = new TissuRequisLaizeOption();
		tissuRequisLaizeOption.setRequis(tissuRequisService.getById(id));
		saveOrUpdate(tissuRequisLaizeOption);
  }

	public int getLongueurMinByRequis(int id) {
		Integer longueurMin =  dao.getLongueurMinByRequis(id);
		if (longueurMin != null){
			return longueurMin.intValue();
		}
		return 0;
	}
}
