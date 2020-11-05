package com.company;

import java.sql.*;
import java.util.Scanner;

import static java.lang.System.exit;

public class Test {

    static Commands availableCommands = new Commands();
    static Validation validator = new Validation(availableCommands);

    static Connection conn;

    //Initialize the availableCommands obj to store all commands, flags and
    static {
        String newCommand;

        // List command
        newCommand = "l";
        availableCommands.addCommand(newCommand);
        // List + Starring
        availableCommands.addFlag(newCommand, "-v", false);
        // List + Search by title
        availableCommands.addFlag(newCommand, "-t", true);
        // List + Search by Director
        availableCommands.addFlag(newCommand, "-d", true);
        // List + Search by Actor
        availableCommands.addFlag(newCommand, "-a", true);
        // List + Order ascending order by their length
        availableCommands.addFlag(newCommand, "-la", false);
        // List + Order descending order by their length
        availableCommands.addFlag(newCommand, "-ld", false);




        // Add command
        newCommand = "a";
        availableCommands.addCommand(newCommand);
        // Add people
        availableCommands.addFlag(newCommand, "-p", false);
        // Add movie
        availableCommands.addFlag(newCommand, "-m", false);


        // Delete command
        newCommand = "d";
        availableCommands.addCommand(newCommand);

        // Delete people
        availableCommands.addFlag(newCommand, "-p", true);
        // Delete movie
        availableCommands.addFlag(newCommand, "-m", true);

    }


