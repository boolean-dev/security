package com.tao.security.core.validate.image;

import com.tao.security.core.validate.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * @ClassName ImageCodeProcessor
 * @Descriiption 图片发送器
 * @Author yanjiantao
 * @Date 2019/7/22 18:11
 **/

@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageValidateCode> {

    /**
     * 发送图形验证码，并将其写入相应中
     * @param request   请求
     * @param imageValidateCode  验证码
     * @throws Exception
     */
    @Override
    protected void send(ServletWebRequest request, ImageValidateCode imageValidateCode) throws Exception {
        ImageIO.write(imageValidateCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
