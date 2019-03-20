package com.hptpd.taskdispatcherserver;

import com.hptpd.taskdispatcherserver.domain.Label;
import com.hptpd.taskdispatcherserver.repository.LabelRep;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskDispatcherServerApplicationTests {

    @Resource(name = "labelRep")
    private LabelRep labelRep;

    @Test
    public void contextLoads() {
        List<Label> labels = Lists.newArrayList();
        Label label =new Label();
        label.setLabelName("事务型");
        labels.add(label);

        Label label1 =new Label();
        label1.setLabelName("商务沟通");
        labels.add(label1);

        labelRep.saveAll(labels);


    }

}
