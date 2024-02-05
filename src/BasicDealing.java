public class BasicDealing implements Dealing{
    @Override
    public void Dealing(PlayerList plrList,BasicDeck deck){
        int siz = deck.getDeck().size();
        for(int i =0;i<siz;i++){
            plrList.getActions().simple_increment(plrList);
            Card tCard = deck.getDeck().removeFirst();
            plrList.getActions().getCurrentPlayer(plrList).AddCard(tCard);
        }
        plrList.getActions().reset(plrList);
    }
}
