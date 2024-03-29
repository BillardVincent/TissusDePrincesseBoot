package fr.vbillard.tissusdeprincesseboot.controller.utils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public abstract class ViewListController implements IController {

	@FXML
	protected TilePane cardPane;
	@FXML
	protected FontAwesomeIconView previousIcon;
	@FXML
	protected FontAwesomeIconView nextIcon;
	@FXML
	protected Label start;
	@FXML
	protected Label end;
	@FXML
	protected Label total;

	protected StageInitializer initializer;
	protected Specification specification;

	protected int page;
	protected static final int PAGE_SIZE = 10;
	private long totalElement;

	protected abstract void setElements();

	protected void setPageInfo(long totalElement) {
		this.totalElement = totalElement;
		start.setText(Integer.toString((page) * PAGE_SIZE + 1));
		end.setText(Long.toString(Math.min((page + 1L) * PAGE_SIZE, totalElement)));
		total.setText(Long.toString(totalElement));

		previousIcon.setVisible(page > 0);
		previousIcon.setDisable(page <= 0);
		ClassCssUtils.setStyle(previousIcon, ClassCssUtils.CLICKABLE, page > 0);

		nextIcon.setVisible((page + 1L) * PAGE_SIZE <= totalElement);
		nextIcon.setDisable((page + 1L) * PAGE_SIZE > totalElement);
		ClassCssUtils.setStyle(nextIcon, ClassCssUtils.CLICKABLE, (page + 1L) * PAGE_SIZE <= totalElement);


	}

	@Override
	public void setStageInitializer(StageInitializer initializer, FxData data) {
		page = 0;
		this.initializer = initializer;
		if (data != null) {
			this.specification = data.getSpecification();
		}
		setElements();
	}

	public abstract void addNewElement(MouseEvent mouseEvent);

	@FXML
	public void previousPage(MouseEvent mouseEvent) {
		if (page > 0) {
			page--;
		}
		setElements();
	}

	@FXML
	public void nextPage(MouseEvent mouseEvent) {
		if ((page + 1L) * PAGE_SIZE < totalElement) {
			page++;
			setElements();
		}

	}
}
