package media.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import media.service.MediaDataService;

@RestController
public class ApiMainController {

	@Autowired
	private MediaDataService mediaService;

    @RequestMapping("/api")
    public String home() {
        return "API HOME";
    }
	
    @RequestMapping(path = "/static/**", method = RequestMethod.GET)
    public byte[] getStaticData( HttpServletRequest request ) throws IOException {

    	String filePath = new AntPathMatcher().extractPathWithinPattern( "/static/**", request.getRequestURI() );

    	return mediaService.loadFileAsByte(filePath);
    }
}
