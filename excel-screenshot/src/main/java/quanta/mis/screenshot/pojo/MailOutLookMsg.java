package quanta.mis.screenshot.pojo;

import lombok.Data;

import java.util.Map;

@Data
public class MailOutLookMsg {
    /**
     * 邮件接收人
     */
    private String[] receiver;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件的文本内容
     */
    private String content;
    /**
     * 抄送人
     */
    private String[] cc;
    /**
     * 邮件附件的文件名
     */
    private String[] attachFileNames;
    /**
     * 邮件内容内嵌图片
     */
    private Map<String,String> imageMap;
}
