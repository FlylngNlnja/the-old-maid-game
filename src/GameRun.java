import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class GameRun extends Game{
    public void Initialize(){
        this.setCardFactory(new CardFactory());
        this.setPlayerList(new PlayerList(new BasicPlayerListActions()));
        this.setDeck(new BasicDeck(new BasicDeckActions()));
        this.setDealing(new BasicDealing());
        Scanner scanner = new Scanner(System.in);
        while(true) {
            if(scanner.hasNextInt()) {
                int i = scanner.nextInt();
                if (i>1) {
                    this.setPlayers(i);
                    break;
                }
            }
        }
        scanner.close();
        for (int i = 0;i<this.getPlayers();i++){
            this.getPlayerList().AddPlayer(i,lock);
        }
        this.getDeck().setDeck(this.getDeck().getDeckActions().CreateDeck(this.getCardFactory()));
        this.getDealing().Dealing(this.getPlayerList(),this.getDeck());
        for (Player player : this.getPlayerList().getPlayer_List()) {
            Thread plr = new Thread(player);
            plr.start();
        }
        synchronized (lock) {
            try {
                while (!isGameOver()) {
                    lock.wait();
                }
                System.out.println("Player " + Objects.requireNonNull(return_Loser()).getPlrNum() + " Lost the game.");
                for(Player plr : getPlayerList().getList()){
                    plr.GameOver();
                }
                exit(0);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    protected boolean isGameOver() {
        int playersWithCards = 0;
        for (Player player : this.getPlayerList().getPlayer_List()) {
            if (!player.getCard_List().isEmpty()) {
                playersWithCards++;
            }
        }
        return playersWithCards <= 1;
    }

    private Player return_Loser(){
        for (Player player : this.getPlayerList().getPlayer_List()) {
            if (!player.getCard_List().isEmpty()) {
                return player;
            }
        }
        return null;
    }

}
