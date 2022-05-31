package quanta.mis.screenshot.service;


import quanta.mis.screenshot.pojo.MailOutLookMsg;

public interface SendEmailService {
    /**
     * 功能描述：发送text邮件
     *
     * @param mailOutLookMsg 邮件信息
     */
    void sendSimpleTextEmail(MailOutLookMsg mailOutLookMsg);

    /**
     * 发送图片内容的邮件
     *
     * @param mailOutLookMsg 邮件信息
     */
    void sendContentIncludeImgEmail(MailOutLookMsg mailOutLookMsg);


    void sendPictureEmail(MailOutLookMsg mailOutLookMsg);
}
