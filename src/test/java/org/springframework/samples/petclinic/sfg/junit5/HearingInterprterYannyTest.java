package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.sfg.BaseConfig;
import org.springframework.samples.petclinic.sfg.HearingInterprter;
import org.springframework.samples.petclinic.sfg.YannyConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("base")
@SpringJUnitConfig(classes = {BaseConfig.class, YannyConfig.class})
class HearingInterprterYannyTest {

    @Autowired
    HearingInterprter hearingInterprter;
    @Test
    void whatIheard() {
        //when
        String result = hearingInterprter.whatIheard();
        //the
        assertThat("Yanny").isEqualTo(result);
    }
}