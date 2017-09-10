package media.console;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConsoleController {

	@RequestMapping("/console")
	public String console( Map<String, Object> model ) {
		
		
		System.out.println( "Console Home Accessed" );
		model.put("message", "123344566");
		return "console";
	}
}
