import java.util.ArrayList;
import java.util.Random;

public class Player implements Runnable{
    int plrNum;
    ArrayList<Card> Card_List = new ArrayList<>();

    PlayerList PlayerList;
    final Object lock;
    boolean GameOver = false;

    public int getPlrNum() {
        return plrNum;
    }

    public void GameOver(){
        this.GameOver = true;
    }

    public Player(int i, Object lock,PlayerList PlayerList){
        this.plrNum = i;
        this.lock = lock;
        this.PlayerList = PlayerList;
    }
    public void setCard_List(ArrayList<Card> card_List) {
        Card_List = card_List;
    }
    public void AddCard(Card card){
        this.Card_List.add(card);
    }

    public boolean isMyTurn(){
        return PlayerList.get_head() == this.plrNum;
    }
    public ArrayList<Card> getCard_List() {
        return Card_List;
    }

    @Override
    public void run() {
        ArrayList<Card> matchingPairs = findMatchingPairs();
        System.out.println(this.plrNum + " discarded matching pairs: " + matchingPairs);
        while (!this.Card_List.isEmpty()) {
            synchronized (lock) {
                try {
                    while (!isMyTurn()) {
                        lock.wait();
                    }
                    if (GameOver || PlayerList.returnPrev() == this) {
                        return;
                    }
                    System.out.println("TURN");
                    PlayTurn();
                    PlayerList.increment();
                    lock.notifyAll();
                    if (this.Card_List.isEmpty()) {
                        return;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void PlayTurn(){
        Player prevPlayer = PlayerList.returnPrev();
        if (!prevPlayer.getCard_List().isEmpty()) {
            Card cardToTake = prevPlayer.getCard_List().remove(new Random().nextInt(prevPlayer.getCard_List().size()));
            this.AddCard(cardToTake);
            System.out.println(this.plrNum + " took a card from " + prevPlayer.plrNum + ": " + cardToTake);
        }
        ArrayList<Card> matchingPairs = findMatchingPairs();
        System.out.println(this.plrNum + " discarded matching pairs: " + matchingPairs);

    }

    private ArrayList<Card> findMatchingPairs() {
        ArrayList<Card> matchingPairs = new ArrayList<>();
        for(Player temp_plr : PlayerList.getPlayer_List()){
            System.out.println(temp_plr.Card_List.size());
        }
        for (int i = 0; i < this.Card_List.size() - 1; i++) {
            for (int j = i + 1; j < this.Card_List.size(); j++) {
                Card card1 = this.Card_List.get(i);
                Card card2 = this.Card_List.get(j);
                if (card1.getValue() == card2.getValue() && card1.getcolor().equals(card2.getcolor())) {
                    matchingPairs.add(card1);
                    matchingPairs.add(card2);
                }
            }
        }
        Card_List.removeAll(matchingPairs);
        return matchingPairs;
    }

}