import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class GameRun extends Game{
    @Override
    public void Play(){
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
        CreateDeck();
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
                for(Player plr : getPlayerList().Player_List){
                    plr.GameOver();
                }

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
    protected void CreateDeck(){
        CardFactory CF = this.getCardFactory();
        ArrayList<Card> deck = this.getDeck();
        for(int i = 1;i <= 13; i++){
            deck.add(CF.createCard("Club",i));
        }
        for(int i = 1;i <= 13; i++){
            deck.add(CF.createCard("Spade",i));
        }
        for(int i = 1;i <= 13; i++){
            deck.add(CF.createCard("Heart",i));
        }
        for(int i = 1;i <= 13; i++){
            deck.add(CF.createCard("Diamond",i));
        }
        deck.add(CF.createCard("Joker"));
        Collections.shuffle(deck);
    }
    @Override
    protected void DealCards(){
        PlayerList plrList = this.getPlayerList();
        int siz = deck.size();
        for(int i =0;i<siz;i++){
            plrList.simple_increment();
            Card tCard = deck.removeFirst();
            plrList.getCurrentPlayer().AddCard(tCard);
        }
        plrList.reset();
    }
}
