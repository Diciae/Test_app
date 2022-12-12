package kuzmindenis.test.project.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConf {

    //экземпляр RestTemplate для нашего сервиса
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
