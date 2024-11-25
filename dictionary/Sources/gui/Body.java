
package gui;

import bll.BodyHandler;
import bll.LikeHandler;
import dal.XMLReader;
import dal.DictionaryWord;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Body {
    private static JPanel mainBody;
    private final LoadingPopup loadingPopup;
    
    private static XMLReader reader;
    //private static XMLWriter writer;
    
    private static JPanel itemCardContainer;
    private static LikeHandler lh;
    private Boolean isInverted = false;
    
    
    private static ArrayList<DictionaryWord> dictionaryAV;
    private static ArrayList<DictionaryWord> dictionaryVA;
    private static ArrayList<DictionaryWord> specialDictionary;
    private boolean isSpecial = false;
    private boolean isFav = false;
    
    private int page;
    private final int itemCount = 4;
    private int pageCountAV;
    private int pageCountVA;
    private String language; // EN / VI
    
    private final String EN = "EN";
    private final String VI = "VI";
    
    private static JPanel navigation;
    private static JButton navPrev;
    private static JButton navNext;
    private static JLabel navPageCount;
    
    private final String NEXT = "NEXT";
    private final String PREV = "PREV";
    
    private  ArrayList<DictionaryWord> getDictionary() {
        return (language == null ? EN == null : language.equals(EN)) ? dictionaryAV : dictionaryVA;
    }
    
//    public void search(String query) {
//        ArrayList<DictionaryWord> dictionary = getDictionary();
//        
//    }
    public LoadingPopup getPU() {
        return this.loadingPopup;
    }
    
    public boolean getFavoriteStatus() {
        return this.isFav;
    }
    
    public void setFavoriteStatus(boolean value) {
        this.isFav = value;
    }
    
    public boolean getLanguage() {
        return (language == null ? EN == null : language.equals(EN));
    }
    
    public void loadDictionary(String filenameAV, String filenameVA) throws ParserConfigurationException, SAXException, IOException {
        reader = new XMLReader(filenameAV, filenameVA);
        reloadDictionary();
        language = "EN";
    }
    
    public void reloadDictionary() {
        
        loadingPopup.showLoadingPopup();
        
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Perform data loading operation here
                dictionaryAV = reader.getList(true);
                pageCountAV = (int) Math.ceil(dictionaryAV.size() / itemCount);

                dictionaryVA = reader.getList(false);
                pageCountVA = (int) Math.ceil(dictionaryVA.size() / itemCount);

                return null;
            }

            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                    loadingPopup.hideLoadingPopup();
                    lh = new LikeHandler(dictionaryAV, dictionaryVA, true);
                    loadContent(page);
                });
            }
        };
        
        worker.execute();
    }
    
    public void revert() {
        this.isSpecial = false;
    }
    
    
    public void loadSearchDictionary(String query) {
        Body.specialDictionary.clear();
        ArrayList<DictionaryWord> reference = null;
        this.isSpecial = true;
        if (isFav) {
            reference = Body.specialDictionary;
        } else {
            if (language.equals(EN)) {
                reference = Body.dictionaryAV;
            } else {
                reference = Body.dictionaryVA;
            }
        }
       
        for (int i = 0; i < reference.size(); i++) {
            DictionaryWord word = reference.get(i);
            if (word.getWord().contains(query)) {
                specialDictionary.add(word);
            }
        }
    }
    
    public void loadFavoriteDictionary() {
        Body.specialDictionary.clear();
        ArrayList<DictionaryWord> reference = null;
        this.isSpecial = true;
        if (language.equals(EN)) {
            reference = Body.dictionaryAV;
        } else {
            reference = Body.dictionaryVA;
        }
        
        for (int i = 0; i < reference.size(); i++) {
            DictionaryWord word = reference.get(i);
            if (word.getFavoriteStatus() == true) {
                specialDictionary.add(word);
            }
        }
        
    }
    
    public void changeLanguage() {
        this.revert();
        if (language == null ? EN == null : language.equals(EN)) {
            language = VI;
            lh.setLang(false);
        } else {
            language = EN;
            lh.setLang(true);
        }    
        page = 1;
        loadContent(page);
    }
    
    public void toggleOrder() {
        isInverted = !isInverted;
    }
    
    public boolean isSpecial() {
        return this.isSpecial;
    }
    
    public boolean inverted() {
        return isInverted;
    }
    
    public int getPage() {
        return this.page;
    }
    
    public int getPageCount() {
        if(this.isSpecial) {
            return (int) Math.ceil(Body.specialDictionary.size() / this.itemCount);
        } else {
            return (language == null ? EN == null : language.equals(EN)) ? this.pageCountAV : this.pageCountVA;
        }
    }
    
    public void nextPage() {
        int size = this.getPageCount();

        
        if (page < size) {
            page = page + 1;
            if (this.isSpecial) {
                this.loadSpecializedContent(page);
            } else {
                this.loadContent(page);
            }
        }
    } 
    
    public void prevPage() {
        if (page > 1) {
            page = page - 1;
            
            if (this.isSpecial) {
                this.loadSpecializedContent(page);
            } else {
                this.loadContent(page);
            }
        }
    }
    
    public void setPageCounter() {
        int size = 0;
        if (language == null ? EN == null : language.equals(EN)) {
            size = pageCountAV;
        } else if(language == null ? VI == null : language.equals(VI)) {
            size = pageCountVA;
        }
        
        navPageCount.setText(String.valueOf(page) + "/" + String.valueOf(size));
    }
    
    public void setPageCounter(int size) {
        navPageCount.setText(String.valueOf(page) + "/" + String.valueOf(size));
    }
    
    public Body() {
        
        loadingPopup = new LoadingPopup();
        mainBody = new JPanel();
        mainBody.setBorder(new EmptyBorder(0, 20, 10, 10));
        
//        searchBar = new JPanel();
//        sbLabel = new JLabel("Search");
        
        BodyHandler bh = new BodyHandler(this);
        
        itemCardContainer = new JPanel();
        itemCardContainer.setLayout(new BoxLayout(itemCardContainer, BoxLayout.Y_AXIS));
        
        navigation = new JPanel();
        navPrev = new JButton("previous");
        navNext = new JButton("next");
        navPageCount = new JLabel();
        navPageCount.setHorizontalAlignment(SwingConstants.CENTER);
        
        navPrev.setActionCommand(PREV);
        navPrev.addActionListener(bh);
        
        navNext.setActionCommand(NEXT);
        navNext.addActionListener(bh);
        
        navigation.setLayout(new BorderLayout());
        navigation.add(navPrev, BorderLayout.LINE_START);
        navigation.add(navPageCount, BorderLayout.CENTER);
        navigation.add(navNext, BorderLayout.LINE_END);
        
        
        mainBody.setLayout(new BorderLayout());
//        mainBody.add(searchBar, BorderLayout.PAGE_START);
        mainBody.add(itemCardContainer, BorderLayout.CENTER);
        mainBody.add(navigation, BorderLayout.PAGE_END);
        
        page = 1;
        Body.specialDictionary = new ArrayList<>();
    }
    
    public void loadSpecializedContent(int pageNumber) {
        itemCardContainer.removeAll();
        
        int pageCount = this.getPageCount();
        page = pageNumber;
        
        int realPage = 0;
        if (isInverted) {
            realPage = pageCount - page + 1;
        } else {
            realPage = page;
        }

        setPageCounter(pageCount);
        if (Body.specialDictionary.isEmpty()) {
            
            ItemCard emptyCard = new ItemCard();
            itemCardContainer.add(emptyCard.getItemCard());
            return;
        }
        for (int i = (realPage - 1) * itemCount; i < realPage * itemCount; i++) {
            String word = null;
            ArrayList<String> meanings = null;
            
            if (i >= specialDictionary.size() || i < 0) {
                return;
            }
            word = specialDictionary.get(i).getWord();
            meanings = specialDictionary.get(i).getMeaningList();
            boolean favorite = specialDictionary.get(i).getFavoriteStatus();
            ItemCard newItemCard = new ItemCard(word, meanings, favorite);
            newItemCard.addEventListener(lh);
            itemCardContainer.add(newItemCard.getItemCard());
        }
       
    }
    
    public void loadContent(int pageNumber) {
        int pageCount = 0;
        ArrayList<DictionaryWord> dictionary;
        if (language == null ? EN == null : language.equals(EN)) {
            pageCount = pageCountAV;
            dictionary = dictionaryAV;
        } else {
            pageCount = pageCountVA;
            dictionary = dictionaryVA;
        }
        page = pageNumber;
        
        int realPage = 0;
        if (isInverted) {
            realPage = pageCount - page + 1;
        } else {
            realPage = page;
        }
        setPageCounter(pageCount);
        itemCardContainer.removeAll();
        boolean empty = true;
        for (int i = (realPage - 1) * itemCount; i < realPage * itemCount; i++) {
            String word = null;
            ArrayList<String> meanings = null;
            boolean favorite = false;
            if (i >= dictionary.size() || i < 0) {
                return;
            }
            word = dictionary.get(i).getWord();
            meanings = dictionary.get(i).getMeaningList();
            favorite = dictionary.get(i).getFavoriteStatus();
            
            ItemCard newItemCard = new ItemCard(word, meanings, favorite);
            newItemCard.addEventListener(lh);
            itemCardContainer.add(newItemCard.getItemCard());
            empty = false;
        }
        
        if (empty) {
            ItemCard emptyCard = new ItemCard();
            itemCardContainer.add(emptyCard.getItemCard());
        }
    }
    
    public JPanel getBody() {
        return Body.mainBody;
    }
    
   
}
