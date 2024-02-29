package com.dawn.shortlink.domain.task;

import com.dawn.shortlink.dao.mappers.UrlMapper;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class TimedCleanJob extends QuartzJobBean {

    @Autowired
    UrlMapper urlMapper;

    /*
    定时删除所有过期数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        urlMapper.deleteExpired(new Date());
    }
}
