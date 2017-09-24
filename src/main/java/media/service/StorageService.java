package media.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

	@Value("${media.database.root.path}")
	private String mediaDataRootPath;



	public byte[] load( String filePath ) throws IOException {
		File file = new File( mediaDataRootPath + "/" + filePath );

        Path path = Paths.get(file.getAbsolutePath());
        return Files.readAllBytes(path);
	}

	public void store(String filePath, MultipartFile file) {
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + "/" + file.getOriginalFilename());
			Files.write(path, bytes);

			System.out.println("successfully saved '" + path + "'");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void remove(String filePath, String fileName) {
		Path path = Paths.get(filePath + "/" + fileName);

		try {
			Files.delete(path);
			System.out.println("successfully deleted '" + path + "'");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
