package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureDao;
import fr.vbillard.tissusdeprincesseboot.dao.Idao;
import fr.vbillard.tissusdeprincesseboot.dao.QuantiteDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Quantite;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FournitureService extends AbstractDtoService<Fourniture, FournitureDto> {

	private MapperService mapper;
	private FournitureDao dao;
	private QuantiteDao quantiteDao;
	private ImageService imageService;

	public ObservableList<FournitureDto> getObservableList() {
		return FXCollections.observableArrayList(convertToDto(dao.findAll()));
	}

	public boolean existByReference(String string) {
		return dao.existsByReference(string);
	}

	public void archive(FournitureDto dto) {
		Fourniture t = convert(dto);
		t.setArchived(true);
		dao.save(t);

	}

	public void delete(FournitureDto dto) {
		delete(convert(dto));
	}

	@Override
	public void beforeDelete(Fourniture entity){
		Optional<Photo> p = imageService.getImage(entity);
		p.ifPresent(photo -> imageService.delete(photo));
	}

	/**
	 * Contient la conversion de l'unité
	 */
	@Override
	protected void beforeSaveOrUpdate(Fourniture entity) {
		/*
		entity.setQuantitePrincipale(quantiteDao.save(entity.getQuantitePrincipale()));
		if (entity.getQuantiteSecondaire() != null){
			entity.setQuantiteSecondaire(quantiteDao.save(entity.getQuantiteSecondaire()));

		}*/

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
	protected FournitureDao getDao() {
		return dao;
	}

	public ObservableList<FournitureDto> getObservablePage(int page, int pageSize) {
		return FXCollections.observableArrayList(convertToDto(dao.findAll(PageRequest.of(page, pageSize))));
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
		return FXCollections.observableArrayList(convertToDto(dao.findAll(specification, PageRequest.of(page, pageSize))));
	}

	/**
	 * S'execute au démarage pour recalculer les quantites restantes
	 */
	public void batchFournitureDisponible() {
		for (Fourniture f : getAll()) {
			if (f.getQuantitePrincipale() != null) {
				float longueurRestante = f.getQuantitePrincipale().getQuantite() - getQuantiteUtilisee(f.getId());
				f.setQuantiteDisponible(longueurRestante < 0 ? 0 : longueurRestante);
				saveOrUpdate(f);
			}
		}
	}

	@Override
	public Fourniture convert(FournitureDto dto) {
		Fourniture entity;
		if (dto.getId() == 0) {
			entity =  new Fourniture();
			entity.setQuantitePrincipale(new Quantite());
		} else {
			entity = getById(dto.getId());
		}
		
		BeanUtils.copyProperties(dto, entity);
		
		if (entity.getQuantitePrincipale() == null ) {
			entity.setQuantitePrincipale(new Quantite());
		}

		if(entity.getType() != null) {
			Unite unitePrincipale = Unite.getEnum(dto.getUnite());
			if (unitePrincipale == null) {
				unitePrincipale = entity.getType().getUnitePrincipaleConseillee();
			}
			entity.getQuantitePrincipale().setQuantite(Unite.convertir(dto.getQuantite(), unitePrincipale));
			entity.getQuantitePrincipale().setUnite(unitePrincipale);

			if (!Strings.isEmpty(dto.getIntituleSecondaire())) {

				if (entity.getQuantiteSecondaire() == null) {
					entity.setQuantiteSecondaire(new Quantite());
				}
				Unite uniteSecondaire = Unite.getEnum(dto.getUniteSecondaire());
				if (uniteSecondaire == null) {
					uniteSecondaire = entity.getType().getUniteSecondaireConseillee();
				}
				entity.getQuantiteSecondaire().setQuantite(Unite.convertir(dto.getQuantiteSec(), uniteSecondaire));
				entity.getQuantiteSecondaire().setUnite(uniteSecondaire);
			}
		}
		
		return entity;
	}

	@Override
	public FournitureDto convert(Fourniture source) {
		return mapper.map(source);
	}

}
