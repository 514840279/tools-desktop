package org.chuxue.desktop;

import org.chuxue.desktop.tools.common.config.SplashScreenCustom;
import org.chuxue.desktop.tools.windows.view.MainView;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;

@SpringBootApplication
@EnableConfigurationProperties
public class ToolsApplication extends AbstractJavaFxApplicationSupport {

	public static void main(String[] args) {
		launch(ToolsApplication.class, MainView.class, new SplashScreenCustom(), args);
	}

}
