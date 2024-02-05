import java.util.*;

import static java.lang.System.exit;

public class Game {
    private int players;
    private PlayerList PlayerList = new PlayerList();
    private CardFactory CardFactory = new CardFactory();
    ArrayList<Card> deck = new ArrayList<>();
    private int Cards;
    final Object lock = new Object();
    public void Play(){
        Scanner scanner = new Scanner(System.in);
        this.players = Integer.parseInt(scanner.nextLine());
        for (int i = 0;i<this.players;i++){
            this.PlayerList.AddPlayer(i,lock);
        }
        CreateDeck();
        DealCards();
        for (Player player : this.PlayerList.getPlayer_List()) {
           new Thread(player).start();
        }
        synchronized (lock) {
            try {
                while (!isGameOver()) {
                    lock.wait();
                }
                System.out.println("Player " + Objects.requireNonNull(return_Loser()).getPlrNum() + " Lost the game.");
                exit(0);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean isGameOver() {
        int playersWithCards = 0;
        for (Player player : this.PlayerList.getPlayer_List()) {
            if (!player.getCard_List().isEmpty()) {
                playersWithCards++;
            }
        }
        return playersWithCards <= 1;
    }
    private Player return_Loser(){
        for (Player player : this.PlayerList.getPlayer_List()) {
            if (!player.getCard_List().isEmpty()) {
                return player;
            }
        }
        return null;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }
    public CardFactory getCardFactory() {
        return CardFactory;
    }
    public void setCardFactory(CardFactory cardFactory) {
        CardFactory = cardFactory;
    }
    public void setPlayerList(PlayerList playerList) {
        PlayerList = playerList;
    }
    public PlayerList getPlayerList() {
        return PlayerList;
    }
    public void SetPlayers(int plr_num){
        this.players = plr_num;
    }
    public int GetPlayers(){
        return this.players;
    }
    public void setCards(int cards) {
        Cards = cards;
    }
    public int getCards() {
        return Cards;
    }
    public void CreateDeck(){
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
    public void DealCards(){
        PlayerList plrList = this.getPlayerList();
        int siz = deck.size();
        for(int i =0;i<siz;i++){
            plrList.simple_increment();
            Card tCard = deck.removeFirst();
            plrList.getCurrentPlayer().AddCard(tCard);
        }
        plrList.reset();
    }

    public class Player implements Runnable{
        int plrNum;
        ArrayList<Card> Card_List = new ArrayList<>();
        final Object lock;

        public int getPlrNum() {
            return plrNum;
        }

        public Player(int i, Object lock){
            this.plrNum = i;
            this.lock = lock;
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
                        System.out.println("TURN");
                        PlayTurn();
                        PlayerList.increment();
                        lock.notifyAll();
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
            //System.out.println(Card_List);
            for(Player plrs : PlayerList.getPlayer_List()){
                System.out.println(plrs.Card_List.size());
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

    public class PlayerList {
        ArrayList<Player> Player_List = new ArrayList<>();
        int head = -1;
        public void setPlayer_List(ArrayList<Player> player_List) {
            Player_List = player_List;
        }
        public ArrayList<Player> getPlayer_List() {
            return Player_List;
        }

        public void AddPlayer(int i,Object lock){
            this.Player_List.add(new Player( i ,lock));
        }

        public void reset(){
            this.head = 0;
        }
        public void increment(){
            int i = 1;
            while (true) {
                if (Player_List.get((head + i + Player_List.size()) % Player_List.size()).getCard_List().isEmpty()) {
                    i++;
                }else{
                    break;
                }
            }
            this.head = ((head + i) % Player_List.size());
        }

        public void simple_increment(){
            this.head = ((head + 1) % Player_List.size());
        }

        public Player returnPrev(){
            int i = 1;
            while (true) {
                if (Player_List.get((head - i + Player_List.size()) % Player_List.size()).getCard_List().isEmpty()) {
                    i++;
                }else{
                    break;
                }
            }
            return Player_List.get((head - i + Player_List.size()) % Player_List.size());
        }
        public int get_head(){
            return  head;
        }
        public Player getCurrentPlayer(){
            return this.Player_List.get(head);
        }

    }

}
