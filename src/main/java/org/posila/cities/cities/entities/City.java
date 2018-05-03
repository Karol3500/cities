package org.posila.cities.cities.entities;

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
        return String.format("City[name='%s']", name);
    }
}
