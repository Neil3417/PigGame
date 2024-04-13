package lab8;
public class PigGameTester
{
    public static void test()
    {
        PigGame game = new PigGame(100);  //in this case, the winning score will be 100

        System.out.println(game.getPlayer1Score());
        //prints 0

        System.out.println(game.getPlayer2Score());
        //prints 0

        System.out.println(game.getTurn());
        //prints 1 (it's player 1's turn)

        System.out.println(game.getTurnTotal());
        //prints 0 (player 1 hasn't scored any points on this turn yet)

        game.roll();

        System.out.println(game.getTurnTotal());
        //prints 6 (player 1 rolled a 6, but could have rolled a 1, 2, 3, 4, or 5)

        System.out.println(game.hasBusted());
        //prints false (player 1 has not yet busted by rolling a 1)

        System.out.println(game.getPlayer1Score());
        //prints 0 (player 1 has not yet decided to keep the turn total of 6)

        System.out.println(game.getTurn());
        //prints 1 (it is still player 1's turn)

        game.roll();

        System.out.println(game.getTurnTotal());
        //prints 0 (player 1 rolled a 1 and busted)

        System.out.println(game.hasBusted());
        //prints true (player 1 busted, since a 1 was rolled)

        System.out.println(game.getTurn());
        //prints 1 (it is still player 1's turn)

        game.endTurn();

        System.out.println(game.getPlayer1Score());
        //prints 0 (player 1 did not score any points on the first turn)

        System.out.println(game.getTurn());
        //prints 2 (it's player 2's turn now)

        System.out.println(game.hasBusted());
        //prints false (player 2 hasn't busted)

        game.roll();

        System.out.println(game.getTurnTotal());
        //prints 3 (player 2 rolled a 3)

        game.roll();

        System.out.println(game.getTurnTotal());
        //prints 9 (player 2 rolled a 6, making the current score 3 + 6 = 9)

        game.endTurn();

        System.out.println(game.getPlayer2Score());
        //prints 9 (player 2 ended the first turn with 9)

        System.out.println(game.getTurn());
        //prints 1 (it's player 1's turn again)

        System.out.println(game.getTurnTotal());
        //prints 0 (player 1 hasn't rolled yet)

        System.out.println(game.getWinner());
        //prints 0 (no one has won yet.  eventually, one of the player's will reach
        //          the winning score (or more), and this will return the winning player's number.
        //          for example, if player 2 reaches the winning score first,
        //          then this will return 2)
    }
}
