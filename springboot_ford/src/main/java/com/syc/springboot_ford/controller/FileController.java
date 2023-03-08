package com.syc.springboot_ford.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ssh.JschRuntimeException;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.syc.springboot_ford.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.controller
 * @Author: syc
 * @CreateTime: 2023-02-24  10:28
 * @Description: 文件上传的控制类
 * @Version: 1.0
 */
@RestController
@RequestMapping("/files")
public class FileController {
    @Value("${server.port}")
    private String port;

    @Value("${fileUpLoad.ip}")
    private String ip;

    /**
     * @description: 上传文件
     * @author: syc
     * @date: 2023/2/24 11:25
     * @param: [file]
     * @return: com.syc.springboot_ford.common.Result<?>
     **/
    @PostMapping("/upload")
    public Result<?> Userupload(MultipartFile file) throws IOException {
        //获取源文件的名称
        String originalFilename = file.getOriginalFilename();
        //定义文件的前缀防止重复覆盖
        String flag = IdUtil.fastSimpleUUID();
        //最终文件名称
        String fileName = flag + "_" + originalFilename;
        //获取上传的路径
        String rootFilePath = System.getProperty("user.dir") + "/files/" + fileName;
        //文件写入上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        //返回结果 url
        return Result.success("http://" + ip + ":" + port + "/files/" + fileName);
    }

    /**
     * @description: 富文本上传
     * @author: syc
     * @date: 2023/3/4 19:36
     * @param: [file]
     * @return: com.syc.springboot_ford.common.Result<?>
     **/
    @PostMapping("/editorupload")
    public JSON editorUserupload(MultipartFile file) throws IOException {
        //获取源文件的名称
        String originalFilename = file.getOriginalFilename();
        //定义文件的前缀防止重复覆盖
        String flag = IdUtil.fastSimpleUUID();
        //最终文件名称
        String fileName = flag + "_" + originalFilename;
        //获取上传的路径
        String rootFilePath = System.getProperty("user.dir") + "/files/" + fileName;
        //文件写入上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        String url = "http://" + ip + ":" + port + "/files/" + fileName;
        JSONObject json = new JSONObject();
        json.set("errno", 0);
        JSONArray arr = new JSONArray();
        JSONObject data = new JSONObject();
        arr.add(data);
        data.set("url", url);
        json.set("data", arr);
        //返回结果 url
        return json;
    }

    /**
     * @description: 下载文件
     * @author: syc
     * @date: 2023/2/24 11:25
     * @param: [flag, response]
     * @return: com.syc.springboot_ford.common.Result<?>
     **/
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response){
        OutputStream os;  //新建输出流对象
        //文件根路径
        String basePath = System.getProperty("user.dir") + "/files/";
        //获取路径的所有文件名
        List<String> fileNames = FileUtil.listFileNames(basePath);
        //对比所有文件名，拿到所需要的文件
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try{
            if (StrUtil.isNotEmpty(fileName)){
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                //拿到文件的路径和名称，下载文件
                byte[] bytes = FileUtil.readBytes(basePath + fileName);
                //通过输出流返回文件
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
