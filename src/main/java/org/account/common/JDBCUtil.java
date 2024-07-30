package org.account.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCUtil {
    private static HikariDataSource dataSource;

    static {
        try {
            // HikariCP 설정
            HikariConfig config = new HikariConfig();
            config.setDriverClassName("com.mysql.cj.jdbc.Driver"); // MySQL 드라이버 클래스명
            config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/kb_account"); // 데이터베이스 URL
            config.setUsername("kb_account"); // 데이터베이스 사용자명
            config.setPassword("kb_account"); // 데이터베이스 비밀번호

            // 커넥션 풀의 설정
            config.setMaximumPoolSize(10); // 최대 커넥션 풀 크기
            config.setConnectionTimeout(30000); // 커넥션 타임아웃 (30초)
            config.setIdleTimeout(600000); // 유휴 타임아웃 (10분)

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
