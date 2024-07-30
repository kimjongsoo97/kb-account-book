package org.account.service;

import context.Context;
import org.account.cli.exception.LoginFailException;
import org.account.cli.ui.Input;
import org.account.dao.UserDao;
import org.account.dao.UserDaoImpl;
import org.account.domain.UserVO;

import java.sql.SQLException;

public class LoginService {
    UserDao dao=new UserDaoImpl();

    public void login() throws SQLException, LoginFailException {
        String username = Input.getLine("사용자 ID: ");
        String password = Input.getLine("비밀번호 : ");
        UserVO user = dao.get(username).orElseThrow(LoginFailException::new);
        if (user.getPassword().equals(password)) {
           Context ctx= Context.getContext();
            ctx.setUser(user);
        } else {
            throw new LoginFailException();
        }
    }

}
