package media;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Value("${media.database.root.path}")
	private String mediaDataRootPath;
	
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		System.out.println( mediaDataRootPath  );

		try {
			createSubDirectory( mediaDataRootPath );
			createSubDirectory( mediaDataRootPath + "/groups" );
			createSubDirectory( mediaDataRootPath + "/videos" );
			createSubDirectory( mediaDataRootPath + "/images" );
			createSubDirectory( mediaDataRootPath + "/gifs" );
		}
		catch( Exception e ) {
			System.err.println( e );
		}

		return;
	}
	
	private void createSubDirectory( String path ) {
		File baseDirectory = new File( path );
		if( ! baseDirectory.exists() ) {
			baseDirectory.mkdirs();
		}
	}
}