package nets150_hw5;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Page {
    
    String url;
    String name;  //link name to get to the page
    String title; //actual wiki page title
    
    Document doc;
    
    HashMap<String, String> linkMap  = new HashMap<String, String>(); //key: link name, value: url
    HashSet<Page> outgoingPages = new HashSet<Page>();  //set of all page objects generated from this page
    HashMap<String, Page> neighborPages = new HashMap<String, Page>(); //key url, value page
    HashSet<String> outgoingUrls = new HashSet<String>(); //set of all outgoing urls on this page
    
    Page parent; //wiki page that has a link to this page
    
 
    /**
     * Constructor
     * @param linkText
     * @param url
     */
    public Page(String linkText, String url) {
        this.url = url;
        this.name = linkText;
    }
    
    
    /**
     * Constructor
     * @param name
     */
    public Page(String name) {
        this.url = createUrl(name);
        this.name = name;

    }
    
 
    /**
     * Creates a wiki url based on a page name
     * ex: "Cologne" becomes https://en.wikipedia.org/wiki/Cologne
     * @param name
     * @return
     */
    public static String createUrl(String name) {
        StringBuilder url = new StringBuilder("");
        url.append("https://en.wikipedia.org/wiki/");
        url.append(name);
        
        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) == ' ') {
                url.deleteCharAt(i);
                url.insert(i, '_');
            }    
        }
        
        String u = url.toString();
        return u;
        
    }
    
    /**
     * Used to set the parent of a page
     * @param p
     */
    public void setParent(Page p) {
        this.parent = p;
    }
    
    

}
