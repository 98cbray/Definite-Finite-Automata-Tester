package Project1;

import java.util.ArrayList;
import java.util.Collections;

public class States {

    ArrayList<StateNodes> states = new ArrayList<StateNodes>();
    ArrayList<StateNodes> NFA_State_Nodes = new ArrayList<StateNodes>();
    ArrayList<StateNodes> diagram_Sort = new ArrayList<StateNodes>();
    ArrayList<String> final_States = new ArrayList<String>();
    ArrayList<String> state_Elements = new ArrayList<String>();
    ArrayList<Integer> final_States_Location = new ArrayList<Integer>();
    Boolean is_This_An_NFA = false;
    String starting_State = null;
    int number_Of_Elements = 0;
    Integer starting_State_Location = -1;
    int the_Tracker = 0;
    int racer = 2;
    int locate;
    String THE_holder;
    String THIS_ONE = "no";

    public States() {
    }

    /**
     * Finds an existing state and returns the states location, or returns -1 if
     * the state doesn't exist
     *
     * @param stateId
     * @param node
     * @return
     */
    public int find_An_Existing_State(String stateId, ArrayList<StateNodes> node) {
        int stateLocation = -1;
        String temp;
        for (int i = 0; i < node.size(); i++) {
            temp = node.get(i).return_ID();
            if (temp.contains(",")) {
                temp = temp.substring(0, temp.indexOf(","));
            }
            if (temp.equals(stateId)) {
                stateLocation = i;
            }
        }
        return stateLocation;
    }

    /**
     * Finds an existing NFA state and returns the states location, or returns -1 if the state doesn't exist
     * @param stateId
     * @param node
     * @return 
     */
    public int find_An_Existing_State_NFA(String stateId, ArrayList<StateNodes> node) {
        int stateLocation = -1;
        String temp;
        for (int i = 0; i < node.size(); i++) {
            temp = node.get(i).stateID;

            if ((temp.contains(","))) {
                temp = temp.substring(0, temp.indexOf(","));
                locate = find_An_Existing_State(temp, states);
                if (!(states.get(locate).NFA_States.isEmpty())) {
                    if (temp.equals(stateId)) {
                        stateLocation = i;
                    }
                }
            } else if (temp.equals(stateId)) {
                stateLocation = i;
            }
        }
        return stateLocation;
    }

    /**
     * adds elements to sigma
     *
     * @param element
     */
    public void add_Element(String element) {
        state_Elements.add(element);
        number_Of_Elements++;
    }

    /**
     * Prints the number of elements in the diagram, and what they are
     */
    public void print_State_Elements() {
        System.out.println("There are: " + number_Of_Elements + " elements in this diagram.");
        System.out.println("The elements used in this diagram are: ");
        for (int i = 0; i < state_Elements.size(); i++) {
            System.out.print(state_Elements.get(i) + "\t");
        }
    }

    /**
     * adds a connection to the stateId
     *
     * @param stateId
     * @param element
     * @param connection
     */
    public void add_A_Connection(String stateId, String element, String connection) {
        int stateLocation = find_An_Existing_State(stateId, states);
        if (stateLocation == -1) {
            System.out.println("falure");
            return;
        }
        if (element.equals("e")) {
            is_This_An_NFA = true;
        }
        states.get(stateLocation).add_Connection(element, connection);
    }

    /**
     * returns true if the diagram is an NFA
     *
     * @return
     */
    public Boolean is_This_An_NFA_Diagram() {
        return is_This_An_NFA;
    }

    /**
     * creates a new state
     *
     * @param stateId
     */
    public void create_New_State(String stateId) {
        states.add(new StateNodes(stateId));
    }

    /**
     * creates the sink state
     */
    public void create_Sink_State() {
        states.add(new StateNodes("{}"));
        states.get(0).set_As_The_Sink_State();
        NFA_State_Nodes.add(new StateNodes("{}"));
        NFA_State_Nodes.get(0).set_As_The_Sink_State();
    }

    /**
     * sets starting state
     *
     * @param stateId
     */
    public void set_Starting_State(String stateId) {
        int stateLocation = find_An_Existing_State(stateId, states);
        if (stateLocation == -1) {
            return;
        }
        states.get(stateLocation).set_As_The_Starting_State();
        starting_State = stateId;
        starting_State_Location = stateLocation;
        //NOTE:this keeps track of the starting state
    }

