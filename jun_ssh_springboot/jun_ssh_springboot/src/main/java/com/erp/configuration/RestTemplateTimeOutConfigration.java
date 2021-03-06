package com.erp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateTimeOutConfigration{
	
	@Value("${erp.timeout.connectionRequestTimeout}")
	private int connectionRequestTimeout;
	@Value("${erp.timeout.connectTimeout}")
	private int connectTimeout;
	@Value("${erp.timeout.readTimeout}")
	private int readTimeout;
	
    @Bean
    public RestTemplate createRestTemplate(){
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
        httpRequestFactory.setConnectTimeout(connectTimeout);
        httpRequestFactory.setReadTimeout(readTimeout);
        return new RestTemplate(httpRequestFactory);
    }
}
