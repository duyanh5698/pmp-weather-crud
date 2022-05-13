package com.oddle.app.weather.dto;

import lombok.Getter;

import javax.persistence.Column;

@Getter
public class WeatherDto {

    private Long id;

    private String main;

    private String description;

    private String icon;
}
