package media.service;

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
public class ViralMediaService {

	@Value("${media.database.root.path}")
	private String mediaDataRootPath;

	private String generatePathToRootDir( String subPath ) {
		return mediaDataRootPath + "/viralmedia/" + subPath;
	}

	private String generateStaticBaseURL( String rootUrl, String subPath ) {
		return rootUrl + "/static/viralmedia/" + subPath + "/";
	}
	
	public List<MediaGroup> loadHomeMediaList( String urlBase ) {

		List<MediaGroup> mediaGroupList = new ArrayList<>();
	    String scanPath = generatePathToRootDir( "home" );
        try {
            File file = new File( scanPath );
            String[] groups = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                  return new File(current, name).isDirectory();
                }
            });
            
            for( String groupName : groups ) {
            	mediaGroupList.add( loadMediaGroup( urlBase, groupName, "home" ) );
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
    	
    	String subPath = StringUtils.isEmpty( subDirectory )
    		? groupName : subDirectory + "/" + groupName;

    	String staticBaseURL = generateStaticBaseURL( urlBase, subPath );
    	
		try {
            File file = new File( generatePathToRootDir( subPath ) );
            String[] files = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                	File mediaFile = new File(current, name);
                    return mediaFile.isFile() && ! mediaFile.isHidden();
                }
            });

            for( String fileName : files ) {
            	String url =  staticBaseURL + fileName;

            	mediaGroup.addMediaData( new MediaData(fileName, url) );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

		return mediaGroup;
	}
}
