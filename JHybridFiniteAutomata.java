void main() {

}

class HybridAutomaton {
    private final String initialState;
    private final Set<String> acceptStates;

    // Map: FromState -> (InputSymbol -> Set of  ToStates)
    private final Map<String, Map<Character, Set<String>>> transitions;

    HybridAutomaton(String initialState, Set<String> acceptStates) {
        this.initialState = initialState;
        this.acceptStates = acceptStates;
        this.transitions = new HashMap<>();
    }

    /**
     * Adds a transition. For a DFA, call this once per symbol.
     * For an NFA, call it multiple times for the same symbol to create branches.
     */
    void addTransition(String from, char symbol, String to) {
        transitions
                .computeIfAbsent(from, k -> new HashMap<>())
                .computeIfAbsent(symbol, k -> new HashSet<>())
                .add(to);
    }

    boolean process(String input) {
        var currentStates = new HashSet<String>();
        currentStates.add(initialState);

        // Enhanced for-each to iterate through input characters
        for (char symbol : input.toCharArray()) {
            var nextStates = new HashSet<String>();

            // Enhanced for-each to check every currently active branch
            for (String state : currentStates) {
                Map<Character, Set<String>> stateTransitions = transitions.get(state);

                if (stateTransitions != null && stateTransitions.containsKey(symbol)) {
                    // Add all possible next states for this symbol
                    nextStates.addAll(stateTransitions.get(symbol));
                }
            }

            if (nextStates.isEmpty()) return false; // Optimization: early exit if all paths die
            currentStates = nextStates;
        }

        // Check if at least one path landed in an accept state
        for (String state : currentStates) {
            if (acceptStates.contains(state)) {
                return true;
            }
        }
        return false;
    }
}