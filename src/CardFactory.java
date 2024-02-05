import java.util.HashMap;

public class CardFactory {
    public Card createCard(String symbol,int value){
        HashMap<String,String> SymbolMapper = new HashMap<>();
        SymbolMapper.put("Spade","Black");
        SymbolMapper.put("Club","Black");
        SymbolMapper.put("Diamond","Red");
        SymbolMapper.put("Heart","Red");
        return new Card(SymbolMapper.get(symbol),symbol,value);
    }
    public Card createCard(String symbol){
        return new Card("green",symbol,14);
    }
}
