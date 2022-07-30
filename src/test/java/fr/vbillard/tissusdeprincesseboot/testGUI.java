package fr.vbillard.tissusdeprincesseboot;

import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;

import org.junit.Before;
import org.junit.BeforeClass;

public class testGUI extends ApplicationTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		ApplicationTest.launch(TissusDePrincesseFxApp.class);
	}

	@Before
	public void setUp() {
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.show();
	}
}
