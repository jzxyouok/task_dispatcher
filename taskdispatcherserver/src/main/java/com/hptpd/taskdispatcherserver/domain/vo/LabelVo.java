package com.hptpd.taskdispatcherserver.domain.vo;


import lombok.Data;

import java.util.Date;

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
}
