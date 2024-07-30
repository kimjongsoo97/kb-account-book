package org.account.service;

import org.account.cli.exception.PasswordMissmatchException;
import org.account.cli.exception.UsernameDuplicateException;
import org.account.cli.ui.Input;
import org.account.dao.UserDao;
import org.account.dao.UserDaoImpl;
import org.account.domain.UserVO;

import java.sql.SQLException;
import java.util.Optional;


public class AccountService {
    UserDao dao=new UserDaoImpl();
    public void join() {
        try {
            UserVO user = getUser();
            dao.create(user);
        } catch (UsernameDuplicateException | PasswordMissmatchException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean checkDuplication(String username) throws UsernameDuplicateException, SQLException {
        Optional<UserVO> result = dao.get(username);
        if (result.isPresent()) {
            throw new UsernameDuplicateException();
        }return false;
    }
    private UserVO getUser() throws SQLException, UsernameDuplicateException,PasswordMissmatchException {
        String username = Input.getLine("사용자 ID:");
        checkDuplication(username);

        String password = Input.getLine("비밀번호 : ");
        String password2 = Input.getLine("비밀번호 확인: ");
        if (!password.equals(password2)) {
            throw new PasswordMissmatchException();
        }
        String name = Input.getLine("이름: ");
        String role = Input.getLine("직업: ");
        String gender = Input.getLine("성별: ");
        String phoneNumber=Input.getLine("핸드폰 번호: ");
        String email = Input.getLine("이메일 주소: ");
        String birthYear=Input.getLine("생년월일 :");
        return new UserVO().builder()
                .id(username)
                .password(password)
                .name(name)
                .role(role)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .email(email)
                .birthYear(birthYear)
                .build();
    }
}