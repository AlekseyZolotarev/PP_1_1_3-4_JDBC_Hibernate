//package jm.task.core.jdbc.dao;
//
//import jm.task.core.jdbc.model.User;
//import jm.task.core.jdbc.util.Util;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserDaoJDBCImpl implements UserDao {
//    public UserDaoJDBCImpl() {
//
//    }
//
//    Connection connection = Util.getConnection();
//
//    public void createUsersTable() {
//        String sql = """
//                CREATE TABLE Product (
//                    ID Integer Not Null AUTO_INCREMENT Primary Key,
//                    name VarChar(100) ,
//                \tlastName VarChar(100),
//                    age int);""";
//        Statement statement = null;
//        try {
//            statement = connection.createStatement();
//            statement.execute(sql);
//            System.out.println("createUsersTable OK");
//            statement.close();
//
//        } catch (SQLException e) {
//            System.out.println("createUsersTable ERROR " + e);
//        }
//    }
//
//    public void dropUsersTable() {
//        String sql = "DROP TABLE IF EXISTS Product";
//        Statement statement = null;
//        try {
//            statement = connection.createStatement();
//            statement.execute(sql);
//            System.out.println("dropUsersTable OK");
//            statement.close();
//        } catch (SQLException e) {
//            System.out.println("dropUsersTable ERROR " + e);
//        }
//    }
//
//    public void saveUser(String name, String lastName, byte age) {
//        String sql = "insert product (name, lastname, age) values ( ?, ?, ?);";
//        PreparedStatement preparedStatement = null;
//        try {
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, lastName);
//            preparedStatement.setByte(3, age);
//            preparedStatement.executeUpdate();
//            System.out.println("saveUser OK");
//            preparedStatement.close();
//        } catch (SQLException e) {
//            System.out.println("saveUser ERROR " + e);
//        }
//    }
//
//    public void removeUserById(long id) {
//        String sql = "DELETE FROM product WHERE Id=(?);";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setLong(1, id);
//            preparedStatement.executeUpdate();
//            System.out.println("removeUserById OK");
//            preparedStatement.close();
//        } catch (SQLException e) {
//            System.out.println("removeUserById ERROR " + e);
//        }
//    }
//
//    public List<User> getAllUsers() {
//        List<User> userList = new ArrayList<>();
//        String sql = "select id, name, lastname, age from Product";
//        Statement statement = null;
//        try {
//            statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                User user = new User();
//                user.setId(resultSet.getLong("id"));
//                user.setName(resultSet.getString("name"));
//                user.setLastName(resultSet.getString("lastName"));
//                user.setAge(resultSet.getByte("age"));
//                userList.add(user);
//            }
//            System.out.println("getAllUsers OK");
//            statement.close();
//        } catch (SQLException e) {
//            System.out.println("getAllUsers ERROR " + e);
//        }
//        return userList;
//    }
//
//    public void cleanUsersTable() {
//        String sql = "TRUNCATE TABLE Product";
//
//        try {
//            Statement statement = connection.createStatement();
//            statement.execute(sql);
//            System.out.println("cleanUsersTableOK");
//            statement.close();
//        } catch (SQLException e) {
//            System.out.println("cleanUsersTable ERROR " + e);
//        }
//    }
//}
