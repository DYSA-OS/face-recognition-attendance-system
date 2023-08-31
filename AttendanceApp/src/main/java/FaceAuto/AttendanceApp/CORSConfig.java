package FaceAuto.AttendanceApp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//@EnableWebMvc
//public class CORSConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // 모든 엔드포인트에 대해 CORS 설정을 적용
//                .allowedOrigins("*") // 허용할 오리진들 (원하는 도메인을 지정)
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드들
//                .allowedHeaders("*") // 허용할 헤더들
//                .allowCredentials(true); // 인증 정보 (쿠키 등) 허용 여부
//    }
//}

//이 부분은 js으로 직접 cors 모드로 변경