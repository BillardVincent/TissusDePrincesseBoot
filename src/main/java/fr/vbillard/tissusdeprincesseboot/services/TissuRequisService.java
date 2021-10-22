package fr.vbillard.tissusdeprincesseboot.services;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.mappers.TissuRequisMapper;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@Service
@AllArgsConstructor
public class TissuRequisService {

	private TissusRequisDao tissuRequisDao;
	private ModelMapper mapper;

	public List<TissuRequis> getAllTissuRequisByPatron(int id){
		return tissuRequisDao.getAllByPatronId(id);
	}
	
	public TissuRequisDto createOrUpdate(TissuRequisDto tissu, PatronDto patron) {
		TissuRequis t = TissuRequisMapper.map(tissu, mapper.map(patron, Patron.class));
		 return mapper.map(tissuRequisDao.save(t), TissuRequisDto.class);

	}

	public TissuRequis findTissuRequis(int tissuRequisId) {
		
		return tissuRequisDao.findById(tissuRequisId).get();
	}

	public void delete(TissuRequisDto tissu) {
		tissuRequisDao.delete(mapper.map(tissu, TissuRequis.class));
		
	}
	public void delete(TissuRequis tissu) {
		tissuRequisDao.delete(tissu);

	}

	public List<TissuRequisDto> getAsObservableAllTissuRequisByPatron(int id) {
		//return mapper.getAsObservable(getAllTissuRequisByPatron(id));
		return null;
	}


}
