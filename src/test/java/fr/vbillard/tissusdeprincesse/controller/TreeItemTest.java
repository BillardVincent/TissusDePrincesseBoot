package fr.vbillard.tissusdeprincesse.controller;

import com.jfoenix.controls.JFXTreeView;
import fr.vbillard.tissusdeprincesseboot.controller.rangement.RangementTreeViewHelper;
import fr.vbillard.tissusdeprincesseboot.dao.RangementRootDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.RangementDto;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.RangementRoot;
import fr.vbillard.tissusdeprincesseboot.service.RangementRootService;
import fr.vbillard.tissusdeprincesseboot.service.RangementService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static fr.vbillard.tissusdeprincesse.testUtils.JavaFxTestUtil.initJavaFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TreeItemTest {

    @BeforeAll
    static void initJfxRuntime() {
        initJavaFx();
    }

    @InjectMocks
    private RangementTreeViewHelper rangementTreeViewHelper;
    @Mock
    private RangementService rangementService;
    @Mock
    private RangementRootService rangementRootService;

    @Mock
    private RangementRootDao rangementRootDao;

    @Test
    void treeViewTest(){

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

        List<RangementRoot> lst = new ArrayList<>();
        lst.add(root);

        when(rangementRootService.getDao()).thenReturn(rangementRootDao);
        when(rangementRootService.getAll()).thenReturn(lst);
        when(rangementRootService.convert(any())).thenCallRealMethod();
        when(rangementService.convert(any())).thenCallRealMethod();

        JFXTreeView<RangementDto> view2 =  rangementTreeViewHelper.buildByRoot(new JFXTreeView<>());

        assertEquals(1, view2.getRoot().getChildren().size());
        assertEquals(2, view2.getRoot().getChildren().get(0).getChildren().size());
        assertEquals(2, view2.getRoot().getChildren().get(0).getChildren().get(0).getChildren().size());
        assertEquals("0.1.4", view2.getRoot().getChildren().get(0).getChildren().get(0).getChildren().get(1).getValue().getNom());
    }
}
