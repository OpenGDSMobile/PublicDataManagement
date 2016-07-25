package com.openGDSMobileApplicationServer;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

/**
 * Created by intruder on 16. 7. 25.
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        DefaultResourceLoader loader = new DefaultResourceLoader();
        try{
            String CLASSPATH_STATIC_LOCATIONS =  loader.getResource("/static/").getURL().toString();
            registry.addResourceHandler("/static/**").addResourceLocations(CLASSPATH_STATIC_LOCATIONS);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
