package fr.vbillard.tissusdeprincesseboot.controller.utils;

import java.net.URL;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Inventaire;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.controller.utils.path.PathEnum;
import lombok.Data;

@Data
@Component
public class FxData {

	private Page page;
	private PathEnum path;
	private TissuDto tissu;
	private PatronDto patron;
	private ProjetDto projet;
	private TissuUsed tissuUsed;
	private TissuRequisDto tissuRequis;
	private TissuVariantDto tissuVariant;
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

	public FxData getCopy() {
		try {
			return (FxData) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			throw new RuntimeException("Echec du clonage : " + this.toString());
		}
	}
}