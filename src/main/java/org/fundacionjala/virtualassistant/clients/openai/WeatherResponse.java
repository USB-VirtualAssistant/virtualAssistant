package org.fundacionjala.virtualassistant.clients.openai;


public class WeatherResponse {
    public String location;
    public WeatherUnit unit;
    public int temperature;
    public String description;

    public WeatherResponse(String location, WeatherUnit unit, int temperature, String description) {
        this.location = location;
        this.unit = unit;
        this.temperature = temperature;
        this.description = description;
    }
}
