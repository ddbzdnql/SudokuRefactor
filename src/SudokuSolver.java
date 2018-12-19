import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SudokuSolver extends Application {

    private static final int CENTER_WIDTH = 300;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku Solver");

        Font titleFont = new Font("Courier New", 20);

        Font tileFont = new Font(13);
        BorderStroke tileStroke = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID, new CornerRadii(1),new BorderWidths(2));
        Border tileBorder = new Border(tileStroke);

        BorderStroke terminalStroke = new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(2));
        Border terminalBorder = new Border(terminalStroke);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15, 15, 5, 15));
        Text title = new Text();
        //title.setWrappingWidth();
        title.setText("Sudoku Solver");
        title.setFont(titleFont);
        title.setTextAlignment(TextAlignment.CENTER);

        root.setTop(title);
        root.setAlignment(root.getTop(), Pos.CENTER);

        FlowPane centerObject = new FlowPane();
        centerObject.setOrientation(Orientation.VERTICAL);
        centerObject.setMaxWidth(CENTER_WIDTH);

        TilePane inputBoard = new TilePane();
        inputBoard.setPadding(new Insets(10,0,10,0));
        inputBoard.setHgap(3);
        inputBoard.setVgap(3);
        inputBoard.setMinWidth(CENTER_WIDTH);
        inputBoard.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < 81; i++){
            TextField cur = new TextField();
            cur.setFont(tileFont);
            cur.setMaxWidth(30);
            cur.setMaxHeight(30);
            cur.setMinHeight(30);
            cur.setBorder(tileBorder);
            inputBoard.getChildren().add(cur);
        }
        centerObject.getChildren().add(inputBoard);

        FlowPane buttonPane = new FlowPane();
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        buttonPane.setMaxWidth(CENTER_WIDTH);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(10);
        buttonPane.setPadding(new Insets(3,10,3,10));

        Button solve = new Button();
        solve.setMinWidth(50);
        //solve.setPadding(new Insets(3,10,10,3));
        solve.setTextAlignment(TextAlignment.CENTER);
        solve.setText("Solve");

        Button clear = new Button();

        clear.setMinWidth(50);
        //clear.setPadding(new Insets(3,10,10,3));
        clear.setTextAlignment(TextAlignment.CENTER);
        clear.setText("Clear");
        buttonPane.getChildren().addAll(solve, clear);
        buttonPane.setPadding(new Insets(0,0,10,0));
        centerObject.getChildren().add(buttonPane);

        ScrollPane answerPane = new ScrollPane();
        answerPane.setPrefHeight(200);
        answerPane.setPrefWidth(CENTER_WIDTH);
        answerPane.setBorder(terminalBorder);
        centerObject.getChildren().add(answerPane);
        answerPane.setPadding(new Insets(5,5,5,5));

        root.setCenter(centerObject);
        root.setAlignment(root.getCenter(), Pos.CENTER);

        clear.setOnAction(e->{
            System.out.println("Clear button clicked");
            //answerPane.setContent(null);
            List<Node> tiles = inputBoard.getChildren();
            for (Node cur : tiles){
                ((TextField)cur).setText("");
            }
            Text cmdLog = new Text();
            cmdLog.setText("Board cleared");
            answerPane.setContent(cmdLog);
        });

        solve.setOnAction(e->{
            System.out.println("Solve button clicked");
            Text cmdLog = new Text();
            String log = "Start solving puzzle.\n";
            List<Node> tiles = inputBoard.getChildren();
            int i = 0, err = 0;
            int[][] board = new int[9][9];
            for (Node cur : tiles){
                String content = ((TextField)cur).getText();
                if (content.equals("")){
                    board[i/9][i%9] = 0;
                }else{
                    int curNum = Integer.parseInt(content);
                    if (curNum > 9 || curNum < 0){
                        log += "Error: Invalid input at tile (" + (i/9) + ", " + (i%9) + "): value " + content + ".\n";
                        err = 1;
                        break;
                    }
                    else{
                        board[i/9][i%9] = curNum;
                    }
                }
                i++;
            }
            if (err != 1){
                SBoard toSolve = new SBoard(board);
                System.out.println(toSolve);
                ArrayList<SBoard> results = toSolve.solve();
                if (results == null){
                    log += "Sorry, could not solve the given puzzle.\n";
                }
                else{
                    for (SBoard curBoard : results){
                        log += curBoard + "\n";
                    }
                }
            }
            cmdLog.setText(log);
            answerPane.setContent(cmdLog);
        });

        primaryStage.setScene(new Scene(root, 450,675));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
