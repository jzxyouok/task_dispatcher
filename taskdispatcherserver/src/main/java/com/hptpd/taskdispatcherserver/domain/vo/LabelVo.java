package com.hptpd.taskdispatcherserver.domain.vo;


import com.google.common.collect.Lists;
import com.hptpd.taskdispatcherserver.common.util.AbstractMyBeanUtils;
import com.hptpd.taskdispatcherserver.domain.Label;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-15 09:38
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@Data
public class LabelVo {

    private String id;

    private String labelName;


    private Date creatTime;


    private String creatUser;


    /**
     * @param labels
     * @return
     */
    public static List<LabelVo> labelToVo(List<Label> labels){
        List<LabelVo> labelVos = Lists.newArrayList();
        for (Label label:labels){
            LabelVo labelVo =new LabelVo();
            AbstractMyBeanUtils.copyProperties(label,labelVo);
            labelVos.add(labelVo);
        }
        return labelVos;
    }
}
