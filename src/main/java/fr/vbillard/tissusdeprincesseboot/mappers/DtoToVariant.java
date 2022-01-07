package fr.vbillard.tissusdeprincesseboot.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import com.github.rozidan.springboot.modelmapper.WithModelMapper;
import fr.vbillard.tissusdeprincesseboot.config.ModelMapperConfig;
import fr.vbillard.tissusdeprincesseboot.dao.MatiereDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissageDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dao.TypeTissuDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.TissuVariant;
import lombok.AllArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;

@Component
@AllArgsConstructor
public class DtoToVariant extends TypeMapConfigurer<TissuVariantDto, TissuVariant> {
    MatiereDao ms;
    TypeTissuDao tts;
    TissageDao ts;
    TissusRequisDao trs;



    @Configuration
    @WithModelMapper(basePackages = {"fr.vbillard.tissusdeprincesseboot.mappers"},
            basePackageClasses = {ModelMapperConfig.class})
    public static class Application {
    }

    @Override
    public void configure(TypeMap<TissuVariantDto, TissuVariant> typeMap) {
        typeMap.setPostConverter(context -> {
            context.getDestination().setMatiere(ms.getByValue(context.getSource().getMatiere()));
            context.getDestination().setTypeTissu(tts.getByValue(context.getSource().getTypeTissu()));
            context.getDestination().setTissage(ts.getByValue(context.getSource().getTissage()));
            context.getDestination().setTissuRequis(trs.getById(context.getSource().getTissuRequisId()));
            return context.getDestination();
        });
        typeMap.addMappings(mapper -> mapper.using(new MatiereConverter()).map(TissuVariantDto::getMatiere, TissuVariant::setMatiere));
    }

    private class MatiereConverter extends AbstractConverter<String, Matiere> {
        @Override
        protected Matiere convert(String source) {
            return ms.getByValue(source);
        }
    }
}
