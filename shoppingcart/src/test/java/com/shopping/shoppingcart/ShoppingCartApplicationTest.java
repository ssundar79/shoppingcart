package com.shopping.shoppingcart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.restlet.data.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.shopping.config.MongoConfig;
import com.shopping.constants.Constants;
import com.shopping.data.Request;
import com.shopping.domain.Product;
import com.shopping.logger.ILogger;
import com.shopping.logger.LoggerFactory;


@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("mock")
@SpringBootTest
public class ShoppingCartApplicationTest {

    private ILogger logger = LoggerFactory.getBusinessLogger(ShoppingCartApplicationTest.class);

    @Autowired
    CamelContext camelContext;

    @Autowired
    ProducerTemplate producerTemplate;

    
    @Before
	public void initMocks() throws Exception{
		MockitoAnnotations.initMocks(this);
		RouteDefinition routeDef = camelContext.getRouteDefinition("producerRoute");
		routeDef.adviceWith(camelContext, new AdviceWithRouteBuilder() {
		        @Override
		        public void configure() throws Exception {
		        	interceptSendToEndpoint("mock:foo")
					.skipSendToOriginalEndpoint()
					.to("mock:out");
		   		}
		    });
	}
    
    @Test
    public void testRouteWithValidJson() throws Exception {
     
        logger.info("Start testRouteWithValidJson");


        Request req = new Request();
        req.setProductID(110);
        req.setProductName("SAMSUNG 8");
        req.setProductPrice(900);
        String excepetdResult="SUCCESSFULLY";
        
        String myJSON = convertObjectToJsonBytes(req);

        String actualOutput = producerTemplate.requestBody("{{testRoute}}", myJSON, String.class);
      
     
            logger.info("jsonResult--->"+actualOutput);
      

            assertTrue(actualOutput.contains(excepetdResult));
        logger.info("End testRouteWithValidJson");

    }


   

    private static String convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }
}