    /**
     * prints starting state
     */
    public void print_Starting_State() {
        System.out.println("The starting state is: " + states.get(starting_State_Location).stateID + " and is located at: " + starting_State_Location);
    }

    /**
     * sets final state/states
     *
     * @param stateId
     */
    public void set_Final_State(String stateId) {
        int stateLocation = find_An_Existing_State(stateId, states);
        if (stateLocation == -1) {
            return;
        }
        // This method does not keep track of the final states.However the StateNodes class does keep track of final states; on an individual basis
        final_States.add(stateId);
        final_States_Location.add(stateLocation);
        // This ArrayList will also keep track of your final states       
        states.get(stateLocation).set_As_A_Final_State();
    }

    /**
     * prints final states
     */
    public void print_Final_State() {
        System.out.println("The final states for this diagram are: ");
        for (int i = 0; i < final_States.size(); i++) {
            int locate = find_An_Existing_State(final_States.get(i), states);
            System.out.println("Final state: " + final_States.get(i) + " found at: " + locate);
            //System.out.print(final_States.get(i) + "\t");
        }
    }

    /**
     * checks if the string input is possible given the NFA/DFA
     *
     * @param element
     * @param node
     * @return
     */
    public String string_Validation_Check(String element, ArrayList<StateNodes> node) {
        element = find_Existing_Connection(element, 0, starting_State, node);  //node);
        element = element + THIS_ONE;
        return element;
    }

    /**
     * Tests strings for statesId's
     * @param stateId
     * @param node
     * @return 
     */
    public int find_An_Existing_State_Checker(String stateId, ArrayList<StateNodes> node) {
        int locate = find_An_Existing_State_Checker_Helper(stateId, node);
        //System.out.println(locate);
        if (locate == -1) {
            if (stateId.contains(",")) {
                stateId = stateId.substring(0, stateId.indexOf(","));
                locate = find_An_Existing_State_Checker_Helper(stateId, node);
            }
        }
        //System.out.println("stateid: "+stateId +" "+locate);
        return locate;
    }

    /**
     * extends find_An_Existing_State_Checker
     * @param stateId
     * @param node
     * @return 
     */
    public int find_An_Existing_State_Checker_Helper(String stateId, ArrayList<StateNodes> node) {
        int stateLocation = -1;
        String temp;
        for (int i = 0; i < node.size(); i++) {
            temp = node.get(i).return_ID();
            if (temp.equals(stateId)) {
                stateLocation = i;
            }
        }
        return stateLocation;
    }

    /**
     * extention of string_Validation_Check
     *
     * @param strings
     * @param location
     * @param stateId
     * @param node
     * @return
     */
    public String find_Existing_Connection(String strings, int location, String stateId, ArrayList<StateNodes> node) {

        THIS_ONE = strings;
        int locate = find_An_Existing_State_Checker(stateId, node);
        stateId = node.get(locate).find_Connection_DFA(strings.substring(location, location + 1));
        // if (stateId.contains(",")) {
        //stateId = stateId.substring(0, stateId.indexOf(","));
        //}
        THE_holder = stateId;
        //the string is rejected
         System.out.println("stateID: " + THE_holder);
        if (stateId.equals("false")) {
            //System.out.println("FAILURE: connection not found");
        	if (final_States.get(0) == states.get(starting_State_Location).stateID){
            THIS_ONE = " is accepted.";// " " on initial state = final state
        	}
        	else {
        		THIS_ONE = " is rejected."; // default false state ID is rejection
        	}        	
        } //recursion for next element in the string
        else if ((location + 1) != strings.length()) {
            location++;
            System.out.println("location: "+location+" string length: "+ strings.length());
            find_Existing_Connection(strings, location, stateId, node);
        }
        if (!(THE_holder.equals("false"))) {
            if ((node.get(find_An_Existing_State_Checker(THE_holder, node)).is_A_Final_State == false)) {
                System.out.println("FAILURE: does not end on final state");
                THIS_ONE = " is rejected.";
            } else {
                THIS_ONE = " is accepted.";
            }            
        } else {
            THIS_ONE = " is rejected.";
        }
        //if it makes it here the string is accepted
        return strings;
    }

