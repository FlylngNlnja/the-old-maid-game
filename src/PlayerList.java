import java.util.ArrayList;

public class PlayerList {
    ArrayList<Player> Player_List = new ArrayList<>();
    private int head = -1;
    public void setPlayer_List(ArrayList<Player> player_List) {
        Player_List = player_List;
    }

    public int getHead() {
        return head;
    }


    public void setHead(int head) {
        this.head = head;
    }


    public ArrayList<Player> getPlayer_List() {
        return Player_List;
    }

    public void AddPlayer(int i, Object lock){
        this.Player_List.add(new Player( i ,lock, this));
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