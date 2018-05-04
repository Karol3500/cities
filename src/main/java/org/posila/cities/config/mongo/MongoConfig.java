package org.posila.cities.config.mongo;

import org.posila.cities.cities.lifecycleevents.ContinentCascadeLifecycleEvent;
import org.posila.cities.cities.lifecycleevents.CountryCascadeLifecycleEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Bean
    public CountryCascadeLifecycleEvent userCascadingCountryMongoEventListener() {
        return new CountryCascadeLifecycleEvent();
    }

    @Bean
    public ContinentCascadeLifecycleEvent userCascadingContinentMongoEventListener() {
        return new ContinentCascadeLifecycleEvent();
    }
}
