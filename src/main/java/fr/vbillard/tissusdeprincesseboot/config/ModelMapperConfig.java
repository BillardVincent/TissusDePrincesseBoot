package fr.vbillard.tissusdeprincesseboot.config;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.ConfigurationConfigurer;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import org.modelmapper.Provider;
import org.modelmapper.convention.MatchingStrategies;
@Component

public class ModelMapperConfig extends ConfigurationConfigurer{

    /**
     * Utilisation de la lib modelmapper-spring-boot-starter pour configurer modelmapper<br>
     *
     * @see <a href=
     *      "https://github.com/rozidan/modelmapper-spring-boot-starter">https://github.com/rozidan/modelmapper-spring-boot-starter</a>
     *
     * @author Rémy RABIER
     *
     */


        @Override
        public void configure(org.modelmapper.config.Configuration configuration) {
            configuration.setSkipNullEnabled(true);
            configuration.setPropertyCondition(value -> Utils.isNotEmpty(value));
            configuration.setMatchingStrategy(MatchingStrategies.STRICT);
            configuration.setProvider(delegatingProvider);
        }

        /**
         * Instancie un bean avec l'AbstractEntityBased vide pour ne pas écraser les valeurs déjà présente sur une entité en
         * cas de mise à jour
         */
        private Provider<Object> delegatingProvider = new Provider<Object>() {
            public Object get(ProvisionRequest<Object> request) {

                try {
                    if (request.getRequestedType().asSubclass(AbstractEntity.class) == null)
                        return null;
                } catch (ClassCastException e) {
                    return null;
                }

                if (request.getSource() == null)
                    return null;
                AbstractEntity entity = (AbstractEntity) BeanUtils.instantiateClass(request.getRequestedType());
                return entity;
            }
        };
    }


