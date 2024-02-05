import java.lang.*;
public class Card {
    private int value;
    private String color;
    private String symbol_name;

    public Card(String color,String symbol_name,int value){
        this.color = color;
        this.symbol_name = symbol_name;
        this.value = value;
    }
    public Card(String symbol_name){
        this.color = "joker";
        this.symbol_name = symbol_name;
        this.value = 14;
    }
    public void set_color(String symbol) {
        this.color = symbol;
    }

    public void set_symbol_name(String symbol) {
        this.symbol_name = symbol_name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public String get_symbol_name() {
        return symbol_name;
    }
    public String get_color() {
        return color;
    }

    @Override
    public String toString() {
        return "Card " +
                "value=" + value +
                ", color=" + color +
                ", symbol_name='" + symbol_name;
    }
    public boolean compareTo(Card card)
    {
        return this.color.equals(card.get_color()) && this.value == card.getValue();
    }
}

