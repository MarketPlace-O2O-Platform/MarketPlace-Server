package com.appcenter.marketplace.global.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;


/*
    @requestPart 어노테이션은 content-type이 multipart-form인 요청에서 JSON과 파일 데이터를 동시에 객체로 만들어주는 어노테이션이다.
    하지만 content-type이 multipart-form 하나이므로 json데이터는 content-type이 null로 누락이 된다.
    content-type이 null이면 application/octet-stream 타입으로 지정해버린다.
    따라서 application/octet-stream을 다뤄주는 HttpMessageConverter를 추가하면 된다
 */
@Component
// application/octet-stream 타입의 Http 메시지에 대한 읽기 작업(Http 메시지 -> Java class)을 수행할 커스텀 메시지 컨버터.
// 즉, multipart-form 요청에서 JSON 데이터를 받아올 수 있게 된다.
public class OctetStreamReadMsgConverter extends AbstractJackson2HttpMessageConverter {
    @Autowired
    public OctetStreamReadMsgConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }

    // 기존 application/octet-stream 타입을 쓰기로 다루는 메시지 컨버터가 이미 존재 (ByteArrayHttpMessageConverter)
    // 따라서 해당 컨버터는 쓰기 작업에는 이용하면 안됨
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    protected boolean canWrite(MediaType mediaType) {
        return false;
    }
}
