package org.fundacionjala.virtualassistant;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:postgres.properties"),
        @PropertySource("classpath:mongodb.properties")
})
public class DataBaseConfig {
}
