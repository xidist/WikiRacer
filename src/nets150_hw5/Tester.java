package nets150_hw5;


public class Tester {
    
    public static void main(String[] args) {
        
        long start = System.currentTimeMillis();     
        
        
        //tests
        Racer r = new Racer("Joe Stephenson", "Bloomington, Minnesota");
        r.bfs(); 
        
       
        Racer r1 = new Racer("Billie Eilish", "Michael Jackson");
        r1.bfs();
        
        
        Racer r2 = new Racer("Earthquake", "Cologne");
        r2.bfs();
        
        //time analysis
        long end = System.currentTimeMillis();
        System.out.println("#### time analysis ####");
        System.out.println("time: " + (end - start));
        System.out.println();
        
        //heap analysis
        int mb = 1024*1024;
        
        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();
        
        System.out.println("##### Heap utilization statistics [MB] #####");
        
        //Print used memory
        System.out.println("Used Memory:" 
            + (runtime.totalMemory() - runtime.freeMemory()) / mb);

        //Print free memory
        System.out.println("Free Memory:" 
            + runtime.freeMemory() / mb);
        
        //Print total available memory
        System.out.println("Total Memory:" + runtime.totalMemory() / mb);

        //Print Maximum available memory
        System.out.println("Max Memory:" + runtime.maxMemory() / mb);

//        Racer r3 = new Racer("Jeremy Bentham", "Hydrochloric acid" );
//        r3.bfs();
       
          
        
    }

}
