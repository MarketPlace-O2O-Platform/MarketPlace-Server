package com.appcenter.marketplace.global.oracleRepository;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// sql 쿼리 실행 시 발생할 수 있는 예외를 Spring이 처리 한다.
@Repository
@Log4j2
public class InuLoginRepository {

    private final JdbcTemplate jdbcTemplate;

    public InuLoginRepository(@Qualifier("oracleJdbc") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean loginCheck(String studentId, String password) {
        String sql = "SELECT F_LOGIN_CHECK(?,?) FROM DUAL";
        try {
            String result = jdbcTemplate.queryForObject(sql, String.class,studentId,password);
            log.info("학교 디비 조회 결과 : {}",result);
            return "Y".equals(result);
        } catch (Exception e) {
            log.info("데이터베이스 연결 오류 메시지 : {}",e.getMessage());
            return false;
        }

    }
}
