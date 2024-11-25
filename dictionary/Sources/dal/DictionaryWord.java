
package dal;

import java.util.ArrayList;

public class DictionaryWord {
    private String word;
    private ArrayList<String> meanings;
    private boolean favorite;
    private int count = 1;
    
    public String getWord() {
        return this.word;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public ArrayList<String> getMeaningList() {
        return this.meanings;
    }
    
    public boolean getFavoriteStatus() {
        return this.favorite;
    }
    
    public void setFavorite(boolean value) {
        favorite = value;
    }
    
    public void setFavorite(int value) {
        if (value == 1) {
            System.out.println("word is like in memory");
        } else {
            System.out.println("word is unlike in memory");
        }
        favorite = value == 1;
    }
    
    DictionaryWord(String word, ArrayList<String> meanings, boolean favoriteStatus) {
        this.word = word;
        this.meanings = meanings;
        this.favorite = favoriteStatus;
        //this.meanings.removeFirst();
    }  
    
    DictionaryWord(String word, int count) {
        this.word = word;
        this.count = count;
    }  
}