    /**
     * prints states, states and there connections
     */
    public void print_States_And_Connections() {

        System.out.println("\n" + "Printing all states and connections now: ");
        for (int i = 0; i < states.size(); i++) {
            System.out.println("\n" + "Current state: " + states.get(i).return_ID() + " and its connections are: " + "\t");
            for (int t = 0; t < states.get(i).stateConections.size(); t++) {
                String temp = states.get(i).stateConections.get(t);
                System.out.print(temp.charAt(0) + " connected to: " + temp.substring(1) + "\n");
            }
        }
    }

    /**
     * prints diagram_Sort, states and there connections
     */
    public void print_SORTER() {

        System.out.println("\n" + "Printing all states and connections now: ");
        for (int i = 0; i < diagram_Sort.size(); i++) {
            System.out.println("\n" + "Current state: " + diagram_Sort.get(i).return_ID() + " and its connections are: " + "\t");
            for (int t = 0; t < diagram_Sort.get(i).stateConections.size(); t++) {
                String temp = diagram_Sort.get(i).stateConections.get(t);
                System.out.print(temp.charAt(0) + " connected to: " + temp.substring(1) + "\n");
            }
        }
    }

    /**
     * prints NFA_States, states and there connections
     */
    public void print_NFA_Connections() {

        System.out.println("\n" + "Printing all NFA connections now: ");
        for (int i = 0; i < states.size(); i++) {
            System.out.println("\n" + "Current state: " + states.get(i).return_ID() + " and its NFA connections are: " + "\t");
            for (int t = 0; t < states.get(i).NFA_States.size(); t++) {
                System.out.print(states.get(i).NFA_States.get(t));
            }
        }
    }

    /**
     * prints e-transitions
     */
    public void print_e_Trans() {
        for (int i = 0; i < states.size(); i++) {
            if (!(states.get(i).NFA_States.isEmpty())) {
                System.out.println("\n" + "State Id: " + states.get(i).stateID + " e-trans to: ");
                for (int k = 0; k < states.get(i).NFA_States.size(); k++) {
                    System.out.print(states.get(i).NFA_States.get(k) + "\t");
                }
            }
        }
    }

    /**
     * combines e-transition states
     */
    public void the_Converter() {
        //System.out.println("\n" + "Locating epsilon transitions: ");
        the_Converter_Helper(starting_State_Location, starting_State_Location, "e", 0);
    }

    /**
     * extends the_Converter
     *
     * @param connected_State_Location
     * @param tracker
     * @param search_Value
     * @param input_Type
     */
    public void the_Converter_Helper(int connected_State_Location, int tracker, String search_Value, int input_Type) {

        if (states.get(tracker).NFA_e_Trans_Done == true) {
            return;
        }
        // next_State is the epsilon connected state, and current_State_Connection is the location of the current state
        String next_State = states.get(connected_State_Location).find_Connection_DFA(search_Value);

        // if an search_Value is found the following loop will run
        if (!(next_State.equals("false"))) {

            if (input_Type == 0) {
                states.get(tracker).add_NFA_StateIds(next_State);
                connected_State_Location = find_An_Existing_State(next_State, states);

                //recurssion
                the_Converter_Helper(connected_State_Location, tracker, search_Value, input_Type);
            }
        }

        //if the search_Value is not found, every state has not yet been search the following loop will run
        if (tracker + 1 < states.size()) {

            states.get(tracker).NFA_e_Trans_Done = true;
            //tracker is the location of the "overall" state we are currently working on, 
            //and we are working this linearly through the states list starting at 1 (starting state location)
            tracker++;
            //set current_State_Location to tracker value
            connected_State_Location = tracker;

            //recurssion
            the_Converter_Helper(connected_State_Location, tracker, search_Value, input_Type);

        }

    }

    /**
     * prints NFA_State_Nodes, states and there connections
     */
    public void print_NFA_Nodes() {
        System.out.println("\n" + "Printing all NFA to DFA states and connections now: ");
        for (int i = 0; i < NFA_State_Nodes.size(); i++) {
            System.out.println("\n" + "Current state: " + NFA_State_Nodes.get(i).return_ID() + " and its connections are: " + "\t");
            for (int t = 0; t < NFA_State_Nodes.get(i).stateConections.size(); t++) {
                String temp = NFA_State_Nodes.get(i).stateConections.get(t);
                System.out.print(temp.charAt(0) + " connected to: " + temp.substring(1) + "\n");
            }
        }
    }

