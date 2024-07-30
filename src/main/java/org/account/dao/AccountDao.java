package org.account.dao;

import org.account.domain.AccountVO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccountDao {
    int getTotalAmount(String userId)throws SQLException;
    int create(AccountVO account) throws SQLException;
    List<AccountVO> getList(String userId) throws SQLException;
    Optional<AccountVO> get(String userId,Long id) throws SQLException;
    List<AccountVO> search(String userId,String keyword)throws SQLException;
    int update(String userId,AccountVO account) throws SQLException;
    int delete(String userId,Long id)throws SQLException;
    List<AccountVO> getList(String userId, int page, int pageSize) throws SQLException;
    List<AccountVO> getListByDateRange(String userId, Date startDate, Date endDate) throws SQLException;
    Map<String, Integer> getIncomeAndExpenseTotal(String userId, Date startDate, Date endDate) throws SQLException;
    boolean exists(String userId, Long id) throws SQLException;
    List<AccountVO> getListByUserIds(List<String> userIds) throws SQLException;
}
