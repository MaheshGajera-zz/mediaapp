package media.api;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.ResponseData;

@RestController
public class MediaController {

	
	@Autowired
	private MediaService mediaService;
	
    @RequestMapping("/")
    public String home() {
        return "Hello there";
    }

    @RequestMapping(value = "/api", method = RequestMethod.GET)
    public @ResponseBody ResponseData categories() {

        return mediaService.loadMediaFileList();
    }

    @RequestMapping(value = "/api/category/{categoryName}", method = RequestMethod.GET)
    public @ResponseBody String[] category( @PathVariable String categoryName ) {

    	System.out.println( "category : " + categoryName );
    	
        String[] directories = null;
        try {
            File file = new File("./data/categories/" + categoryName);
            directories = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                  return new File(current, name).isFile();
                }
              });
            System.out.println(Arrays.toString(directories));

        } 
        catch (Exception e) {
            System.err.println( e );
            e.printStackTrace();
        }

        return directories;
    }
}
