package org.apache.camel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.twitter.TwitterComponent;
import org.apache.camel.component.facebook.FacebookComponent;
import org.apache.camel.component.facebook.FacebookConstants;
import org.apache.camel.component.facebook.config.FacebookConfiguration;
import java.util.ArrayList;

/**
*
* @author Diego Jacobs 13160
*/
public class TwitterFacebookRoute extends RouteBuilder {

    private String searchTerm;
    private int delay = 2;
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;
    private String OAuthAppId;
    private String OAuthAppSecret;
    private ArrayList<String> fbIds;
    private ArrayList<String> twitterAccounts;
    
    public TwitterFacebookRoute() {
        fbIds = new ArrayList<>();
        twitterAccounts = new ArrayList<>();
    }

    public void addFacebookId(String fbId)
    {
        fbIds.add(fbId);
    }
    
    public void addTwitterAccount(String twitterAccount)
    {
    	twitterAccounts.add(twitterAccount);
    }
    
    public String getOAuthAppId() {
        return OAuthAppId;
    }

    public void setOAuthAppId(String OAuthAppId) {
        this.OAuthAppId = OAuthAppId;
    }

    public String getOAuthAppSecret() {
        return OAuthAppSecret;
    }

    public void setOAuthAppSecret(String OAuthAppSecret) {
        this.OAuthAppSecret = OAuthAppSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public void configure() throws Exception {
        // Twitter component
        TwitterComponent tc = getContext().getComponent("twitter", TwitterComponent.class);
        tc.setAccessToken(accessToken);
        tc.setAccessTokenSecret(accessTokenSecret);
        tc.setConsumerKey(consumerKey);
        tc.setConsumerSecret(consumerSecret);

        // Facebook component
        FacebookComponent fc = getContext().getComponent("facebook", FacebookComponent.class);
        FacebookConfiguration fConfig = new FacebookConfiguration();
        fConfig.setOAuthAppId(OAuthAppId);
        fConfig.setOAuthAppSecret(OAuthAppSecret);
        fc.setConfiguration(fConfig);

     
        // Procesador
        Processor processor = new FacebookPostFilter();        

        // Fecha desde cuando se desea contenido
        String since = "RAW(" + new SimpleDateFormat(FacebookConstants.FACEBOOK_DATE_FORMAT).format(
                    new Date(System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(15, TimeUnit.DAYS))) + ")";

        //Crear rutas de origen para cada pagina que se desea
        for(String fbId: this.fbIds)
        {
        	from("facebook://getFeed?userId=" 
                    + fbId + "&reading.limit=2&reading.since="
                    + since + "&consumer.initialDelay=1000&consumer.delay=3000&consumer.sendEmptyMessageWhenIdle=true")
                    .to("direct:aggregateRoute");
        }
        
    	//Ejecutar el resto del ruteo
        from("direct:aggregateRoute")
            .process(processor)
            .filter(header("post").isEqualTo("yes"))
            .choice()
            .when(header("Content-Type").isEqualTo("rusia"))
            .to("twitter://directmessage?user=" + this.twitterAccounts.get(0))
            .otherwise()
            .to("twitter://directmessage?user=" + this.twitterAccounts.get(1));
    }
}
