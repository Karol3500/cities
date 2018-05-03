package org.posila.cities.config.mongo;

import org.posila.cities.cities.lifecycleevents.ContinentCascadeSaveLifecycleEvent;
import org.posila.cities.cities.lifecycleevents.CountryCascadeSaveLifecycleEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Bean
    public CountryCascadeSaveLifecycleEvent userCascadingCountryMongoEventListener() {
        return new CountryCascadeSaveLifecycleEvent();
    }

    @Bean
    public ContinentCascadeSaveLifecycleEvent userCascadingContinentMongoEventListener() {
        return new ContinentCascadeSaveLifecycleEvent();
    }
}
