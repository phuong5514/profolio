package datalayer;
import businessLayer.SessionManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBC {
    private Connection _con = null;
    private String _host = "";
    private String _primary = "";
    
    public final String STUDENT_TABLE = "HOCSINH";
    public final String COURSE_TABLE = "COURSE";
    public final String SCOREBOARD = "COURSE_STUDENT";
    
    public JDBC(String port, String database, String primary ,String username, String password) {
        System.out.println("Hello, this is JDBC constructor!");
        establishConnection(port, database, primary, username, password);
    }    
    
    public void establishConnection(String port, String database, String primary, String username, String password) {
        System.out.println("Hello, this is JDBC's establishConnection");
        
        _host = "jdbc:sqlserver://localhost:" + port;
        _primary = primary;
        String connectionUrl = _host + ";encrypt=true;trustServerCertificate=true;database=" + database + ";"; 
        System.out.println(connectionUrl);
        try {
            _con = DriverManager.getConnection(connectionUrl, username, password);
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to establish connection.");
            e.printStackTrace();
        }
    }
    
    private ResultSet executeQuery(String sql, String param) {
        try {
            PreparedStatement preparedStatement = _con.prepareStatement(sql);
            preparedStatement.setString(1, param);
            return preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private ResultSet executeQuery(String sql) {
        try {
            PreparedStatement preparedStatement = _con.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private int executeUpdate(String sql, String param) {
        try {
            PreparedStatement preparedStatement = _con.prepareStatement(sql);
            preparedStatement.setString(1, param);
            System.out.print("1");
            System.out.print(preparedStatement.toString());
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public ResultSet nameFilter(String table, String query) {
        String sql = "SELECT * FROM " + table + " WHERE name LIKE ?";
        String param = "%" + query + "%";
        return executeQuery(sql, param);
    }
    
    public ResultSet getTable(String table) {
        String sql = "SELECT * FROM " + table;
        return executeQuery(sql);
    }
    
    public int addEntry(Entry newEntry, String table) {
        String insertQuery = "INSERT INTO " + table + " VALUES (";
        for (int i = 0; i < newEntry.getSize(); i++) {
            insertQuery += "?";
            if (i < newEntry.getSize() -1) {
                insertQuery += ", ";
            }
        }
        insertQuery += ")";
        //String data = newEntry.parse(); // (val1,val2,val3);
        System.out.println(insertQuery);
        
        try {
            PreparedStatement preparedStatement = _con.prepareStatement(insertQuery);
            for (int i = 0; i < newEntry.getSize(); i++) { 
                preparedStatement.setString(i+1, newEntry.get(i));
            }
            // Execute the update
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
    }
    
    public int deleteEntry(String deleteId, String table) {
        String sql = "DELETE FROM " + table + " WHERE " + _primary + " = ?";
        return executeUpdate(sql, deleteId);
    }
    
    public int editEntry(String table, String deleteId, Entry newEntry, Entry header) {
        String sql = "UPDATE " + table + " SET ";
        for (int i = 0; i < newEntry.getSize(); i++) {
            sql += header.get(i) + " = ?";
            if (i < newEntry.getSize() - 1) {
                sql += ", ";
            }
        }
        
        if (deleteId != null) {
            sql += " WHERE " + _primary + "= ?";
        }
        
        try {
            PreparedStatement preparedStatement = _con.prepareStatement(sql);
            int size = newEntry.getSize();
            for (int i = 0; i < size; i++) { 
                preparedStatement.setString(i+1, newEntry.get(i));
            }
            preparedStatement.setString(size + 1, deleteId);
            // Execute the update
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    } 
    
    public ResultSet search(String table, String query, ArrayList<String> keys) {
    // Check if the keys list is empty or null
    if (keys == null || keys.isEmpty()) {
        System.out.println("Empty");
        return null;
    }

    // Construct the SQL query
    StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM ").append(table).append(" WHERE ");
    int length = keys.size();
    for (int i = 0; i < length; i++) {
        sqlBuilder.append(keys.get(i)).append(" LIKE ?");
        if (i < length - 1) {
            sqlBuilder.append(" OR ");
        }
    }

    try {
        System.out.println(sqlBuilder.toString());
        PreparedStatement preparedStatement = _con.prepareStatement(sqlBuilder.toString());
        // Set parameters for each key
        for (int i = 0; i < length; i++) {
            preparedStatement.setString(i + 1, "%" + query + "%");
        }

        // Execute the query
        return preparedStatement.executeQuery();
    } catch (SQLException ex) {
        Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        return null;
    }
}

    

    // Append column-value pairs
    

    
    public Connection connection() {
        return _con;
    }
    
    public String host() {
        return _host;
    }

    public void close() throws SQLException {
        _con.close();
    }
    
    public ResultSet fetchStudentClassesAndGrades(String id) {
        String sql = "SELECT COURSE.NAME AS N'Tên lớp', COURSE.ID AS N'Mã lớp', GRADE.GRADE AS N'Điểm', COURSE.YEAR AS N'Năm học' FROM " + STUDENT_TABLE + " HS JOIN " + SCOREBOARD + " GRADE ON HS.ID = GRADE.STUDENTID ";
        sql += " JOIN " + COURSE_TABLE + " COURSE ON GRADE.COURSEID = COURSE.ID";
        sql += " WHERE HS.ID = '" + id + "'";

        System.out.println(sql);
        return executeQuery(sql);
    }

    public ResultSet fetchClassStudentsData(String id) {
        String sql = "SELECT HS.NAME AS N'Họ tên', HS.ID AS 'MSSV', GRADE.GRADE AS N'Điểm', COURSE.YEAR AS N'Năm học' FROM " + STUDENT_TABLE + " HS JOIN " + SCOREBOARD + " GRADE ON HS.ID = GRADE.STUDENTID ";
        sql += " JOIN " + COURSE_TABLE + " COURSE ON GRADE.COURSEID = COURSE.ID";
        sql += " WHERE GRADE.COURSEID = '" + id + "'";

        System.out.println(sql);
        return executeQuery(sql);
    }
    
    public ResultSet fetchGeneralData(String id) {
        String sql = "SELECT HS.NAME AS N'Họ tên', COURSE.NAME AS N'Tên lớp', COURSE.YEAR AS N'Năm học', GRADE.GRADE AS N'Điểm' FROM " + STUDENT_TABLE + " HS JOIN " + SCOREBOARD + " GRADE ON HS.ID = GRADE.STUDENTID ";
        sql += " JOIN " + COURSE_TABLE + " COURSE ON GRADE.COURSEID = COURSE.ID";
        sql += " WHERE GRADE.ID = '" + id + "'";

        System.out.println(sql);
        return executeQuery(sql);
    }
    
    public ResultSet fetchStudentClassesAndGrades(String id, String year) {
        String sql = "SELECT COURSE.NAME AS N'Tên lớp', COURSE.ID AS N'Mã lớp', GRADE.GRADE AS N'Điểm', COURSE.YEAR AS N'Năm học' FROM " + STUDENT_TABLE + " HS JOIN " + SCOREBOARD + " GRADE ON HS.ID = GRADE.STUDENTID ";
        sql += " JOIN " + COURSE_TABLE + " COURSE ON GRADE.COURSEID = COURSE.ID";
        sql += " WHERE HS.ID = '" + id + "'";
        sql += " AND COURSE.YEAR = '" + year + "'";

        System.out.println(sql);
        return executeQuery(sql);
    }

    public ResultSet fetchClassStudentsData(String id, String year) {
        String sql = "SELECT HS.NAME AS N'Họ tên', HS.ID AS 'MSSV', GRADE.GRADE AS N'Điểm', COURSE.YEAR AS N'Năm học' FROM " + STUDENT_TABLE + " HS JOIN " + SCOREBOARD + " GRADE ON HS.ID = GRADE.STUDENTID ";
        sql += " JOIN " + COURSE_TABLE + " COURSE ON GRADE.COURSEID = COURSE.ID";
        sql += " WHERE GRADE.COURSEID = '" + id + "'";
        sql += " AND COURSE.YEAR = '" + year + "'";
        
        System.out.println(sql);
        return executeQuery(sql);
    }
    
    public ResultSet fetchGeneralData(String id, String year) {
        String sql = "SELECT HS.NAME AS N'Họ tên', COURSE.NAME AS N'Tên lớp', COURSE.YEAR AS N'Năm học', GRADE.GRADE AS N'Điểm' FROM " + STUDENT_TABLE + " HS JOIN " + SCOREBOARD + " GRADE ON HS.ID = GRADE.STUDENTID ";
        sql += " JOIN " + COURSE_TABLE + " COURSE ON GRADE.COURSEID = COURSE.ID";
        sql += " WHERE GRADE.ID = '" + id + "'";
        sql += " AND COURSE.YEAR = '" + year + "'";
        
        System.out.println(sql);
        return executeQuery(sql);
    }

    
//     public ResultSet fetchStudentClassesAndGrades(String id) {
//        String sql = "SELECT COURSE.NAME AS N'Tên lớp', COURSE.ID AS N'Mã lớp', GRADE.GRADE AS N'Điểm' FROM " + STUDENT_TABLE + " HS JOIN " + SCOREBOARD + " GRADE ON HS.ID = GRADE.STUDENTID ";
//        sql += " JOIN " + COURSE_TABLE + " COURSE ON GRADE.COURSEID = COURSE.ID";
//        sql += " WHERE HS.ID = ?";
//
//        try (PreparedStatement pstmt = _con.prepareStatement(sql)) {
//            pstmt.setString(1, id.trim());
//            return pstmt.executeQuery();
//        } catch (SQLException ex) {
//            return null;
//        }
//    }
//
//    public ResultSet fetchClassStudentsData(String id) {
//        String sql = "SELECT HS.NAME AS N'Họ tên', HS.ID AS N'MSSV', GRADE.GRADE AS N'Điểm' FROM " + STUDENT_TABLE + " HS JOIN " + SCOREBOARD + " GRADE ON HS.ID = GRADE.STUDENTID ";
//        sql += " JOIN " + COURSE_TABLE + " COURSE ON GRADE.COURSEID = COURSE.ID";
//        sql += " WHERE COURSE.ID = ?";
//
//        try (PreparedStatement pstmt = _con.prepareStatement(sql)) {
//            pstmt.setString(1, id.trim());
//            return pstmt.executeQuery();
//        } catch (SQLException ex) {
//            return null;
//        }
//    }
    
}