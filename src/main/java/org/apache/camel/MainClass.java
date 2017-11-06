package org.apache.camel;

import org.apache.camel.main.Main;
import org.apache.log4j.BasicConfigurator;

/**
*
* @author Diego Jacobs 13160 - Daniel Orozco 13312
*/
public class MainClass {
	//Twitter
//Jewish
//    private static String consumerKey = "EggHAYHLpVR0Pqdky20q43xhd";
//    private static String consumerSecret = "XYIzhjOYHBsgCDiU00EdFOH5kdwc1hqz7VF7zv1NOt7B2DakAW";
//    private static String accessToken = "69966536-GDW8RAFjZzoDpb8wZC7dgoK7C6gi46OLkD3oqoOcC";
//    private static String accessTokenSecret = "TWEjUDtNxeHZAGRO6SMUsJ50SOjnfVgyzuLC8ZetdXYP9";
//    
//    //Facebook
//    private static String OAuthAppId = "148937112519047";
//    private static String OAuthAppSecret = "72c87306b3677c1606df72da92f9448f";

//Orozco
    private static String consumerKey = "I0YFzQbnsaKczffTjKVV4srez";
    private static String consumerSecret = "cnephOqbAcfSxLYjzCovDjsM0e59TK5BpAcFL8fKuwgrBjwiNn";
    private static String accessToken = "362731310-2g1o2M17nI6tgH5QQpmL0VvURoI2KP2ZS4htV6Dy";
    private static String accessTokenSecret = "2CDrhK1rfzUgsfwM8pIgwLjejuxh5JAn7Bjjgoo5XZTSQ";
    
    //Facebook
    private static String OAuthAppId = "1224717607673316";
    private static String OAuthAppSecret = "7b3a69a4845e392dec65f0f31deb47d5";

    
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
        route.addFacebookId("somosinvictos");
        route.addFacebookId("sport.es");
        route.addFacebookId("MundoDeportivo.com");
        route.addFacebookId("Marca");
        
        // add Tiwtter Accounts
        route.addTwitterAccount("diegojacobs95");
        route.addTwitterAccount("DA_Orozco_");

        // add our routes to Camel
        main.addRouteBuilder(route);

        // and run, which keeps blocking until we terminate the JVM (or stop CamelContext)
        main.run();
    }
}
