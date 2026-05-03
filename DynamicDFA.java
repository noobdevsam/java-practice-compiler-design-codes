import java.util.HashMap;
import java.util.Map;
import java.util.Set;

void main() {
    IO.print("Hello, World!");
}

static class DynamicDFA {

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
