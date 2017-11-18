import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteAppender {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("out.txt"));
        String line = null;
        while((line=reader.readLine()) != null){
            String[] tokens = line.split(",");
            Connection connection;
            try{
                connection = prepareConnection();
                if (connection != null){
                    String sql = String.format("insert into distance " +
                                                "values('%s', '%s', '%s')",
                                                tokens[0],tokens[1],tokens[2]);
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);
                    System.out.println("insert " + line);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static Connection prepareConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:" + "vanScheduler.db";
            Connection conn = DriverManager.getConnection(dbURL);

            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("connection Fail cannot find database");
        }
        return null;
    }
}
