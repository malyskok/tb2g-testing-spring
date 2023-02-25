package org.springframework.samples.petclinic.sfg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("base")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BaseConfig.class, LaurelConfig.class})
public class HearingInterprterTest {

    @Autowired
    HearingInterprter hearingInterprter;

    @Test
    public void whatIheard() {
        //given

        //when
        String result = hearingInterprter.whatIheard();

        //then
        assertThat("Laurel").isEqualTo(result);
    }
}