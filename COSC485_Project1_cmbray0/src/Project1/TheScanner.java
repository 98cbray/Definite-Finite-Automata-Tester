package Project1;


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TheScanner {

    States diagram = new States();
    Scanner input = new Scanner(System.in);
    String path = null;
    String line = null;
    ArrayList<String> NFA_DFA = new ArrayList<String>();
    ArrayList<String> the_Strings = new ArrayList<String>();
    ArrayList<String> output = new ArrayList<String>();

    public TheScanner() {
    }

    /**
     * main method that compiles the class
     *
     * @param DFA_or_NFA
     * @param Strings
     * @param Answers
     * @throws IOException
     */
    public void userMessage(String DFA_or_NFA, String Strings, String Answers) throws IOException {

         System.out.println(DFA_or_NFA);
         System.out.println(Strings);
         System.out.println(Answers);
         
    

        //System.out.println("Please enter the location/path to your DFA/NFA file now. For example: 'C:\\COSC485_Project1_cmbray0\\...\\DFA.txt'"+ "\n");
        //path=input.nextLine();      
        reader(0, DFA_or_NFA);
        //System.out.println("Please enter the location/path to your String file now. For example: 'C:\\COSC485_Project1_cmbray0\\...\\String.txt'"+ "\n");
        //path=input.nextLine(); 
        reader(1, Strings);
		

        data_Entry(0);

        data_Entry(1);

        designate_State_Connections();

        designate_Starting_And_Final_States();

        diagram.double_Check_For_NFA();

        System.out.println("\n" + "State Elements Are:" + "\n" + "-------------------------------");
        
        diagram.print_State_Elements();
        System.out.println("\n" + "-------------------------------");
        
        System.out.println("\n" + "Original Starting & Final States:" + "\n" + "-------------------------------");
        
        diagram.print_Starting_State();
        System.out.println();
        
        diagram.print_Final_State();
        System.out.println("\n");

        //System.out.println("\n" + "Original Connections:" + "\n" + "-------------------------------");

        //diagram.print_States_And_Connections();

        //System.out.println("-------------------------------");

        //diagram.print_NFA_Nodes();

        //System.out.println("-------------------------------");


        if (diagram.is_This_An_NFA_Diagram() == true) {

            diagram.the_Converter();

            // System.out.println("\n" + "(e-Trans are found in the NFA_States/StateNodes):" + "\n" + "-------------------------------");

            // diagram.print_e_Trans();

            //System.out.println("\n" + "-------------------------------");

            diagram.build_NFA_States();

            diagram.build_NFA();

            //System.out.println("\n" + "NFA Built Connections (Using NFA_Nodes):" + "\n" + "-------------------------------");

            //diagram.print_NFA_Nodes();

        }

        if (diagram.is_This_An_NFA == true) {
            testing_Strings(diagram.NFA_State_Nodes);
        } else {
            testing_Strings(diagram.states);
        }
        create_Output(Answers); // create the output text file
        
    }
    

    /**
     * reads DFA/NFA and Strings text files and imports them into ArrayLists
     *
     * @param choice
     * @param location_Txt
     */
    public void reader(int choice, String path) {
        try {
        	Scanner temp = new Scanner(System.in);
        	if (choice == 0) {System.out.println("Please enter DFA file name, without .txt"); }
        	else { System.out.println("Please enter Strings file name, without .txt"); }
        	String textLocation = temp.nextLine()+".txt";
            File file = new File(textLocation);
            //File file = new File(path);
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                line = scan.nextLine();
                if ((choice == 0) && (!(line.isEmpty()))) {
                    line = line.trim().toLowerCase();
                    NFA_DFA.add(line);
                } else if ((choice == 1)) {
                	if (line.isEmpty()) {
                		line = " ";
                	}
                    line = line.toLowerCase();
                    the_Strings.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unfortunately the file pathing was incorect. Please restart program and try again.");
            //reader(choice);
            System.out.println("Current choice is "+choice);
        }
    }

    /**
     * prints the NFA/DFA text file ArrayList
     */
    public void print_NFA_DFA() {
        System.out.println("The following is the ArrayList for the DFA.txt: ");
        for (int i = 0; i < NFA_DFA.size(); i++) {
            System.out.println(NFA_DFA.get(i));
        }
        System.out.println();
    }

    /**
     * prints the Strings text file ArrayList
     */
    public void print_String() {
        System.out.println("The following is the ArrayList for the String.txt: ");
        for (int i = 0; i < the_Strings.size(); i++) {
            System.out.println(the_Strings.get(i));
        }
        System.out.println();
    }

    /**
     * creates states from DFA/NFA text file and import them to the States class
     *
     * @param type
     */
    public void data_Entry(int type) {
        if (type == 0) {
            diagram.create_Sink_State();
        }
        String temp_Seperated = null;
        int start;
        int end;
        int counter = 1;

        String first_Check = "states";
        String second_Check = "final";

        if (type == 1) {
            first_Check = "alphabet";
            second_Check = "final";
        }

        int found = -1;
        int k = 1;
        while ((k <= NFA_DFA.size()) && (found == -1)) {
            if ((NFA_DFA.get(k).contains(first_Check)) && (!(NFA_DFA.get(k).contains(second_Check)))) {
                found = k;
            }
            k++;
        }

        String temp = NFA_DFA.get(found);

        start = temp.indexOf("{");
        end = temp.indexOf("}");
        temp = temp.substring(start + 1, end - 1);

        int t = 0;
        int i = 0;
        boolean ending = false;
        while (i < temp.length()) {
            if (temp.substring(i, i + 1).equals(",")) {
                counter++;
                temp_Seperated = temp.substring(t, i);
                temp_Seperated = temp_Seperated.trim();
                if (temp_Seperated.equals("")) {
                    temp_Seperated = " ";
                }
                if (type == 0) {
                    diagram.create_New_State(temp_Seperated);
                }
                if (type == 1) {
                    diagram.add_Element(temp_Seperated);
                }
                t = i + 1;
            } else if ((!(temp.substring(t).contains(","))) && (ending == false)) {
                temp_Seperated = temp.substring(t);
                temp_Seperated = temp_Seperated.trim();
                if (temp_Seperated.equals("")) {
                    temp_Seperated = " ";
                }
                ending = true;
                if (type == 0) {
                    diagram.create_New_State(temp_Seperated);
                }
                if (type == 1) {
                    diagram.add_Element(temp_Seperated);
                }
            }
            i++;
        }
    }

    /**
     * designates the starting and final states from DFA/NFA text file and
     * import them to the States class
     */
    public void designate_Starting_And_Final_States() {

        int found = -1;
        int k = 1;
        String temp = null;
        String temp2 = null;
        while ((k < NFA_DFA.size()) && (found == -1)) {
            if ((NFA_DFA.get(k).contains("starting")) && (!(NFA_DFA.get(k).contains("alphabet")))) {
                temp = NFA_DFA.get(k);
                temp = temp.substring(temp.indexOf("=") + 1);
                temp = temp.substring(0, temp.length() - 1);
                if (temp.contains("{")) {
                    temp.replace("{", "");
                }
                if (temp.contains("}")) {
                    temp.replace("}", "");
                }
                if (temp.contains(",")) {

                    temp.replace(",", "");
                }
                temp = temp.trim();
                if (temp.equals("")) {
                    temp = " ";
                }
                diagram.set_Starting_State(temp);
                found = k;
            }
            k++;
        }
        diagram.set_Final_State(temp2); ///////////////////////
        found = -1;
        k = 1;
        while (k < NFA_DFA.size()) {
            if ((NFA_DFA.get(k).contains("final")) && (!(NFA_DFA.get(k).contains("alphabet")))) {
                temp2 = NFA_DFA.get(k);
                temp2 = temp2.substring(temp2.indexOf("=") + 1);
                found = k;
            }
            k++;
        }
        designate_Final_States(temp2);
    }

    /**
     * designates the final states
     *
     * @param temp
     */
    public void designate_Final_States(String temp2) {
        if (temp2.contains(",")) {
            String part = temp2;
            part = part.substring(0, part.indexOf(","));

            if (part.contains("{")) {
                part = part.substring(part.indexOf("{") + 1);
            }
            if (part.contains("}")) {
                part = part.substring(0, part.indexOf("}"));
            }
            part = part.trim();
            if (part.equals("")) {
                part = " ";
            }
            diagram.set_Final_State(part);
            temp2 = temp2.substring(temp2.indexOf(",") + 1);
            temp2 = temp2.trim();
            if (temp2.equals("")) {
                temp2 = " ";
            }
            if (temp2.contains(",")) {
                designate_Final_States(temp2);
            }
            return;
        }
    }

    /**
     * creates state connections from DFA/NFA text file and import them to the
     * States class
     */
    public void designate_State_Connections() {
        int found = -1;
        int k = 1;
        int locate_End;
        int locate_First;
        int locate;
        String element;
        String connection;
        String current;
        String stateId;
        String current_Broken;
        while (k < NFA_DFA.size()) {
            if ((NFA_DFA.get(k).contains("transition")) && (!(NFA_DFA.get(k).contains("final")))) {
                found = k;
            }
            k++;
        }
        String temp = "";

        while (found < NFA_DFA.size()) {
            temp = temp + " " + NFA_DFA.get(found);
            found++;
        }

        temp = temp.substring(temp.indexOf("{") + 1).trim();
        temp = temp.substring(0, temp.length() - 1).trim();

        current = temp;
        while (current.contains("(")) {
            locate_First = current.indexOf("(");
            current = current.substring(locate_First + 1);
            locate_End = current.indexOf(")");
            current_Broken = current.substring(0, locate_End - 1);

            locate_First = current.indexOf(",");
            stateId = current_Broken.substring(0, locate_First).trim();
            if (stateId.equals("")) {
                stateId = " ";
            }
            current_Broken = current_Broken.substring(locate_First + 1).trim();

            if (current_Broken.substring(0, 1).equals("")) {
                element = " ";
            } else {
                element = current_Broken.substring(0, 1);
            }

            locate_First = current.indexOf(",");
            current_Broken = current_Broken.substring(1, current_Broken.length()).trim();
            current_Broken = current_Broken.substring(1, current_Broken.length()).trim();

            if (current_Broken.equals("")) {
                connection = " ";
            } else {
                connection = current_Broken;
            }

            diagram.add_A_Connection(stateId, element, connection);

            /*
            System.out.println(temp);
            System.out.println(stateId);
            System.out.println(element);
            System.out.println(connection);
             */
        }
        diagram.sort_States(diagram.states);
        //diagram.sort_Connections();
    }

    /**
     * tests strings from Strings text file for correctness
     *
     * @param node
     */
    public void testing_Strings(ArrayList<StateNodes> node) {
        String correction = null;
        for (int i = 0; i < the_Strings.size(); i++) {
            System.out.println(the_Strings.get(i));
            correction = diagram.string_Validation_Check(the_Strings.get(i), node);
            output.add(correction);
            System.out.println(output.get(i) + "\n" + "-------------------------------------" + "\n");
        }
    }

    /**
     * creates and writes tested string at the Answers location
     *
     * @param Answers
     * @throws IOException
     */
    public void create_Output(String Answers) throws IOException {
        File file = new File(Answers);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        //System.out.println(file.canWrite());
        FileWriter file_Location = new FileWriter(file);       
        BufferedWriter writer = new BufferedWriter(file_Location);
        writer.newLine();
        for (int i = 0; i < output.size(); i++) {
            writer.write(output.get(i));
            writer.newLine();
        }
         
        writer.close();
    }
}