import java.util.ArrayList;
import java.util.Scanner;

public class CMDTester {
    private static final int LENGTH = 9;
    public static void main(String[] args){
        System.out.println("Please input the puzzle");
        Scanner in = new Scanner(System.in);
        int[][] inBoard = new int[LENGTH][LENGTH];
        for (int i = 0; i < LENGTH; i++){
            for (int j = 0; j < LENGTH; j++){
                System.out.println("Input tile (" + i + ", " + j + "): ");
                inBoard[i][j] = in.nextInt();
            }
        }

        SBoard toSolve = new SBoard(inBoard);
        ArrayList<SBoard> results = toSolve.solve();
        System.out.println(results);
    }
}
