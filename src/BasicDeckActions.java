import java.util.ArrayList;
import java.util.Collections;

public class BasicDeckActions implements DeckActions{
    @Override
    public ArrayList<Card> CreateDeck(CardFactory CF){
        ArrayList<Card> deckk = new ArrayList<>();
        for(int i = 1;i <= 13; i++){
            deckk.add(CF.createCard("Club",i));
        }
        for(int i = 1;i <= 13; i++){
            deckk.add(CF.createCard("Spade",i));
        }
        for(int i = 1;i <= 13; i++){
            deckk.add(CF.createCard("Heart",i));
        }
        for(int i = 1;i <= 13; i++){
            deckk.add(CF.createCard("Diamond",i));
        }
        deckk.add(CF.createCard("Joker"));
        Collections.shuffle(deckk);
        return deckk;
    }
}
