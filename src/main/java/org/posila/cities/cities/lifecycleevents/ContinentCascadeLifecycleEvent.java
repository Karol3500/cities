package org.posila.cities.cities.lifecycleevents;

import org.posila.cities.cities.dao.CountryDAO;
import org.posila.cities.cities.entities.Continent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class ContinentCascadeLifecycleEvent extends AbstractMongoEventListener<Continent> {
    @Autowired
    private CountryDAO dao;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Continent> event) {
        dao.saveAll(event.getSource().getCountries());
    }
}
