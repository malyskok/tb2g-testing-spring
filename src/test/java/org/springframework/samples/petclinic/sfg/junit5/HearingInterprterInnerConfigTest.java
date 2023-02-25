package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.HearingInterprter;
import org.springframework.samples.petclinic.sfg.LaurelWordProducer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("inner")
@SpringJUnitConfig(classes = HearingInterprterInnerConfigTest.InnerConfig.class)
class HearingInterprterInnerConfigTest {

    @Configuration
    @Profile("inner")
    static class InnerConfig {
        @Bean
        HearingInterprter hearingInterprter(){
            return new HearingInterprter(new LaurelWordProducer());
        }
    }

    @Autowired
    HearingInterprter hearingInterprter;

    @Test
    void whatIheard() {
        //when
        String result = hearingInterprter.whatIheard();
        //the
        assertThat("Laurel").isEqualTo(result);
    }
}