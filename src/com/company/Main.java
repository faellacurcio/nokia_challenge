package com.company;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
//    static List<String> availableCommands = new ArrayList<>();
//    static HashMap<String, Flags> availableCommands = new HashMap<>();
    static Commands availableCommands = new Commands();

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
//        System.out.println(matchList);
        Iterator<String> matchListIterator = matchList.iterator();

        if (matchListIterator.hasNext()){
            String main_command = matchListIterator.next();

            if(!availableCommands.hasCommand(main_command)){
                System.out.println(main_command+" is not a valid operation.");
            }else{
                boolean isParam = false;

                // Loop through flags and parameters
                while(matchListIterator.hasNext()){
                    String testingSwitch = matchListIterator.next();

                    if(isParam){
                        // Check integrity of regex
                        isParam = false;
                    }else{
                        System.out.println("Testing "+testingSwitch+", is it a flag?  "+availableCommands.hasFlag(main_command, testingSwitch));
                        if(availableCommands.hasFlag(main_command, testingSwitch)){
                            System.out.println(testingSwitch+" has parameters");
                            isParam = true;
                        }else{
                            System.out.println(testingSwitch+" has no parameters");
                        }
                    }
                }

            }
        }


        return true;
    }
}
