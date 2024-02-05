import java.util.HashMap;

public class CardFactory {
    HashMap<String,String> SymbolMapper = new HashMap<>();
    public CardFactory(){
        SymbolMapper.put("Spade","Black");
        SymbolMapper.put("Club","Black");
        SymbolMapper.put("Diamond","Red");
        SymbolMapper.put("Heart","Red");
    }
    public Card createCard(String symbol,int value){
        return new Card(SymbolMapper.get(symbol),symbol,value);
    }
    public Card createCard(String symbol){
        return new Card(symbol);
    }
}
