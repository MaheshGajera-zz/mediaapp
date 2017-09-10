package media.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class MediaDataService {

	@Value("${media.database.root.path}")
	private String mediaDataRootPath;



	public byte[] loadFileAsByte( String filePath ) throws IOException {
		File file = new File( mediaDataRootPath + "/" + filePath );

        Path path = Paths.get(file.getAbsolutePath());
        return Files.readAllBytes(path);
	}
}
