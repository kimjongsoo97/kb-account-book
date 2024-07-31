package org.account;

import context.Context;
import org.account.cli.App;
import org.account.cli.exception.LoginFailException;
import org.account.cli.ui.Input;
import org.account.cli.ui.Menu;
import org.account.cli.ui.MenuItem;
import org.account.dao.AccountDaoImpl;
import org.account.service.AccountDaoService;
import org.account.service.AccountService;
import org.account.service.LoginService;

import java.sql.SQLException;

public class AccountApp extends App {
    private LoginService loginService;
    private AccountService accountService;
    private Menu anonymousMenu;
    private Menu userMenu;
    private AccountDaoService accountDao;
    public AccountApp() {
        loginService = new LoginService();
        accountService = new AccountService();
        accountDao=new AccountDaoService();
    }

    @Override
    public void createMenu(Menu menu) {
        anonymousMenu = new Menu();
        anonymousMenu.add(new MenuItem("로그인", this::login));
        anonymousMenu.add(new MenuItem("가입", accountService::join));
//        anonymousMenu.add(new MenuItem("종료", this::exit));

        userMenu = new Menu();
        userMenu.add(new MenuItem("검색", accountDao::search));
        userMenu.add(new MenuItem("상세", accountDao::detail));
        userMenu.add(new MenuItem("추가", accountDao::insert));
        userMenu.add(new MenuItem("수정",accountDao::update));
        userMenu.add(new MenuItem("삭제", accountDao::delete));
        userMenu.add(new MenuItem("로그아웃", this::logout));
        userMenu.add(new MenuItem("종료", this::exit));

        setMenu(anonymousMenu);
    }

    private void exit() {
        System.exit(0);
    }

    private void login() {
        try {
            loginService.login();
            setMenu(userMenu);
        } catch (SQLException | LoginFailException e) {
            System.out.println(e.getMessage());
        }
    }

    private void logout() {
        if (Input.confirm("로그아웃 할까요?")) {
            Context.getContext().setUser(null);
            setMenu(anonymousMenu);
        }
    }

    public static void main(String[] args) {
        AccountApp app = new AccountApp();
        app.run();
    }
}