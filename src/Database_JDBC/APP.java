package Database_JDBC;

import java.sql.*;

public class APP {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/book_exercise";
        String userName = "root";
        String password = "12345";
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT E.EMP_ID, E.NM, O.ORDER_ID, O.CUST_ID, C.CUST_NM" +
                "FROM EMPLOYEE E LEFT JOIN ORDERS O ON E.EMP_ID = O.EMP_ID" +
                " LEFT JOIN CUSTOMERS C ON C.CUST_ID = O.CUST_ID " +
                "ORDER BY 1; ");
        while(resultSet.next()) {
            String name = resultSet.getString(1);
            int ID = resultSet.getInt(2);
            int AMT = resultSet.getInt(3);
            System.out.println(name + " " + ID +" " + AMT);
        }
    }
}

