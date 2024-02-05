public interface PlayerListActions {
     void reset(PlayerList plrList);
     void increment(PlayerList plrList);

     void simple_increment(PlayerList plrList);

     Player returnPrev(PlayerList plrList);

     Player getCurrentPlayer(PlayerList plrList);
}
