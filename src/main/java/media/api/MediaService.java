package media.api;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import model.MediaData;
import model.ResponseData;

@Service
public class MediaService {

	private final static String DATA_ROOT_PATH = "./data";
	
	public ResponseData loadMediaFileList() {

		ResponseData responseData = new ResponseData();
		
        try {
            File file = new File( DATA_ROOT_PATH + "/categories" );
            String[] categories = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                  return new File(current, name).isDirectory();
                }
            });
            responseData.setCategories(categories);
            
            responseData.addMediaData( loadMediaData( "image" ) );
            responseData.addMediaData( loadMediaData( "video" ) );
            responseData.addMediaData( loadMediaData( "gif" ) );
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

		return responseData;
	}

	private List<MediaData> loadMediaData( String type ) {
		
		List<MediaData> mediaDataList = new ArrayList<>();
		
		try {
            File file = new File( DATA_ROOT_PATH + "/" + type );
            String[] files = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                	File mediaFile = new File(current, name);
                    return mediaFile.isFile() && ! mediaFile.isHidden();
                }
            });

            for( String fileName : files ) {
            	mediaDataList.add( new MediaData(fileName, type, DATA_ROOT_PATH + "/" + type + "/" + fileName) );
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
	
		return mediaDataList;
	}	
}
