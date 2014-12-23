package GUI;



public class CardTreeSetNode extends CardTreeNode{
    String setFilePath;
    boolean isLoaded;
    
    public CardTreeSetNode(Object userObject, String setFilePath) {
        super(userObject);
        this.setFilePath = setFilePath;
        isLoaded = false;
    }
    
    public String getSetFilePath() {
        return setFilePath;
    }
    
    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }
    
    public boolean isLoaded() {
        return isLoaded;
    }

}