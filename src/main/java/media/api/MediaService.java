package media.api;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import media.model.MediaData;
import media.model.MediaGroup;


@Service
public class MediaService {

	@Value("${media.database.root.path}")
	private String mediaDataRootPath;

	public List<MediaGroup> loadMediaGroupList() {

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
            	mediaGroupList.add( loadMediaGroup( groupName, "groups" ) );
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

		return mediaGroupList;
	}

	public MediaGroup loadMediaGroup( String groupName, String subDirectory ) {

    	MediaGroup mediaGroup = new MediaGroup();
    	mediaGroup.setGroupName( groupName );
    	
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
            	mediaGroup.addMediaData( new MediaData(fileName, "http://localhost:8080/static/" + path + "/" + fileName) );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

		return mediaGroup;
	}	
}
