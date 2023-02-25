package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@Profile("base")
@Configuration
public class BaseConfig {

    @Bean
    HearingInterprter hearingInterprter(WordProducer wordProducer){
        return new HearingInterprter(wordProducer);
    }
}
