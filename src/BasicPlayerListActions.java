public class BasicPlayerListActions implements PlayerListActions{
    @Override
    public void reset(PlayerList plrList){
        plrList.setHead(0);
    }
    @Override
    public void increment(PlayerList plrList){
        int i = 1;
        while (true) {
            if (plrList.getList().get((plrList.getHead() + i + plrList.getList().size()) % plrList.getList().size()).getCard_List().isEmpty()) {
                i++;
            }else{
                break;
            }
        }
        plrList.setHead(((plrList.getHead() + i) % plrList.getList().size()));
    }
    @Override

    public void simple_increment(PlayerList plrList){
        plrList.setHead((plrList.getHead() + 1) % plrList.getList().size());
    }
    @Override

    public Player returnPrev(PlayerList plrList){
        int i = 1;
        while (true) {
            if (plrList.getList().get((plrList.getHead() - i + plrList.getList().size()) % plrList.getList().size()).getCard_List().isEmpty()) {
                i++;
            }else{
                break;
            }
        }
        return plrList.getList().get((plrList.getHead() - i + plrList.getList().size()) % plrList.getList().size());
    }

    @Override
    public Player getCurrentPlayer(PlayerList plrList) {
        return plrList.getPlayer_List().get(plrList.getHead());
    }

}
