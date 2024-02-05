import java.util.*;
public abstract class Game {
    private int players;
    private PlayerList PlayerList = new PlayerList();
    private CardFactory CardFactory = new CardFactory();
    ArrayList<Card> deck = new ArrayList<>();
    private int Cards;
    final Object lock = new Object();
    abstract void Play();
    abstract boolean isGameOver();
    abstract void CreateDeck();
    abstract void DealCards();
    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
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
}
