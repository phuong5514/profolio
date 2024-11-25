package main.studentmanagementweb;

import businessLayer.SessionManager;
import datalayer.JDBC;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "app", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {
    public SessionManager sm;
    public JDBC con;

    // edit these to match your database, dont push this to github in a real project
    private static final String JDBC_USERNAME = "";
    private static final String JDBC_PASSWORD = "";
    private static final String JDBC_PORT = "8888";
    private static final String JDBC_DATABASE = "QLHOCSINH";
    private static final String JDBC_PRIMARY = "ID";


    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("Hello, this is servlet init");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (con == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // somehow this line make the below work
                con = new JDBC(JDBC_PORT, JDBC_DATABASE, JDBC_PRIMARY, JDBC_USERNAME, JDBC_PASSWORD);
                sm = new SessionManager(con);
                sm.loadTable();
                request.getSession().setAttribute("sm", sm);
            } catch (Exception e) {
                throw new ServletException("Failed to initialize resources", e);
            } 
        }
        
        String table = request.getParameter("table");
        String query = request.getParameter("query");
        
        System.out.println(table);
        if (table != null) {
            if (sm != null) {
                try {
                    sm.switchTable(table);
                } catch (SQLException ex) {
                    Logger.getLogger(DashboardServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            response.setContentType("text/plain");
            response.getWriter().write("Action performed successfully");
        } else if (query != null) {
            if (sm != null) {
                try {
                    sm.search(query);
                } catch (SQLException ex) {
                    Logger.getLogger(DashboardServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            response.setContentType("text/plain");
            response.getWriter().write("Action performed successfully");
        }
        
        
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
    
    
    @Override
    public void destroy() {
        super.destroy();
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DashboardServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
