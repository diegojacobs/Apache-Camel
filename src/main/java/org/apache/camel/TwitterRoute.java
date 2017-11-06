package org.apache.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.twitter.*;

/**
 * A Camel route that updates from twitter all tweets using having the search term.
 * And post the changes to web-socket, that can be viewed from a web page
 */
public class TwitterRoute extends RouteBuilder {

    private int port = 9090;
    private String searchTerm;
    private int delay = 5000;
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;

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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void configure() throws Exception {
        // setup Twitter component
        TwitterComponent tc = getContext().getComponent("twitter-search", TwitterComponent.class);
        tc.setAccessToken(accessToken);
        tc.setAccessTokenSecret(accessTokenSecret);
        tc.setConsumerKey(consumerKey);
        tc.setConsumerSecret(consumerSecret);

        //Stream from twitter
        fromF("twitter-search://%s?delay=%s", searchTerm, delay)
            .to("log:tweet")
            // and push tweets to web socket
            .to("websocket:camel-tweet?sendToAll=true")
			.to("twitter:timeline/user");
    }
}