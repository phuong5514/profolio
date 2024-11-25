<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "datalayer.*" %>
<%@ page import = "businessLayer.SessionManager" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student management</title>
    <link rel="stylesheet" type="text/css" href="UItheme.css">
    <script src="script.js" defer></script> 
</head>

<body class = "imageBackground">
    <%
    SessionManager sm = (SessionManager) request.getSession().getAttribute("sm");
    Table table = sm.getTable();
    Entry header = table.getHeader();
    
    
    String headerList = "[";
    for (int i = 0; i < header.getSize(); i++) {
        headerList += "\'" + header.get(i) + "\'";
        if(i < header.getSize() - 1) {
            headerList += ", ";
        }
    }
    
    headerList += "]";
    
    String tableString = "[";
    for (int i = 0; i < table.getLineCount(); i++) {
        tableString += "[";
        Entry line  = table.getEntry(i);
        for (int j = 0; j < table.getColCount(); j++) {
            tableString +=  "\'" + line.get(j) + "\'";
            if (j < table.getColCount() - 1) {
                tableString += ", ";
            }
        }
        tableString += "]";
        if (i < table.getLineCount() - 1) {
            tableString += ", ";
        }
    }
    tableString += "]";
    %>
    
    <div class = "mainBody">
        
        
        <header class="header bottomBorder roundBorder">
            <div class="button median textCenter paddingSmall            textStandard" onclick="switchTable('STUDENT')">Sinh viên </div>
            <div class="button median textCenter paddingSmall leftBorder textStandard" onclick="switchTable('COURSE')">Lớp học </div>
            <div class="button median textCenter paddingSmall leftBorder textStandard" onclick="switchTable('SCOREBOARD')">Bảng điểm </div>
            <div class="majority"> </div>
        </header>


        
        <div class = "toolbar roundBorder bottomBorder"> 
            <div class = "button textCenter paddingSmall textStandard" onclick="add('Thêm', <%=headerList%>)"> Thêm </div>
            <div class = "button textCenter paddingSmall leftBorder textStandard" onclick = "edit('Chỉnh sửa', <%=headerList%>, <%=tableString%>)"> Chỉnh sửa </div>
            <%
                if (sm.currentTable() != "SCOREBOARD") {
            %>
                    <div class = "button textCenter paddingSmall leftBorder textStandard" onclick = "sort(<%=tableString%> ,<%=headerList%>)"  id = "sortButton"> Sắp xếp (NOR)</div>
                    <div class = "median leftBorder"></div>

                    <input type="search" class = "majority paddingSmall pillBorder" id = 'searchbar'> 
                    <div class = "button textCenter paddingSmall cell textStandard pillBorder" type="submit" onclick = 'find()'>Tìm kiếm </div>
            <%
                }
            %>    
            
            
            
        </div>
        
        <div class = "semiTransparent majority paddingMed scrollable">
            <table class = "solidBackground fill roundBorder paddingSmall">
                <thead>
                    <tr>
                        <%
                        for (int i = 0; i < header.getSize(); i++) {
                        %>
                            <th class="paddingMed cell">
                            <%= header.get(i) %>
                            </th>
                        <%
                        }
                        %>
                    </tr>
                </thead>
                <tbody id="tableBody" class = "unsorted">
                    <%
                    for (int i = 0; i < table.getLineCount(); i++) {
                        Entry line  = table.getEntry(i);
                        
                    %>
                    <tr class = "bottomBorder borderBox buttonAlt" onclick = "showDetails(<%=i%>)" id  = <%="row" + i%>>
                        <%
                            for (int j = 0; j < table.getColCount(); j++) {
                        %>
                        <td class = "paddingMed cell">
                            <%=line.get(j)%>
                        </td>
                        <%
                            // give the first column a "key" id;
                            }
                        %>
                        </tr>
                    <%
                    }
                    %>
                </tbody>
            </table>
        </div> 
        
      
                  
    </div>
                
    
</body>

<div class = "shadow disable" id = "shadow" onclick = "removePopup()">
</div>            
<div class = "popup disable colContainer" id = "popup">
</div>  
</html>
