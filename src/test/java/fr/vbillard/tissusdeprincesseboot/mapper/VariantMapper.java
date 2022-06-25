package fr.vbillard.tissusdeprincesseboot.mapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.rozidan.springboot.modelmapper.WithModelMapper;

import fr.vbillard.tissusdeprincesseboot.dao.MatiereDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissageDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.factory.FactoryContext;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;

@RunWith(SpringRunner.class)
public class VariantMapper {

	@InjectMocks
	ModelMapper mapper;
	@Mock
	MatiereDao matiereDao;
	@Mock
	TissageDao tissageDao;
	@Mock
	TissusRequisDao tissusRequisDao;

	@Configuration
	@WithModelMapper(basePackages = "fr.vbillard.tissusdeprincesseboot")
	public static class Application {
	}

	@Test
	public void DtoToVariant() {
		FactoryContext context = new FactoryContext();
		TissuVariantDto dto = context.getTissuVariantDto();

		when(matiereDao.getByValue(dto.getMatiere())).thenReturn(context.getMatiere());
		when(tissageDao.getByValue(dto.getTissage())).thenReturn(context.getTissage());
		when(tissusRequisDao.getById(dto.getTissuRequisId())).thenReturn(context.getTissuRequis());
		TissuVariant variant = mapper.map(dto, TissuVariant.class);

		assertEquals(variant.getId(), dto.getId());
		assertEquals(variant.getMatiere().getValue(), dto.getMatiere());
		assertEquals(variant.getMatiere().getId(), context.getMatiere().getId());

	}
}
