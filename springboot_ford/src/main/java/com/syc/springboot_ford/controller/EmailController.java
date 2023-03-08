package com.syc.springboot_ford.controller;

import com.syc.springboot_ford.common.Result;
import com.syc.springboot_ford.config.VerificationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.controller
 * @Author: syc
 * @CreateTime: 2023-02-20  16:51
 * @Description: 发送邮件控制类
 * @Version: 1.0
 */
@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private VerificationConfig verificationConfig;

    /**
     * @description: 获取验证码
     * @author: syc
     * @date: 2023/2/20 16:55
     * @param: [phone]
     * @return: java.lang.String
     **/
    @GetMapping("/sendphoneCode")
    public Result sendPhoneCode(@RequestParam String email){
        //获取验证码给注册方
        String name = verificationConfig.verifyCode(email);

        //设置邮件内容
        String mailText = "【二手交易市场】 验证码："+name+"   您正在注册二手交易网站账号，请勿将验证码告诉他人哦。验证码有效期5分钟。";

        if(!email.isEmpty()){
            verificationConfig.toMail(email,mailText);
            return Result.success("0", "发送成功");
        }
        return Result.error("1", "发送失败");
    }

    /**
     * @description: 验证码验证登录
     * @author: syc
     * @date: 2023/2/20 16:57
     * @param: [phone, code]
     * @return: java.lang.String
     **/
    public boolean verificationPhoneCode(String email,String code){
        boolean flag = verificationConfig.getRedisCode(email,code);
        return flag;
    }
}
