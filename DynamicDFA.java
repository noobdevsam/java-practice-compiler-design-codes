import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

void main() {
    // Example: A DFA that accepts strings ending in '1' (Alphabet: {0, 1})
    var acceptStates = new HashSet<String>(List.of("q1"));
    var dfa = new DynamicDFA("q0", acceptStates);

    // Define transitions
    dfa.addTransition("q0", '0', "q0"); // From q0, on '0', stay in q0
    dfa.addTransition("q0", '1', "q1"); // From q0, on '1', go to q1
    dfa.addTransition("q1", '0', "q0"); // From q1, on '0', go back to q0
    dfa.addTransition("q1", '1', "q1"); // From q1, on '1', stay in q1

    // Test the DFA with some input strings
    var testStrings = List.of("101", "1100", "000", "111", "0101");
    for (var testString : testStrings) {
        boolean result = dfa.process(testString);
        System.out.println("Input: " + testString + " -> Accepted: " + result);
    }
}

class DynamicDFA {

    private String currentState;
    private final String startState;
    private final Set<String> acceptStates;

    private final Map<String, Map<Character, String>> transitions;

    public DynamicDFA(String startState, Set<String> acceptStates) {
        this.startState = startState;
        this.currentState = startState;
        this.acceptStates = acceptStates;
        this.transitions = new HashMap<>();
    }

    public void addTransition(String fromState, char symbol, String toState) {
        transitions.computeIfAbsent(fromState, key -> new HashMap<>())
                   .put(symbol, toState);
    }

    public boolean process(String input) {
        currentState = startState; // Reset for new input

        for (
            char symbol : input.toCharArray()
        ) {
            var stateTransitions = transitions.get(currentState); // Get transitions for the current state

            if (stateTransitions == null || !stateTransitions.containsKey(symbol)) { // No transition defined for this symbol from the current state
                return false; // Reaches a "dead" state or undefined transition
            }

            currentState = stateTransitions.get(symbol); // Move to the next state
        }

        return acceptStates.contains(currentState); // Check if in an accept state
    }
    
}
