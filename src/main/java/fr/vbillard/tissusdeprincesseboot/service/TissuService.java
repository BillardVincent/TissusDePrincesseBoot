package fr.vbillard.tissusdeprincesseboot.service;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TissuService extends AbstractService<Tissu> {

	private ModelMapper mapper;
	private TissuDao dao;

	public ObservableList<TissuDto> getObservableList() {
		return FXCollections.observableArrayList(
				dao.findAll().stream().map(t -> mapper.map(t, TissuDto.class)).collect(Collectors.toList()));
	}

	public ObservableList<TissuDto> filter(String text) {
		ObservableList<TissuDto> result = FXCollections.observableArrayList();
		for (TissuDto t : getObservableList()) {
			if (t.getDescription().contains(text) || t.getLieuAchat().contains(text) || t.getMatiere().contains(text)
					|| t.getTissage().contains(text) || t.getTypeTissu().contains(text))
				result.add(t);
		}
		return result;
	}

	public ObservableList<TissuDto> filter(TissuDto tissuDto) {
		ObservableList<TissuDto> result = FXCollections.observableArrayList();
		for (TissuDto t : getObservableList()) {
			if (t.getDescription().contains(tissuDto.getDescription())
					|| t.getLieuAchat().contains(tissuDto.getLieuAchat())
					|| t.getMatiere().contains(tissuDto.getMatiere()) || t.getTissage().contains(tissuDto.getTissage())
					|| t.getTypeTissu().contains(tissuDto.getTypeTissu()))
				result.add(t);
		}
		return result;
	}

	public boolean existByReference(String string) {
		return dao.existsByReference(string);

	}

	public void archive(TissuDto dto) {
		Tissu t = mapper.map(dto, Tissu.class);
		t.setArchived(true);
		dao.save(t);

	}

	public void delete(TissuDto dto) {
		delete(mapper.map(dto, Tissu.class));
	}

	public TissuDto saveOrUpdate(TissuDto dto) {
		Tissu t = mapper.map(dto, Tissu.class);
		return mapper.map(saveOrUpdate(t), TissuDto.class);
	}

	@Override
	protected Idao getDao() {
		return dao;
	}

	public ObservableList<TissuDto> getObservablePage(int page, int pageSize) {
		return FXCollections.observableArrayList(dao.findAll(PageRequest.of(page, pageSize)).stream()
				.map(t -> mapper.map(t, TissuDto.class)).collect(Collectors.toList()));
	}

	public int getLongueurUtilisée(int tissuId) {
		return dao.longueurUtilisee(tissuId);
	}

}
