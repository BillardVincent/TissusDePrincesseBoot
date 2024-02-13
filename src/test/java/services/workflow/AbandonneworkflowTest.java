package services.workflow;

import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.service.workflow.AbandonneWorkFlow;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
class AbandonneWorkflowTest {

    @InjectMocks
    AbandonneWorkFlow workFlow;

    @Test
    void firstTest(){
        Projet p = new Projet();
        p.setStatus(ProjectStatus.ABANDONNE);
        workFlow.setProjet(p);

        assertFalse(workFlow.isCancelPossible());
        assertFalse(workFlow.isNextPossible());
        assertFalse(workFlow.isArchivePossible());
        assertTrue(workFlow.isDeletePossible());
    }
}