    /**
     * adds all connections to combined e-transition states
     */
    public void build_NFA_States() {
        int location;
        for (int i = 0; i < states.size(); i++) {
            for (int t = 0; t < states.get(i).NFA_States.size(); t++) {
                states.get(i).stateID = states.get(i).stateID + "," + states.get(i).NFA_States.get(t);
                location = find_An_Existing_State(states.get(i).NFA_States.get(t), states);

                for (int k = 0; k < states.get(location).stateConections.size(); k++) {
                    String element = states.get(location).stateConections.get(k);
                    String connection = element;
                    element = element.substring(0, 1);
                    connection = connection.substring(1);
                    states.get(i).add_Connection(element, connection);
                }
                // states.remove(location);
            }
        }
        remove_Connection_From_All("e");
        sort_Connections(states);
        Matching_Connections_For_All(states);
    }

    /**
     * removes connections from all states
     *
     * @param element
     */
    public void remove_Connection_From_All(String element) {
        for (int j = 0; j < states.size(); j++) {
            remove_Connection_From_One(j, element);
        }
    }

    /**
     * removes connections from one state
     *
     * @param state
     * @param element
     */
    public void remove_Connection_From_One(int state, String element) {
        for (int k = 0; k < states.get(state).stateConections.size(); k++) {
            String temp = states.get(state).stateConections.get(k);
            temp = temp.substring(0, 1);
            if (temp.equals(element)) {
                states.get(state).stateConections.remove(k);
                remove_Connection_From_One(state, element);
            }
        }
    }

    /**
     * sorts all states by stateId
     *
     * @param node
     */
    public void sort_States(ArrayList<StateNodes> node) {
        ArrayList<String> connections = new ArrayList<String>();
        String state;
        int location;
        for (int i = 1; i < node.size(); i++) {
            connections.add(node.get(i).stateID);
        }
        Collections.sort(connections);
        connections.add(0, "{}");
        for (int i = 0; i < connections.size(); i++) {
            int locate = find_An_Existing_State(connections.get(i), node);
            diagram_Sort.add(new StateNodes(connections.get(i)));
            for (int t = 0; t < node.get(locate).stateConections.size(); t++) {
                String temp = node.get(locate).stateConections.get(t);
                diagram_Sort.get(i).add_Connection(temp.substring(0, 1), temp.substring(1));
            }
        }

        node.clear();

        for (int i = 0; i < diagram_Sort.size(); i++) {
            node.add(new StateNodes(diagram_Sort.get(i).stateID));
            for (int t = 0; t < diagram_Sort.get(i).stateConections.size(); t++) {
                String temp = diagram_Sort.get(i).stateConections.get(t);
                node.get(i).add_Connection(temp.substring(0, 1), temp.substring(1));
            }
        }

        diagram_Sort.clear();
        sort_Connections(node);
    }

    /**
     * sorts all state connections
     *
     * @param node
     */
    public void sort_Connections(ArrayList<StateNodes> node) {

        for (int j = 0; j < node.size(); j++) {
            Collections.sort(node.get(j).stateConections);
        }

        for (int j = 0; j < node.size(); j++) {
            Collections.sort(node.get(j).NFA_States);
        }
        for (int j = 0; j < node.size(); j++) {
            Collections.sort(node.get(j).NFA_States_Holder);
        }
    }

