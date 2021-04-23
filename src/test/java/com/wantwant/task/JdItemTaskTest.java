package com.wantwant.task;

import com.wantwant.SpringBootTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class JdItemTaskTest extends SpringBootTest {

    @Autowired
    private JdItemTask jdItemTask;

    @Test
    public void JditemTaskTest() {
        jdItemTask.doJdItemTask(2);
    }

}
