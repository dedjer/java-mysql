import model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Start");

        List<Player> players = new ArrayList<>();

        String insert1 = "INSERT INTO player (FIRSTNAME, LASTNAME) VALUES ('Derrick', 'Rose');";
        String insert2 = "INSERT INTO player (FIRSTNAME, LASTNAME) VALUES ('Carlos', 'Boozer');";

        String selectQuery = "SELECT * from player";

        try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stats", "root", "slack");
            PreparedStatement insertStatement1 = conn.prepareStatement(insert1);
            PreparedStatement insertStatement2 = conn.prepareStatement(insert2);
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery))
        {
            if (conn!=null)
            {
                System.out.println("Connected to database!");
            }
            else
            {
                System.out.println("Failed to connect!");
            }

//            System.out.println("Inserting row 1");
//            insertStatement1.execute();
//
//            System.out.println("Inserting row 2");
//            insertStatement2.execute();

            System.out.println("Getting data");
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next())
            {
                int id = resultSet.getInt("ID");
                String firstname = resultSet.getString("FIRSTNAME");
                String lastname = resultSet.getString("LASTNAME");

                Player p = new Player();

                p.setID(id);
                p.setFirstname(firstname);
                p.setLastname(lastname);

                System.out.println("ID: " + id + " Firstname: " + firstname + " Lastname: " + lastname );
                players.add(p);
                System.out.println("Player Added!");
            }

            System.out.println("Displaying players: ");
            players.forEach(p -> System.out.println("ID: " + p.getID() + " Firstname: " + p.getFirstname() + " Lastname: " + p.getLastname() ));

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
