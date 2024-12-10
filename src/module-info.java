module galactic_commanders {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires com.google.gson; // Correct Gson module name

    opens application to javafx.graphics, javafx.fxml, com.google.gson; // Open to Gson for reflection
    exports application; // Export application package if needed elsewhere
}