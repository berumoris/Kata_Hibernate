package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/kata_users";
    private static final String USER = "root";
    private static final String PASSWORD = "FPTmca9FPTmca9()123";
    private static final String DIALECT = "org.hibernate.dialect.MySQL5Dialect";
    private static final String HBM2DDL_AUTO = "update";

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

//    jdbc
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC драйвер для СУБД не найден!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !");
        }
        return connection;
    }

//    Hibernate
//    public static SessionFactory getSessionFactory() {
//
//        Properties properties = new Properties();
//        properties.setProperty(Environment.DIALECT, DIALECT);
//        properties.setProperty(Environment.HBM2DDL_AUTO, HBM2DDL_AUTO);
//        properties.setProperty(Environment.DRIVER, DB_DRIVER);
//        properties.setProperty(Environment.USER, USER);
//        properties.setProperty(Environment.PASS, PASSWORD);
//        properties.setProperty(Environment.URL, URL);
//
//        Configuration cfg = new Configuration()
//                .setProperties(properties)
//                .addAnnotatedClass(User.class)
//                .configure();
//
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
//        SessionFactory sessionFactory = cfg.buildSessionFactory(serviceRegistry);
//
//        return sessionFactory;
//    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DB_DRIVER);
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USER);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, DIALECT);

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
