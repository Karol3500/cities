package org.posila.cities.cities.lifecycleevents;

import org.posila.cities.cities.dao.CityDAO;
import org.posila.cities.cities.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class CountryCascadeSaveLifecycleEvent extends AbstractMongoEventListener<Country> {
    @Autowired
    private CityDAO cityDAO;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Country> event) {
        cityDAO.saveAll(event.getSource().getCities());
    }
}
