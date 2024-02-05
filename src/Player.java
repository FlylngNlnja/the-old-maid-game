import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

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
                    System.out.println("NEW TURN");
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
            System.out.println("Player " + this.plrNum + " took a card from " + prevPlayer.plrNum + ": " + cardToTake);
        }
        ArrayList<Card> matchingPairs = findMatchingPairs();
        System.out.println("Player " + this.plrNum + " discarded pairs: " + matchingPairs);

    }



    private ArrayList<Card> findMatchingPairs() {
        ArrayList<Card> matchingPairs = new ArrayList<>();
        for(Player temp_plr : PlayerList.getPlayer_List()){
            System.out.println(temp_plr.Card_List.size());
        }
        HashMap<String,Integer> Mapping = new HashMap<>();
        StringBuilder str = new StringBuilder();
        for (Card card1 : this.Card_List) {
            str.delete(0, str.length());
            str.append(card1.get_color());
            str.append(card1.getValue());
            int count = Mapping.getOrDefault(str.toString(), 0);
            Mapping.put(str.toString(), count + 1);
        }
        HashMap<String, Integer> filteredMapping = Mapping.entrySet().stream()
                .filter(entry -> entry.getValue() % 2 == 0)
                .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue, (e1, e2) -> e1, HashMap::new));
        for (Card card1 : this.Card_List) {
            str.delete(0, str.length());
            str.append(card1.get_color());
            str.append(card1.getValue());
            String strts = str.toString();
            if(filteredMapping.containsKey(strts)) {
                int num = filteredMapping.get(strts);
                if (num > 0) {
                    matchingPairs.add(card1);
                    filteredMapping.put(strts, num - 1);
                }
            }
        }

        Card_List.removeAll(matchingPairs);
        return matchingPairs;
    }

}