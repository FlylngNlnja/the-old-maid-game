import java.util.ArrayList;

public class BasicDeck {
    private ArrayList<Card> deck;
    private DeckActions deckActions;
    public DeckActions getDeckActions() {
        return deckActions;
    }

    public void setDeckActions(DeckActions deckActions) {
        this.deckActions = deckActions;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }
}
