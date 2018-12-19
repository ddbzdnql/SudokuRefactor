import java.util.ArrayList;

public class SBoard {
    private static final int LENGTH = 9;
    private static final int[] crossout = {1,1,1,1,1,1,1,1,1,1};
    int[][] board = new int[LENGTH][LENGTH];

    protected SBoard(int[][] in){
       if (in.length != LENGTH){
           System.err.println("Error: Invalid input size");
           return;
       }
       if (in[0].length != LENGTH){
           System.err.println("Error: Invalid input size");
           return;
       }

       for (int i = 0; i < LENGTH; i++){
           for (int j = 0; j < LENGTH; j++){
               board[i][j] = in[i][j];
           }
       }

       return;
    }

    protected ArrayList<SBoard> solve(){
        int nd = 0;
        for (int i = 0; i < LENGTH; i++){
            for (int j = 0; j < LENGTH; j++){
                if (board[i][j] == 0){
                    nd = 1;
                    int[] cross = crossout.clone();
                    int total = LENGTH;
                    for (int s = 0; s < LENGTH; s++){
                        int toCross = board[i][s];
                        if (toCross != 0 && cross[toCross] != 0){
                            total -= 1;
                            cross[toCross] = 0;
                        }
                        toCross = board[s][j];
                        if (toCross != 0 && cross[toCross] != 0){
                            total -= 1;
                            cross[toCross] = 0;
                        }
                        toCross = board[i - i%3 + s/3][j - j%3 + s%3];
                        if (toCross != 0 && cross[toCross] != 0){
                            total -= 1;
                            cross[toCross] = 0;
                        }
                    }
                    if (total == 0){
                        return null;
                    }
                    ArrayList<SBoard> result = new ArrayList<>();
                    for (int s = 1; s <= LENGTH; s++){
                        if (cross[s] != 0){
                            SBoard next = new SBoard(this.board);
                            next.board[i][j] = s;
                            ArrayList partialResult = next.solve();
                            if (partialResult != null){
                                result.addAll(partialResult);
                            }
                        }
                    }
                    return result;
                }
            }
        }

        ArrayList<SBoard> toRet = new ArrayList<>();
        toRet.add(this);
        return toRet;
    }

    @Override
    public String toString(){
        String toRet = "";
        for (int i = 0; i < LENGTH; i++){
            for (int j = 0; j < LENGTH; j++){
                toRet += board[i][j] + " ";
            }
            toRet += "\n";
        }
        return toRet;
    }
}
