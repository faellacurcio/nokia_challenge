package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Runner {
    private Connection conn;
    private Commands availableCommands;
    private Statement statement;

    public Runner(Connection conn, Commands availableCommands) throws SQLException {
        this.conn = conn;
        this.availableCommands = availableCommands;
        this.statement = conn.createStatement();
    }

    public void runCommand(String raw_command) throws SQLException {
        HashMap<String, String> flags = Util.parseToHashMap(raw_command, availableCommands);

        List<String> matchList = Util.parseToList(raw_command);
        List<String> flagList = Util.parseToGetKeys(raw_command,availableCommands);

        Iterator<String> matchListIterator = matchList.iterator();

        if (matchListIterator.hasNext()) {
            String main_command = matchListIterator.next();
            System.out.println(flagList);

            switch (main_command) {
                case "l" -> {
                    String regex = "";
                    if(flagList.contains("-t")) {
                        regex = " AND m.title REGEXP "+flags.get("-t")+" ";
                    }
                    if(flagList.contains("-d")) {
                        regex = " AND m.title REGEXP "+flags.get("-d")+" ";
                    }
                    if(flagList.contains("-a")) {
                    regex = " AND (p.name) REGEXP "+flags.get("-a")+" ";
                    }
                    if(flagList.isEmpty()){
                        ResultSet resultSet = conn.createStatement().executeQuery(
                                "SELECT p.name, m.title, duration "+
                                    "FROM movies m JOIN people p ON "+
                                    "m.director_id = p.people_id ORDER BY m.title ASC"
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
                                    " ORDER BY m.title ASC"
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
                            System.out.println("");

                        }
                    }else{
                        System.out.println("SELECT p.name,m.title, duration "+
                                "FROM movies m JOIN people p ON "+
                                "m.director_id = p.people_id "+regex+
                                " ORDER BY m.title ASC");
                        ResultSet resultSet = conn.createStatement().executeQuery(
                                "SELECT p.name, m.title, m.duration "+
                                    "FROM movies m JOIN people p ON "+
                                    "m.director_id = p.people_id "+regex+
                                    " ORDER BY m.title ASC"
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
                        conn.createStatement().execute(
                            "INSERT INTO `people` (`name`, `nationality`) VALUES ('"+nome+"', '"+nationality+"')"
                        );
                    }
                    if(flagList.contains("-m")) {
                        System.out.println("Title: ");
                        String title = scanner2.nextLine();

                        ResultSet resultAdd = null;
                        String director = "";
                        boolean directorOk = false;
                        do{
                            System.out.println("Director: ");
                            director = scanner2.nextLine();
                            //TODO: Make sure person exists
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

                        resultAdd = null;
                        String actor = "";
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

                                if(resultAdd.next())
                                    actor = resultAdd.getString("name");
                                else
                                    System.out.println("We could not find \""+actor+"\", try again!");

                                directorOk = true;
                            }catch (Exception e){

                                e.printStackTrace();
                            }

                        }while(true);

                    }

                }
                case "d" -> {
                    System.out.println("delete");
                }
            }
        }



    }
}
