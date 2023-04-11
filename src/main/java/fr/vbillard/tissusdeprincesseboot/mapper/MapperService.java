package fr.vbillard.tissusdeprincesseboot.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import lombok.AllArgsConstructor;

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
}
