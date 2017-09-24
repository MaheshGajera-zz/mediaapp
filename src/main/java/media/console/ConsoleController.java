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

    @PostMapping("/uploadFile")
    public String handleFileUpload(
    		@RequestParam("filePath") String filePath,
    		@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

    	System.out.println( "upload filePath : " + filePath );
    	
        storageService.store(filePath, file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/console";
    }

    @GetMapping("/removeFile/{fileName:.+}")
    public String removeFile( @PathVariable String fileName, 
    		RedirectAttributes redirectAttributes, HttpServletRequest request ) {
    	String filePath = request.getParameter( "filePath" );
        
    	System.out.println( "remove filePath : " + filePath );
    	
        storageService.remove(filePath, fileName);
        redirectAttributes.addFlashAttribute("message",
                "You successfully removed " + fileName + "!");

        return "redirect:/console";
    }
}
