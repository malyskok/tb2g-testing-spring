package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.HearingInterprter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("component")
@SpringJUnitConfig(classes = HearingInterprterComponentScanTest.TestConfig.class)
class HearingInterprterComponentScanTest {

    @Configuration
    @Profile("component")
    @ComponentScan("org.springframework.samples.petclinic.sfg")
    static class TestConfig{

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