package listeners;

import DBManager.DBManager;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class contextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
         DBManager manager = new DBManager();
         sce.getServletContext().setAttribute("DbManager", manager);
    }
}
