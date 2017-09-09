package media.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
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
    public @ResponseBody List<MediaGroup> apiHome( HttpServletRequest request ) {

        return mediaService.loadMediaGroupList( getURLBase( request ) );
    }

    @RequestMapping(value = "/api/{groupName}", method = RequestMethod.GET)
    public @ResponseBody MediaGroup mediaDataByType( @PathVariable String groupName, HttpServletRequest request ) {
        return mediaService.loadMediaGroup( getURLBase( request ), groupName, null  );
    }

    @RequestMapping(path = "/static/**", method = RequestMethod.GET)
    public ResponseEntity<Resource> download( HttpServletRequest request ) throws IOException {

    	String filePath = new AntPathMatcher().extractPathWithinPattern( "/static/**", request.getRequestURI() );

    	ByteArrayResource resource = mediaService.loadFileAsByte(filePath);

        return ResponseEntity.ok()
            .contentLength( resource.contentLength() )
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
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
