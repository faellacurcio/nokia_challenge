package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static List<String> availableCommands = new ArrayList<>();

    static HashMap<String, Flag> availableFlags = new HashMap<>();
//    static List<String> availableFlags = new ArrayList<String>();

    static {
        String newCommand;

        // List command
        newCommand = "l";
        availableCommands.add(newCommand);

        // List + Starring
        availableFlags.put(newCommand, new Flag("-v", false));
        // List + Search by title
        availableFlags.put(newCommand, new Flag("-t", true));
        // List + Search by Director
        availableFlags.put(newCommand, new Flag("-d", true));
        // List + Order ascending order by their length
        availableFlags.put(newCommand, new Flag("-la", false));
        // List + Order descending order by their length
        availableFlags.put(newCommand, new Flag("-ld", false));


        // Add command
        newCommand = "a";
        availableCommands.add(newCommand);

        // Add people
        availableFlags.put(newCommand, new Flag("-p", false));
        // Add movie
        availableFlags.put(newCommand, new Flag("-m", false));

        // Delete command
        newCommand = "d";
        availableCommands.add(newCommand);

        // Add people
        availableFlags.put(newCommand, new Flag("-p", false));

    }

    public static void main(String[] args) {
	// write your code here
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("> ");
            String command = scanner.nextLine();
            validateCommand(command);

            if(command.equals("exit")){
                break;
            }
        }
    }

    public static boolean validateCommand(String raw_command){


        List<String> matchList = new ArrayList<>();
        Pattern regex;
        regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
        Matcher regexMatcher = regex.matcher(raw_command);
        while (regexMatcher.find()) {
            matchList.add(regexMatcher.group());
        }

        //Debug
        System.out.println(matchList);

        if(!availableCommands.contains(matchList.get(0))){
            return false;
        }else{
            System.out.println("First command ok!");
        }

        return true;
    }
}
