package com.bootiful.framework.converters;

import com.bootiful.framework.models.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import javax.persistence.EntityManager;

public class StringToBaseModelConverterFactory implements ConverterFactory<String, BaseModel> {

    @Autowired
    EntityManager em;

    public <T extends BaseModel> Converter<String, T> getConverter(Class<T> targetType) {

        return new StringToBaseModelConverter<T>(targetType, em);

    }

    private final class StringToBaseModelConverter<T extends BaseModel> implements Converter<String, T> {

        private Class<T> type;

        private EntityManager em;

        public StringToBaseModelConverter( Class<T> type, EntityManager em ) {
            this.type = type;
            this.em = em;
        }

        public T convert(String source) {

            if(source == null || source.isEmpty()){

                return null;
            }

            try {

                return em.find(type, Long.parseLong(source.trim()));

            } catch (Exception e) {

                return null;

            }

        }
    }
}
