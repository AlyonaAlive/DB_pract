import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseConnection {
	
	private static DatabaseConnection databaseConnection = null;
	public static Connection con;
	private static Statement statement = null;
	
	DatabaseConnection() {

	}	
	
	public static DatabaseConnection getInstance() {
		if (databaseConnection == null) {
			databaseConnection = new DatabaseConnection();
		}
		return databaseConnection;
	}
		
	public void connectToDB() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println("Нема драйверу");
			e.printStackTrace();
		}
		

		try {
			Scanner userInput = new Scanner(System.in);
			String username;
			String password;
			System.out.println("Логін до серверу: ");
			username = userInput.next();
			System.out.println("Пароль до серверу: ");
			password = userInput.next();

			con = DriverManager.getConnection("jdbc:mysql://localhost/", username,
					password);
			System.out.println("З'єднано.");
			con.setAutoCommit(false);

		} catch (SQLException e) {
			System.out.println("Помилка");
			e.printStackTrace();
		}

	}
	
	public Connection getConnection() {
		return con;
	}
	
	public void disconnectFromDB() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Від'єднано від БД");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Не встановлений конект");
		}
	}

	

	public static Vector<String> getFields(String tableName) {
		switch (tableName) {
		case "client":
			Vector<String> fields = new Vector<>();
			fields.addElement("id");
			fields.addElement("name");
			fields.addElement("passport");
			fields.addElement("address");
			return fields;
		case "score":
			Vector<String> fields1 = new Vector<>();
			fields1.addElement("scoreId");
			fields1.addElement("clientId");
			fields1.addElement("score");
			return fields1;
		case "visit":
			Vector<String> fields2 = new Vector<>();
			fields2.addElement("visitId");
			fields2.addElement("clientId");
			fields2.addElement("scoreId");
			fields2.addElement("before");
			fields2.addElement("aftter");
			fields2.addElement("date");
			return fields2;
		default:
			return null;
		}
	}

	private static ResultSet read(String request) {
		try {
			statement = con.createStatement();
			ResultSet result = statement.executeQuery(request);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Vector<Vector<String>> getAllData(String tableName) {
		switch (tableName) {
		case "client":
			ResultSet result = read("SELECT * FROM kozak_db.client;");
			Vector<String> columns;
			Vector<Vector<String>> data = new Vector<Vector<String>>();
			try {
				while (result.next()) {
					columns = new Vector<String>();
					columns.addElement(result.getString("clientId"));
					columns.addElement(result.getString("name"));
					columns.addElement(result.getString("passport"));
					columns.addElement(result.getString("address"));
					data.addElement(columns);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return data;
		case "score":
			ResultSet result1 = read("SELECT * FROM kozak_db.score;");
			Vector<String> columns1;
			Vector<Vector<String>> data1 = new Vector<Vector<String>>();
			try {
				while (result1.next()) {
					columns1 = new Vector<String>();
					columns1.addElement(result1.getString("scoreId"));
					columns1.addElement(result1.getString("clientId"));
					columns1.addElement(result1.getString("score"));
					data1.addElement(columns1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return data1;
		case "visit":
			ResultSet result2 = read("SELECT * FROM kozak_db.visit;");
			Vector<String> columns2;
			Vector<Vector<String>> data2 = new Vector<Vector<String>>();
			try {
				while (result2.next()) {
					columns2 = new Vector<String>();
					columns2.addElement(result2.getString("visitId"));
					columns2.addElement(result2.getString("clientId"));
					columns2.addElement(result2.getString("scoreId"));
					columns2.addElement(result2.getString("beforeDeposit"));
					columns2.addElement(result2.getString("aftterDeposit"));
					columns2.addElement(result2.getString("date"));
					data2.addElement(columns2);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return data2;
		default:
			return null;
		}
	}

	public static boolean checkIfClientExist(String passport) {
		ResultSet result = read("SELECT name, passport FROM kozak_db.client where passport=\"" + passport + "\";");
		try {
			if (result.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static void addVisit(String passport, int score, int deposit) {
		String sqlClient = "SELECT clientId FROM kozak_db.client where passport=\"" + passport + "\";";
		ResultSet result = read(sqlClient);
		try {
			int id = 0;
			while (result.next()) {
				id = result.getInt("clientId");
			}
			String sql = "SELECT * FROM kozak_db.visit where scoreId=" + score + " order by date desc limit 1;";
			result = read(sql);
			int after = 0;
			while (result.next()) {
				after = result.getInt("aftterDeposit");
			}
			Date date = new Date();

			PreparedStatement pstmt = con.prepareStatement(
					"insert into kozak_db.visit (clientId, scoreId, beforeDeposit,aftterDeposit, date) values (?,?,?,?,?)");
			pstmt.setInt(1, id);
			pstmt.setInt(2, score);
			pstmt.setInt(3, after);
			pstmt.setInt(4, after + deposit);
			pstmt.setDate(5, new java.sql.Date(date.getTime()));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addScore(String passport, int score) {
		String sqlClient = "SELECT clientId FROM kozak_db.client where passport=\"" + passport + "\";";
		ResultSet result = read(sqlClient);
		try {
			String id = null;
			while (result.next()) {
				id = result.getString("clientId").toString();
			}
			String sql = "INSERT INTO kozak_db.score (clientId, score) VALUES (\"" + id + "\"," + score + ");";
			statement = con.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Vector<Score> getScoresByNameClient(String passport) {
		Vector<Score> scores = new Vector<Score>();
		String sql = "SELECT clientId FROM kozak_db.client where passport=\"" + passport + "\";";
		ResultSet result = read(sql);
		try {
			String id = null;
			while (result.next()) {
				id = result.getString("clientId").toString();
			}
			String sqlClient = "SELECT * FROM kozak_db.score where clientId=\"" + id + "\";";
			result = read(sqlClient);
			while (result.next()) {
				Score sc = new Score(result.getString("clientId"), result.getString("scoreId"),
						result.getString("score"));
				scores.addElement(sc);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scores;
	}

	public static Vector<Car> getClientsNames() {
		Vector<Car> clients = new Vector<Car>();
		ResultSet result = read("SELECT * FROM kozak_db.client;");
		try {
			while (result.next()) {
				Car c = new Car(result.getString("name").toString(), result.getString("passport").toString(),
						result.getString("address").toString());
				clients.addElement(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clients;
	}

	public static void addClient(String name, String passport, String address) {
		String sql = "INSERT INTO kozak_db.client (name,passport,address) VALUES ('" + name + "', '" + passport + "', '"
				+ address + "');";
		try {
			statement = con.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	public static void deleteClient(List<String> passport) {
		try {
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM kozak_db.client WHERE passport = ?");

			for (String id : passport) {
				pstmt.setString(1, id);
				pstmt.executeUpdate();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void deleteScore(List<String> scoreId) {
		try {
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM kozak_db.score WHERE scoreId = ?");

			for (String id : scoreId) {
				pstmt.setString(1, id);
				pstmt.executeUpdate();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	}


