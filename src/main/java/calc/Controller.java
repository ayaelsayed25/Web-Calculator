package calc;

import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
public class Controller {
    Model model;
    public Controller()
    {
        model = new Model();
    }
    @GetMapping("/operation")
    public String operation(@RequestParam String firstOperand, @RequestParam String secondOperand, @RequestParam String operator) {
        return model.operations(firstOperand, secondOperand, operator);
    }


    @GetMapping("/backSpace")
    public String backSpace(@RequestParam String operand)
    {
        return model.backspace(operand);
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/operation").allowedOrigins("http://localhost:8081");
                registry.addMapping("/backSpace").allowedOrigins("http://localhost:8081");
            }
        };
    }




}
