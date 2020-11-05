package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import static java.lang.System.exit;

/**
 * The Main class is the brain of the console applications, using all the necessary classes to create the console
 * application proposed b the Nokia Challenge.
 *
 */
public class Main {

    static Commands availableCommands = new Commands();
    static Validation validator = new Validation(availableCommands);

    static Connection conn = null;

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

    public static void main(String[] args) {

        // Tries to connect with the database
        try {
            String mysqlUrl = "jdbc:mysql://localhost/nokia_challenge_db?serverTimezone=UTC";
            String username = "admin";
            String password = "ra4Vy77W78LhCZd";

            conn = DriverManager.getConnection(
                    mysqlUrl,
                    username,
                    password
            );

            System.out.println("Connection with the database established...");
            System.out.println(
                    "\n"+
                    "Wellcome to my solution of the Nokia challenge.\n"+
                    "This is a console application, the commands are:\n"+
                    "l: List the movies\n"+
                    "\t-t \"regex\" :Search for a title using regex.\n"+
                    "\t-v : Display the actors in the movie\n"+
                    "\t-d \"regex\" :Search for a director using regex.\n"+
                    "\t-a \"regex\" :Search for an actor using regex.\n"+
                    "\t-la : lists the movies with ascending order by their length\n"+
                    "\t-ld : lists the movies with descending order by their length\n"+
                    "\n"+
                    "a: Add new people/movies"+
                    "\t-p : Add new people.\n"+
                    "\t-m : Add new movie.\n"+
                    "\n"+
                    "d: Delete people/movies"+
                    "\t-p : Delete new people.\n"+
                    "\t-m : Delete new movie.\n"
            );
            //TODO: Run script to initialize the database through JAVA.

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            exit(0);
        }


        try{
            Runner runner = new Runner(conn, availableCommands);

            Scanner scanner = new Scanner(System.in);

            // Main loop, receives multiple inputs validate and run the commands.
            while(true){
                System.out.print("> ");
                String command = scanner.nextLine();

                if(validator.checkIntegrity(command)){
                    runner.runCommand(command);
                }

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
