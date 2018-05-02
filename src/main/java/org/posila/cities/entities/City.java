package org.posila.cities.entities;

import org.springframework.data.annotation.Id;

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
