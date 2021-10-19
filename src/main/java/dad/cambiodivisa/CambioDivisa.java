package dad.cambiodivisa;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CambioDivisa extends Application {

	// VISTAS
	private TextField texto1;
	private TextField texto2;
	private ComboBox<Divisa> combo1;
	private ComboBox<Divisa> combo2;
	private Button cambiar;

	// MODELO
	private ObjectProperty<Divisa> divisa1 = new SimpleObjectProperty<>();
	private ObjectProperty<Divisa> divisa2 = new SimpleObjectProperty<>();
	private DoubleProperty cash1 = new SimpleDoubleProperty();
	private DoubleProperty cash2 = new SimpleDoubleProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Creamos cuadro de texto superior
		texto1 = new TextField();
		texto1.setPrefColumnCount(5);
		texto1.setPromptText("0");

		// Creamos cuadro de texto inferior
		texto2 = new TextField();
		texto2.setPrefColumnCount(5);
		texto2.setPromptText("0");
		texto2.setEditable(false);

		// Creamos lista desplegable (comboBox) superior
		combo1 = new ComboBox<>();
		combo1.getItems().addAll(new Divisa("Euro", 1.0), new Divisa("Dolar", 1.2007), new Divisa("Libra", 0.8873),
				new Divisa("Yen", 133.59));
		combo1.getSelectionModel().selectFirst();

		// Creamos lista desplegable (comboBox) inferior
		combo2 = new ComboBox<>();
		combo2.getItems().addAll(new Divisa("Euro", 1.0), new Divisa("Dolar", 1.2007), new Divisa("Libra", 0.8873),
				new Divisa("Yen", 133.59));
		combo2.getSelectionModel().selectLast();

		// Creamos botÃ³n para realizar el cambio de divisa
		cambiar = new Button("Cambiar");
		cambiar.setOnAction(e -> onCambiarDivisa(e));

		// Creamos panel horizontal 1
		HBox hbox1 = new HBox();
		hbox1.setPadding(new Insets(5));
		hbox1.setSpacing(5);
		hbox1.getChildren().addAll(texto1, combo1);

		// Creamos panel horizontal 2
		HBox hbox2 = new HBox();
		hbox2.setPadding(new Insets(5));
		hbox2.setSpacing(5);
		hbox2.getChildren().addAll(texto2, combo2);

		// Creamos panel vertical con los 2 HBox
		VBox root = new VBox();
		root.setPadding(new Insets(5));
		root.setSpacing(5);
		root.getChildren().addAll(hbox1, hbox2, cambiar);
		root.setFillWidth(false);
		root.setAlignment(Pos.CENTER);

		// Scena
		Scene scene = new Scene(root, 320, 200);

		// Ventana
		primaryStage.setTitle("CambioDivisa");
		primaryStage.setScene(scene);
		primaryStage.show();

		// BINDEOS
		divisa1.bind(combo1.getSelectionModel().selectedItemProperty());
		divisa2.bind(combo2.getSelectionModel().selectedItemProperty());

		texto2.textProperty().bind(cash2.asString());
	}

	private void onCambiarDivisa(ActionEvent e) {
		String cadena = texto1.getText();
		Double num;
		Divisa d1 = divisa1.getValue();
		Divisa d2 = divisa2.getValue();

		try {
			num = Double.parseDouble(cadena);
			cash1.setValue(num);
			cash2.setValue(Divisa.fromTo(d1, d2, cash1.get()));

		} catch (Exception e2) {
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("CambioDivisa");
			alertError.setHeaderText("ERROR");
			alertError.setContentText(
					"Ha introducido letras, carÃ¡cteres invÃ¡lidos o ha dejado el campo de texto vacÃ­o.");
			alertError.showAndWait();

		}
	}

	public static void main(String[] args) {
		launch(args);

	}

}
