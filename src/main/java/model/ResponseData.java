package model;

import java.util.ArrayList;
import java.util.List;

public class ResponseData {

	private String[] categories;

	private List<MediaData> mediaDataList = new ArrayList<>();

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
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
