package john.com.listeners;

import john.com.utilitiesDb.DbConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class DbListener implements ServletContextListener {
    DbConnection dbConnection;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            dbConnection = new DbConnection();
            ServletContext scx = sce.getServletContext();
            scx.setAttribute("dbConnection", dbConnection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dbConnection==null)
            return;
        try {
            dbConnection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
