package media.console;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import media.service.StorageService;
import media.service.ViralMediaService;

@Controller
public class ConsoleController {
	
	@Autowired
    private StorageService storageService;

	@Autowired
	private ViralMediaService viralMediaService;
	
    @GetMapping("/console")
    public String listUploadedFiles(Model model) throws IOException {
    	
    	model.addAttribute("otherGroups", viralMediaService.loadOtherMediaList() );
    	model.addAttribute("homeGroups", viralMediaService.loadHomeMediaList() );

        return "console";
    }

    @PostMapping("/createGroup")
    public String createGroup( RedirectAttributes redirectAttributes,
    		HttpServletRequest request ) {
    	
    	String dateStore = request.getParameter( "dateStore" );
    	String parentGroup = request.getParameter( "parentGroup" );
    	String newGroupName = request.getParameter( "newGroupName" );

    	try {

    		storageService.createGroup( dateStore, parentGroup, newGroupName.replaceAll("\\s+", "-") );
    		redirectAttributes.addFlashAttribute("msgType", "successMsg");
    		redirectAttributes.addFlashAttribute("message",
        		"Successfully created new Group " + newGroupName  
	        		+ " under " + parentGroup + " in " + dateStore + "!");
    	}
    	catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msgType", "failedMsg");
			redirectAttributes.addFlashAttribute("message",
	        	"Failed to create new Group " + newGroupName
		        	+ " under " + parentGroup + " in " + dateStore + "!");
		}
    	
        return "redirect:/console";
    }
    
    @GetMapping("/removeGroup")
    public String removeGroup( RedirectAttributes redirectAttributes,
    		HttpServletRequest request ) {
    	
    	String groupPath = request.getParameter( "groupPath" );
    	String groupName = groupPath.substring( groupPath.lastIndexOf('/') + 1 );
    	try {

    		storageService.removeGroup( groupPath );
    		redirectAttributes.addFlashAttribute("msgType", "successMsg");
        	redirectAttributes.addFlashAttribute("message",
        		"Successfully deleted group " + groupName + "!");
    	}
    	catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msgType", "failedMsg");
			redirectAttributes.addFlashAttribute("message",
	        	"Failed to delete Group " + groupName + "!");
		}
    	
        return "redirect:/console";
    }
    
    @PostMapping("/uploadFile")
    public String uploadFile(
    		@RequestParam("filePath") String filePath,
    		@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

    	try {
	        storageService.storeFile(filePath, file);
	        redirectAttributes.addFlashAttribute("msgType", "successMsg");
	        redirectAttributes.addFlashAttribute("message",
                "Successfully uploaded " + file.getOriginalFilename() + "!");

    	}
    	catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msgType", "failedMsg");
			redirectAttributes.addFlashAttribute("message",
	                "Failed to upload " + file.getOriginalFilename() + "!");
		}

        return "redirect:/console";
    }

    @GetMapping("/removeFile/{fileName:.+}")
    public String removeFile( @PathVariable String fileName, 
    		RedirectAttributes redirectAttributes, HttpServletRequest request ) {
    	
    	String filePath = request.getParameter( "filePath" );

    	try {
	        storageService.removeFile(filePath, fileName);
	        redirectAttributes.addFlashAttribute("msgType", "successMsg");
	        redirectAttributes.addFlashAttribute("message",
                "Successfully deleted " + fileName + "!");

    	}
    	catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msgType", "failedMsg");
			redirectAttributes.addFlashAttribute("message",
	                "Failed to deleted " + fileName + "!");
		}
    	
        return "redirect:/console";
    }
}
