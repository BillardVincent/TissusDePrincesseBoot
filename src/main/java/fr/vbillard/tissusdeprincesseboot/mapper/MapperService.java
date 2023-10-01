package fr.vbillard.tissusdeprincesseboot.mapper;

import fr.vbillard.tissusdeprincesseboot.dao.RangementDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.*;
import fr.vbillard.tissusdeprincesseboot.model.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional
public class MapperService {
	
	ModelMapper mapper;

	public TissuDto map(Tissu source) {
		return mapper.map(source, TissuDto.class);
	}
	
	public Tissu map(TissuDto source) {
		return mapper.map(source, Tissu.class);
	}
	
	public FournitureDto map(Fourniture source) {
		return mapper.map(source, FournitureDto.class);
	}

	public PatronDto map(Patron source) {
		return mapper.map(source, PatronDto.class);
	}
	
	public Patron map(PatronDto source) {
		return mapper.map(source, Patron.class);
	}
	
	public ProjetDto map(Projet source) {
		return mapper.map(source, ProjetDto.class);
	}
	
	public Projet map(ProjetDto source) {
		return mapper.map(source, Projet.class);
	}
	
	public FournitureRequise map(FournitureRequiseDto source) {
		return mapper.map(source, FournitureRequise.class);
	}
	
	public FournitureRequiseDto map(FournitureRequise source) {
		return mapper.map(source, FournitureRequiseDto.class);
	}

	public TissuRequisDto map(TissuRequis source) {
		return mapper.map(source, TissuRequisDto.class);
	}

	public TissuRequis map(TissuRequisDto source) {
		return mapper.map(source, TissuRequis.class);
	}

  public PatronVersion map(PatronVersionDto source) { return mapper.map(source, PatronVersion.class); }

	public PatronVersionDto map(PatronVersion source) { return mapper.map(source, PatronVersionDto.class); }

}
