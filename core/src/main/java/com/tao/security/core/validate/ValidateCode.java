package com.tao.security.core.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName ValidateCode
 * @Descriiption 验证码父类
 * @Author yanjiantao
 * @Date 2019/7/22 11:12
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCode {

    private String code;
    private LocalDateTime expireTime;

    public Boolean isExpried() {
        return LocalDateTime.now().isAfter(this.expireTime);
    }

    public ValidateCode(String code, long expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusMinutes(expireIn);
    }
}
