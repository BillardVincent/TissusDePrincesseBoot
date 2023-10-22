package fr.vbillard.tissusdeprincesseboot.controller.utils;

import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.*;
import fr.vbillard.tissusdeprincesseboot.model.*;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Data
@Component
public class FxData {

	private static final Logger LOGGER = LogManager.getLogger(FxData.class);

	private Page page;
	private PathEnum path;
	private TissuDto tissu;
	private PatronDto patron;
	private ProjetDto projet;
	private TissuUsed tissuUsed;
	private TissuRequisDto tissuRequis;
	private FournitureDto fourniture;
	private FournitureRequiseDto fournitureRequise;
	private int longueurRequise;
	private float quantiteRequise;
	private URL url;
	private List<String> listValues;
	private List<String> listDataCBox;
	private Specification specification;
	private Inventaire inventaire;
	private List<Inventaire> listInventaire;
	private IController parentController;
	private FournitureUsed fournitureUsed;
	private PatronVersionDto patronVersion;
	private boolean allSelectedEqualsNull = true;
	private Integer rank;
	private RangementRoot rangementRoot;
	private String nom;

	public FxData getCopy() {
		try {
			return (FxData) this.clone();
		} catch (CloneNotSupportedException e) {
			LOGGER.error(e);
			throw new RuntimeException("Echec du clonage : " + this.toString());
		}
	}

}
