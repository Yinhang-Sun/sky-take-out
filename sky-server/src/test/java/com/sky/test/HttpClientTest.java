package com.sky.test;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class HttpClientTest {

    /**
     * Test sending GET request through httpclient
     * @throws IOException
     */
    @Test
    public void testGet() throws IOException {
        //create Httpclient object
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //create request object
        HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");

        //send request, and accept response
        CloseableHttpResponse response = httpClient.execute(httpGet);

        //get the status code return from server
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Status code returned from server: " + statusCode);

        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println("Data returned from server: " + body);

        //close resources
        response.close();
        httpClient.close();
    }

    /**
     * Test sending GET request through httpclient
     */
    @Test
    public void testPost() throws Exception {

        //create Httpclient object
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //create request object
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "admin");
        jsonObject.put("password", "123456");

        StringEntity entity = new StringEntity(jsonObject.toString());

        //Specify request encoding method
        entity.setContentEncoding("UTF-8");

        //Specify data format
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        //send request, and accept response
        CloseableHttpResponse response = httpClient.execute(httpPost);

        //parse returned data
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response code returned from server: " + statusCode);

        HttpEntity entity1 = response.getEntity();
        String body = EntityUtils.toString(entity1);
        System.out.println("Data returned from server: " + body);

        //close resources
        response.close();
        httpClient.close();


    }
}
