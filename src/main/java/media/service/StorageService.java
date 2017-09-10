package media.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class StorageService {

    public void store(MultipartFile file) {
    	try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get( "/home/mahesh/mediadata/viralmedia/images/" + file.getOriginalFilename());
            Files.write(path, bytes);

            System.out.println(
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        }
    	catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stream<Path> loadAll() {
    	try {
	    	Path path = Paths.get( "/home/mahesh/mediadata/viralmedia/images" );
	    	return Files.list( path );
    	}
    	catch (IOException e) {
            e.printStackTrace();
        }
    	return null;
    }

    public Resource loadAsResource(String filename) {
    	
    	try {
    		File file = new File( "/home/mahesh/mediadata/viralmedia/images/" + filename );
        	Path path = Paths.get(file.getAbsolutePath());
			return new ByteArrayResource(Files.readAllBytes(path));
		} 
    	catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }

}
