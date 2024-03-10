import javafx.animation.KeyFrame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HangmanGame extends Application {
    private Secret phrase;
    private Secret hiddenPhrase;
    private int attempts = 6;

    @Override
    public void start(Stage primaryStage) {
       primaryStage.setTitle("Hangman Game");
       
       //Create first label, textfield, and button
       Label wordToGuessLabel = new Label("Enter a word or phrase:");
       wordToGuessLabel.setFont(new Font(24));
       TextField wordToGuessField = new TextField();
       wordToGuessField.setFont(new Font(24));
       Button submitWordToGuessButton = new Button("Submit");
       submitWordToGuessButton.setFont(new Font(24));
       
       //Create second label, textfield, and button
       Label guessCharLabel = new Label("Enter a letter (in lowercase) to guess:");
       guessCharLabel.setFont(new Font(24));
       TextField guessCharField = new TextField();
       guessCharField.setFont(new Font(18));
       Button submitLetterButton = new Button("Submit");
       submitLetterButton.setFont(new Font(18));
       
       //Create gameover text
       Label gameOverText = new Label("Your man has been hung! Game Over. :(");
       gameOverText.setFont(new Font(24));
       
       //Create gamewon text
       Label gameWonText = new Label("You guessed the phrase! You won! :D");
       gameWonText.setFont(new Font(24));
       
       Label secretWord = new Label("");
       
       Image image = new Image("./hangtree.png");
       Image head = new Image("./head.png");
       Image torso = new Image("./torso.png");
       Image rightarm = new Image("./rightarm.png");
       Image leftarm = new Image("./leftarm.png");
       Image rightleg = new Image("./rightleg.png");
       Image leftleg = new Image("./leftleg.png");
       
       ImageView imageView = new ImageView(image);
       double desiredWidth = 250;
       double desiredHeight = 250;
       imageView.setFitWidth(desiredWidth);
       imageView.setFitHeight(desiredHeight);
       
       Text welcomeMsg = new Text("Welcome to Hangman!\n\n    By: Luis F. Villalon");
       welcomeMsg.setFont(Font.font("Arial", 24));
       
       VBox vbox = new VBox(10); 
       vbox.setAlignment(Pos.CENTER);
       vbox.getChildren().add(welcomeMsg);
       
       BorderPane root = new BorderPane();
       root.setCenter(vbox);
       
       Scene scene = new Scene(root, 550, 475);
       primaryStage.setScene(scene);
       primaryStage.show();
       
         // Replace the text after 3 seconds
         Timeline timer = new Timeline(new KeyFrame(Duration.seconds(3), e -> {        
 
             // Replace the initial text with the new text
             vbox.getChildren().clear();
             vbox.getChildren().addAll(wordToGuessLabel, wordToGuessField, submitWordToGuessButton);
         }));
         timer.play();
         
         submitWordToGuessButton.setOnAction(e -> {
            
            phrase = new Secret(wordToGuessField.getText());
            String temp = "";
            for (int i = 0; i < phrase.getWord().length(); ++i) {
               if (phrase.getWord().charAt(i) == ' ') {
                  temp += ' ';
               } else {
                  temp += '*';
               }
            }
            
            hiddenPhrase = new Secret(temp);
            
            vbox.getChildren().clear();
            
             secretWord.setText(hiddenPhrase.getWord());
             secretWord.setFont(new Font(24));
            
            vbox.getChildren().add(imageView);
            vbox.getChildren().add(secretWord);
            vbox.getChildren().add(guessCharLabel);
            vbox.getChildren().add(guessCharField);
            vbox.getChildren().add(submitLetterButton);
        });
       
        submitLetterButton.setOnAction(e -> {
             String guessChar = guessCharField.getText();
             for (int i = 0; i < phrase.getWord().length(); ++i) {
                if (phrase.getWord().charAt(i) == guessChar.charAt(0) || phrase.getWord().charAt(i) == Character.toUpperCase(guessChar.charAt(0)) ) {
                  char[] secretArr = hiddenPhrase.getWord().toCharArray();
                  if (Character.isUpperCase(phrase.getWord().charAt(i))) {
                     secretArr[i] = Character.toUpperCase(guessChar.charAt(0));
                  } else {
                     secretArr[i] = guessChar.charAt(0);
                  }
                  hiddenPhrase.setWord(String.valueOf(secretArr));
                }                 
             }
             String temp = phrase.getWord().toLowerCase();
             if ( !temp.contains(guessChar) ) {
               if (attempts == 6) {
                  imageView.setImage(head);
                  attempts -= 1;
               } else if (attempts == 5) {
                  imageView.setImage(torso);
                  attempts -= 1;
               } else if (attempts == 4) {
                  imageView.setImage(rightarm);
                  attempts -= 1;
               } else if (attempts == 3) {
                  imageView.setImage(leftarm);
                  attempts -= 1;
               } else if (attempts == 2) {
                  imageView.setImage(rightleg);
                  attempts -= 1;
               } else if (attempts == 1) {
                  imageView.setImage(leftleg);
                  attempts -= 1;
               } else if (attempts == 0) {
                  vbox.getChildren().remove(guessCharLabel);
                  vbox.getChildren().remove(guessCharField);
                  vbox.getChildren().remove(submitLetterButton);
                  vbox.getChildren().add(gameOverText);
               }
             }
             
             secretWord.setText(hiddenPhrase.getWord());
             
             if (!hiddenPhrase.getWord().contains("*")) {
                     vbox.getChildren().remove(guessCharLabel);
                     vbox.getChildren().remove(guessCharField);
                     vbox.getChildren().remove(submitLetterButton);
                     vbox.getChildren().add(gameWonText);
             }
        });
       
    }

    public static void main(String[] args) {
        launch(args);
    }
}
