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

    
    
}
