void main() {

    // Example: NFA that accepts strings containing "10" OR "11"
    var acceptStates = new HashSet<>(List.of("q_accept"));
    var automaton = new HybridAutomaton("q0", acceptStates);

    // Transitions
    automaton.addTransition("q0", '1', "q1"); // Start of "10" or "11"
    automaton.addTransition("q0", '0', "q0"); // Stay at start
    automaton.addTransition("q0", '1', "q0"); // Stay at start (NFA branching)

    automaton.addTransition("q1", '0', "q_accept");
    automaton.addTransition("q1", '1', "q_accept");

    // Final state loops
    automaton.addTransition("q_accept", '0', "q_accept");
    automaton.addTransition("q_accept", '1', "q_accept");

    String test = "0010";
    IO.println("Input '" + test + "' accepted: " + automaton.process(test));

}

private class HybridAutomaton {
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
                .computeIfAbsent(from, _ -> new HashMap<>())
                .computeIfAbsent(symbol, _ -> new HashSet<>())
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