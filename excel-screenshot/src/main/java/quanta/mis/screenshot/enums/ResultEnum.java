package quanta.mis.screenshot.enums;

import lombok.Getter;

/**
 * @author by.陈
 * @since 2022/5/12 3:58
 */
@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数不正确"),
    FILE_FORMAT_WRONG(2, "文件格式不对"),
    HTML_CREATE_FAIL(3,"创建页面失败!"),
    SEND_EMAIL_FAIL(4,"邮件发送失败!");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
