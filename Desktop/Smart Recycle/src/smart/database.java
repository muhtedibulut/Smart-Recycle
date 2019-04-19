package smart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class database {
private static Connection conn;
	
	public static Connection  dbConnection()
	{
		
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = null;
			conn = DriverManager.getConnection("jdbc:sqlite:smartRecycle.db");
			System.out.println("VERÝ TABANI BAÐLANTISI SAÐLANDI");
			creatTable();
			return conn;
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "VERÝ TABANI BAÐLANTISI SAÐLANAMADI");
			System.out.println("VERÝ TABANI BAÐLANTISI SAÐLANAMADI  - Error:"+e);
			return null;
		}
	}

	private static void creatTable() throws SQLException
	{
		Statement state = (Statement) conn.createStatement();
		ResultSet res = state.executeQuery("SELECT * FROM sqlite_master WHERE type='table' AND name='user'");
		if(!res.next())
		{
			Statement state2 = (Statement) conn.createStatement();
			state2.executeUpdate("CREATE TABLE `user` ("
					+ "`id` INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "`ad` varchar(32) NOT NULL,"
					+ "`soyad` varchar(32) NOT NULL,"
					+ "`tc` varchar(16) NOT NULL UNIQUE,"
					+ "`kart_id` varchar(16) NOT NULL UNIQUE)");
			state2.executeUpdate("CREATE TABLE `satis` ("
					+ "`id` INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "`kard_id` varchar(16) NOT NULL,"
					+ "`tarih` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,"
					+ "`satilan_urunler` text NOT NULL,"
					+ "`puan` int(11) NOT NULL)");
			state2.executeUpdate("CREATE TABLE `urunler` ("
					+ "`id` INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "`urunAd` varchar(64) NOT NULL,"
					+ "`urunOzellik` varchar(32) NOT NULL,"
					+ "`puan` int(11) NOT NULL)");
		}
	}
}
