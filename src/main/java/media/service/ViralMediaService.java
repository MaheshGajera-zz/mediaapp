package media.service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import media.api.ApiMainController;
import media.model.MediaData;
import media.model.MediaGroup;

@Service
public class ViralMediaService {

	@Value("${media.database.root.path}")
	private String mediaDataRootPath;

	private final static String APP_NAME = "viralmedia";
	private final static String HOME_GROUP_NAME = "Home";

	private String generatePathToRootDir(String subPath) {
		return mediaDataRootPath + "/" + APP_NAME + "/" + subPath;
	}

	private String generateStaticURL(String baseUrl, String subPath, String fileName) {
		return baseUrl.replace("**", APP_NAME + "/" + subPath + "/" + fileName);
	}


	public String[] loadMediaGroupNames() {
		String[] groups = null;

		try {
			String scanPath = generatePathToRootDir("");
			File file = new File(scanPath);
			groups = file.list(new FilenameFilter() {
				@Override
				public boolean accept(File current, String name) {
					return new File(current, name).isDirectory();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return groups;
	}

	public List<MediaGroup> loadHomeMediaList() {

		List<MediaGroup> mediaGroupList = new ArrayList<>();
		String scanPath = generatePathToRootDir(HOME_GROUP_NAME);
		try {
			File file = new File(scanPath);
			String[] groups = file.list(new FilenameFilter() {
				@Override
				public boolean accept(File current, String name) {
					return new File(current, name).isDirectory();
				}
			});

			for (String groupName : groups) {
				mediaGroupList.add(loadMediaGroup(groupName, HOME_GROUP_NAME));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mediaGroupList;
	}

	public List<MediaGroup> loadOtherMediaList() {
		List<MediaGroup> mediaGroupList = new ArrayList<>();
		try {
			String scanPath = generatePathToRootDir("");
			File file = new File(scanPath);
			String[] groups = file.list(new FilenameFilter() {
				@Override
				public boolean accept(File current, String name) {
					return new File(current, name).isDirectory();
				}
			});

			for (String groupName : groups) {
				if (HOME_GROUP_NAME.equals(groupName))
					continue;

				mediaGroupList.add(loadMediaGroup(groupName, ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mediaGroupList;
	}

	public MediaGroup loadMediaGroup(String groupName, String subDirectory) {

		MediaGroup mediaGroup = new MediaGroup();
		mediaGroup.setGroupName(groupName.replaceAll("-", " "));

		String subPath = StringUtils.isEmpty(subDirectory) ? groupName : subDirectory + "/" + groupName;

		String groupPath = generatePathToRootDir(subPath);

		mediaGroup.setGroupPath(groupPath);

		String baseUrl = MvcUriComponentsBuilder.fromMethodName(ApiMainController.class, "getStaticData", "")
				.build().toString();

		try {
			File file = new File(groupPath);
			String[] files = file.list(new FilenameFilter() {
				@Override
				public boolean accept(File current, String name) {
					File mediaFile = new File(current, name);
					return mediaFile.isFile() && !mediaFile.isHidden();
				}
			});

			for (String fileName : files) {
				String url = generateStaticURL(baseUrl, subPath, fileName);
				mediaGroup.addMediaData(new MediaData(fileName, url));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mediaGroup;
	}
}
