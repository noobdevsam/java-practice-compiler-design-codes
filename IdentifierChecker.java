void main() {
    var initial_variable = IO.readln("Input initial value: ");
    var regex = "^[a-zA-Z_$][a-zA-Z\\d_$]*$";

    boolean isValid = Pattern.matches(regex, initial_variable);

    IO.println("Is the provided variable name valid ? : " + isValid);
}

