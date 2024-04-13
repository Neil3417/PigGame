package lab8;



public class PigGame
{
    private int p1score;
    private int p2score;
    private int winscore;
    private int turn;
    private int totalTurns;
    private int score;
    private int p1fill;
    private int p2fill;
    private int roll;
    public PigGame(int a){
        roll = (int)(Math.random()* 5 + 1);
        p1score = 0;
        p1fill=0;
        p2fill=0;
        p2score = 0;
        turn = 1;
        winscore = a;
        totalTurns = 0;
    }

    public void roll(){
        boolean b=hasBusted();
        if(getTurn() == 1&&b)
        {
            endTurn();
        }
        else
        {
            p1fill=roll+p1fill;
        }
        if(getTurn() == 2&&b)
        {
            endTurn();
        }
        else
        {
            p2fill=roll+p2fill;
        }
        roll = (int)(Math.random()* 5 + 1);
    }

    public int getPlayer1Score(){
        return p1score;
    }

    public int getPlayer2Score(){
        return p2score;
    }

    public int getTurn(){
        return turn;
    }

    public boolean hasBusted(){
        if(roll == 1)
        {
            return true;
        }
        else{
            return false;
        }
    }

    public void endTurn(){
        if(turn==1){
            if(!hasBusted()){
                p1score=p1score+p1fill;
            }
            turn=2;
            roll=0;
        }
        else if(turn==2){
            if(!hasBusted()){
                p2score=p2score+p2fill;
            }
            turn=1;
            roll=0;
        }
        p1fill = 0;
        p2fill = 0;
        if(p1fill==0||p2fill==0){
            totalTurns=0;
        }
    }

    public int getWinner(){
        if(p1score>p2score && p1score>=winscore) {
            return 1;
        }
        else if (p2score>p1score && p2score>=winscore)
        {
            return 2;
        }
        return 0;
    }

    public int getTurnTotal(){
        if(turn==1){
            return p1fill;
        }
        return p2fill;
    }
}


