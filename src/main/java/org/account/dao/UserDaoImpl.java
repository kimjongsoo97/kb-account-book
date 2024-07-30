package org.account.dao;

import org.account.common.JDBCUtil;
import org.account.domain.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    // SQL 쿼리문
    private static final String USER_LIST = "SELECT * FROM users";
    private static final String USER_GET = "SELECT * FROM users WHERE id=?";
    private static final String USER_INSERT = "INSERT INTO users (id, password, name, role, gender, phone_number, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String USER_UPDATE = "UPDATE users SET name=?, role=?, gender=?, phone_number=?, email=? WHERE id=?";
    private static final String USER_DELETE = "DELETE FROM users WHERE id=?";

    @Override
    public int create(UserVO user) throws SQLException {
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(USER_INSERT)) {
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getPhoneNumber());
            stmt.setString(7, user.getEmail());
            return stmt.executeUpdate();
        }
    }

    private UserVO map(ResultSet rs) throws SQLException {
        return UserVO.builder()
                .id(rs.getString("id"))
                .password(rs.getString("password"))
                .name(rs.getString("name"))
                .gender(rs.getString("gender"))
                .phoneNumber(rs.getString("phone_number"))
                .role(rs.getString("role"))
                .email(rs.getString("email"))
                .build();
    }

    @Override
    public List<UserVO> getList() throws SQLException {
        List<UserVO> userList = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(USER_LIST);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UserVO user = map(rs);
                userList.add(user);
            }
        }
        return userList;
    }

    @Override
    public Optional<UserVO> get(String id) throws SQLException {
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(USER_GET)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public int update(UserVO user) throws SQLException {
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(USER_UPDATE)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getRole());
            stmt.setString(3, user.getGender());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getId());
            return stmt.executeUpdate();
        }
    }

    @Override
    public int delete(String id) throws SQLException {
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(USER_DELETE)) {
            stmt.setString(1, id);
            return stmt.executeUpdate();
        }
    }
}