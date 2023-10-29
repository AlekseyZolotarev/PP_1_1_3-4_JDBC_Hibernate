package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    private static String sql;
    private static Transaction transaction = null;
    public static final SessionFactory sessionFactory = Util.getSessionFactory();
    public void closeSessionFactory () {
        if(!sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            sql = "CREATE TABLE Users (" +
                    "ID Integer Not Null AUTO_INCREMENT Primary Key," +
                    "name VarChar(100) ," +
                    "\tlastName VarChar(100)," +
                    "age int);";
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("createUsersTable" + e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            sql = "DROP TABLE IF EXISTS Users";
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("dropUsersTable ERROR " + e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("saveUser ERROR " + e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("removeUserById " + e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            System.out.println("getAllUsers ERROR" + e);
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            sql = "TRUNCATE TABLE Users";
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("dropUsersTable ERROR " + e);
        }
    }
}
