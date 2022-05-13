package com.oddle.app.weather.repository;

import com.oddle.app.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWeatherRepository extends JpaRepository<Weather, Long> {
    List<Weather> findByCityName(String name);


}
