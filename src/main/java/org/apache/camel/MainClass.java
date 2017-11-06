package org.apache.camel;

import org.apache.camel.main.Main;
import org.apache.log4j.BasicConfigurator;

/**
*
* @author Diego Jacobs 13160
*/
public class MainClass {
	//Twitter
    private static String consumerKey = "EggHAYHLpVR0Pqdky20q43xhd";
    private static String consumerSecret = "XYIzhjOYHBsgCDiU00EdFOH5kdwc1hqz7VF7zv1NOt7B2DakAW";
    private static String accessToken = "69966536-GDW8RAFjZzoDpb8wZC7dgoK7C6gi46OLkD3oqoOcC";
    private static String accessTokenSecret = "TWEjUDtNxeHZAGRO6SMUsJ50SOjnfVgyzuLC8ZetdXYP9";
    
    //Facebook
    private static String OAuthAppId = "148937112519047";
    private static String OAuthAppSecret = "72c87306b3677c1606df72da92f9448f";
    
    private MainClass() {
        // to pass checkstyle we have a private constructor
    }

    public static void main(String[] args) throws Exception {
    	BasicConfigurator.configure();
    	
        // create a new Camel Main so we can easily start Camel
        Main main = new Main();

        TwitterFacebookRoute route = new TwitterFacebookRoute();

        // setup twitter application authentication
        route.setAccessToken(accessToken);
        route.setAccessTokenSecret(accessTokenSecret);
        route.setConsumerKey(consumerKey);
        route.setConsumerSecret(consumerSecret);
        
        // setup facebook application authentication
        route.setOAuthAppId(OAuthAppId);
        route.setOAuthAppSecret(OAuthAppSecret);
        
        // add facebook pages
        route.addFacebookId("9gag");
        //route.addFacebookId("fakeuselesspage");

        // add our routes to Camel
        main.addRouteBuilder(route);

        // and run, which keeps blocking until we terminate the JVM (or stop CamelContext)
        main.run();
    }
}
