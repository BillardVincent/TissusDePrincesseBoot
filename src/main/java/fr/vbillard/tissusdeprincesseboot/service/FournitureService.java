package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureDao;
import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FournitureService extends AbstractService<Fourniture> {

	private ModelMapper mapper;
	private FournitureDao dao;

	public ObservableList<FournitureDto> getObservableList() {
		return FXCollections.observableArrayList(
				dao.findAll().stream().map(t -> mapper.map(t, FournitureDto.class)).collect(Collectors.toList()));
	}

	public boolean existByReference(String string) {
		return dao.existsByReference(string);
	}

	public void archive(FournitureDto dto) {
		Fourniture t = mapper.map(dto, Fourniture.class);
		t.setArchived(true);
		dao.save(t);

	}

	public void delete(FournitureDto dto) {
		delete(mapper.map(dto, Fourniture.class));
	}

	public FournitureDto saveOrUpdate(FournitureDto dto) {
		Fourniture t = mapper.map(dto, Fourniture.class);
		return mapper.map(saveOrUpdate(t), FournitureDto.class);
	}

	/**
	 * Contient la conversion de l'unité
	 */
	@Override
	protected void beforeSaveOrUpdate(Fourniture entity) {
		/*
		if (UnitePoids.GRAMME_M.equals(entity.getUnitePoids())) {
			if (entity.getLaize() == 0) {
				throw new IllegalData(
						"La laize doit être renseignée pour calculer le poids en g/m² à partir du poids en g" + ".m².");
			}
			float conversion = (float) entity.getPoids() / ((float) entity.getLaize() / 100f);
			entity.setPoids(Math.round(conversion));
			entity.setUnitePoids(UnitePoids.GRAMME_M_CARRE);
		}

		 */
	}

	@Override
	protected Idao getDao() {
		return dao;
	}

	public ObservableList<FournitureDto> getObservablePage(int page, int pageSize) {
		return FXCollections.observableArrayList(dao.findAll(PageRequest.of(page, pageSize)).stream()
				.map(t -> mapper.map(t, FournitureDto.class)).collect(Collectors.toList()));
	}

	public float getQuantiteUtilisee(int fournitureId) {
		Float result = null;
		if (fournitureId != 0) {
			try {
				result = dao.quantiteUtilisee(fournitureId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result == null ? 0f : result;
	}

	public List<FournitureDto> getObservablePage(int page, int pageSize, FournitureSpecification specification) {
		return FXCollections.observableArrayList(dao.findAll(specification, PageRequest.of(page, pageSize)).stream()
				.map(t -> mapper.map(t, FournitureDto.class)).collect(Collectors.toList()));
	}

	/**
	 * S'execute au démarage pour recalculer les longueurs restantes
	 */
	public void batcheFournitureDisponible() {
		for (Fourniture f : getAll()) {
			float longueurRestante = f.getQuantite() - getQuantiteUtilisee(f.getId());
			f.setQuantiteDisponible(longueurRestante < 0 ? 0 : longueurRestante);
			saveOrUpdate(f);
		}
	}

}
