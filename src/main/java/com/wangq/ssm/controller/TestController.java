package com.wangq.ssm.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangq.ssm.entity.Test;
import com.wangq.ssm.service.TestService;
import com.wangq.ssm.util.ChanngeUtil;
import com.wangq.ssm.util.ValueUtil;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Map;


@Controller
@RequestMapping("/test")
public class TestController {

    private static Logger logger = Logger.getLogger(TestMain.class);

    @Autowired
    private TestService serviceUser;


    @ResponseBody
    @RequestMapping(value="/1",method = RequestMethod.GET)
    public String get(){
        return ValueUtil.toJson(HttpStatus.SC_OK,"hello my name is wangwangqian lala");
    }

    @ResponseBody
    @RequestMapping(value="/2",method = RequestMethod.GET)
    public  String getOne(){
        return "hello my name is wangwangqian lala";
    }

    @ResponseBody
    @RequestMapping(value="/3",method = RequestMethod.GET)
    public Object gettow(){
        return ChanngeUtil.toJson(HttpStatus.SC_OK,serviceUser.getOne());
    }

    @ResponseBody
    @RequestMapping(value="/4",method = RequestMethod.GET)
    public Object intert(@RequestParam Map<String,String> map){
        return ChanngeUtil.toJson(HttpStatus.SC_OK,serviceUser.insert(map));
    }

    @ResponseBody
    @RequestMapping(value="/5",method = RequestMethod.GET)
    public Object find(String name){
        return ChanngeUtil.toJson(HttpStatus.SC_OK,serviceUser.findPassword(name));
    }

    @ResponseBody
    @RequestMapping(value="/6",method = RequestMethod.GET)
    public Object find1(Test test){
        return ChanngeUtil.toJson(HttpStatus.SC_OK,serviceUser.updateUser(test));
    }

    @ResponseBody
    @RequestMapping(value="/7",method = RequestMethod.GET)
    public Object find2(Integer id){
        return ChanngeUtil.toJson(HttpStatus.SC_OK,serviceUser.deleteUser(id));
    }

    @ResponseBody
    @RequestMapping(value="/8",method = RequestMethod.GET)
    public Object find3(ServletRequest request){
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String partlyUrl = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();
        logger.info("请求路径为==》"+partlyUrl +"  "+method);
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<3;i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("aa", "aaaa");
            jsonObject.put("bb", "bbbb");
            jsonObject.put("cc", "cccc");
            jsonArray.add(jsonObject);
            logger.info(jsonObject);
        }
        return ChanngeUtil.toJson(HttpStatus.SC_OK,jsonArray);
    }
    //文件上传
    @ResponseBody
    @RequestMapping(value="/9",method = RequestMethod.POST)
    public Object find4(@RequestParam("uploadFiles")MultipartFile uploadFiles){
        String tempPath="/home/hz/apps/wangqian";
        File tmpFile = new File(tempPath);
        if (!tmpFile.exists()) {
            //创建临时目录
            tmpFile.mkdir();
        }
        String origName = uploadFiles.getOriginalFilename();
        InputStream inputStream = null;
        try {
            //创建一个文件输出流
            FileOutputStream out = new FileOutputStream("/home/hz/apps/wangqian/"+origName);
            inputStream = uploadFiles.getInputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1)
            {
                out.write(bytes, 0, len);
            }
            inputStream.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ChanngeUtil.toJson(HttpStatus.SC_OK);
    }

    @ResponseBody
    @RequestMapping(value="/10",method = RequestMethod.POST)
    public Object find5(@RequestParam("uploadFiles")MultipartFile uploadFiles){
        String tempPath="/home/hz/apps/wangqian";
        File tmpFile = new File(tempPath);
        if (!tmpFile.exists()) {
            //创建临时目录
            tmpFile.mkdir();
        }
        String origName = uploadFiles.getOriginalFilename();
        try {
            //创建一个文件输出流
            byte[] bytes = uploadFiles.getBytes();
            FileOutputStream out = new FileOutputStream("/home/hz/apps/wangqian/"+origName);
            out.write(bytes);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ChanngeUtil.toJson(HttpStatus.SC_OK);
    }



}