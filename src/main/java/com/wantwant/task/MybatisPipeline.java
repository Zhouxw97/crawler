package com.wantwant.task;

import com.wantwant.entity.po.JdItemPo;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class MybatisPipeline implements Pipeline {


    @Override
    public void process(ResultItems resultItems, Task task) {
        JdItemPo jdItemPo = resultItems.get("jdItemPo");

        System.out.println("jdItemPo = " + jdItemPo);
    }


}
