package main.studentmanagementweb;

import businessLayer.SessionManager;
import datalayer.Entry;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "EditEntry", urlPatterns = {"/edit-entry"})
public class EditEntry extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        Map<String, String[]> parameterMap = request.getParameterMap();
        
        System.out.println("Hello, editEntry here!: ");
        boolean receiveData = false;
        Entry newEntry = new Entry();
        
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            System.out.println(entry.getValue()[0]);
            newEntry.insert(entry.getValue()[0]);
            receiveData = true;
        }
        String queryID = newEntry.pop();
        
        System.out.println(queryID);
        for (int i = 0; i < newEntry.getSize(); i++) {
            System.out.println("----");
            System.out.println(newEntry.get(i));
        }    
       
        
        SessionManager sm = (SessionManager) request.getSession().getAttribute("sm");
        int result = 0;
        
        try {
            result = sm.edit(queryID, newEntry);
        } catch (SQLException ex) {
            Logger.getLogger(EditEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.setContentType("application/json");
        if (result == 0) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            response.getWriter().write("add new entry fail, please check your input!");
        } else {
            response.setStatus(HttpServletResponse.SC_OK); // 200
            response.sendRedirect("/studentManagementWeb/dashboard");
        }
        
    }

}
