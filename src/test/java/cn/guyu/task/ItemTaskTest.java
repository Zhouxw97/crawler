package cn.guyu.task;

import cn.guyu.SpringBootTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ItemTaskTest extends SpringBootTest {

    @Autowired
    private ItemTask itemTask;

    @Test
    public void itemTaskTest() throws JsonProcessingException {
        itemTask.itemTask();
    }

}
