package com.oddle.app.weather.service;

import com.google.gson.Gson;
import com.oddle.app.weather.config.Message;
import com.oddle.app.weather.dto.WeatherDto;
import com.oddle.app.weather.exception.RestExceptionHandler;
import com.oddle.app.weather.model.Weather;
import com.oddle.app.weather.repository.IWeatherRepository;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private IWeatherRepository weatherRepository;


    public String findWeather() {
        return "This is Weather!";
    }

    public static final String API_KEY = "fac22ae3ca72e36786cb53465bb780bf";
    public static final String WEATHER = "weather";
    public static final String CITY = "name";

    private JSONObject getObjectWeather(String cityName){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid="+API_KEY+"&lang=vi").build();
        try{
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return new JSONObject();
    }

    private String getStringWeather(String cityName){
        JSONArray array = getObjectWeather(cityName).getJSONArray(WEATHER);
        List<String> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            list.add(array.getJSONObject(i).toString());
        }
        return list.get(0);
    }

    private String getStringCity(String cityName){
        return getObjectWeather(cityName).getString(CITY);
    }

    public WeatherDto searchWeather(String cityName){
        String weather = getStringWeather(cityName);
        WeatherDto dto = new WeatherDto();
        if(weather != null){
            Gson g = new Gson();
            dto = g.fromJson(weather, WeatherDto.class);
            if(dto != null){
                Weather savedWeather = new Weather(dto.getId(), dto.getMain(), dto.getDescription(), dto.getIcon(), getStringCity(cityName));
                weatherRepository.save(savedWeather);
            }
        }
        return dto;
    }

    public List<Weather> findHistorical(){
        return weatherRepository.findAll();
    }

    public Weather updateRecordById(Long id, WeatherDto data) {
        Optional<Weather> weather = weatherRepository.findById(id);
        if(!weather.isPresent())
            throw new RestExceptionHandler(Message.NOT_FOUND);
        Weather updateWeather = weather.get();
        updateWeather.setWeather(data);
        return weatherRepository.save(updateWeather);
    }

    public boolean deleteRecordById(Long id) {
        Optional<Weather> weather = weatherRepository.findById(id);
        if(!weather.isPresent())
            throw new RestExceptionHandler(Message.NOT_FOUND);
        weatherRepository.deleteById(id);
        return true;
    }

    public boolean deleteRecordsByName(String name){
        List<Weather> weathers = weatherRepository.findByCityName(name);
        if(weathers != null)
            throw new RestExceptionHandler(Message.NOT_FOUND);
        weatherRepository.deleteAll(weathers);
        return true;
    }
}
