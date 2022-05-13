package com.oddle.app.weather.controller;

import com.oddle.app.weather.dto.WeatherDto;
import com.oddle.app.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/search")
    public ResponseEntity<?> searchWeather(@RequestParam String cityName){
        return new ResponseEntity<>(weatherService.searchWeather(cityName), HttpStatus.OK);
    }

    @GetMapping("/search/historical")
    public ResponseEntity<?> searchWeather(){
        return new ResponseEntity<>(weatherService.findHistorical(), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateWeatherRecord(@PathVariable Long id, @RequestBody WeatherDto data){
        return new ResponseEntity<>(weatherService.updateRecordById(id, data), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteWeatherRecord(@PathVariable Long id){
        return new ResponseEntity<>(weatherService.deleteRecordById(id), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteWeatherRecordByCity(@RequestParam String name){
        return new ResponseEntity<>(weatherService.deleteRecordsByName(name), HttpStatus.OK);
    }

    @GetMapping("")
    public Map<String, Object> getWeathers() {
        return Collections.singletonMap("message", "Welcome to Oddle Backend Challenge");
    }
}