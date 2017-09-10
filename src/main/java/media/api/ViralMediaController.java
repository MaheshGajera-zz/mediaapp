package media.api;

import java.net.MalformedURLException;
import java.net.URL;
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
    public @ResponseBody List<MediaGroup> apiHome( HttpServletRequest request ) {

        return viralMediaService.loadHomeMediaList( getURLBase( request ) );
    }

    @RequestMapping(value = "/api/viralmedia/{groupName}", method = RequestMethod.GET)
    public @ResponseBody MediaGroup mediaDataByType( @PathVariable String groupName, HttpServletRequest request ) {
        return viralMediaService.loadMediaGroup( getURLBase( request ), groupName, null  );
    }

    public String getURLBase(HttpServletRequest request) {

    	try {
	        URL requestURL = new URL(request.getRequestURL().toString());
	        String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
	        return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
    	}
    	catch ( MalformedURLException e ) {
    		e.printStackTrace();
    	}

    	return "";
    }
}
