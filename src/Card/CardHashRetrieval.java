package Card;

import java.util.Hashtable;

public class CardHashRetrieval {
    Hashtable<String, String> hash;
    
    public CardHashRetrieval() {
        hash = new Hashtable<String, String>();
        initHash();
    }
    
    public void initHash() {
        hash.put("S18", "Accel World");
    }
    
    public String get(String str) {
        return hash.get(str);
    }
    
    
}
