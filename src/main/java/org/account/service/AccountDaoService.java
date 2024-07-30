package org.account.service;

import context.Context;
import org.account.cli.ui.Input;
import org.account.dao.AccountDao;
import org.account.dao.AccountDaoImpl;
import org.account.dao.UserDaoImpl;
import org.account.domain.AccountVO;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;

public class AccountDaoService {
    AccountDao dao=new AccountDaoImpl();
    Supplier<String> userId=()-> Context.getContext().getUser().getId();

    public void search(){
        String keyword = Input.getLine("검색어 : ");
        keyword = "%" + keyword + "%";
        try{
            try {
                List<AccountVO> list=dao.search(userId.get(),keyword);
                System.out.println("총 건수: "+list.size());

                for (AccountVO account:list){
                    System.out.printf("%03d] %s%s\n",account.getId(),account.getTitle(),account.getDescription(),account.getIncome(),account.getExpense(),account.getDate());

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
