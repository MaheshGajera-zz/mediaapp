package media.model;

import java.util.ArrayList;
import java.util.List;

public class MediaGroup {

	private String groupName;

	private List<MediaData> mediaDataList = new ArrayList<>();


	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<MediaData> getMediaDataList() {
		return mediaDataList;
	}

	public void setMediaDataList(List<MediaData> mediaDataList) {
		this.mediaDataList = mediaDataList;
	}
	
	public void addMediaData( MediaData mediaData ) {
		if ( mediaData == null ) return;
		
		mediaDataList.add( mediaData );
	}

	public void addMediaData( List<MediaData> mediaData ) {
		if ( mediaData == null ) return;
		
		mediaDataList.addAll( mediaData );
	}
}
