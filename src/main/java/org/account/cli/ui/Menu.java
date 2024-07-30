package org.account.cli.ui;


import org.account.cli.command.Command;

import java.util.ArrayList;
public class Menu {
  ArrayList<MenuItem> menus;
  public Menu() {
      menus=new ArrayList<>();
  }
  public void add(MenuItem item){
      menus.add(item);
  }
    public void printMenu() {
        System.out.println("----------------------");
        for (int i = 0; i < menus.size(); i++) {
            System.out.printf("%d.%s",i+1,menus.get(i).getTitle());
        }
        System.out.println();
        System.out.println("---------------------");
    }

    public Command getSelect() {
        int selectNo= Input.getInt("선택>");

        return menus.get(selectNo-1).getCommand();
    }


}
