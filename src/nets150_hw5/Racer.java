package nets150_hw5;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Racer {
    
    Page start; //start page
    
    HashSet<String> discoveredUrls; //stores all the urls that we have discovered in bfs
 
    String startUrl; //url for the starting wiki page
    String endUrl; //url for the ending wiki page
    
    String startName; //name of the starting wiki page
    String endName;  //name of the ending wiki page
    
    
    
    /**
     * creates a racer object
     * @param start
     * @param end
     */
    public Racer(String start, String end) {
        
        //create new page objects
        this.start = new Page(start);
        this.startUrl = this.start.url;
  
        
        this.endUrl = Page.createUrl(end);
        this.endName = end;
        this.startName = start;
        
        discoveredUrls  = new HashSet<String>();
        
     
    }
    
    /**
     * performs bfs on this racer
     * ends when we find a wiki page that matches the endurl
     */
   public void bfs() {
       System.out.println("starting race from " + startName + " to " + endName);
       
       LinkedList<Page> queue = new LinkedList<Page>();
       boolean found = false;
       
       //add start page to queue
       queue.add(this.start);
       
       //store mem for end page
       Page endPage = null;
       
       //begin bfs
       while (!queue.isEmpty() && !found) {
           
           Page currPage = queue.poll();
           
           if (discoveredUrls.contains(currPage.url)) {
               continue;
           }

           discoveredUrls.add(currPage.url);
           
           endPage = currPage; //let end page point to currPage
       
           
           //if currPage's url is the endurl then end
           if (currPage.url.contentEquals(endUrl)) {
               found = true;
               break;
           }
           
           //get neighbors
           boolean setDocBool = setDoc(currPage);
           if (setDocBool) {
               boolean getNeighborsBool = getNeighbors(currPage);

               if (getNeighborsBool) {
                   //add neighbors to queue
                   queue.addAll(currPage.outgoingPages);
                 
                   //end bfs when we have found a page that has a link to the target page
                   if (currPage.linkMap.containsKey(endName) || currPage.outgoingUrls.contains(endUrl)){
                      // System.out.println("FOUND");
                       endPage = currPage.neighborPages.get(endUrl);
                       found = true;
                       break;
                   }
               }
           } //if there was an error loading the doc,, continue with other pages in the queue
           else {
               continue;
           }
           
           
       }
       

       //set doc for endPage so we can set its title
       setDoc(endPage);
       
       
       printRace(endPage);
 
   }
   
   /**
    * creates a Document object for a Page 
    * @param p
    * @return
    */
   public boolean setDoc(Page p) {
       boolean noError = true;
       
       try {
           p.doc = Jsoup.connect(p.url).get();
       } catch (IOException e) {
           noError = false;
       }
       
       //if there were no errors loading the doc
       // set the title of the page
       if (noError) {
           setTitle(p);
       }
       
       return noError;
   }
   
   /**
    * Sets the title for a Page
    * @param p
    */
   
   public void setTitle(Page p) {
       Element titleElement = p.doc.selectFirst("h1");
       p.title = titleElement.text();
   }
   
   /**
    * Gets all of the links on the current page.
    * For each link found a page object is created
    * and we set the parent to be @param p
    * We store all the children and its relevant attributes
    * in the p.linkMap, p.outgoingPages, p.neighborPages, p.outgoingUrls
    * @param p
    * @return
    */
   
   public boolean getNeighbors(Page p) {
       
       if (p.doc == null) {
           return false;
       }
       
       
       Elements links = p.doc.select("a[href]");

       for (Element link : links) {
           
           //if non wiki link... then skip
           if (!link.absUrl("href").contains("https://en.wikipedia.org/wiki/")) {
               continue;
           }
           
           if (!p.linkMap.containsKey(link.text())) {
               p.linkMap.put(link.text(), link.absUrl("href"));
               Page child = new Page(link.text(), link.absUrl("href") );
               child.setParent(p);
               p.outgoingPages.add(child);
               p.neighborPages.put(link.absUrl("href"), child);
               p.outgoingUrls.add(link.absUrl("href"));
               
           }
           
       }
       return true;
   }
    
    
    
    
    /**
     * Here, we print the results of the bfs
     * via the parent parameters
     * @param ending
     */
    public void printRace(Page ending) {
        
        LinkedList<Page> results = new LinkedList<Page>();

        
        System.out.println("::::::::::::results::::::::::::::");
        Page pg = ending;
        results.addFirst(pg);
        while (!pg.url.contentEquals(startUrl)) {
            pg = pg.parent;
            results.addFirst(pg);
        }
        for (Page i : results) {
            
            if (i.name.contentEquals(startName)) {
                System.out.println("    page title: " + i.title);
                System.out.println("    page url: " + i.url);
            } else {
                
                System.out.println("> click on link: " + i.name);
                System.out.println("    page title: " + i.title);
                System.out.println("    page url: " + i.url);
            }
            System.out.println();
            
        }
        
        System.out.println("shortest path size: " + ( results.size() - 1) );
        System.out.println();
        
        printSize();
    }
    
 
   /**
    * Here we print the total number of wiki pages visited 
    */
    public void printSize() {
        System.out.println("total num of wiki pages visited: " + discoveredUrls.size());
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println();
    }
    

}
