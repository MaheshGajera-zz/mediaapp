package media.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import media.model.MediaGroup;

@RestController
public class MediaController {

	
	@Autowired
	private MediaService mediaService;
	
    @RequestMapping("/")
    public String home() {
        return "Hello there";
    }

    @RequestMapping(value = "/api", method = RequestMethod.GET)
    public @ResponseBody List<MediaGroup> apiHome() {

        return mediaService.loadMediaGroupList();
    }

    @RequestMapping(value = "/api/{groupName}", method = RequestMethod.GET)
    public @ResponseBody MediaGroup mediaDataByType( @PathVariable String groupName ) {
        return mediaService.loadMediaGroup( groupName, null  );
    }
}
