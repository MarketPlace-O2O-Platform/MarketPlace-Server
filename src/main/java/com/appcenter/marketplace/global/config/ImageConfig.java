package com.appcenter.marketplace.global.config;

import com.appcenter.marketplace.global.swagger.OctetStreamReadMsgConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


// MVC 기능을 커스터마이징 하고 확장하기 위한 설정
@Configuration
@RequiredArgsConstructor
public class ImageConfig implements WebMvcConfigurer{

    @Value("${image.upload.path}")
    private String imageUploadFolder;
    @Value("${tempImage.upload.path}")
    private String tempImageUploadFolder;
    @Value("${receipt.upload.path}")
    private String receiptUploadFolder;

    private final OctetStreamReadMsgConverter octetStreamReadMsgConverter;


    //외부 폴더에 있는 이미지에 접근하기 위한 설정, handler url로 요청시 접근 가능하다.
    //프론트는 폴더이름을 몰라도 /image/**로 요청하면 외부 폴더에 매핑되서 접근가능하다.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                // 윈도우는 file:/, 리눅스는 file:/// 이다. 도커에서 실행할경우 file:/이다.
                // 리눅스에선 URI형식이 첫 번째 /는 URI의 스킴을 나타내고, 두 번째 /는 리눅스의 파일 시스템 루트를 나타낸다.
                .addResourceLocations("file:"+ imageUploadFolder);

        registry.addResourceHandler("/image/tempMarket/**")
                .addResourceLocations("file:"+ tempImageUploadFolder);

        registry.addResourceHandler("/image/receipt/**")
                .addResourceLocations("file:"+ receiptUploadFolder);

    }

    // Spring이 기본적으로 생성하는 HttpMessageConverter 리스트에 추가로 생성해 줄 커스텀 메시지 컨버터를 추가해주기 위한 설정 파일.
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(octetStreamReadMsgConverter);
    }

}
