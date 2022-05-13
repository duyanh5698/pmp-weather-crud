package com.oddle.app.weather.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oddle.app.weather.dto.WeatherDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WEATHER", schema = "weather")
@Data
@NoArgsConstructor
public class Weather
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "weather_id")
    private Long weatherId;

    @Column(name = "main")
    private String main;

    @Column(name = "description")
    private String description;

    @Column(name = "icon")
    private String icon;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "createDate")
    private Date createDate;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "updateDate")
    private Date updateDate;

    @Column(name = "city_name")
    private String cityName;

    @PrePersist
    public void addCreateDate(){
        this.createDate = new Date();
        this.updateDate = this.createDate;
    }

    @PreUpdate
    public void addUpdateDate(){
        this.updateDate = new Date();
    }

    public Weather(Long weatherId, String main, String description, String icon, String cityName) {
        this.weatherId = weatherId;
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.cityName = cityName;
    }

    public void setWeather(WeatherDto dto){
        this.main = dto.getMain();
        this.description = dto.getDescription();
        this.icon = dto.getIcon();
    }
}
