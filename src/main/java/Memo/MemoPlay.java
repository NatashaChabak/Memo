package Memo;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MemoPlay extends Application {
    private static TextField player1Field, player2Field;
    private static Label label1score, label2score, labelCurrent;
    private CardList cardList;
    private static Player currentPlayer, player1, player2;
    private static int step, id, length,score;
    private static Button currentButton;
    private boolean isPaused;
    private static long startTime;
    @Override
    public void start(Stage stage) {
        Label titleLabel = new Label("PLAY MEMO GAME");
        drawStartPanel(stage, titleLabel);
    }

    public void drawStartPanel(Stage stage, Label titleLabel) {

        setLabelParams(titleLabel);
        titleLabel.setPrefWidth(300);
        titleLabel.setTextFill(Color.BROWN);
        titleLabel.setAlignment(Pos.BASELINE_CENTER);

        VBox playersBox = drawPlayers();
        VBox btnBox = startButtons(stage);

        HBox mainBox = new HBox(10);
        mainBox.getChildren().addAll(playersBox, btnBox);
        mainBox.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);

        gridPane.setAlignment(Pos.CENTER_LEFT);

        Background greenBackground = new Background(new BackgroundFill(Color.AZURE, null, null));
        gridPane.setBackground(greenBackground);

        gridPane.add(titleLabel, 0, 0);
        gridPane.add(mainBox, 0, 1);

        Scene scene = new Scene(gridPane, 320, 165);
        stage.setScene(scene);
        stage.setTitle("MEMO");
        stage.setResizable(false);
        stage.show();
    }

    public VBox startButtons(Stage stage) {

        Button btn1 = new Button("Play with Cats");
        btn1.setOnAction(e -> startGame("Cats", stage));
        Button btn2 = new Button("Play with Figures");
        btn2.setOnAction(e -> startGame("Figures", stage));
        Button btn3 = new Button("Play with Flowers");
        btn3.setOnAction(e -> startGame("Flowers", stage));
        btnParameters(0, 120, 30, btn1, btn2, btn3);

        VBox buttonsBox = new VBox(2);
        buttonsBox.getChildren().addAll(btn1, btn2, btn3);
        buttonsBox.setAlignment(Pos.BASELINE_LEFT);
        return buttonsBox;
    }

    public static VBox drawPlayers() {
        VBox playersBox = new VBox();
        player1Field = new TextField();
        fieldWithLabel(player1Field, (player1 != null ? player1.getName() : ""), "Enter player's name:", playersBox);
        player2Field = new TextField();
        fieldWithLabel(player2Field, (player2 != null ? player2.getName() : ""), "Enter player's name:", playersBox);
        return playersBox;
    }

    public static void fieldWithLabel(TextField field, String text, String name, VBox playersBox) {
        field.setPrefWidth(150);
        field.setText(text);
        Label label = new Label(name);
        label.setPrefWidth(155);
        playersBox.getChildren().addAll(label, field);
    }

    private void startGame(String folderName, Stage stage) throws IllegalArgumentException {
        try {
            cardList = new CardList(folderName);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        player1 = new Player(player1Field.getText());
        player2 = new Player(player2Field.getText());
        currentPlayer = player1;
        step = 0;
        score = 0;
        startTime = System.nanoTime();
        drawImagesOnGrid(cardList, stage);
        showInfo();
    }

    public void drawImagesOnGrid(CardList cardList, Stage stage) {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(2));
        gridPane.setHgap(2);
        gridPane.setVgap(2);

        Background gridBckground = new Background(new BackgroundFill(Color.AZURE, null, null));
        gridPane.setBackground(gridBckground);

        length = cardList.getCards().size();
        int columns = (int) Math.ceil(Math.sqrt(length));

        HBox scoreBox = infoPanel();
        gridPane.add(scoreBox, 0, 0);

        int counter = 0;
        for (int i = 0; i<columns; i++) {
            HBox hbox = new HBox(10);
            for (int j=0; j<columns; j++) {
                if (counter < length) {
                    Card card = cardList.getCards().get(counter);
                    Button btn = new Button();
                    btn.setOnAction(e -> clickOnIt(btn, card, stage));
                    btnParameters(0, 100,100, btn);
                    hbox.getChildren().addAll(btn);
                }
                counter++;
             }
            hbox.setPadding(new Insets(10));
            gridPane.add(hbox, 0, i+1);
        }

        GridPane.setHgrow(scoreBox, Priority.ALWAYS);

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.setTitle("MEMO game");
        stage.setResizable(false);
        stage.show();
    }

    public void clickOnIt(Button btn, Card card, Stage stage) {
        if (isPaused) { return;}
        ImageView imageView = card.getImage();
        btn.setGraphic(imageView);
        if (step == 0) {
            id = card.getID();
            currentButton = btn;
            card.setState(1);
        } else {
            int state = (id == card.getID() ? 2: 0);
            card.setState(state);
            if (state == 2) {
                currentPlayer.increaseScore();
                score += 2;
            } else {
                currentPlayer = (currentPlayer.equals(player1)) ? player2 : player1;
            }
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                isPaused = false;
                btnParameters(state, 100, 100, btn, currentButton);
                showInfo();
                if (score == length) {
                    finishTheGame(stage);
                }
            });
            pause.play();
            isPaused = true;
        }
        step = (step == 1) ? 0 : 1;
    }

    public void finishTheGame(Stage stage) {
        long endTime = System.nanoTime();
        double elapsedTimeInSeconds = Math.round((endTime - startTime) / 1_000_000_000.0);

        Label titleLabel = new Label("WIN: "+ currentPlayer.getName() + "  TIME: " + elapsedTimeInSeconds + " sec.");
        drawStartPanel(stage, titleLabel);
    }

    public void btnParameters(int state, int btnW, int btnH, Button ...btns) {
        for (Button btn : btns) {
            btn.setMinWidth(btnW);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setMinHeight(btnH);
            btn.setMaxHeight(Double.MAX_VALUE);
            btn.setGraphic(null);
           if (state ==0) {
               btn.setDisable(false);
               btn.setStyle(
                       "-fx-background-color: #f2f2f2;" +
                               "-fx-border-color: #808080;" +
                               "-fx-border-width: 2px;" +
                               "-fx-border-radius: 5px;"
               );
           } else {
               btn.setDisable(true);
               btn.setStyle("-fx-background-color: #cccccc;");
           }
        }
    }

    public HBox
    infoPanel() {
        HBox flBox = new HBox(10);

        Label label1name = new Label(player1.getName() + ": ");
        label1name.setPrefWidth(100);
        label1name.setAlignment(Pos.BASELINE_RIGHT);

        label1score = new Label(Integer.toString(player1.getScore()));
        label1score.setPrefWidth(30);
        label1score.setAlignment(Pos.BASELINE_CENTER);

        Label label2name = new Label(player2.getName() + ": ");
        label2name.setPrefWidth(100);
        label2name.setAlignment(Pos.BASELINE_RIGHT);

        label2score = new Label(Integer.toString(player2.getScore()));
        label2score.setPrefWidth(30);
        label1score.setAlignment(Pos.BASELINE_CENTER);

        labelCurrent = new Label("CURRENT PLAYER: " + player1.getName());
        setLabelParams(label1name, label1score, label2name, label2score, labelCurrent);

        labelCurrent.setPrefWidth(250);
        labelCurrent.setAlignment(Pos.BASELINE_CENTER);
        labelCurrent.setTextFill(Color.BROWN);

        flBox.getChildren().addAll(label1name, label1score, labelCurrent, label2name, label2score);

        return flBox;
    }

    public void setLabelParams(Label... lbls) {
        for (Label lbl : lbls) {
            lbl.setFont(Font.font("Arial", 16));
            lbl.setStyle("-fx-font-weight: bold;");
        }
    }

    public void showInfo() {
        label1score.setText(Integer.toString(player1.getScore()));
        label2score.setText(Integer.toString(player2.getScore()));
        labelCurrent.setText("CURRENT PLAYER: " + currentPlayer.getName());
    }

    public static void main(String[] args) {
        launch(args);
    }

}