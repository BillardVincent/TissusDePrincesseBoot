package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.TissuDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TissuService extends AbstractDtoService<Tissu, TissuDto> {

	private MapperService mapper;
	private TissuDao dao;

	private static final Logger LOGGER = LogManager.getLogger(TissuService.class);


	public boolean existByReference(String string) {
		return dao.existsByReference(string);
	}

	@Transactional
	public void delete(TissuDto dto) {
		delete(convert(dto));
	}

	@Override
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
			float conversion =  entity.getPoids() / (entity.getLaize() / 100f);
			entity.setPoids(Math.round(conversion));
			entity.setUnitePoids(UnitePoids.GRAMME_M_CARRE);
		}
	}

	@Override
	public TissuDao getDao() {
		return dao;
	}

	public ObservableList<TissuDto> getObservablePage(int page, int pageSize) {
		return FXCollections.observableArrayList(dao.findAllByArchived(PageRequest.of(page, pageSize), false).stream()
				.map(this::convert).toList());
	}

	public int getLongueurUtilisee(int tissuId) {
		Integer result = null;
		if (tissuId != 0) {
			try {
				result = dao.longueurUtilisee(tissuId);
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}
		return result == null ? 0 : result;
	}

	public List<TissuDto> getObservablePage(int page, int pageSize, TissuSpecification specification) {
		return FXCollections.observableArrayList(dao.findAll(specification, PageRequest.of(page, pageSize)).stream()
				.map(this::convert).toList());
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
		return mapper.map(dto);
	}

	@Override
	public TissuDto convert(Tissu entity) {
		return mapper.map(entity);
	}

    public int countByRangementId(int id) {
		return getDao().countByRangementIdAndArchived(id, false);
    }

    public TissuDto addRangement(int id, Rangement rangement) {
		Tissu t = getById(id);
		t.setRangement(rangement);
		saveOrUpdate(t);
		return convert(t);
    }
}
