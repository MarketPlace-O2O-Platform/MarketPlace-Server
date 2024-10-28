package com.appcenter.marketplace.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



// MVC 기능을 커스터마이징 하고 확장하기 위한 설정
@Configuration
public class ImageConfig implements WebMvcConfigurer{

    @Value("${image.upload.path}")
    private String uploadFolder;

    //외부 폴더에 있는 이미지에 접근하기 위한 설정, handler url로 요청시 접근 가능하다.
    //프론트는 폴더이름을 몰라도 /image/**로 요청하면 외부 폴더에 매핑되서 접근가능하다.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                // 윈도우는 file:/, 리눅스는 file:/// 이다. 도커에서 실행할경우 file:/이다.
                // 리눅스에선 URI형식이 첫 번째 /는 URI의 스킴을 나타내고, 두 번째 /는 리눅스의 파일 시스템 루트를 나타낸다.
                .addResourceLocations("file:"+ uploadFolder);

    }

}
