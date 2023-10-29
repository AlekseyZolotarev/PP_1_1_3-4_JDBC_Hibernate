package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
//import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        Util.getSessionFactory();
        System.out.println(Util.getSessionFactory().isClosed());
        UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();
        userDao.dropUsersTable();

        userDao.createUsersTable();
//
        userDao.saveUser("Name1", "LastName1", (byte) 20);
        userDao.saveUser("Name2", "LastName2", (byte) 25);
        userDao.saveUser("Name3", "LastName3", (byte) 31);
        userDao.saveUser("Name4", "LastName4", (byte) 38);
//
        System.out.println(userDao.getAllUsers().toString());
        userDao.removeUserById(1);
        System.out.println(userDao.getAllUsers().toString());
//
        userDao.cleanUsersTable();
        System.out.println(userDao.getAllUsers().toString());

        userDao.dropUsersTable();
        userDao.closeSessionFactory();
    }

}

