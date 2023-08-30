package FaceAuto.AttendanceApp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // addResourceHandler : 스프링부트에서 확인할 경로 설정
        // addResourceLocations : 실제 시스템의 폴더 위치 설정

        // 아래 경로를 해당 폴더의 경로로 수정 해야된다.
        registry.addResourceHandler("/img/**").addResourceLocations("file:///Users/sola/Documents/GitHub/FaceAutoAttendence/data/");
    }
}