    public static void main(String[] args) throws SQLException {
// Tries to connect with the database
        try {
            String mysqlUrl = "jdbc:mysql://localhost/nokia_challenge_TEST_db?serverTimezone=UTC";
            String username = "admin";
            String password = "ra4Vy77W78LhCZd";

            conn = DriverManager.getConnection(
                    mysqlUrl,
                    username,
                    password
            );
            System.out.println("\n");

            System.out.println("Connection with the database established...");
            //TODO: Run script to initialize the database through JAVA.

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        System.out.println("===========================================");
        System.out.println("========== TESTING THE DATABASE ===========");
        System.out.println("========== TESTING THE DATABASE ===========");
        System.out.println("========== TESTING THE DATABASE ===========");
        System.out.println("========== TESTING THE DATABASE ===========");
        System.out.println("===========================================");


        System.out.println("Deleting all content from tables...");

        try{
            ResultSet resultAdd;
            resultAdd = conn.createStatement().executeQuery(
                    "SELECT movies_id  FROM movies"
            );
            System.out.println("\n");
            while(resultAdd.next()){
                String movies_id = resultAdd.getString("movies_id");
                conn.createStatement().execute(
                        "DELETE FROM `movies` WHERE (`movies_id` = '"+movies_id+"')"
                );
                System.out.println("\n");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            ResultSet resultAdd;
            resultAdd = conn.createStatement().executeQuery(
                    "SELECT people_id  FROM people "
            );
            System.out.println("\n");
            while(resultAdd.next()){
                String person_id = resultAdd.getString("people_id");
                conn.createStatement().execute(
                        "DELETE FROM `people` WHERE (`people_id` = '"+person_id+"')"
                );
                System.out.println("\n");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        System.out.println("Inserting new people on people's table");
        conn.createStatement().execute(
                "INSERT INTO `people` (`name`, `nationality`) VALUES ('Alice', 'Br')"
        );
        System.out.println("\n");
        conn.createStatement().execute(
                "INSERT INTO `people` (`name`, `nationality`) VALUES ('Bob', 'Hu')"
        );
        System.out.println("\n");
        conn.createStatement().execute(
                "INSERT INTO `people` (`name`, `nationality`) VALUES ('Carlos', 'Ca')"
        );
        System.out.println("\n");
        conn.createStatement().execute(
                "INSERT INTO `people` (`name`, `nationality`) VALUES ('Daniel', 'Ag')"
        );
        System.out.println("\n");
        conn.createStatement().execute(
                "INSERT INTO `people` (`name`, `nationality`) VALUES ('Edward', 'Us')"
        );
        System.out.println("\n");
        conn.createStatement().execute(
                "INSERT INTO `people` (`name`, `nationality`) VALUES ('Frank', 'Ca')"
        );
        System.out.println("\n");

        System.out.println("Trying to insert duplicate");
        try{
            conn.createStatement().execute(
                    "INSERT INTO `people` (`name`, `nationality`) VALUES ('Carlos', 'Ca')"
            );
            System.out.println("\n");
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("\n");
        }

        System.out.println("Inserting new movies on movies's table");
        conn.createStatement().execute(
                "INSERT INTO `movies` (`title`, `director_id`, `duration`) VALUES ('Aline in ', '1', '1200');"
        );
        conn.createStatement().execute(
                "INSERT INTO `movies` (`title`, `director_id`, `duration`) VALUES ('Base line, slap like', '2', '800');"
        );
        conn.createStatement().execute(
                "INSERT INTO `movies` (`title`, `director_id`, `duration`) VALUES ('kkkrying, sad times', '2', '1800');"
        );
        conn.createStatement().execute(
                "INSERT INTO `movies` (`title`, `director_id`, `duration`) VALUES ('Power metal', '3', '8000');"
        );
        conn.createStatement().execute(
                "INSERT INTO `movies` (`title`, `director_id`, `duration`) VALUES ('Sonata 1', '5', '352');"
        );
        conn.createStatement().execute(
                "INSERT INTO `movies` (`title`, `director_id`, `duration`) VALUES ('Sonata 2', '5', '353');"
        );
        conn.createStatement().execute(
                "INSERT INTO `movies` (`title`, `director_id`, `duration`) VALUES ('Sonata 3', '5', '350');"
        );
        System.out.println("\n");

        System.out.println("Inserting new movie with same title but different author...");

        conn.createStatement().execute(
                "INSERT INTO `movies` (`title`, `director_id`, `duration`) VALUES ('kkkrying, sad times', '3', '1800');"
        );
        System.out.println("\n");

//        "INSERT INTO `nokia_challenge_test_db`.`starring` (`movies_id`, `people_id`) VALUES ('2', '4');"


        System.out.println("Inserting starring Actors...");

        System.out.println("\n");
        System.out.println("Inserting new movie with same title but different author...");
        conn.createStatement().execute(
        "INSERT INTO `nokia_challenge_test_db`.`starring` (`movies_id`, `people_id`) VALUES ('1', '1');"
        );

        System.out.println("Inserting new movie with same title but different author...");
        conn.createStatement().execute(
        "INSERT INTO `nokia_challenge_test_db`.`starring` (`movies_id`, `people_id`) VALUES ('1', '2');"
        );

        System.out.println("Inserting new movie with same title but different author...");
        conn.createStatement().execute(
        "INSERT INTO `nokia_challenge_test_db`.`starring` (`movies_id`, `people_id`) VALUES ('2', '4');"
        );

        System.out.println("Inserting new movie with same title but different author...");
        conn.createStatement().execute(
        "INSERT INTO `nokia_challenge_test_db`.`starring` (`movies_id`, `people_id`) VALUES ('3', '1');"
        );

        System.out.println("Inserting new movie with same title but different author...");
        conn.createStatement().execute(
        "INSERT INTO `nokia_challenge_test_db`.`starring` (`movies_id`, `people_id`) VALUES ('1', '5');"
        );

        System.out.println("Deleting a person that IS NOT a Director");
        System.out.println("\n");
        conn.createStatement().execute(
                "DELETE FROM `nokia_challenge_test_db`.`people` WHERE (`people_id` = '6')"
        );


        System.out.println("Deleting a person that IS a Director");
        System.out.println("\n");
        try{
            conn.createStatement().execute(
                    "DELETE FROM `nokia_challenge_test_db`.`people` WHERE (`people_id` = '1')"
            );
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("===========================================");
        System.out.println("========= TESTING THE APPLICATION =========");
        System.out.println("========= TESTING THE APPLICATION =========");
        System.out.println("========= TESTING THE APPLICATION =========");
        System.out.println("========= TESTING THE APPLICATION =========");
        System.out.println("===========================================");


        System.out.println("\n");
        runCommand("l");
        runCommand("l -t \"Sonata*\" ");
        runCommand("l -v");
        runCommand("l -v -t \"Sonata*\"");
        runCommand("l -la");
        runCommand("l -ld");
        runCommand("l -la -ld");
        runCommand("l -t \"Sonata*\"");
        runCommand("l -t\"Sonata*\"");
        runCommand("l a-t\"Sonata*\"");

        System.out.println("===========================================");
        System.out.println("============ INTERACTIVE TEST =============");
        System.out.println("============ INTERACTIVE TEST =============");
        System.out.println("============ INTERACTIVE TEST =============");
        System.out.println("============ INTERACTIVE TEST =============");
        System.out.println("===========================================");

        runCommand("a");
        runCommand("a -p");
        runCommand("a -m");

        runCommand("d");
        runCommand("d -p");
        runCommand("d -m");



    }

    public static void runCommand(String command) throws SQLException {
        Runner runner = new Runner(conn, availableCommands);

        System.out.println("Running: "+command);
        try{
            if(validator.checkIntegrity(command)){
                runner.runCommand(command);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
