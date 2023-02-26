package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TissuService extends AbstractDtoService<Tissu, TissuDto> {

	private ModelMapper mapper;
	private TissuDao dao;

	public ObservableList<TissuDto> getObservableList() {
		return FXCollections.observableArrayList(
				dao.findAll().stream().map(this::convert).collect(Collectors.toList()));
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
		Tissu t = convert(dto);
		t.setArchived(true);
		dao.save(t);

	}

	public void delete(TissuDto dto) {
		delete(mapper.map(dto, Tissu.class));
	}

	public TissuDto saveOrUpdate(TissuDto dto) {
		Tissu t = convert(dto);
		return convert(saveOrUpdate(t));
	}

	/**
	 * Contient la conversion du poids de g/m en g/m²
	 */
	@Override
	protected void beforeSaveOrUpdate(Tissu entity) {
		if (UnitePoids.GRAMME_M.equals(entity.getUnitePoids())) {
			if (entity.getLaize() == 0) {
				throw new IllegalData(
						"La laize doit être renseignée pour calculer le poids en g/m² à partir du poids en g" + ".m².");
			}
			float conversion = (float) entity.getPoids() / ((float) entity.getLaize() / 100f);
			entity.setPoids(Math.round(conversion));
			entity.setUnitePoids(UnitePoids.GRAMME_M_CARRE);
		}
	}

	@Override
	protected TissuDao getDao() {
		return dao;
	}

	public ObservableList<TissuDto> getObservablePage(int page, int pageSize) {
		return FXCollections.observableArrayList(dao.findAll(PageRequest.of(page, pageSize)).stream()
				.map(this::convert).collect(Collectors.toList()));
	}

	public int getLongueurUtilisee(int tissuId) {
		Integer result = null;
		if (tissuId != 0) {
			try {
				result = dao.longueurUtilisee(tissuId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result == null ? 0 : result;
	}

	public List<TissuDto> getObservablePage(int page, int pageSize, TissuSpecification specification) {
		return FXCollections.observableArrayList(dao.findAll(specification, PageRequest.of(page, pageSize)).stream()
				.map(this::convert).collect(Collectors.toList()));
	}

	/**
	 * S'execute au démarage pour recalculer les longueurs restantes
	 */
	public void batchTissuDisponible() {
		for (Tissu t : getAll()) {
			int longueurRestante = t.getLongueur() - getLongueurUtilisee(t.getId());
			t.setLongueurDisponible(Math.max(longueurRestante, 0));
			saveOrUpdate(t);
		}
	}

	@Override
	public Tissu convert(TissuDto dto) {
		return mapper.map(dto, Tissu.class);
	}

	@Override
	public TissuDto convert(Tissu entity) {
		return mapper.map(entity, TissuDto.class);
	}

}
