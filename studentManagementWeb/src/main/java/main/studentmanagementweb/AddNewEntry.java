/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package main.studentmanagementweb;

import businessLayer.SessionManager;
import datalayer.Entry;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

@WebServlet(name = "addNewEntry", urlPatterns = {"/add-entry"})
public class AddNewEntry extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("Hello, addNewEntry here!: ");
        boolean receiveData = false;
        Entry newEntry = new Entry();

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            System.out.println(entry.getValue()[0]);
            newEntry.insert(entry.getValue()[0]);
            receiveData = true;
        }
        
        System.out.println(receiveData);
        System.out.println(newEntry.parse());
        
        SessionManager sm = (SessionManager) request.getSession().getAttribute("sm");
        int result = sm.pushBack(newEntry);
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
