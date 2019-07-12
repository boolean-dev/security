package com.tao.security.core.validate.image;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName ImageCode
 * @Descriiption 图形验证码
 * @Author yanjiantao
 * @Date 2019/7/11 17:17
 **/
@Data
public class ImageCode {
    /**
     * 图片
     */
    private BufferedImage image;

    /**
     * 验证码
     */
    private String code;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    public ImageCode(BufferedImage image, String code, Integer expireIn) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusMinutes(expireIn);
    }
}
