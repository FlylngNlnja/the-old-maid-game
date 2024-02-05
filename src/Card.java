import java.lang.*;
public class Card {
    int value;
    String color;
    String symbol_name;

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
    public void setcolor(String symbol) {
        this.color = symbol;
    }

    public void setsymbol_name(String symbol) {
        this.symbol_name = symbol_name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public String getsymbol_name() {
        return symbol_name;
    }
    public String getcolor() {
        return color;
    }

    @Override
    public String toString() {
        return "Card " +
                "value=" + value +
                ", color=" + color +
                ", symbol_name='" + symbol_name;
    }
}

