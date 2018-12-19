package com.walker.lock.web;

import com.walker.lock.LockApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author walker
 * @date 2018/12/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void concurrentComment() {
        String url = "http://localhost:9090/comment";
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(() -> {
                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("articleId", "1");
                params.add("content", "测试内容" + finalI);
                String result = testRestTemplate.postForObject(url, params, String.class);
            }).start();
        }
    }
}