package org.account.service;

import context.Context;
import org.account.cli.ui.Input;
import org.account.dao.AccountDao;
import org.account.dao.AccountDaoImpl;
import org.account.dao.UserDaoImpl;
import org.account.domain.AccountVO;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AccountDaoService {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    AccountDao dao = new AccountDaoImpl();
    Supplier<String> userId = () -> Context.getContext().getUser().getId();

    public void search() {
        String keyword = Input.getLine("검색어 : ");
        try {
            List<AccountVO> list = dao.search(userId.get(), keyword);
            System.out.println("총 건수: " + list.size());

            for (AccountVO account : list) {
                System.out.printf("%03d] %s %s %d %d %s\n", account.getId(), account.getTitle(), account.getDescription(), account.getIncome(), account.getExpense(), account.getDate());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void detail() {
        long id = (long) Input.getInt("사용자 ID : ");

        try {
            AccountVO account = dao.get(userId.get(), id).orElseThrow(NoSuchElementException::new);
            System.out.printf("Todo ID: %d\n", account.getId());
            System.out.printf("날짜 : %s\n", account.getDate());
            System.out.printf("항목 : %s\n", account.getTitle());
            System.out.printf("카테고리: %s\n", account.getCategory());
            System.out.printf("설명 : %s\n", account.getDescription());
            System.out.printf("지출 : %d\n", account.getExpense());
            System.out.printf("수입 : %d\n", account.getIncome());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert() {
        String dateString = Input.getLine("날짜 (yyyy-MM-dd): ");
        Date date = null;

        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        String category = Input.getLine("카테고리: ");
        String title = Input.getLine("항목: ");
        Integer income = Input.getInt("수입: ");
        Integer expense = Input.getInt("지출: ");
        String description = Input.getLine("설명: ");

        AccountVO account = new AccountVO();
        account.setDate(date);
        account.setCategory(category);
        account.setTitle(title);
        account.setIncome(income);
        account.setExpense(expense);
        account.setDescription(description);
        account.setUserId(userId.get()); // Add userId to the account

        try {
            dao.create(account);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        Long id = (long) Input.getInt("수정할 ID : ");

        try {
            AccountVO account = dao.get(userId.get(), id).orElseThrow(NoSuchElementException::new);

            String dateString = Input.getLine("날짜 (yyyy-MM-dd): ");
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                System.err.println("Invalid date format. Please use yyyy-MM-dd.");
                return;
            }

            String category = Input.getLine("카테고리: ");
            String title = Input.getLine("항목: ");
            Integer income = Input.getInt("수입: ");
            Integer expense = Input.getInt("지출: ");
            String description = Input.getLine("설명: ");

            account.setDate(date);
            account.setCategory(category);
            account.setTitle(title);
            account.setIncome(income);
            account.setExpense(expense);
            account.setDescription(description);

            dao.update(userId.get(), account);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        Long id = (long) Input.getInt("삭제할 ID: ");
        boolean answer = Input.confirm("삭제할까요?");
        if (answer) {
            try {
                dao.delete(userId.get(), id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void listByPage() {
        String userId = this.userId.get();
        int page = Input.getInt("페이지 번호: ");
        int pageSize = Input.getInt("페이지 크기: ");
        try {
            List<AccountVO> accounts = dao.getList(userId, page, pageSize);
            accounts.forEach(account -> System.out.printf("%03d] %s %s %d %d %s\n",
                    account.getId(),
                    account.getTitle(),
                    account.getDescription(),
                    account.getIncome(),
                    account.getExpense(),
                    account.getDate()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listByDateRange() {
        String userId = this.userId.get();
        String start = Input.getLine("시작 날짜 (yyyy-MM-dd): ");
        String end = Input.getLine("종료 날짜 (yyyy-MM-dd): ");
        try {
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);
            List<AccountVO> accounts = dao.getListByDateRange(userId, startDate, endDate);
            accounts.forEach(account -> System.out.printf("%03d] %s %s %d %d %s\n",
                    account.getId(),
                    account.getTitle(),
                    account.getDescription(),
                    account.getIncome(),
                    account.getExpense(),
                    account.getDate()));
        } catch (ParseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getIncomeAndExpenseTotal() {
        String userId = this.userId.get();
        String start = Input.getLine("시작 날짜 (yyyy-MM-dd): ");
        String end = Input.getLine("종료 날짜 (yyyy-MM-dd): ");
        try {
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);
            Map<String, Integer> totals = dao.getIncomeAndExpenseTotal(userId, startDate, endDate);
            System.out.printf("Total Income: %d, Total Expense: %d\n", totals.get("totalIncome"), totals.get("totalExpense"));
        } catch (ParseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listByUserIds() {
        String input = Input.getLine("사용자 ID 목록 (쉼표로 구분): ");
        if (input == null || input.trim().isEmpty()) {
            System.err.println("유효한 사용자 ID 목록을 입력해주세요.");
            return;
        }

        // Split the input and trim each part to ensure no leading/trailing spaces
        List<String> userIds = Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(id -> !id.isEmpty())
                .collect(Collectors.toList());

        if (userIds.isEmpty()) {
            System.err.println("유효한 사용자 ID 목록을 입력해주세요.");
            return;
        }

        try {
            List<AccountVO> accounts = dao.getListByUserIds(userIds);
            accounts.forEach(account -> System.out.printf("%03d] %s %s %d %d %s\n",
                    account.getId(),
                    account.getTitle(),
                    account.getDescription(),
                    account.getIncome(),
                    account.getExpense(),
                    account.getDate()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