    /**
     * combines matching connections for all states
     *
     * @param nodes
     */
    public void Matching_Connections_For_All(ArrayList<StateNodes> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            for (int k = 0; k < nodes.get(i).stateConections.size(); k++) {

                if (k != 0) {

                    String last_State = nodes.get(i).stateConections.get(k - 1);
                    last_State = last_State.substring(0, 1);


                    String current_State = nodes.get(i).stateConections.get(k);
                    current_State = current_State.substring(0, 1);

                    if (current_State.equals(last_State)) {

                        current_State = nodes.get(i).stateConections.get(k);
                        last_State = nodes.get(i).stateConections.get(k - 1);
                        current_State = current_State.substring(1);
                        if (!(last_State.substring(1).equals(current_State))) {

                            last_State = last_State + "," + current_State;

                            nodes.get(i).stateConections.add(k - 1, last_State);
                            nodes.get(i).stateConections.remove(k);
                            nodes.get(i).stateConections.remove(k);
                        } else {
                            //WARNING::::::::::::::::::::::::::this maybe be k-1 or k+1
                            nodes.get(i).stateConections.remove(k);
                        }
                        k = k - 1;
                    }
                }
            }
        }
    }

    /**
     * builds NFA from e-transition combined states
     */
    public void build_NFA() {
        add_NFA_State(starting_State_Location);
        build_NFA_Helper(NFA_State_Nodes.size());
        set_Final_NFA_States();
    }

    public void build_NFA_Helper(int location) {
        do {
            NFA_New_States();
            only_Linear();
            location++;
        } while (location <= NFA_State_Nodes.size());
    }

    /**
     * extends build_NFA_Helper
     */
    public void only_Linear() {
        while (racer < NFA_State_Nodes.size()) {
            NFA_Combine_States(racer);
            racer++;
        }
        sort_Connections(NFA_State_Nodes);
        Matching_Connections_For_All(NFA_State_Nodes);
        //remove_Repeating_States(NFA_State_Nodes);
    }

    /**
     * removes any repeating stateID's
     *
     * @param nodes
     */
    public void remove_Repeating_States(ArrayList<StateNodes> nodes) {
        for (int i = 1; i < NFA_State_Nodes.size(); i++) {
            String last_State = nodes.get(i).stateID;
            String current_State = nodes.get(i - 1).stateID;
            if (current_State.equals(last_State)) {
                nodes.remove(i);
            }
        }
    }

    /**
     * extends build_NFA_Helper
     *
     * @param h
     */
    public void NFA_Combine_States(int h) {
        if (NFA_State_Nodes.get(h).NFA_States_Holder.isEmpty()) {
            int location = find_An_Existing_State(NFA_State_Nodes.get(h).stateID, states);
            for (int i = 0; i < states.get(location).stateConections.size(); i++) {
                String temp = states.get(location).stateConections.get(i);
                NFA_State_Nodes.get(h).add_Connection(temp.substring(0, 1), temp.substring(1));
            }
        }
        //-------------------------------------------------------------------------------------------------------------------------------
        for (int z = 0; z < NFA_State_Nodes.get(h).NFA_States_Holder.size(); z++) {
            String value = NFA_State_Nodes.get(h).NFA_States_Holder.get(z);
            int location = find_An_Existing_State(value, states);
            for (int i = 0; i < states.get(location).stateConections.size(); i++) {
                String temp = states.get(location).stateConections.get(i);
                NFA_State_Nodes.get(h).add_Connection(temp.substring(0, 1), temp.substring(1));
            }
            for (int t = 0; t < states.get(location).NFA_States.size(); t++) {
                NFA_State_Nodes.get(h).NFA_States_Holder.add(states.get(location).NFA_States.get(t));
            }

        }
        change_NFA_State_Id(h);
        String stateId = NFA_State_Nodes.get(h).stateID;
        if (NFA_State_Nodes.get(h).NFA_States_Holder.isEmpty()) {
            int location = find_An_Existing_State(NFA_State_Nodes.get(h).stateID, states);
            stateId = states.get(location).stateID;
        }
        for (int j = 0; j < NFA_State_Nodes.get(h).NFA_States_Holder.size(); j++) {
            if (j == 0) {
                return;
            }
            stateId = stateId + "," + NFA_State_Nodes.get(h).NFA_States_Holder.get(j);
        }
        NFA_State_Nodes.get(h).stateID = stateId;
    }

    /**
     * extends build_NFA_Helper
     *
     * @param h
     */
    public void change_NFA_State_Id(int h) {
        Collections.sort(NFA_State_Nodes.get(h).NFA_States_Holder);
        for (int i = 1; i < NFA_State_Nodes.get(h).NFA_States_Holder.size(); i++) {
            if (NFA_State_Nodes.get(h).NFA_States_Holder.get(i - 1).equals(NFA_State_Nodes.get(h).NFA_States_Holder.get(i))) {
                NFA_State_Nodes.get(h).NFA_States_Holder.remove(i);
                i = i - 1;
            }
        }
        String temp = "";
        if (NFA_State_Nodes.get(h).NFA_States_Holder.isEmpty()) {
            temp = NFA_State_Nodes.get(h).stateID;
        }
        //System.out.println("the node is: "+NFA_State_Nodes.get(location).stateID+ " and: "+ temp);
        for (int t = 0; t < NFA_State_Nodes.get(h).NFA_States_Holder.size(); t++) {
            if (t == 0) {
                temp = temp + NFA_State_Nodes.get(h).NFA_States_Holder.get(t);

            } else {
                temp = temp + "," + NFA_State_Nodes.get(h).NFA_States_Holder.get(t);
                //System.out.println(NFA_State_Nodes.get(location).NFA_States_Holder.get(t)+" and: "+temp);
            }
        }
        //System.out.println("+++++++++++++++++: "+temp);
        NFA_State_Nodes.get(h).stateID = temp;
    }

    /**
     * extends build_NFA_Helper
     */
    public void NFA_New_States() {
        the_Tracker++;
        for (int i = 0; i < NFA_State_Nodes.get(the_Tracker).stateConections.size(); i++) {
            String connection = NFA_State_Nodes.get(the_Tracker).stateConections.get(i);
            connection = connection.substring(1);
            int found = find_An_Existing_State_NFA(connection, NFA_State_Nodes);
            if (found == -1) {
                NFA_State_Nodes.add(new StateNodes(connection));
                NFA_States_Holder(connection, NFA_State_Nodes.size() - 1);
            }
        }
    }

    /**
     * extends build_NFA_Helper
     *
     * @param connection
     * @param location
     */
    public void NFA_States_Holder(String connection, int location) {
        if (!(connection.contains(","))) {
            return;
        }
        String temp;
        while (connection.contains(",")) {
            temp = connection.substring(0, connection.indexOf(","));
            connection = connection.substring(connection.indexOf(",") + 1);
            NFA_State_Nodes.get(location).NFA_States_Holder.add(temp);
        }
        NFA_State_Nodes.get(location).NFA_States_Holder.add(connection);
    }

    /**
     * prints NFA_State_Nodes, states and there connections
     */
    public void print_NFA_States_Holder() {
        for (int t = 0; t < NFA_State_Nodes.size(); t++) {
            System.out.println("The current state is: " + NFA_State_Nodes.get(t).stateID);
            for (int i = 0; i < NFA_State_Nodes.get(t).NFA_States_Holder.size(); i++) {
                System.out.println(NFA_State_Nodes.get(t).NFA_States_Holder.get(i));
            }
        }
    }

    /**
     * extends build_NFA_Helper
     *
     * @param location
     */
    public void add_NFA_State(int location) {
        String stateId = states.get(location).stateID;
        NFA_State_Nodes.add(new StateNodes(stateId));
        for (int i = 0; i < states.get(location).stateConections.size(); i++) {
            String element = states.get(location).stateConections.get(i);
            String connection = element.substring(1);
            element = element.substring(0, 1);
            NFA_State_Nodes.get(NFA_State_Nodes.size() - 1).add_Connection(element, connection);
        }
    }

    /**
     * sets final states for NFA_State_Nodes
     */
    public void set_Final_NFA_States() {
        for (int i = 0; i < NFA_State_Nodes.size(); i++) {
            String temp = NFA_State_Nodes.get(i).stateID;
            for (int t = 0; t < final_States.size(); t++) {
                if (temp.contains(final_States.get(t))) {
                    NFA_State_Nodes.get(i).set_As_A_Final_State();
                }
            }
        }
    }

    /**
     * double checks states for multiple element connections for one state. 
     * If multiple connections are found then this is an NFA
     */
    public void double_Check_For_NFA() {
        for (int i = 0; i < states.size(); i++) {
            for (int k = 0; k < states.get(i).stateConections.size(); k++) {
                if (k != 0) {
                    String last_Element = states.get(i).stateConections.get(k - 1);
                    last_Element = last_Element.substring(0, 1);
                    String current_Element = states.get(i).stateConections.get(k);
                    current_Element = current_Element.substring(0, 1);

                    if (current_Element.equals(last_Element)) {
                        is_This_An_NFA = true;
                    }
                }
            }
        }
    }
}