package org.posila.cities.entities;

import javax.persistence.Id;

public class City {

    @Id
    public String id;
    public String name;

    public City(){

    }

    public City(String name){
        this.name = name;
    }
    public String toString() {
        return String.format(
                "City[id=%s, name='%s']",
                id, name);
    }
}
