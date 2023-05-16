package dbConn.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingletonHelper {
	   private static Connection conn;
	   private ConnectionSingletonHelper() { }
	   
	   public static Connection getConnection(String dsn) {
	      if (conn !=null) {
	         return conn;
	      }
	      try {
	         Class.forName("oracle.jdbc.OracleDriver");
	         conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","soccer", "oracle");
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         return conn;
	      }
	   }

	   public static void close() throws SQLException  {
	      if( conn!=null) {
	         if(!conn.isClosed()) {
	            conn.close();
	         }
	      }
	   }
	}
