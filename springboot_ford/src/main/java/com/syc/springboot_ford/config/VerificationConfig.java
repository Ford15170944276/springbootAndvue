package com.syc.springboot_ford.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.config
 * @Author: syc
 * @CreateTime: 2023-02-20  15:25
 * @Description: 邮箱验证码配置类
 * @Version: 1.0
 */
@Component
public class VerificationConfig {
    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String email;

    /*
     * @description:验证码效验
     * @author: syc
     * @date: 2022/10/13 15:32
     * @param: [phone, code]
     * @return: void
     **/
    public boolean getRedisCode (String phone,String code){
        boolean flag = false;
        //验证码key
        String codeKey = phone + ":code";
        //获取数据库里codekey的值
        String redisCode =(String) redisTemplate.opsForValue().get(codeKey);
        if (redisCode.equals(code)) {
            flag = true;
        }
        return flag;
    }

    /*
     * @description: 2 每个手机每天只能发送3次， 验证码放入redis，设置过期时间
     * @author: syc
     * @date: 2022/10/13 11:26
     * @param: [phone, code]
     * @return: String
     **/
    public String verifyCode(String phone){


        //拼接key  手机发送次数
        String countKey = phone + ":count";
        //验证码key
        String codeKey = phone + ":code";

        //每个手机每天只能发送3次，查询手机发送次数
        Integer count = (Integer) redisTemplate.opsForValue().get(countKey);
//        System.out.println(count);

//        如果发送次数为空，表示未查询过
        if (count == null){
            //设置发送次数为一
            redisTemplate.opsForValue().set(countKey,1, 24*60*60, TimeUnit.SECONDS);
        } else if (count <= 2) {
            //设置countKey自增   这个方法貌似只能Integer类型的
            redisTemplate.getConnectionFactory().getConnection().incr(redisTemplate.getKeySerializer().serialize(countKey));

        } else if (count > 2) {
            return "今天已经发送达到上限了";
        }
        String code = getCode();
//        给codekey值增加过期时间
        redisTemplate.opsForValue().set(codeKey,code,300,TimeUnit.SECONDS);
        return code;
    }

    /*
     * @description: 1 生成6为验证码
     * @author: syc
     * @date: 2022/10/13 11:26
     * @param: []
     * @return: java.lang.String
     **/
    public static String getCode(){

        //生成验证码
        String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
        Random rand = new Random();
        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < 6; j++){
            flag.append(sources.charAt(rand.nextInt(9)) + "");
        }
        String verifycode = flag.toString();

        //生成验证码2
//        Random random = new Random();
//        String code = "";
//        int rand = 0;
//        for (int i = 0; i <6; i++) {
//            rand = random.nextInt(10);
//            code += rand;
//        }
        return verifycode;
    }

    /**
     * @description: 发送邮件，公共方法
     * @author: syc
     * @date: 2023/2/20 16:53
     * @param: [mailTo, mailText]
     * @return: void
     **/
    public void toMail(String mailTo,String mailText){
        //定义发送标题
        String title = "二手交易市场";
        //设置发送方
        String mailFrom = email;
        try {
            //构建邮件对象
            SimpleMailMessage message = new SimpleMailMessage();
            //设置邮件主题
            message.setSubject(title);
            //设置邮件发送者
            message.setFrom(mailFrom);
            //设置邮件接收者
            message.setTo(mailTo);
            //设置邮件发送日期
            message.setSentDate(new Date());
            //设置邮件正文
            message.setText(mailText);
            //发送邮件
            javaMailSender.send(message);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
