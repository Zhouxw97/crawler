package com.wantwant.task;

import com.wantwant.entity.po.JdItemPo;
import com.wantwant.entity.vo.JdItemVo;
import com.wantwant.service.JdItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class MybatisPipeline implements Pipeline {

    @Autowired
    protected JdItemService jdItemService;

    private static MybatisPipeline mybatisPipeline;

    @PostConstruct
    public void init(){
        mybatisPipeline = this;
        mybatisPipeline.jdItemService = this.jdItemService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        JdItemPo jdItemPo = resultItems.get("jdItemPo");
        log.info("jdItemPo:{}",jdItemPo);
        if (Objects.nonNull(jdItemPo) && !StringUtils.isEmpty(jdItemPo.getSku())) {
            System.out.println("jdItemPo.getSku() = " + jdItemPo.getSku());

            List<JdItemVo> itemBySku = mybatisPipeline.jdItemService.getItemBySku(jdItemPo.getSku());
            if (CollectionUtils.isEmpty(itemBySku)){
                mybatisPipeline.jdItemService.saveJdItem(jdItemPo);
            }
        }

    }


}
