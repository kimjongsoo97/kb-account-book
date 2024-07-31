package org.account.dao;

import org.account.common.JDBCUtil;
import org.account.domain.AccountVO;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class AccountDaoImpl implements AccountDao {

    @Override
    public int getTotalAmount(String userId) throws SQLException {
        String sql = "SELECT SUM(EXPENSE+INCOME) AS totalAmount FROM accounts where userId";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("totalAmount");
                }
            }

        }
        return 0;
    }


    @Override
    public int create(AccountVO account) throws SQLException {
        String sql = "INSERT INTO account (id, title, income, expense, description, date, userId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, account.getId());
            stmt.setString(2, account.getTitle());
            stmt.setObject(3, account.getIncome(), Types.INTEGER);
            stmt.setObject(4, account.getExpense(), Types.INTEGER);
            stmt.setString(5, account.getDescription());
            stmt.setDate(6, account.getDate() != null ? new java.sql.Date(account.getDate().getTime()) : null);
            stmt.setString(7, account.getUserId());
            return stmt.executeUpdate();
        }
    }

    private AccountVO map(ResultSet rs) throws SQLException {
        AccountVO account = new AccountVO();
        account.setId(rs.getLong("id"));
        account.setTitle(rs.getString("title"));
        account.setIncome(rs.getObject("income", Integer.class));
        account.setExpense(rs.getObject("expense", Integer.class));
        account.setTotal(rs.getObject("total", Integer.class));
        java.sql.Date sqlDate = rs.getDate("date");
        account.setDate(sqlDate != null ? new java.util.Date(sqlDate.getTime()) : null);
        account.setDescription(rs.getString("description"));
        return account;

    }

    public List<AccountVO> mapList(ResultSet rs) throws SQLException {
        List<AccountVO> accountList = new ArrayList<>();
        while (rs.next()) {
            AccountVO account = map(rs);
            accountList.add(account);
        }
        return accountList;
    }

    @Override
    public List<AccountVO> getList(String user) throws SQLException {
        String sql = "SELECT * FROM account where userId=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user);
            try (ResultSet rs = stmt.executeQuery()) {
                return mapList(rs);
            }
        }
    }

    @Override
    public Optional<AccountVO> get(String userId, Long id) throws SQLException {
        String sql = "SELECT * FROM account where userId=? and id=?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setLong(2, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<AccountVO> search(String userId, String keyword) throws SQLException {
        String sql = "SELECT * FROM account where userId=? and (title like? or description like ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                return mapList(rs);
            }
        }
    }
        @Override
        public int update(String userId, AccountVO account) throws SQLException {
            String sql = "UPDATE accounts SET title = ?, total = ?, income = ?, expense = ?, category = ?, description = ?, date = ? WHERE user_id = ? AND id = ?";
            try (Connection connection = JDBCUtil.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, account.getTitle());
                stmt.setInt(2, account.getTotal());
                stmt.setInt(3, account.getIncome());
                stmt.setInt(4, account.getExpense());
                stmt.setString(5, account.getCategory());
                stmt.setString(6, account.getDescription());
                stmt.setDate(7, new java.sql.Date(account.getDate().getTime()));
                stmt.setString(8, userId);
                stmt.setLong(9, account.getId());
                return stmt.executeUpdate();
            }
        }

    @Override
    public int delete(String userId, Long id) throws SQLException {
        String sql = "DELETE FROM account where userId=? and id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setLong(2, id);
            stmt.executeUpdate();
        }
        return 0;
    }

    @Override
    public List<AccountVO> getList(String userId, int page, int pageSize) throws SQLException {
        String sql = "SELECT * FROM account WHERE userId=? LIMIT ? OFFSET ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setInt(2, pageSize); // 페이지 크기
            stmt.setInt(3, (page - 1) * pageSize); // 페이지 오프셋
            try (ResultSet rs = stmt.executeQuery()) {
                return mapList(rs);
            }
        }
    }

    @Override
    public List<AccountVO> getListByDateRange(String userId, Date startDate, Date endDate) throws SQLException {
        String sql = "SELECT * FROM account WHERE userId=? AND date BETWEEN ? AND ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setDate(2, new java.sql.Date(startDate.getTime())); // java.util.Date를 java.sql.Date로 변환
            stmt.setDate(3, new java.sql.Date(endDate.getTime()));   // java.util.Date를 java.sql.Date로 변환
            try (ResultSet rs = stmt.executeQuery()) {
                return mapList(rs);
            }
        }
    }

    @Override
    public Map<String, Integer> getIncomeAndExpenseTotal(String userId, Date startDate, Date endDate) throws SQLException {
        String sql = "SELECT SUM(income) AS totalIncome, SUM(expense) AS totalExpense FROM account " +
                "WHERE userId=? AND date BETWEEN ? AND ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setDate(2, new java.sql.Date(startDate.getTime()));
            stmt.setDate(3, new java.sql.Date(endDate.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Integer> result = new HashMap<>();
                    // Use getInt and check if the result was null
                    result.put("totalIncome", rs.getObject("totalIncome") != null ? rs.getInt("totalIncome") : 0);
                    result.put("totalExpense", rs.getObject("totalExpense") != null ? rs.getInt("totalExpense") : 0);
                    return result;
                }
            }
        }
        return Collections.emptyMap(); // 결과가 없을 때 빈 맵 반환
    }

    @Override
    public boolean exists(String userId, Long id) throws SQLException {
        String sql = "SELECT 1 FROM account WHERE userId=? AND id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setLong(2, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // 결과가 있으면 true, 없으면 false
            }
        }
    }

    @Override
    public List<AccountVO> getListByUserIds(List<String> userIds) throws SQLException {
        if (userIds.isEmpty()) {
            return Collections.emptyList();
        }

        String sql = "SELECT * FROM account WHERE userId IN (" +
                String.join(",", Collections.nCopies(userIds.size(), "?")) + ")";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < userIds.size(); i++) {
                stmt.setString(i + 1, userIds.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                return mapList(rs);
            }
        }
    }
}