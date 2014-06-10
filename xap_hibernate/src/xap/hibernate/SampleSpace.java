package xap.hibernate;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;


public class SampleSpace {
	static UrlSpaceConfigurer configurer = new UrlSpaceConfigurer("jini://*/*/myGrid");
	static GigaSpace gigaSpace = new GigaSpaceConfigurer(configurer).create();
	
	public static void main(String[] args) {
		
	}
}
