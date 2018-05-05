package org.posila.cities.cities;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.posila.cities.cities.dao.CityDAO;
import org.posila.cities.cities.dao.ContinentDAO;
import org.posila.cities.cities.dao.CountryDAO;
import org.posila.cities.cities.entities.City;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.ContinentsWrapper;
import org.posila.cities.cities.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {

    private ContinentDAO continentDAO;
    private CountryDAO countryDAO;
    private CityDAO cityDAO;

    @RequestMapping(value = "/all", method = RequestMethod.GET,
            produces = "application/json")
    public String printAll() throws JsonProcessingException {
        return new ContinentsWrapper(continentDAO.findAll()).asJson();
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public void clearAll() {
        cityDAO.deleteAll();
        countryDAO.deleteAll();
        continentDAO.deleteAll();
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public void init() {
        clearAll();
        Continent africa = new Continent("Africa")
                .withCountry(new Country("Algeria").withCity(new City("Algiers")))
                .withCountry(new Country("Angola").withCity(new City("Luanda")))
                .withCountry(new Country("Benin").withCity(new City("Porto Novo, Cotonou")))
                .withCountry(new Country("Botswana").withCity(new City("Gaborone")))
                .withCountry(new Country("Burkina Faso").withCity(new City("Ouagadougou")))
                .withCountry(new Country("Burundi").withCity(new City("Bujumbura")))
                .withCountry(new Country("Cameroon").withCity(new City("Yaoundé")))
                .withCountry(new Country("Cape Verde").withCity(new City("Praia")))
                .withCountry(new Country("Central African Republic").withCity(new City("Bangui")))
                .withCountry(new Country("Chad").withCity(new City("N'Djamena")))
                .withCountry(new Country("Comoros").withCity(new City("Moroni")))
                .withCountry(new Country("Republic of the Congo").withCity(new City("Brazzaville")))
                .withCountry(new Country("Democratic Republic of the Congo").withCity(new City("Kinshasa")))
                .withCountry(new Country("Côte d'Ivoire").withCity(new City("Yamoussoukro")))
                .withCountry(new Country("Djibouti").withCity(new City("Djibouti")))
                .withCountry(new Country("Egypt").withCity(new City("Cairo")))
                .withCountry(new Country("Equatorial Guinea").withCity(new City("Malabo")))
                .withCountry(new Country("Eritrea").withCity(new City("Asmara")))
                .withCountry(new Country("Ethiopia").withCity(new City("Addis Ababa")))
                .withCountry(new Country("Gabon").withCity(new City("Libreville")))
                .withCountry(new Country("The Gambia").withCity(new City("Banjul")))
                .withCountry(new Country("Ghana").withCity(new City("Accra")))
                .withCountry(new Country("Guinea").withCity(new City("Conakry")))
                .withCountry(new Country("Guinea").withCity(new City("Bissau")))
                .withCountry(new Country("Kenya").withCity(new City("Nairobi")))
                .withCountry(new Country("Lesotho").withCity(new City("Maseru")))
                .withCountry(new Country("Liberia").withCity(new City("Monrovia")))
                .withCountry(new Country("Libya").withCity(new City("Tripoli")))
                .withCountry(new Country("Madagascar").withCity(new City("Antananarivo")))
                .withCountry(new Country("Malawi").withCity(new City("Lilongwe")))
                .withCountry(new Country("Mali").withCity(new City("Bamako")))
                .withCountry(new Country("Mauritania").withCity(new City("Nouakchott")))
                .withCountry(new Country("Mauritius").withCity(new City("Port Louis")))
                .withCountry(new Country("Morocco").withCity(new City("Rabat")))
                .withCountry(new Country("Mozambique").withCity(new City("Maputo")))
                .withCountry(new Country("Namibia").withCity(new City("Windhoek")))
                .withCountry(new Country("Niger").withCity(new City("Niamey")))
                .withCountry(new Country("Nigeria").withCity(new City("Abuja")))
                .withCountry(new Country("Rwanda").withCity(new City("Kigali")))
                .withCountry(new Country("São Tomé and Príncipe").withCity(new City("São Tomé")))
                .withCountry(new Country("Senegal").withCity(new City("Dakar")))
                .withCountry(new Country("Seychelles").withCity(new City("Victoria")))
                .withCountry(new Country("Sierra Leone").withCity(new City("Freetown")))
                .withCountry(new Country("Somalia").withCity(new City("Mogadishu")))
                .withCountry(new Country("South Africa").withCity(new City("Pretoria")))
                .withCountry(new Country("South Sudan").withCity(new City("Juba")))
                .withCountry(new Country("Sudan").withCity(new City("Khartoum")))
                .withCountry(new Country("Swaziland").withCity(new City("Mbabane")))
                .withCountry(new Country("Tanzania").withCity(new City("Dodoma")))
                .withCountry(new Country("Togo").withCity(new City("Lome")))
                .withCountry(new Country("Tunisia").withCity(new City("Tunis")))
                .withCountry(new Country("Uganda").withCity(new City("Kampala")))
                .withCountry(new Country("Western Sahara").withCity(new City("El Aaiún")))
                .withCountry(new Country("Zambia").withCity(new City("Lusaka")))
                .withCountry(new Country("Zimbabwe").withCity(new City("Harare")));

        Continent europe = new Continent("Europe")
                .withCountry(new Country("Albania").withCity(new City("Tirana")))
                .withCountry(new Country("Andorra").withCity(new City("Andorra la Vella")))
                .withCountry(new Country("Austria").withCity(new City("Vienna")))
                .withCountry(new Country("Belarus").withCity(new City("Minsk")))
                .withCountry(new Country("Belgium").withCity(new City("Brussels")))
                .withCountry(new Country("Bosnia and Herzegovina").withCity(new City("Sarajevo")))
                .withCountry(new Country("Bulgaria").withCity(new City("Sofia")))
                .withCountry(new Country("Croatia").withCity(new City("Zagreb")))
                .withCountry(new Country("Cyprus (Kupros)").withCity(new City("Nicosia")))
                .withCountry(new Country("Czech Republic (Česko)").withCity(new City("Prague")))
                .withCountry(new Country("Denmark (Danmark)").withCity(new City("Copenhagen")))
                .withCountry(new Country("Estonia (Eesti)").withCity(new City("Tallinn")))
                .withCountry(new Country("Finland (Suomi)").withCity(new City("Helsinki")))
                .withCountry(new Country("France").withCity(new City("Paris")))
                .withCountry(new Country("Germany (Deutschland)").withCity(new City("Berlin")))
                .withCountry(new Country("Greece (Ελλάδα)").withCity(new City("Athens")))
                .withCountry(new Country("Hungary (Magyarország)").withCity(new City("Budapest")))
                .withCountry(new Country("Iceland (Island)").withCity(new City("Reykjavik")))
                .withCountry(new Country("Republic of Ireland (Éire)").withCity(new City("Dublin")))
                .withCountry(new Country("Italy (Italia)").withCity(new City("Rome")))
                .withCountry(new Country("Kosovo").withCity(new City("Pristina")))
                .withCountry(new Country("Latvia (Latvija)").withCity(new City("Riga")))
                .withCountry(new Country("Liechtenstein").withCity(new City("Vaduz")))
                .withCountry(new Country("Lithuania (Lietuva)").withCity(new City("Vilnius")))
                .withCountry(new Country("Luxembourg").withCity(new City("Luxembourg City")))
                .withCountry(new Country("Macedonia (FYROP) (Македонија)").withCity(new City("Skopje")))
                .withCountry(new Country("Malta").withCity(new City("Valletta")))
                .withCountry(new Country("Moldova").withCity(new City("Chisinau")))
                .withCountry(new Country("Monaco").withCity(new City("Monaco")))
                .withCountry(new Country("Montenegro (Crna Gora, Црна Гора)").withCity(new City("Podgorica")))
                .withCountry(new Country("Netherlands (Nederland)").withCity(new City("Amsterdam (Capital), The Hague (Location of Government)")))
                .withCountry(new Country("Norway (Norge)").withCity(new City("Oslo")))
                .withCountry(new Country("Poland").withCity(new City("Warsaw")).withCity(new City("Krakow")).withCity(new City("Gdansk")).withCity(new City("Poznan")).withCity(new City("Zgierz")))
                .withCountry(new Country("Portugal").withCity(new City("Lisbon")))
                .withCountry(new Country("Romania (România)").withCity(new City("Bucharest")))
                .withCountry(new Country("Russia (Россия)").withCity(new City("Moscow")))
                .withCountry(new Country("San Marino").withCity(new City("San Marino")))
                .withCountry(new Country("Serbia (Србија)").withCity(new City("Belgrade")))
                .withCountry(new Country("Slovakia (Slovensko)").withCity(new City("Bratislava")))
                .withCountry(new Country("Slovenia (Slovenija)").withCity(new City("Ljubljana")))
                .withCountry(new Country("Spain (España)").withCity(new City("Madrid")))
                .withCountry(new Country("Sweden (Sverige)").withCity(new City("Stockholm")))
                .withCountry(new Country("Switzerland (German: Schweiz, French: Suisse, Italian: Svizzera, Romansh: Svizra)").withCity(new City("Bern")))
                .withCountry(new Country("Ukraine (Україна)").withCity(new City("Kyiv or Kiev")))
                .withCountry(new Country("United Kingdom").withCity(new City("London")))
                .withCountry(new Country("Vatican City (Italian: Città del Vaticano, Latin: Civitas Vaticana)").withCity(new City("Vatican City")));

        Continent northAmerica = new Continent("North America")
                .withCountry(new Country("Canada").withCity(new City("Ottawa")))
                .withCountry(new Country("Greenland").withCity(new City("Nuuk")))
                .withCountry(new Country("United States").withCity(new City("Washington DC")))
                .withCountry(new Country("Mexico").withCity(new City("Mexico City")));

        continentDAO.save(africa);
        continentDAO.save(europe);
        continentDAO.save(northAmerica);

    }

    @Autowired
    public void setContinentDAO(ContinentDAO continentDAO) {
        this.continentDAO = continentDAO;
    }

    @Autowired
    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    @Autowired
    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }
}
