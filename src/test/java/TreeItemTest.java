import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.RangementDto;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.RangementRoot;
import fr.vbillard.tissusdeprincesseboot.service.RangementService;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class TreeItemTest {

    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {});
    }

    @InjectMocks
    private RangementService rangementService;

    @Test
    public void treeViewTest(){

        RangementRoot root = new RangementRoot();
        root.setNom("0");
        Rangement r1 = new Rangement();
        r1.setNom("0.1");
        Rangement r2 = new Rangement();
        r2.setNom("0.2");
        root.getSubdivision().addAll(List.of(r1, r2));

        Rangement r3 = new Rangement();
        r3.setNom("0.1.3");
        Rangement r4 = new Rangement();
        r4.setNom("0.1.4");
        r1.getSubdivision().addAll(List.of(r3, r4));

       JFXTreeView<RangementDto> view =  rangementService.buildByRoot(root);

        assertEquals(2, view.getRoot().getChildren().size());
        assertEquals(2, view.getRoot().getChildren().get(0).getChildren().size());
        assertEquals("0.1.4", view.getRoot().getChildren().get(0).getChildren().get(1).getValue().getNom());

    }
}
