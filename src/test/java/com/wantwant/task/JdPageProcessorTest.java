package com.wantwant.task;

import com.wantwant.SpringBootTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JdPageProcessorTest extends SpringBootTest {

    @Autowired
    private JdPageProcessor jdPageProcessor;

    @Test
    public void processTest(){
        jdPageProcessor.doProcess();
    }
}
