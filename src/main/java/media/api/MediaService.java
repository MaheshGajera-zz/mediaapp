package media.api;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import media.model.MediaData;
import media.model.MediaGroup;


@Service
public class MediaService {

	@Value("${media.database.root.path}")
	private String mediaDataRootPath;

	public List<MediaGroup> loadMediaGroupList( String urlBase ) {

		List<MediaGroup> mediaGroupList = new ArrayList<>();
	    String scanPath = mediaDataRootPath + "/groups";
        try {
            File file = new File( scanPath );
            String[] groups = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                  return new File(current, name).isDirectory();
                }
            });
            
            for( String groupName : groups ) {
            	mediaGroupList.add( loadMediaGroup( urlBase, groupName, "groups" ) );
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

		return mediaGroupList;
	}

	public MediaGroup loadMediaGroup( String urlBase, String groupName, String subDirectory ) {

    	MediaGroup mediaGroup = new MediaGroup();
    	mediaGroup.setGroupName( groupName.replaceAll("-", " ") );
    	
    	String path = StringUtils.isEmpty( subDirectory )
    		? groupName : subDirectory + "/" + groupName;

		try {
            File file = new File( mediaDataRootPath + "/" + path );
            String[] files = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                	File mediaFile = new File(current, name);
                    return mediaFile.isFile() && ! mediaFile.isHidden();
                }
            });

            for( String fileName : files ) {
            	String url = urlBase + "/static/" + path + "/" + fileName;

            	mediaGroup.addMediaData( new MediaData(fileName, url) );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

		return mediaGroup;
	}
	
	public ByteArrayResource loadFileAsByte( String filePath ) throws IOException {
		File file = new File( mediaDataRootPath + "/" + filePath );

        Path path = Paths.get(file.getAbsolutePath());
        return new ByteArrayResource(Files.readAllBytes(path));
	}
}
