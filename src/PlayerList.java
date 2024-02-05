import java.util.ArrayList;

public class PlayerList {
    private PlayerListActions Actions;
    private ArrayList<Player> Player_List = new ArrayList<>();
    private int head = -1;
    public void setPlayer_List(ArrayList<Player> player_List) {
        Player_List = player_List;
    }

    public PlayerList(PlayerListActions plrAc){
        this.Actions = plrAc;
    }

    public int getHead() {
        return head;
    }
    public void setHead(int head) {
        this.head = head;
    }

    public void setActions(PlayerListActions actions) {
        Actions = actions;
    }

    public PlayerListActions getActions() {
        return Actions;
    }

    public ArrayList<Player> getList(){
        return this.Player_List;
    }



    public ArrayList<Player> getPlayer_List() {
        return Player_List;
    }

    public void AddPlayer(int i, Object lock){
        this.Player_List.add(new Player( i ,lock, this));
    }
}