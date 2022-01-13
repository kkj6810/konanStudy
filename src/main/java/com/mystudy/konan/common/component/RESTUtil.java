package com.mystudy.konan.common.component;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RESTUtil {
    private static final Logger logger = LoggerFactory.getLogger(RESTUtil.class);

    @Value("${engine.charsetType}")
    private String charsetType;

    @Value("${HTTP_TIMEOUT}")
    private int timeout;

    public String request(String targetURL) {
        return request(targetURL, null, "GET");
    }

    public String request(String targetURL, String payload, String httpMethod) {
        return request(targetURL, payload, httpMethod, charsetType);
    }

    public String request(String targetURL, String payload, String httpMethod, final String charset) {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    HttpEntity entity = response.getEntity();
                    if ((status >= 200 && status < 300) || status == 400) {
                        return entity != null ? EntityUtils.toString(entity, charset) : null;
                    } else {
                        throw new IOException();
                    }
                }
            };

            String responseBody;

            if ("POST".equalsIgnoreCase(httpMethod)) { //create
                HttpPost http = new HttpPost(targetURL);
                //time out config
                RequestConfig requestConfig = RequestConfig.custom()
                        .setSocketTimeout(timeout)
                        .setConnectTimeout(timeout)
                        .setConnectionRequestTimeout(timeout)
                        .build();
                http.setConfig(requestConfig);
                http.setEntity(new StringEntity(payload, charset));
                responseBody = httpclient.execute(http, responseHandler);
            } else if ("PUT".equalsIgnoreCase(httpMethod)) { //update
                HttpPut http = new HttpPut(targetURL);
                //time out config
                RequestConfig requestConfig = RequestConfig.custom()
                        .setSocketTimeout(timeout)
                        .setConnectTimeout(timeout)
                        .setConnectionRequestTimeout(timeout)
                        .build();
                http.setConfig(requestConfig);
                http.setHeader("Accept", "application/json");
                http.setHeader("Content-type", "text/plain; charset=" + charset);
                if (payload != null)
                    http.setEntity(new StringEntity(payload, charset));
                responseBody = httpclient.execute(http, responseHandler);
            } else if ("DELETE".equalsIgnoreCase(httpMethod)) { //delete
                HttpDelete http = new HttpDelete(targetURL);
                //time out config
                RequestConfig requestConfig = RequestConfig.custom()
                        .setSocketTimeout(timeout)
                        .setConnectTimeout(timeout)
                        .setConnectionRequestTimeout(timeout)
                        .build();
                http.setConfig(requestConfig);
                responseBody = httpclient.execute(http, responseHandler);
            } else { //get
                HttpGet http = new HttpGet(targetURL);
                //time out config
                RequestConfig requestConfig = RequestConfig.custom()
                        .setSocketTimeout(timeout)
                        .setConnectTimeout(timeout)
                        .setConnectionRequestTimeout(timeout)
                        .build();
                http.setConfig(requestConfig);
                responseBody = httpclient.execute(http, responseHandler);
            }
            //logger.debug("responseBody : " + responseBody );
            return responseBody;
        } catch (Exception e) {
            logger.error("responseBody fail : " + logger.getName() + " \n" + e.getMessage());
            return null;
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
            }
        }
    }
}