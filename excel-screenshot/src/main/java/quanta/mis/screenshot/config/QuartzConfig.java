package quanta.mis.screenshot.config;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ParseException;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import quanta.mis.screenshot.job.DeleteFileJob;

import java.util.Objects;

@Configuration
public class QuartzConfig {
    @Bean(name = "detailFactoryBean")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(DeleteFileJob deleteFileJob){
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean ();
        bean.setTargetObject (deleteFileJob);
        bean.setTargetMethod ("executeInternal");
        bean.setConcurrent (false);
        return bean;
    }

    @Bean(name = "cronTriggerBean")
    public CronTriggerFactoryBean cronTriggerBean(@Qualifier("detailFactoryBean") MethodInvokingJobDetailFactoryBean detailFactoryBean){
        CronTriggerFactoryBean tiger = new CronTriggerFactoryBean();
        tiger.setJobDetail (Objects.requireNonNull(detailFactoryBean.getObject()));
        try {
            //每天凌晨0点执行
            tiger.setCronExpression("0 0 0 * * ? ");
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        return tiger;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactory(@Qualifier("cronTriggerBean") CronTrigger cronTriggerBean){
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setTriggers(cronTriggerBean);
        return bean;
    }
}
