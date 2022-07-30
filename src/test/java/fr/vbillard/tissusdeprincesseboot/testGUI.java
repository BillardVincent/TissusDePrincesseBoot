package fr.vbillard.tissusdeprincesseboot;

import org.testfx.framework.junit.ApplicationTest;
import org.junit.Before;
import org.junit.BeforeClass;

public class testGUI extends ApplicationTest {

	protected TableView<Player> playerTable;

	@BeforeClass
	public static void setUpClass() throws Exception {
		ApplicationTest.launch(ClientApplication.class);
	}

	@Before
	public void setUp() {
		playerTable = lookup("#playerTable").queryTableView();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.show();
	}
}
