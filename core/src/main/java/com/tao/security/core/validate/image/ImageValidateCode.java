package com.tao.security.core.validate.image;

import com.tao.security.core.validate.ValidateCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName ImageValidateCode
 * @Descriiption 图形验证码
 * @Author yanjiantao
 * @Date 2019/7/11 17:17
 **/

@Data
@EqualsAndHashCode(callSuper = true)
public class ImageValidateCode extends ValidateCode{
    /**
     * 图片
     */
    private BufferedImage image;

    public ImageValidateCode(String code, long expireIn, BufferedImage image) {
        super(code, expireIn);
        this.image = image;
    }
}
