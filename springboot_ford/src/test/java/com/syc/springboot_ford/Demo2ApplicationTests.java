package com.syc.springboot_ford;


import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo2ApplicationTests {



/*
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Resource
    AdminMapper adminMapper;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private VerificationConfig verificationConfig;

    @Value("${fileUpLoad.ip}")
    private String ip;

    @Test
    void test8(){
        News news = new News();
        news.setTime(new Timestamp(new Date(System.currentTimeMillis()).getTime()));

        System.out.println(news);
    }

    @Test
    void contextLoads() {
        List<User> demos = userMapper.selectList(null);
        demos.forEach(System.out::println);

    }

    @Test
    void test2(){
       Admin admin = null;

        List<Admin> su = null;
        if (admin != null) {
//            su = adminMapper.selectList(Wrappers.<Admin>lambdaQuery()
//                    .eq(Admin::getAdminId, admin.getAdminId()));

        }else {
            su = adminMapper.selectList(Wrappers.<Admin>lambdaQuery());
        }
        for (Admin admin2: su) {
            System.out.println(admin2);
        }
    }

    @Test
    void sendPhoneCode(){
        String email = "2720289496@qq.com";
        //获取验证码给注册方
        String name = verificationConfig.verifyCode(email);

        //设置邮件内容
        String mailText = "【二手交易市场】 验证码："+name+"   您正在注册二手交易网站账号，请勿将验证码告诉他人哦。";

        if(!email.isEmpty()){
            verificationConfig.toMail(email,mailText);
            System.out.println("发送成功");
        }else {
            System.out.println("发送失败");
        }

    }

    /**
     * @description: 验证码验证登录
     * @author: syc
     * @date: 2023/2/20 16:57
     * @param: [phone, code]
     * @return: java.lang.String
     * * /

    @Test
    void verificationPhoneCode(){

        String email = "2720289496@qq.com";
        String code = "174148";
        boolean flag = verificationConfig.getRedisCode(email,code);
        if (flag == true) {
            System.out.println("登录成功");
        }else
            System.out.println("登录失败");


    }

*/
}
