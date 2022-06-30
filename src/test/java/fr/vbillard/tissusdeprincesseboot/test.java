package fr.vbillard.tissusdeprincesseboot;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.vbillard.tissusdeprincesseboot.controller.common.CheckBoxChoiceController;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.stage.Stage;

@SpringBootTest(classes = TissuDePrincesseBootApplication.class)
public class test {

	@Autowired
	private CheckBoxChoiceController controller;

	@Test
	public void test() {
		// Arrange
		FxData data = new FxData();
		data.setListDataCBox(Arrays.asList("test1", "test2", "test3"));
		// Act
		controller.setStage(new Stage(), data);

		// Assert
		assertEquals(3, controller.content.getChildren().size());

	}

}
