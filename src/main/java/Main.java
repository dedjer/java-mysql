import model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Start");

        List<Player> players = new ArrayList<>();

        String selectQuery = "SELECT * from player";

        try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stats", "root", "slack");
            PreparedStatement preparedStatement = conn.prepareStatement(selectQuery))
        {
            if (conn!=null)
            {
                System.out.println("Connected to database!");
            }
            else
            {
                System.out.println("Failed to connect!");
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                int id = resultSet.getInt("ID");
                String firstname = resultSet.getString("FIRSTNAME");
                String lastname = resultSet.getString("LASTNAME");

                Player p = new Player();

                p.setID(id);
                p.setFirstname(firstname);
                p.setLastname(lastname);

                System.out.println("ID:" + id + " Firstname:" + firstname + " Lastname: " + lastname );
                players.add(p);
                System.out.println("Player Added!");
            }

            System.out.println("Displaying players:");
            players.forEach(p -> System.out.println(p));

        }
        catch(SQLException e)
        {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        catch(Exception  e)
        {
            e.printStackTrace();
        }
    }
}
