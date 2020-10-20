package conn;

import java.sql.Connection;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Connections {
	private static DataSource pool = null;

	public static Connection getProductionConnection() throws Exception {
		if (pool != null) {
			return pool.getConnection();
		}

		HikariConfig config = new HikariConfig();

		config.setJdbcUrl(String.format("jdbc:mysql:///%s", System.getProperty("databasename")));
		config.setUsername(System.getProperty("username"));
		config.setPassword(System.getProperty("password"));

		config.addDataSourceProperty("socketFactory", System.getProperty("sockectfactory"));
		config.addDataSourceProperty("cloudSqlInstance", System.getProperty("cloudsqlinstance"));
		config.addDataSourceProperty("useSSL", "false");

		pool = new HikariDataSource(config);

		Connection conn = null;
		conn = pool.getConnection();
		return conn;
	}

	public static Connection getDevConnection() throws Exception {
		if (pool != null) {
			return pool.getConnection();
		}

		HikariConfig config = new HikariConfig();

		config.setDriverClassName(System.getProperty("drivername"));
		config.setJdbcUrl("jdbc:mysql://localhost:3306/" + System.getProperty("databasename"));
		config.setUsername(System.getProperty("localusername"));
		config.setPassword(System.getProperty("localpassword"));

		pool = new HikariDataSource(config);

		Connection conn = null;
		conn = pool.getConnection();
		return conn;
	}
}
