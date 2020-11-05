package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


/**
 * The Runner class is the class that converts user input to action.
 *
 * input: Connection obj, availableCommands obj
 *
 */
public class Runner {
    private final Connection conn;
    private final Commands availableCommands;

    public Runner(Connection conn, Commands availableCommands) {
        this.conn = conn;
        this.availableCommands = availableCommands;
    }

    /**
     * The runCommand is the heart of the algorithm and does the heavy lifting interpreting the user input
     * and calling the appropriate functions to execute the instructions, including all the communication with the
     * database
     *
     * input String raw_command
     * output none
     */
    public void runCommand(String raw_command) throws SQLException {
        /**
         * Firstly the method extracts some usefull information of the availableCommands and raw_command (user command)
         */
        HashMap<String, String> flags = Util.parseToHashMap(raw_command, availableCommands);
        List<String> matchList = Util.parseToList(raw_command);
        List<String> flagList = Util.parseToGetKeys(raw_command,availableCommands);

        Iterator<String> matchListIterator = matchList.iterator();

        /**
         * Then it iterate through the commands realizing the appropriate actions according to the command/flags/params
         */
        if (matchListIterator.hasNext()) {
            String main_command = matchListIterator.next();

            switch (main_command) {
                /**
                 * List movies according to the flags/params
                 */
                case "l" -> {
                    String regex = "";
                    String order = "ORDER BY m.title ASC ";
                    if(flagList.contains("-t")) {
                        regex = " AND m.title REGEXP "+flags.get("-t")+" ";
                    }
                    if(flagList.contains("-d")) {
                        regex = " AND (p.name) REGEXP "+flags.get("-d")+" ";
                    }
                    //TODO: Fix -a flag
                    if(flagList.contains("-a")) {
                        regex = " AND (p.name) REGEXP "+flags.get("-a")+" ";
                    }
                    if(flagList.contains("-la")) {
                        order = " ORDER BY m.duration ASC ";
                    }
                    if(flagList.contains("-ld")) {
                        order = " ORDER BY m.duration DESC ";
                    }
                    if(flagList.isEmpty()){
                        ResultSet resultSet = conn.createStatement().executeQuery(
                                "SELECT p.name, m.title, duration "+
                                    "FROM movies m JOIN people p ON "+
                                    "m.director_id = p.people_id "+
                                    order
                        );
                        while (resultSet.next()){
                            String title = resultSet.getString("title");
                            String name = resultSet.getString("name");
                            String duration = resultSet.getString("duration");

                            System.out.println(title+" by "+name+" "+duration+"s");
                        }
                    }else if(flagList.contains("-v")){
                        ResultSet resultSet = conn.createStatement().executeQuery(
                                "SELECT p.name, m.title, duration "+
                                    "FROM movies m JOIN people p "+
                                    "ON m.director_id = p.people_id "+regex+
                                    order
                        );
                        //SELECT stuff FROM table1 JOIN table2 ON table1.relatedColumn = table2.relatedColumn
                        while (resultSet.next()){
                            String title = resultSet.getString("title");
                            String name = resultSet.getString("name");
                            String duration = resultSet.getString("duration");

                            System.out.println(title+" by "+name+" "+duration+"s");

                            ResultSet actorSet = conn.createStatement().executeQuery(
                                    "select distinct p.name "+
                                    "from people p, movies m, starring s "+
                                    "where p.people_id = s.people_id and m.movies_id = s.movies_id "+regex+
                                    "and m.title = '"+title+"'"
                            );
                            System.out.println("\tStarring:");
                            while (actorSet.next()) {
                                System.out.println(
                                        "\t\t"+
                                        actorSet.getString("name")
                                );
                            }
                            System.out.println();

                        }
                    }else{

                        ResultSet resultSet = conn.createStatement().executeQuery(
                                "SELECT p.name, m.title, m.duration "+
                                    "FROM movies m JOIN people p ON "+
                                    "m.director_id = p.people_id "+regex+
                                    order
                        );
                        //SELECT stuff FROM table1 JOIN table2 ON table1.relatedColumn = table2.relatedColumn
                        while (resultSet.next()){
                            System.out.println(
                                    resultSet.getString("title") +
                                            " by " +
                                            resultSet.getString("name")+
                                            " "+
                                            resultSet.getString("duration")
                            );
                        }
                    }

                }
                /**
                 * Add new people/movies according to the flags/params
                 */
                case "a" -> {
                    Scanner scanner2 = new Scanner(System.in);

                    if(flagList.isEmpty()) {
                        String option;
                        do{
                            System.out.println("Would you like to add people (p) or movies (m)?");
                            option = scanner2.nextLine();
                        }while(!(option.equals("p") || option.equals("m")));

                        flagList.add("-"+option);

                    }

                    if(flagList.contains("-p")) {
                        System.out.println("Person name: ");
                        String nome = scanner2.nextLine();
                        System.out.println("nationality: ");
                        String nationality = scanner2.nextLine();
                        try{
                            conn.createStatement().execute(
                                    "INSERT INTO `people` (`name`, `nationality`) VALUES ('"+nome+"', '"+nationality+"')"
                            );
                        }catch (Exception e){
                            System.out.println("Error: Duplicated entry!");
                        }

                    }
                    if(flagList.contains("-m")) {
                        System.out.println("Title: ");
                        String title = scanner2.nextLine();

                        ResultSet resultAdd;
                        String director;
                        boolean directorOk = false;
                        do{
                            System.out.println("Director: ");
                            director = scanner2.nextLine();
                            try{
                                resultAdd = conn.createStatement().executeQuery(
                                        "SELECT p.people_id "+
                                                " FROM people p "+
                                                " WHERE p.name = '"+director+"'"
                                );

                                resultAdd.next();
                                director = resultAdd.getString("people_id");

                                directorOk = true;
                            }catch (Exception e){
                                System.out.println("We could not find \""+director+"\", try again!");
                                //e.printStackTrace();
                            }

                        }while(!directorOk);


                        System.out.println("Duration: ");
                        String duration = scanner2.nextLine();
                        System.out.println(
                                "INSERT INTO `movies` (`title`, `director_id`, `duration`) "+
                                "VALUES ('"+title+"', '"+director+"', '"+duration+"')"
                        );

                        conn.createStatement().execute(
                            "INSERT INTO `movies` (`title`, `director_id`, `duration`) "+
                                "VALUES ('"+title+"', '"+director+"', '"+duration+"')"
                        );

                        String actor;
                        do{
                            System.out.println("Starring: ");
                            actor = scanner2.nextLine();
                            if(actor.equals("exit")){
                                break;
                            }

                            try{
                                resultAdd = conn.createStatement().executeQuery(
                                        "SELECT p.name "+
                                                "FROM people p "+
                                                "WHERE p.name = '"+actor+"'"
                                );

                                //TODO: Finish up adding the actor~movie to Database
                                if(resultAdd.next())
                                    actor = resultAdd.getString("name");
                                else
                                    System.out.println("We could not find \""+actor+"\", try again!");

                            }catch (Exception e){

                                e.printStackTrace();
                            }

                        }while(true);

                    }

                }
                /**
                 * Delete movies according to the flags/params
                 */
                case "d" -> {
                    Scanner scanner3 = new Scanner(System.in);

                    if(flagList.isEmpty()) {
                        String option;
                        do{
                            System.out.println("Would you like to delete people (p) or movies (m)?");
                            option = scanner3.nextLine();
                        }while(!(option.equals("p") || option.equals("m")));

                        flagList.add("-"+option);

                    }

                    if(flagList.contains("-p")) {
                        System.out.println("Person name (exactly): ");
                        String nome = scanner3.nextLine();

                        try{
                            ResultSet resultAdd;
                            resultAdd = conn.createStatement().executeQuery(
                                    "SELECT p.people_id "+
                                            " FROM people p "+
                                            " WHERE p.name = '"+nome+"'"
                            );

                            resultAdd.next();
                            String person_id = resultAdd.getString("people_id");
                            System.out.println("Trying to remove");
                            conn.createStatement().execute(
                                    "DELETE FROM `people` WHERE (`people_id` = '"+person_id+"')"
                            );


                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }else if(flagList.contains("-m")) {
                        System.out.println("Movie title (exactly): ");
                        String nome = scanner3.nextLine();

                        try{
                            ResultSet resultAdd;
                            resultAdd = conn.createStatement().executeQuery(
                                    "SELECT m.title "+
                                            " FROM movies m "+
                                            " WHERE m.title = '"+nome+"'"
                            );

                            resultAdd.next();
                            String person_id = resultAdd.getString("title");
                            conn.createStatement().execute(
                                    "DELETE FROM `movies` WHERE (`title` = '"+nome+"')"
                            );
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }

                }
            }
        }



    }
}
