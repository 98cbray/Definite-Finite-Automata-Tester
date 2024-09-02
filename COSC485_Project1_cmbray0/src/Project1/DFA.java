package Project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.IOException;

public class DFA {

    public static void main(String[] args) throws IOException {
   	
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the file path for DFA document: ");
            String DFA_or_NFA = scanner.nextLine();

            System.out.print("Enter the file path for strings document: ");
            String Strings = scanner.nextLine();

            System.out.print("Enter the file name for outputted answers document, remember to add .txt to make text file. ");
            String Answers = scanner.nextLine();                     
    	
        TheScanner the_Scanner = new TheScanner(); // run the scanner method for main class


        the_Scanner.userMessage(DFA_or_NFA, Strings, Answers);
        
    } 
}
