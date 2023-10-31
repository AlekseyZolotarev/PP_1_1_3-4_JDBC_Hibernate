package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    private static Connection connection = Util.getConnection();

    @Override
    public void createUsersTable() {
        String sql = """
                CREATE TABLE Users (
                    ID BIGINT Not Null AUTO_INCREMENT Primary Key,
                    name VarChar(100) ,
                \tlast_name VarChar(100),
                    age TINYINT);""";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(sql);
            System.out.println("createUsersTable OK");
        } catch (SQLException e) {
            System.out.println("createUsersTable ERROR " + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }


    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(sql);
            System.out.println("dropUsersTable OK");
        } catch (SQLException e) {
            System.out.println("dropUsersTable ERROR " + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert Users (name, last_name, age) values ( ?, ?, ?);";
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(ex);
                ;
            }
            System.out.println("saveUser ERROR " + e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM Users WHERE Id=(?);";
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("removeUserById OK");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(ex);
                ;
            }
            System.out.println("removeUserById ERROR " + e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "select id, name, last_name, age from Users";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            System.out.println("getAllUsers OK");
        } catch (SQLException e) {
            System.out.println("getAllUsers ERROR " + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE Users";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(sql);
            System.out.println("cleanUsersTableOK");
        } catch (SQLException e) {
            System.out.println("cleanUsersTable ERROR " + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}
