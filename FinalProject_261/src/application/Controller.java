package application;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller {

	@FXML
	private Button btnHistory;

	@FXML
	private Button btnNewGame;

	@FXML
	private HBox hBox;

	@FXML
	private Button btnHold;

	@FXML
	private Button btnMainMenu2;

	@FXML
	private Button btnMainMenu;

	@FXML
	private Button btnRoll;

	@FXML
	private BorderPane borderPane;

	@FXML
	private Button btnPlayGame;

	@FXML
	private RadioButton radioOnePlayer;

	@FXML
	private RadioButton radioTwoPlayer;

	@FXML
	private TextField txtPlayerOneName;

	@FXML
	private TextField txtPlayerTwoName;

	@FXML
	private Button btnSetup;

	@FXML
	private ToggleGroup group;

	@FXML
	private Text txtErrors;

	@FXML
	private Label lblPlayerOne;

	@FXML
	private Label lblPlayerTwo;

	@FXML
	private TextField playerOneTotalPoints;

	@FXML
	private TextField playerOneTurnPoints;

	@FXML
	private TextField playerTwoTotalPoints;

	@FXML
	private TextField playerTwoTurnPoints;

	@FXML
	private ImageView imgDice;

	@FXML
	private AnchorPane playerOnePane;

	@FXML
	private AnchorPane playerTwoPane;

	@FXML
	private TextField txtGameOver;

	@FXML
	private Button btnPlayAgain;

	@FXML
	private TableView<Game> tblHistory;

	@FXML
	private TableColumn<Game, LocalDate> columnDate;

	@FXML
	private TableColumn<Game, String> columnName;

	@FXML
	private TableColumn<Game, String> columnResult;

	@FXML
	private TableColumn<Game, Integer> columnScore;

	@FXML
	private TextArea txtWins;

	private ToggleGroup toggleGroup;

	private static String playerOneName;

	private static String playerTwoName;

	private static int numberOfPlayers = 0;

	private static ArrayList<Player> players = new ArrayList<>();

	static {
		Player bot = new Player("bot");
		players.add(bot);
	}

	private static ArrayList<Game> games;

	private static Game currentGame;

	private List<String> imageFilenames;

	private Player playerOne;

	private Player playerTwo;

	private ObservableList<Game> gamesData;

	@FXML
	void btnHoldClicked(ActionEvent event) {
		if (currentGame.turnPlayerNumber == 1) {
			currentGame.playerOneScore += currentGame.playerOneTurnScore;
			if (currentGame.playerOneScore >= 100) {
				finishGame();
			} else {
				currentGame.turnPlayerNumber = 2;

				currentGame.playerOneTurnScore = 0;

				playerOneTotalPoints.setText(Integer.toString(currentGame.playerOneScore));

				playerOneTurnPoints.setText("0");

				Color greenColor = Color.rgb(0, 255, 0, 0.5);

				BackgroundFill backgroundFill = new BackgroundFill(greenColor, null, null);
				Background background = new Background(backgroundFill);

				playerOnePane.setBackground(null);
				playerTwoPane.setBackground(background);

				if (numberOfPlayers == 1) {
					btnHold.setVisible(false);
					btnRoll.setVisible(false);
					botTurn();
					btnHold.setVisible(true);
					btnRoll.setVisible(true);
				}
			}
		} else {
			currentGame.playerTwoScore += currentGame.playerTwoTurnScore;
			if (currentGame.playerTwoScore >= 100) {
				finishGame();
			} else {
				currentGame.turnPlayerNumber = 1;

				currentGame.playerTwoTurnScore = 0;

				playerTwoTotalPoints.setText(Integer.toString(currentGame.playerTwoScore));

				playerTwoTurnPoints.setText("0");

				Color greenColor = Color.rgb(0, 255, 0, 0.5);

				BackgroundFill backgroundFill = new BackgroundFill(greenColor, null, null);
				Background background = new Background(backgroundFill);

				playerTwoPane.setBackground(null);
				playerOnePane.setBackground(background);
			}
		}
	}

	@FXML
	void btnRollClicked(ActionEvent event) {
		animateDice();
		rollDice();
	}

	@FXML
	void radioOnePlayerClicked(ActionEvent event) {
		numberOfPlayers = 1;
	}

	@FXML
	void radioTwoPlayerClicked(ActionEvent event) {
		numberOfPlayers = 2;
	}

	@FXML
	void btnHistoryClicked(ActionEvent event) {
		loadAndSetFXML("history.fxml", event);
	}

	@FXML
	void btnMainMenuClicked(ActionEvent event) {
		loadAndSetFXML("main_menu.fxml", event);

	}

	@FXML
	void btnSetupClicked(ActionEvent event) {
		loadAndSetFXML("setup.fxml", event);
	}

	@FXML
	void btnPlayGameClicked(ActionEvent event) {
		if (numberOfPlayers == 0) {
			txtErrors.setText("Please select the number of players");
		} else if (numberOfPlayers == 1 && txtPlayerOneName.getText().trim().isEmpty()) {
			txtErrors.setText("Please enter a name for Player One");
			txtPlayerOneName.getStyleClass().add("text-field-required");
			if ((txtPlayerTwoName.getStyleClass().contains("text-field-required"))) {
				while (txtPlayerTwoName.getStyleClass().contains("text-field-required")) {
					txtPlayerTwoName.getStyleClass().remove("text-field-required");
				}
			}
		} else if (numberOfPlayers == 2) {
			if (txtPlayerOneName.getText().trim().isEmpty() && !txtPlayerTwoName.getText().trim().isEmpty()) {
				txtErrors.setText("Please enter a name for Player One");
				txtPlayerOneName.getStyleClass().add("text-field-required");
				if ((txtPlayerTwoName.getStyleClass().contains("text-field-required"))) {
					while (txtPlayerTwoName.getStyleClass().contains("text-field-required")) {
						txtPlayerTwoName.getStyleClass().remove("text-field-required");
					}
				}
			} else if (!txtPlayerOneName.getText().trim().isEmpty() && txtPlayerTwoName.getText().trim().isEmpty()) {
				txtErrors.setText("Please enter a name for Player Two");
				txtPlayerTwoName.getStyleClass().add("text-field-required");
				if ((txtPlayerOneName.getStyleClass().contains("text-field-required"))) {
					while (txtPlayerOneName.getStyleClass().contains("text-field-required")) {
						txtPlayerOneName.getStyleClass().remove("text-field-required");
					}
				}
			} else if (txtPlayerOneName.getText().trim().isEmpty() && txtPlayerTwoName.getText().trim().isEmpty()) {
				txtErrors.setText("Please enter a name for both players");
				txtPlayerOneName.getStyleClass().add("text-field-required");
				txtPlayerTwoName.getStyleClass().add("text-field-required");
			} else if (!txtPlayerOneName.getText().trim().isEmpty() && !txtPlayerTwoName.getText().trim().isEmpty()) {
				setPlayerNames();
				loadAndSetFXML("game2.fxml", event);
			}
		} else {
			setPlayerNames();
			loadAndSetFXML("game2.fxml", event);
		}
	}

	@FXML
	void btnMainMenu2Clicked(ActionEvent event) {
		loadAndSetFXML("main_menu.fxml", event);
	}

	@FXML
	void btnPlayAgainClicked(ActionEvent event) {
		currentGame = new Game();

		setPoints();

		btnRoll.setDisable(false);
		btnHold.setDisable(false);

		Color greenColor = Color.rgb(0, 255, 0, 0.5);

		BackgroundFill backgroundFill = new BackgroundFill(greenColor, null, null);
		Background background = new Background(backgroundFill);

		playerOnePane.setBackground(background);
		playerTwoPane.setBackground(null);

		btnPlayAgain.setVisible(false);

		txtGameOver.setVisible(false);

		imgDice.setImage(null);

	}

	public void finishGame() {
		if (currentGame.playerOneScore > currentGame.playerTwoScore) {
			currentGame.winner = playerOne;
			currentGame.winnerScore = currentGame.playerOneScore;

			currentGame.loser = playerTwo;
			currentGame.loserScore = currentGame.playerTwoScore;
		} else {
			currentGame.winner = playerTwo;
			currentGame.winnerScore = currentGame.playerTwoScore;

			currentGame.loser = playerOne;
			currentGame.loserScore = currentGame.playerOneScore;
		}

		currentGame.date = LocalDate.now();

		txtGameOver.setVisible(true);

		txtGameOver.setText("Game Over. Play Again?");

		if (btnPlayAgain != null) {
			btnPlayAgain.setVisible(true);
		}

		btnRoll.setDisable(true);
		btnHold.setDisable(true);

		games.add(currentGame);
	}

	public void animateDice() {
		imageFilenames = new ArrayList<>(List.of("1.png", "2.png", "3.png", "4.png", "5.png", "6.png"));

		Collections.shuffle(imageFilenames);

		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);

		List<KeyFrame> keyFrames = new ArrayList<>();

		Duration rollDuration = Duration.seconds(0.2);

		for (int i = 0; i < imageFilenames.size(); i++) {
			int finalIndex = i;
			KeyFrame keyFrame = new KeyFrame(rollDuration.multiply(i), event -> {
				String filename = imageFilenames.get(finalIndex);
				File file = new File("src/dice/" + filename);
				if (file.exists()) {
					Image image = new Image(file.toURI().toString());
					imgDice.setImage(image);
				} else {
					System.err.println("Image not found: " + file.getAbsolutePath());
				}
			});
			keyFrames.add(keyFrame);
		}

		timeline.getKeyFrames().addAll(keyFrames);

		timeline.play();

	}

	public void rollDice() {

		String lastFilename = imageFilenames.get(imageFilenames.size() - 1);

		int roll = Integer.parseInt(lastFilename.substring(0, lastFilename.indexOf(".")));

		if (currentGame.turnPlayerNumber == 1) {
			if (roll >= 2) {
				currentGame.playerOneTurnScore += roll;
				playerOneTurnPoints.setText(Integer.toString(currentGame.playerOneTurnScore));
			} else {
				currentGame.playerOneTurnScore = 0;

				playerOneTurnPoints.setText("0");

				currentGame.turnPlayerNumber = 2;

				Color greenColor = Color.rgb(0, 255, 0, 0.5);

				BackgroundFill backgroundFill = new BackgroundFill(greenColor, null, null);
				Background background = new Background(backgroundFill);

				playerOnePane.setBackground(null);
				playerTwoPane.setBackground(background);

				if (numberOfPlayers == 1) {
					botTurn();
				}
			}
		} else {
			if (roll >= 2) {
				currentGame.playerTwoTurnScore += roll;
				playerTwoTurnPoints.setText(Integer.toString(currentGame.playerTwoTurnScore));
				if (numberOfPlayers == 1) {
					botTurn();
				}
			} else {
				currentGame.playerTwoTurnScore = 0;

				playerTwoTurnPoints.setText("0");

				currentGame.turnPlayerNumber = 1;

				Color greenColor = Color.rgb(0, 255, 0, 0.5);

				BackgroundFill backgroundFill = new BackgroundFill(greenColor, null, null);
				Background background = new Background(backgroundFill);

				playerTwoPane.setBackground(null);
				playerOnePane.setBackground(background);
			}
		}
	}

	public void botTurn() {
		if (numberOfPlayers == 1) {
			ActionEvent event = new ActionEvent(null, null);

			Random random = new Random();
			int randomNumber = random.nextInt(2);

			if (randomNumber == 0) {
				animateDice();
				rollDice();
			} else {
				btnHoldClicked(event);

			}

		}
	}

	public void setPlayerNames() {
		if (numberOfPlayers == 1) {
			playerOneName = txtPlayerOneName.getText().trim().toLowerCase();
			playerTwoName = "bot";

		} else if (numberOfPlayers == 2) {
			playerOneName = txtPlayerOneName.getText().trim().toLowerCase();
			playerTwoName = txtPlayerTwoName.getText().trim().toLowerCase();
		}
	}

	public void setToggleGroup(ToggleGroup toggleGroup) {
		this.toggleGroup = group;
		radioOnePlayer.setToggleGroup(toggleGroup);
		radioTwoPlayer.setToggleGroup(toggleGroup);
	}

	public void setBackgroundForGame2(Image backgroundImage) {
		if (borderPane != null) {
			BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false,
					true, true);
			BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, null, backgroundSize);
			Background background = new Background(backgroundImg);
			borderPane.setBackground(background);
		}
	}

	public void addPlayers() {
		if (numberOfPlayers == 1) {
			if (players.size() >= 1) {
				boolean playerExists = false;
				for (Player player : players) {
					if (player.name.equalsIgnoreCase(playerOneName)) {
						playerExists = true;
						break;
					}
				}

				if (!playerExists) {
					Player newPlayer = new Player(playerOneName);
					players.add(newPlayer);
					playerOne = newPlayer;
					playerTwo = players.get(0);
				} else {
					playerOne = new Player(playerOneName);

				}
			}
		} else if (numberOfPlayers == 2) {
			boolean playerOneExists = false;
			boolean playerTwoExists = false;

			for (Player player : players) {
				if (player.name.equalsIgnoreCase(playerOneName)) {
					playerOneExists = true;
				} else if (player.name.equalsIgnoreCase(playerTwoName)) {
					playerTwoExists = true;
				}

				if (playerOneExists && playerTwoExists) {
					break;
				}
			}

			if (!playerOneExists) {
				Player newPlayerOne = new Player(playerOneName);
				players.add(newPlayerOne);
				playerOne = newPlayerOne;
			} else if (playerOneExists) {
				playerOne = new Player(playerOneName);

			}

			if (!playerTwoExists) {
				Player newPlayerTwo = new Player(playerTwoName);
				players.add(newPlayerTwo);
				playerTwo = newPlayerTwo;
			} else if (playerTwoExists) {
				playerTwo = new Player(playerTwoName);
			}
		}
	}

	public void setLabels() {
		String currentText = lblPlayerOne.getText();
		String updatedText = currentText + " (" + playerOneName.substring(0, 1).toUpperCase()
				+ playerOneName.substring(1) + ")";
		lblPlayerOne.setText(updatedText);

		currentText = lblPlayerTwo.getText();
		updatedText = currentText + " (" + playerTwoName.substring(0, 1).toUpperCase() + playerTwoName.substring(1)
				+ ")";
		lblPlayerTwo.setText(updatedText);
	}

	public void setPoints() {
		playerOneTurnPoints.setText("0");
		playerOneTotalPoints.setText("0");

		playerTwoTurnPoints.setText("0");
		playerTwoTotalPoints.setText("0");
	}

	public void populateTableView() {

	}

	private void loadAndSetFXML(String fxmlFile, ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
			Parent root = loader.load();
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			switch (fxmlFile) {
			case "game2.fxml":
				Image farmImg = new Image("/farm.jpg");

				Controller controller = loader.getController();
				controller.setBackgroundForGame2(farmImg);

				Scene scene = new Scene(root, 600, 500);
				primaryStage.setScene(scene);
				break;
			case "main_menu.fxml":
				scene = new Scene(root, 550, 400);
				primaryStage.setScene(scene);
				break;
			case "setup.fxml":
				scene = new Scene(root, 600, 400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				break;
			case "history.fxml":
				scene = new Scene(root, 700, 500);
				primaryStage.setScene(scene);
				break;
			default:
				break;
			}

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialize() {

		if (toggleGroup == null && radioOnePlayer != null) {
			toggleGroup = new ToggleGroup();
			setToggleGroup(toggleGroup);
		}

		if (players == null) {
			players = new ArrayList<Player>();
		}

		if (games == null) {
			games = new ArrayList<Game>();
		}

		if (lblPlayerOne != null && lblPlayerTwo != null) {
			setLabels();
		}

		if (numberOfPlayers > 0) {
			addPlayers();
		}

		if (playerOneTurnPoints != null && playerTwoTurnPoints != null && playerOneTotalPoints != null
				&& playerTwoTotalPoints != null) {
			setPoints();
		}

		currentGame = new Game();

		if (playerOne != null && playerOnePane != null) {
			Color greenColor = Color.rgb(0, 255, 0, 0.5);

			BackgroundFill backgroundFill = new BackgroundFill(greenColor, null, null);
			Background background = new Background(backgroundFill);

			playerOnePane.setBackground(background);
		}

		if (btnPlayAgain != null) {
			btnPlayAgain.setVisible(false);
		}

		if (txtGameOver != null) {
			txtGameOver.setVisible(false);
		}

		if (tblHistory != null) {

			gamesData = FXCollections.observableArrayList();

			if (games != null) {
				for (Game game : games) {
					gamesData.add(game);
				}
			}

			tblHistory.setItems(gamesData);

			columnName.setCellValueFactory(cellData -> {
				Player winner = cellData.getValue().getWinner();
				Player loser = cellData.getValue().getLoser();

				int playerOneScore = cellData.getValue().getPlayerOneScore();
				int playerTwoScore = cellData.getValue().getPlayerTwoScore();

				String nameToDisplay = (playerOneScore > playerTwoScore) ? winner.getName() : loser.getName();

				return new SimpleStringProperty(nameToDisplay);
			});

			columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

			columnScore.setCellValueFactory(new PropertyValueFactory<>("winnerScore"));

			columnResult.setCellValueFactory(cellData -> {

				int playerOneScore = cellData.getValue().getPlayerOneScore();
				int playerTwoScore = cellData.getValue().getPlayerTwoScore();

				String result = (playerOneScore > playerTwoScore) ? "Win" : "Lose";

				return new SimpleStringProperty(result);

			});
			
			if (games != null && txtWins != null) {
                String winCountsText = games.stream()
                        .filter(game -> game.getWinner() != null)
                        .collect(Collectors.groupingBy(Game::getWinner, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .map(entry -> entry.getKey().getName() + " has " + entry.getValue() + " wins")
                        .collect(Collectors.joining("\n"));
                
                txtWins.setText(winCountsText);
			}
			


		}

	}

}
