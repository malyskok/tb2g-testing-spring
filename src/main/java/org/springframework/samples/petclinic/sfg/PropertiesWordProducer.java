package org.springframework.samples.petclinic.sfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 2019-02-16.
 */
@Component
@Profile({"externalized", "properties-laurel"})
@Primary
public class PropertiesWordProducer implements WordProducer {
    @Value("${say.word}")
    private String word;
    @Override
    public String getWord() {
        return word;
    }
}
