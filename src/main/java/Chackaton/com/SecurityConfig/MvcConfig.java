package Chackaton.com.SecurityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/home.html");
        registry.addViewController("/home").setViewName("forward:/home.html");
        registry.addViewController("/hello").setViewName("forward:/hello.html");
        registry.addViewController("/login").setViewName("forward:/login.html");
        registry.addViewController("/registration").setViewName("forward:/registration.html");
        registry.addViewController("/main").setViewName("forward:/main.html");
        registry.addViewController("/createProduct").setViewName("forward:/createProduct.html");
        registry.addViewController("/AllProducts").setViewName("forward:/AllProducts.html");
        registry.addViewController("/orders-admin").setViewName("forward:/orders-admin.html");
        registry.addViewController("/my-orders").setViewName("forward:/my-orders.html");
        registry.addViewController("/profile-edit").setViewName("forward:/profile-edit.html");
        registry.addViewController("/forgot-password").setViewName("forward:/forgot-password.html");


        registry.addViewController("/catalog").setViewName("forward:/catalog.html");
        registry.addViewController("/index").setViewName("forward:/index.html");
        registry.addViewController("/organizations").setViewName("forward:/organizations.html");
        registry.addViewController("/warehouses").setViewName("forward:/warehouses.html");
        registry.addViewController("/storage-zones").setViewName("forward:/storage-zones.html");
        registry.addViewController("/racks").setViewName("forward:/racks.html");
        registry.addViewController("/shelves").setViewName("forward:/shelves.html");
        registry.addViewController("/stock").setViewName("forward:/stock.html");
        registry.addViewController("/create_organization").setViewName("forward:/create_organization.html");


        registry.addViewController("/documentTTH").setViewName("forward:/documentTTH.html");




    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Уберите или ограничьте обработку статических ресурсов
        if (!registry.hasMappingForPattern("/static/**")) {
            registry.addResourceHandler("/static/**")
                    .addResourceLocations("classpath:/static/");
        }
    }
}