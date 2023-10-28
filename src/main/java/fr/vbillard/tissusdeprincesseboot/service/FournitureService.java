package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.ColorEntity;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Quantite;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.utils.color.ColorUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FournitureService extends AbstractDtoService<Fourniture, FournitureDto> {

	private MapperService mapper;
	private FournitureDao dao;
	private ImageService imageService;

	private static final Logger LOGGER = LogManager.getLogger(FournitureService.class);



	public boolean existByReference(String string) {
		return dao.existsByReference(string);
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

	}

	@Override
	protected FournitureDao getDao() {
		return dao;
	}

	public ObservableList<FournitureDto> getObservablePage(int page, int pageSize) {
		return FXCollections.observableArrayList(convertToDto(dao.findAllByArchived(PageRequest.of(page, pageSize),
				false)));
	}

	public float getQuantiteUtilisee(int fournitureId) {
		Float result = null;
		if (fournitureId != 0) {
			try {
				result = dao.quantiteUtilisee(fournitureId);
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}
		return result == null ? 0f : result;
	}

	public List<FournitureDto> getObservablePage(int page, int pageSize, FournitureSpecification specification) {
		return FXCollections.observableArrayList(convertToDto(dao.findAll(specification, PageRequest.of(page, pageSize))));
	}

	/**
	 * S'exécute au démarrage pour recalculer les quantités restantes
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

		if (dto.getColor() != null && !Color.TRANSPARENT.equals(dto.getColor())) {
			ColorEntity color = ColorUtils.colorToEntity(dto.getColor());
			color.setId(dto.colorId);
			entity.setColor(color);
		}
		
		return entity;
	}

	@Override
	public FournitureDto convert(Fourniture source) {
		return mapper.map(source);
	}

    public int countByRangementId(int id) {
		return getDao().countByRangementIdAndArchived(id, false);
    }

    public FournitureDto addRangement(int id, Rangement rangement) {
	    Fourniture f = getById(id);
	    f.setRangement(rangement);
	    saveOrUpdate(f);
	    return convert(f);

    }
}
