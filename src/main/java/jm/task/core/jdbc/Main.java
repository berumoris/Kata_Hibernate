package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("John", "Lenon", (byte) 65);
        userService.saveUser("Tom", "Cruse", (byte) 58);
        userService.saveUser("Jeky", "Chan", (byte) 18);
        userService.saveUser("Kira", "Knightley", (byte) 65);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }
}
