package com.lichtbuch.gamezone.configs;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var mapper = new ModelMapper();
        mapper
            .getConfiguration()
            .setPropertyCondition(Conditions.isNotNull());

        return mapper;
    }

}
