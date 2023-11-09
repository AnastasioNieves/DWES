package Conexion;
 
import java.sql.Connection;
import java.sql.SQLException;
 
import javax.sql.DataSource;
 
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

/**
 * Clase encargada de la conexion con BD
 */
public class Conexion {
 private static BasicDataSource dataSource = null;
 
 /**
  * metodo para leer los datos de login a la BD
  * @return
  */

private static DataSource getDataSource() {
  if (dataSource == null) {
	  dataSource = new BasicDataSource();
	  dataSource.setDriverClassName("org.mariadb.jdbc.Driver"); 
	  dataSource.setUsername("root");
	  dataSource.setPassword("12345");
	  dataSource.setUrl("jdbc:mariadb://localhost:3306/empresa"); 
	  dataSource.setInitialSize(20);
	  dataSource.setMaxIdle(15);
	  dataSource.setMaxTotal(20);

   
  }
  return dataSource;
 }
 
 /**
  * metodo para completar la conexion a BD
  * @return
  * @throws SQLException
  */
 public static Connection getConnection() throws SQLException {
  return getDataSource().getConnection();
 }
}