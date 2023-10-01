package fr.vbillard.tissusdeprincesseboot.service;

import com.jfoenix.controls.JFXTreeView;
import fr.vbillard.tissusdeprincesseboot.dao.RangementDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.RangementDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TypeRangement;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.RangementRoot;
import javafx.scene.control.TreeItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RangementService extends AbstractService<Rangement> {

	private RangementDao dao;
	private RangementRootService rangementRootService;

	@Override
	protected RangementDao getDao() {
		return dao;
	}

	@Transactional
	public JFXTreeView<RangementDto> buildByRoot(RangementRoot root){

		RangementRoot rr = rangementRootService.getById(root.getId());
		TreeItem<RangementDto> rootItem = new TreeItem<>(rangementRootService.convert(rr));
		for (Rangement r : rr.getSubdivision()){
			rootItem.getChildren().add(buildItem(r));
		}
		return new JFXTreeView<>(rootItem);
	}

	/**
	 * Methode recursive
	 * @param rangement élément à convertir
	 * @return TreeItem<RangementDto>
	 */
	private TreeItem<RangementDto> buildItem(Rangement rangement) {
		TreeItem<RangementDto> item = new TreeItem<>(convert(rangement));
		for (Rangement r : rangement.getSubdivision()){
			item.getChildren().add(buildItem(r));
		}
		return item;

	}

	private RangementDto convert(Rangement source) {
		RangementDto dto = new RangementDto();
		dto.setId(source.getId());
		dto.setRang(0);
		dto.setNom(source.getNom());
		dto.setType(TypeRangement.RANGEMENT);
		return dto;
	}
}
