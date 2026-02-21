void main() {

}

class HybridAutomaton {
    private final String initialState;
    private final Set<String> acceptStates;

    // Map: FromState -> (InputSymbol -> Set of  ToStates)
    private final Map<String, Map<Character, Set<String>>> transitions;

    public HybridAutomaton(String initialState, Set<String> acceptStates) {
        this.initialState = initialState;
        this.acceptStates = acceptStates;
        this.transitions = new HashMap<>();
    }
}