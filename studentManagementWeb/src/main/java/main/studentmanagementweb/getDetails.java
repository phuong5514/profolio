
package main.studentmanagementweb;

import businessLayer.SessionManager;
import datalayer.Entry;
import datalayer.JDBC;
import datalayer.Table;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "getDetails", urlPatterns = {"/show-more-info"})
public class getDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        String year = request.getParameter("year");
        System.out.println("Hello! getDetails here!");
        System.out.println(id);
        System.out.println(year);
        if (id == null) {
            response.getWriter().write("no data");
            return;
        }
        
        SessionManager sm = (SessionManager) request.getSession().getAttribute("sm");
        
        Table resultTable = null;
        Entry header = null;
        int colCount = 0;
        
        try {
            if (year != null) {
                resultTable = sm.getAdditionalData(id.trim(), year.trim());
            }   else {
                resultTable = sm.getAdditionalData(id.trim());
            }
            
            if (resultTable.getLineCount() < 1) {
                response.getWriter().write("no data");
                return;
            }
            header = resultTable.getHeader();
            colCount = resultTable.getColCount();
        } catch (SQLException ex) {
            Logger.getLogger(getDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            StringBuilder DOMBuilder = new StringBuilder();
            DOMBuilder.append("<table class = \"solidBackground fill roundBorder paddingSmall\">\n");
                DOMBuilder.append("<thead>\n");
                    DOMBuilder.append("<tr>\n");
                        for (int i = 0; i < colCount; i++) {
                            String row = "<th class = \"paddingMed cell\">" + header.get(i) + "</th>";
                            DOMBuilder.append(row + "\n");
                        }
                    DOMBuilder.append("<tr>\n");
                DOMBuilder.append("</thead>\n");
                
                DOMBuilder.append("<tbody id=\"tableBody\">\n");
                    for (int i = 0; i < resultTable.getLineCount(); i++) {
                        Entry data = resultTable.getEntry(i);
                        String row = "<tr class = \"bottomBorder borderBox\">";
                        for (int j = 0; j < colCount; j++) {
                            row += "<td class = \"paddingMed cell\">" + data.get(j) + "</td>";
                        }    
                        row += "</tr>\n";        
                        DOMBuilder.append(row);  
                    }
                DOMBuilder.append("</tbody>\n");
            DOMBuilder.append("</table>\n");
            System.out.print(DOMBuilder.toString());
            out.print(DOMBuilder.toString());
        }
        response.getWriter().write("error!");
    }  
}
