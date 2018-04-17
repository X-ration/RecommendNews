package com.adam.rec.user;

import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author adam
 * 创建于 2018-04-17 10:50.
 */
@Repository
public class CityRepository {

    private static List<String> cities = new ArrayList<>();
    static {
        cities.add("北京");
        cities.add("天津");
        cities.add("上海");
        cities.add("广州");
        cities.add("沈阳");
    }

    public List<String> getCities() {
        return cities;
    }

}
