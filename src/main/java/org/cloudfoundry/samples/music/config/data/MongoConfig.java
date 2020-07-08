package org.cloudfoundry.samples.music.config.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@Profile("mongodb")
public class MongoConfig {

    @Autowired
    private Environment env;

    @Bean
    public MongoClient mongoClient() throws JsonProcessingException {
        JsonNode config = new ObjectMapper().readTree(this.env.getProperty("VCAP_SERVICES"));
        String userName = config.findValue("username").asText();
        String password = config.findValue("password").asText();
        String uri = config.findValue("uri").asText();
        String connectionString = uri.replaceFirst("mongodb\\+srv://", "mongodb+srv://" + userName + ":" + password + "@") + "/test";
        return MongoClients.create(connectionString);
    }
    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

}
