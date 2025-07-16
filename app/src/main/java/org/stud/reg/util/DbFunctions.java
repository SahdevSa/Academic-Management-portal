package org.stud.reg.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DbFunctions {

    public Connection connect_to_db(String dbname, String user, String pwd){
        Connection conn = null;

        try{
            Class.forName("org.postgresql.Driver");
            conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname, user, pwd);
            if (conn!=null){
                System.out.println("Connection Build\n");

                String query = "SELECT * FROM students";
                try (PreparedStatement pstmt = conn.prepareStatement(query)){
                    try(ResultSet rs= pstmt.executeQuery()){
                        while (rs.next()){
                            String id = rs.getString("student_id");
                            String name = rs.getString("name");
                            String bid = rs.getString("batch_id");
                            System.out.println(id);
                            System.out.println(name);
                            System.out.println(bid);
                        }
                    }
                }

            }else{
                System.out.println("Connection Failed\n");
            }
        }catch(Exception e){
            System.out.println(e);
        }

        return conn;
    }
}
