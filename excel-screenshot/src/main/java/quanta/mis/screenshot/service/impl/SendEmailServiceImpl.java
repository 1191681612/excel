package quanta.mis.screenshot.service.impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import quanta.mis.screenshot.enums.ResultEnum;
import quanta.mis.screenshot.exception.ScreenException;
import quanta.mis.screenshot.pojo.MailOutLookMsg;
import quanta.mis.screenshot.service.SendEmailService;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Service
public class SendEmailServiceImpl implements SendEmailService {
    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    /**
     * 功能描述：发送text邮件
     * @param mailOutLookMsg 邮件信息
     */
    @Override
    public void sendSimpleTextEmail(MailOutLookMsg mailOutLookMsg) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            //发件人
            mailMessage.setFrom(emailFrom);
            //接收人
            mailMessage.setTo(mailOutLookMsg.getReceiver());
            //邮件主题
            mailMessage.setSubject(mailOutLookMsg.getSubject());
            //邮件抄送
            mailMessage.setCc(mailOutLookMsg.getCc());
            //邮件内容
            mailMessage.setText(mailOutLookMsg.getContent());
            //发送邮件
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ScreenException(ResultEnum.SEND_EMAIL_FAIL);
        }
    }

    /**
     * 发送图片内容的邮件
     * @param mailOutLookMsg
     */
    @Override
    public void sendContentIncludeImgEmail(MailOutLookMsg mailOutLookMsg) {
        String content = mailOutLookMsg.getContent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<div style='width:1500px'");
        Map<String, String> imageMap = mailOutLookMsg.getImageMap();
        for (Map.Entry<String, String> stringStringEntry : imageMap.entrySet()) {
            //图片名字
            String imgName = stringStringEntry.getKey();
            //图片地址
            String imgLocation = stringStringEntry.getValue();
            stringBuilder.append("<br/><![if !vml]><img width='100%' src='cid:").append(imgName).append("'/><![endif]>");
        }
        stringBuilder.append("</div>");
        mailOutLookMsg.setContent(stringBuilder.toString());
        sendEnclosureEmail(mailOutLookMsg);
    }
    @Override
    public void sendPictureEmail(MailOutLookMsg mailOutLookMsg){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String[] receiver = mailOutLookMsg.getReceiver();
        Map<String, String> imageMap = mailOutLookMsg.getImageMap();
        String key  = null;
        String value = null;
        for (Map.Entry<String, String> stringStringEntry : imageMap.entrySet()) {
            key = stringStringEntry.getKey();
            value = stringStringEntry.getValue();
        }
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(receiver);
            FileSystemResource fileSystemResource = new FileSystemResource(new File(value));
            mimeMessageHelper.setText("<html><body><img src='cid:"+key+"'></body></html>.");
            mimeMessageHelper.addInline(key,fileSystemResource);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    };

    /**
     * 功能描述：发送html邮件
     * @param mailOutLookMsg 邮件信息
     * @param html 是否是html邮件
     */
    private void sendHtmlEmail(MailOutLookMsg mailOutLookMsg, boolean html) {
        try {
            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            //true 表示需要创建一个multipart message
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage);
            //发件人
            mimeMessageHelper.setFrom(emailFrom);
            //接收人
            mimeMessageHelper.setTo(mailOutLookMsg.getReceiver());
            //邮件主题
            mimeMessageHelper.setSubject(mailOutLookMsg.getSubject());
            //邮件抄送
            mimeMessageHelper.setCc(mailOutLookMsg.getCc());
            //邮件内容
            mimeMessageHelper.setText(mailOutLookMsg.getContent(), html);
            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ScreenException(ResultEnum.SEND_EMAIL_FAIL);
        }
    }

    /**
     * 功能描述：发送文本内容（内嵌图片）且带附件的html邮件
     * @param mailOutLookMsg 邮件信息
     */
    private void sendEnclosureEmail(MailOutLookMsg mailOutLookMsg) {
        try {
            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            //true 表示需要创建一个multipart message
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            //发件人
            mimeMessageHelper.setFrom(emailFrom);
            //邮件接收人
            mimeMessageHelper.setTo(mailOutLookMsg.getReceiver());
            //邮件主题
            mimeMessageHelper.setSubject(mailOutLookMsg.getSubject());
            //邮件抄送
//            mimeMessageHelper.setCc(mailOutLookMsg.getCc());
            //设置显示的发件时间
            mimeMessageHelper.setSentDate(new Date());

            MimeMultipart allMultipart = new MimeMultipart();

            //创建代表邮件正文和附件的各个MimeBodyPart对象
            MimeBodyPart contentPart = createContent(mailOutLookMsg.getContent(), mailOutLookMsg.getImageMap());
            allMultipart.addBodyPart(contentPart);

            //创建用于组合邮件正文和附件的MimeMultipart对象
            String[] attachFileNames = mailOutLookMsg.getAttachFileNames();
            if (attachFileNames != null) {
                for (int i = 0; i < attachFileNames.length; i++) {
                    allMultipart.addBodyPart(createAttachment(mailOutLookMsg.getAttachFileNames()[i]));
                }
            }
            //设置整个邮件内容为最终组合出的MimeMultipart对象
            mimeMailMessage.setContent(allMultipart);
            mimeMailMessage.saveChanges();

            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ScreenException(ResultEnum.SEND_EMAIL_FAIL);
        }
    }



    /**
     * 功能描述：邮件附件
     *
     * @param filename 文件路径
     * @return {@link MimeBodyPart}
     * @author wi-gang
     * @date 2021/12/29 2:50 下午
     */
    private MimeBodyPart createAttachment(String filename) throws Exception {
        //创建保存附件的MimeBodyPart对象，并加入附件内容和相应的信息
        MimeBodyPart attachPart = new MimeBodyPart();
        FileDataSource fsd = new FileDataSource(filename);
        attachPart.setDataHandler(new DataHandler(fsd));
        attachPart.setFileName(fsd.getName());
        return attachPart;
    }

    /**
     * 功能描述：文本内容
     *
     * @param body html格式的文本内容
     * @return {@link MimeBodyPart}
     * @author wi-gang
     * @date 2021/12/29 2:50 下午
     */
    private MimeBodyPart createContent(String body) throws Exception {
        //创建代表组合Mime消息的MimeMultipart对象，将该MimeMultipart对象保存到MimeBodyPart对象
        MimeBodyPart contentPart = new MimeBodyPart();
        MimeMultipart contentMultipart = new MimeMultipart("related");

        //创建用于保存HTML正文的MimeBodyPart对象，并将它保存到MimeMultipart中
        MimeBodyPart htmlBodyPart = new MimeBodyPart();
        htmlBodyPart.setContent(body, "text/html;charset=UTF-8");
        contentMultipart.addBodyPart(htmlBodyPart);

        //将MimeMultipart对象保存到MimeBodyPart对象
        contentPart.setContent(contentMultipart);
        return contentPart;
    }

    /**
     * 功能描述：文本内嵌图片
     *
     * @param body html格式的文本内容
     * @param map  图片集合
     * @return {@link MimeBodyPart}
     * @author wi-gang
     * @date 2021/12/29 2:50 下午
     */
    private MimeBodyPart createContent(String body, Map<String, String> map) throws Exception {
        //创建代表组合Mime消息的MimeMultipart对象，将该MimeMultipart对象保存到MimeBodyPart对象
        MimeBodyPart contentPart = new MimeBodyPart();
        MimeMultipart contentMultipart = new MimeMultipart("related");

        //创建用于保存HTML正文的MimeBodyPart对象，并将它保存到MimeMultipart中
        MimeBodyPart htmlBodyPart = new MimeBodyPart();
        htmlBodyPart.setContent(body, "text/html;charset=UTF-8");
        contentMultipart.addBodyPart(htmlBodyPart);

        if (map != null && map.size() > 0) {
            Set<Map.Entry<String, String>> set = map.entrySet();
            for (Map.Entry<String, String> entry : set) {
                //创建用于保存图片的MimeBodyPart对象，并将它保存到MimeMultipart中
                MimeBodyPart gifBodyPart = new MimeBodyPart();
                //图片所在的目录的绝对路径
                FileDataSource fds = new FileDataSource(entry.getValue());
                DataHandler dataHandler = new DataHandler(fds);
                gifBodyPart.setDataHandler(dataHandler);
                //cid的值
//                gifBodyPart.setContentID(entry.getKey());
                gifBodyPart.setHeader("Content-ID",entry.getKey());
                gifBodyPart.setFileName(dataHandler.getName());
                contentMultipart.addBodyPart(gifBodyPart);
            }
        }
        //将MimeMultipart对象保存到MimeBodyPart对象
        contentPart.setContent(contentMultipart);
        return contentPart;
    }
}
