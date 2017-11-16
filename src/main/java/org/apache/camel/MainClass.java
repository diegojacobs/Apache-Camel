package org.apache.camel;

import org.apache.camel.main.Main;
import org.apache.log4j.BasicConfigurator;

/**
*
* @author Diego Jacobs 13160 - Daniel Orozco 13312
*/
public class MainClass {
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
        route.addFacebookId("rusia2018.comunidad");
        route.addFacebookId("2018MundialRusia");
        route.addFacebookId("MundialRussia2018.fifa");
        route.addFacebookId("fifaworldcup");
        
        // add Twitter Accounts
        route.addTwitterAccount("diegojacobs95");
        route.addTwitterAccount("DA_Orozco_");

        // add our routes to Camel
        main.addRouteBuilder(route);

        // and run, which keeps blocking until we terminate the JVM (or stop CamelContext)
        main.run();
    }
}
