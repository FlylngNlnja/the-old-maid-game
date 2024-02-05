import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class GameRun extends Game{
    @Override
    public void Initialize(){
        this.Setup();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            if(scanner.hasNextInt()) {
                this.setPlayers(scanner.nextInt());
                break;
            }
        }
        for (int i = 0;i<this.getPlayers();i++){
            this.getPlayerList().AddPlayer(i,lock);
        }
        this.getDeck().setDeck(this.getDeck().getDeckActions().CreateDeck(this.getCardFactory()));
        DealCards();
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

    @Override
    protected void DealCards(){
        PlayerList plrList = this.getPlayerList();
        int siz = this.getDeck().getDeck().size();
        for(int i =0;i<siz;i++){
            plrList.simple_increment();
            Card tCard = this.getDeck().getDeck().removeFirst();
            plrList.getCurrentPlayer().AddCard(tCard);
        }
        plrList.reset();
    }
}
