void main() {
    var filePath = "HelloWorld.java";

    try {
        var code = new String(Files.readAllBytes(Paths.get(filePath)));
        var cleanCode = removeComments(code);

        IO.println("Code without comments:\n" + cleanCode);
    } catch (IOException e) {
        IO.println("Error reading file: " + e.getMessage());
    }
}

String removeComments(String code) {
    var regex = "(?s)//.*?$|/\\*.*?\\*/"; // Matches both single-line and multi-line comments
    var pattern = Pattern.compile(regex, Pattern.MULTILINE);
    var matcher = pattern.matcher(code);
    return matcher.replaceAll(""); // Remove all comments
}
