package media.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

	@Value("${media.database.root.path}")
	private String mediaDataRootPath;

	
	public void createGroup( String dateStore, String parentGroup, String name ) {

		String subPath = "Home".equals( parentGroup ) ? "/Home/" : "/";

		String path = mediaDataRootPath + "/" + dateStore + subPath + name;

		File baseDirectory = new File( path );
		if( ! baseDirectory.exists() ) {
			baseDirectory.mkdirs();
		}
	}
	
	public void removeGroup( String groupPath ) throws IOException {
		Path rootPath = Paths.get( groupPath );
		Files.walk(rootPath)
		    .sorted(Comparator.reverseOrder())
		    .map(Path::toFile)
		    .forEach(File::delete);
	}

	public byte[] loadFile( String filePath ) throws IOException {
		File file = new File( mediaDataRootPath + "/" + filePath );

        Path path = Paths.get(file.getAbsolutePath());
        return Files.readAllBytes(path);
	}

	public void storeFile(String filePath, MultipartFile file) throws IOException {
		byte[] bytes = file.getBytes();
		Path path = Paths.get(filePath + "/" + file.getOriginalFilename());
		Files.write(path, bytes);
	}

	public void removeFile(String filePath, String fileName) throws IOException {
		Path path = Paths.get(filePath + "/" + fileName);
		Files.delete(path);
	}
}
