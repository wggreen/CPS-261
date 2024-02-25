package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GUIConverter extends Application {

	TextField textField1 = new TextField();
	TextField textField2 = new TextField();

	RadioButton rb1 = new RadioButton("Celsius and Fahrenheit");
	RadioButton rb2 = new RadioButton("Miles and Kilometers");
	RadioButton rb3 = new RadioButton("Pounds and Kilograms");
	ToggleGroup group = new ToggleGroup();

	Label label1 = new Label("Celsius");
	Label label2 = new Label("Fahrenheit");

	@Override
	public void start(Stage primaryStage) throws Exception {

		GridPane gridPane = new GridPane();

		rb1.setToggleGroup(group);
		rb1.setSelected(true);
		rb2.setToggleGroup(group);
		rb3.setToggleGroup(group);

		Scene scene = new Scene(gridPane, 250, 250);

		HBox labelBox1 = new HBox(label1);
		HBox labelBox2 = new HBox(label2);

		gridPane.add(labelBox1, 0, 0);
		gridPane.add(textField1, 1, 0);
		gridPane.add(labelBox2, 0, 1);
		gridPane.add(textField2, 1, 1);
		gridPane.add(rb1, 0, 2);
		gridPane.add(rb2, 0, 3);
		gridPane.add(rb3, 0, 4);

		group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			RadioButton selectedRadioButton = (RadioButton) newValue;
			if (selectedRadioButton == rb1) {
				label1.setText("Celsius");
				label2.setText("Fahrenheit");
				textField1.clear();
                textField2.clear();
			} else if (selectedRadioButton == rb2) {
				label1.setText("Miles");
				label2.setText("Kilometers");
				textField1.clear();
                textField2.clear();
			} else if (selectedRadioButton == rb3) {
				label1.setText("Pounds");
				label2.setText("Kilograms");
				textField1.clear();
                textField2.clear();
			}
		});

		textField1.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
				if (selectedRadioButton == rb1) {
					double celsius = Double.parseDouble(textField1.getText());
					double fahrenheit = (celsius * 9 / 5) + 32;
					textField2.setText(String.format("%.2f", fahrenheit));
				} else if (selectedRadioButton == rb2) {
					double miles = Double.parseDouble(textField1.getText());
					double kilometers = miles * 1.60934;
					textField2.setText(String.format("%.2f", kilometers));
				} else if (selectedRadioButton == rb3) {
					double pound = Double.parseDouble(textField1.getText());
					double kilogram = pound * 0.45359237;
					textField2.setText(String.format("%.2f", kilogram));
				}
			}
		});

		textField2.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
				if (selectedRadioButton == rb1) {
		            double fahrenheit = Double.parseDouble(textField2.getText());
		            double celsius = (fahrenheit - 32) * 5 / 9;
		            textField1.setText(String.format("%.2f", celsius));
		        } else if (selectedRadioButton == rb2) {
		            double kilometers = Double.parseDouble(textField2.getText());
		            double miles = kilometers / 1.60934;
		            textField1.setText(String.format("%.2f", miles));
		        } else if (selectedRadioButton == rb3) {
		            double kilograms = Double.parseDouble(textField2.getText());
		            double pounds = kilograms / 0.45359237;
		            textField1.setText(String.format("%.2f", pounds));
		        }
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

}
