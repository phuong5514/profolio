/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package businessLayer;

import datalayer.Entry;
import datalayer.JDBC;
import datalayer.Table;
import java.sql.SQLException;
import java.util.ArrayList;


public class SessionManager {
    enum table {
        STUDENT,
        COURSE,
        SCOREBOARD
    }
    
    protected table currentTable; 
    protected Table dataTable;
    protected static JDBC connection;
    
    public SessionManager(JDBC connection) {
        System.out.println("Hello, this is SessionManager");
        currentTable = table.STUDENT;
        SessionManager.connection = connection;
        dataTable = new Table();
    }    
    
    public String currentTable() {
        return currentTable.toString();
    }
    
    public void switchTable(String command) throws SQLException {
        currentTable = table.valueOf(command);
        loadTable();
    }
    
    public void loadTable() throws SQLException {
        dataTable.parseData(connection.getTable(getTableID()));
    }
    
    public Table getTable() {
        return this.dataTable;
    };
    
    public void search(String query) throws SQLException {
        if (query == null || query == "") {
            loadTable();
        } else {
            Entry header = dataTable.getHeader();
            ArrayList<String> columnsToSearch = new ArrayList<>();
            for (int i = 0; i < header.getSize(); i++) {
                
                String item = header.get(i).trim();
                System.out.println(item);
                if ("NAME".equals(item) || "YEAR".equals(item)) {
                    columnsToSearch.add(item);
                }    
            }
            dataTable.parseData(connection.search(getTableID(), query, columnsToSearch));
        }
    }
    
    public int pushBack(Entry newEntry) {
        if (connection.addEntry(newEntry, getTableID()) != 0) {
            return dataTable.insertRow(newEntry);
        }
        return 0;
    }
    
    public int edit(String rowID, Entry newEntry) throws SQLException {
        if (connection.editEntry(getTableID(), rowID, newEntry, dataTable.getHeader()) != 0) {
            loadTable();
            return 1;
        }
        return 0;
    }
    
    public int remove(String rowID) throws SQLException {
        if (connection.deleteEntry(rowID, getTableID()) != 0) {
            System.out.println("Remove from ");
            // just remove a row here would be ideal, but somehow rowID is not equal to the actual ID in the table
            loadTable();
            return 1;
        }
        return 0;
    }
    
    private String getTableID() {
        switch (currentTable) {
            case STUDENT:
                return connection.STUDENT_TABLE;
            case COURSE:
                return connection.COURSE_TABLE;
            case SCOREBOARD:
                return connection.SCOREBOARD;
        }
        System.out.println("something goes wrong?");
        return "";
        
    }
    
    public static void main(String args[]) {
        // TODO code application logic here
    }
    
    public Table getAdditionalData(String id) throws SQLException {
        Table result = new Table();
        switch (currentTable) {
            case STUDENT:
                result.parseData(connection.fetchStudentClassesAndGrades(id));
                break;
            case COURSE:
                result.parseData(connection.fetchClassStudentsData(id));
                break;
            case SCOREBOARD:
                result.parseData(connection.fetchGeneralData(id));
                break;
        }
        return result;
    }
    
    public Table getAdditionalData(String id, String year) throws SQLException {
        Table result = new Table();
        switch (currentTable) {
            case STUDENT:
                result.parseData(connection.fetchStudentClassesAndGrades(id, year));
                break;
            case COURSE:
                result.parseData(connection.fetchClassStudentsData(id, year));
                break;
            case SCOREBOARD:
                result.parseData(connection.fetchGeneralData(id, year));
                break;
        }
        return result;
    }
}
