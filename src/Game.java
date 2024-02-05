import java.util.*;
public abstract class Game {
    private int players;
    private PlayerList PlayerList;
    private CardFactory CardFactory;
    private BasicDeck deck;
    private Dealing Dealing;
    private int Cards;
    final Object lock = new Object();
    abstract boolean isGameOver();
    abstract void Initialize();

    public Dealing getDealing() {
        return Dealing;
    }

    public void setDealing(Dealing dealing) {
        Dealing = dealing;
    }

    public Object getLock() {
        return lock;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public BasicDeck getDeck() {
        return deck;
    }
    public void setDeck(BasicDeck deck) {
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
