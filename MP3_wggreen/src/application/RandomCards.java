package application;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RandomCards extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane pane = new BorderPane();

		HBox buttons = new HBox();

		buttons.setAlignment(Pos.BOTTOM_CENTER);

		buttons.setSpacing(10);

		Button refresh = new Button("Refresh");

		HBox cards = new HBox(10);
		
		for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(new Image("back.png"));
            imageView.setFitWidth(250);
            imageView.setFitHeight(250);
            cards.getChildren().add(imageView);
        }

		refresh.setOnAction(e -> {
			cards.getChildren().clear();
			int[] selectedCards = new int[4];
			int count = 0;

			while (count < 4) {
				Random random = new Random();

				int card = random.nextInt(52) + 1;
				boolean isDuplicate = false;
				for (int i = 0; i < count; i++) {
					if (selectedCards[i] == card) {
						isDuplicate = true;
						break;
					}
				}

				if (!isDuplicate) {
					selectedCards[count] = card;
					count++;
					ImageView imgView = new ImageView(new Image((++card) + ".png"));
					imgView.setFitWidth(250);
					imgView.setFitHeight(250);
					cards.getChildren().add(imgView);

				}
			}
		});

		buttons.getChildren().add(refresh);

		pane.setTop(buttons);
		
        pane.setBottom(cards);

		BorderPane.setMargin(buttons, new Insets(10, 10, 10, 10));

		pane.setPadding(new Insets(10, 10, 0, 10));

		pane.autosize();

//		pane.getChildren().add(cards);

		Scene scene = new Scene(pane, 250, 250);

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

}
