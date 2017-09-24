package media.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import media.model.MediaGroup;
import media.service.ViralMediaService;

@RestController
public class ViralMediaController {

	
	@Autowired
	private ViralMediaService viralMediaService;

    @RequestMapping(value = "/api/viralmedia", method = RequestMethod.GET)
    public @ResponseBody String[] listMainGroups( HttpServletRequest request ) {

        return viralMediaService.loadMediaGroupNames();
    }

    @RequestMapping(value = "/api/viralmedia/home", method = RequestMethod.GET)
    public @ResponseBody List<MediaGroup> listHomeMediaDetails( HttpServletRequest request ) {

        return viralMediaService.loadHomeMediaList();
    }

    @RequestMapping(value = "/api/viralmedia/other", method = RequestMethod.GET)
    public @ResponseBody List<MediaGroup> listOtherMediaDetails( HttpServletRequest request ) {

        return viralMediaService.loadOtherMediaList();
    }

    @RequestMapping(value = "/api/viralmedia/other/{groupName}", method = RequestMethod.GET)
    public @ResponseBody MediaGroup mediaDataByType( @PathVariable String groupName, HttpServletRequest request ) {
        return viralMediaService.loadMediaGroup( groupName, null  );
    }
}
