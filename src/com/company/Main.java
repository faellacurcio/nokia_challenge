package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static Commands availableCommands = new Commands();
    static Validation validator = new Validation(availableCommands);

    static final boolean DEBUG = true;

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

        Connection conn = null;

        try {
            String mysqlUrl = "jdbc:mysql://localhost/nokia_challenge_db?serverTimezone=UTC";
            String username = "admin";
            String password = "ra4Vy77W78LhCZd";

            conn = DriverManager.getConnection(
                    mysqlUrl,
                    username,
                    password
            );

            System.out.println("Connection with the databse established...");
            //TODO: Run script to initialize the database through JAVA.

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }


        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("> ");
            String command = scanner.nextLine();

            validator.checkIntegrity(command);

            if(command.equals("exit")){
                break;
            }
        }
    }
}
