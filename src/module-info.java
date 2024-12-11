module galactic_commanders
{
	requires javafx.controls;
	requires javafx.graphics;
	requires java.desktop;
	requires org.json;

	opens application to javafx.graphics, javafx.fxml;
}
