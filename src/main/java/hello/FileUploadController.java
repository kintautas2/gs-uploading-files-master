package hello;

import Veikimas.Gija;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import java.util.Iterator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PreDestroy;

@Controller
public class FileUploadController {

    
    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }
    
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name, 
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = 
                        new BufferedOutputStream(new FileOutputStream(new File("./dir/" ,name)));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + "!";
                
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
    
     @RequestMapping(value="/deleteFiles", method=RequestMethod.GET)
     public @ResponseBody String deleteFiles()
     {
         try
         {
            File dir = new File("./dir");
            FileUtils.cleanDirectory(dir);
            return ("All files have been deleted");
         }
         catch (Exception e) {         
            return ("Error");
         }
     }
     
      @RequestMapping(value="/run", method=RequestMethod.GET)
    public @ResponseBody String runScan() {
        final Map<String,Integer> queryCounts =  Collections.synchronizedMap(new HashMap<String,Integer>(1000)); 
        File dir = new File("./dir");
         File[] directoryListing = dir.listFiles();
        
         if (directoryListing != null) {
         
	List<Thread> threads = new ArrayList<Thread>();   
             for (File child : directoryListing) {
               Gija a = new Gija();
               threads.add(a);
                a.run(child,queryCounts);
        
            }
         for (Thread curThread : threads) {
	    try {
		// starting from the first wait for each one to finish.
		curThread.join();
	    } catch (InterruptedException e) {
	
	    }
	}
        try
        {
        PrintWriter writerAG = new PrintWriter("a-g.txt", "UTF-8");
        char[] charArrayAG = new char[] {'a','b','c','d','e','f','g'};
        PrintWriter writerHN = new PrintWriter("h-n.txt", "UTF-8");
        char[] charArrayHN = new char[] {'h','i','j','k','l','m','n'};
        PrintWriter writerOU = new PrintWriter("o-u.txt", "UTF-8");
        char[] charArrayOU = new char[] {'o','p','r','s','t','u'};
        PrintWriter writerVZ = new PrintWriter("v-z.txt", "UTF-8");
        char[] charArrayVZ = new char[] {'v','z'};
        
        
        
        
        

        Iterator it = queryCounts.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry entry = (Map.Entry)it.next();
       
            
            for (char c : charArrayAG)
            {
                if (entry.getKey().toString().startsWith(String.valueOf(c))) writerAG.write(entry.getValue() + " " + entry.getKey());
            }
            for (char c : charArrayHN)
            {
                if (entry.getKey().toString().startsWith(String.valueOf(c))) writerHN.write(entry.getValue() + " " + entry.getKey());
            }
            for (char c : charArrayOU)
            {
                if (entry.getKey().toString().startsWith(String.valueOf(c))) writerOU.write(entry.getValue() + " " + entry.getKey());
            }
            for (char c : charArrayVZ)
            {
                if (entry.getKey().toString().startsWith(String.valueOf(c))) writerVZ.write(entry.getValue() + " " + entry.getKey());
            }
            
        }
    writerAG.close();
    writerHN.close();
    writerOU.close();
    writerVZ.close();
        }
        catch (Exception e)
        {
            
        }


        
        
        
         
         }
         else return "Jokiu failu neikelta";
         return "done";
    
}

}
