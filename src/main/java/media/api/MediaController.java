package media.api;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaController {

    @RequestMapping("/")
    public String home() {
        return "Hello there";
    }


    @RequestMapping("/categories")
    public String[] categories() {

        String[] directories = null;
        try {
            File file = new File("./data/categories");
            directories = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                  return new File(current, name).isDirectory();
                }
              });
            System.out.println(Arrays.toString(directories));

        } catch (Exception e) {
            System.err.println( e );
            e.printStackTrace();
        }

        //TODO: Read other gif, videos and images and send in this as json
        return directories;
    }

    @RequestMapping("/category/${categoryName}")
    public String[] category( @PathVariable String categoryName ) {

        String[] directories = null;
        try {
            File file = new File("./data/" + categoryName);
            directories = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                  return new File(current, name).isDirectory();
                }
              });
            System.out.println(Arrays.toString(directories));

        } catch (Exception e) {
            System.err.println( e );
            e.printStackTrace();
        }

        return directories;
    }
}
