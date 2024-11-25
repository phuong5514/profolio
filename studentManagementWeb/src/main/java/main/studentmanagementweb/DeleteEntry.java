/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package main.studentmanagementweb;

import businessLayer.SessionManager;
import datalayer.Entry;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "DeleteEntry", urlPatterns = {"/delete-entry"})
public class DeleteEntry extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String searchID = request.getParameter("ID");
        System.out.println(searchID);
        if (searchID == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            response.getWriter().write("ID haven't chosen or ID does not exist!");
        }
        
        SessionManager sm = (SessionManager) request.getSession().getAttribute("sm");
        
        int result = 0;
        try {
            result = sm.remove(searchID);
        } catch (SQLException ex) {
            Logger.getLogger(DeleteEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(result);
        
        
        if (result == 0) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            response.getWriter().write("deletion failed!");
        } else {
            response.setStatus(HttpServletResponse.SC_OK); // 200
            response.sendRedirect("/studentManagementWeb/dashboard");
        }
        
    }

}
