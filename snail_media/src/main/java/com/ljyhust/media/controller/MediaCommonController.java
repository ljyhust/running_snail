package com.ljyhust.media.controller;

import com.ljyhust.dto.common.PublicResponseDto;
import com.ljyhust.media.service.MediaCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 对外接口，提供多媒体传输功能
 * Created by Administrator on 2018/5/5.
 */
@RestController
@RequestMapping("/mediaCommon")
public class MediaCommonController {

    private Logger logger = LoggerFactory.getLogger(MediaCommonController.class);
    @Autowired
    private MediaCommonService mediaCommonService;

    /**
     * 下载音频文件接口
     * @param request
     * @return
     */
    @RequestMapping("downloadAudioFile")
    @ResponseBody
    public PublicResponseDto downloadAudioFile(HttpServletRequest request, HttpServletResponse response) {
        PublicResponseDto resDto = new PublicResponseDto();
        OutputStream outputStream = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("E:\\data\\test_file\\lanlianhua_leiting.mp3"));
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=lanlianhua_leiting_out.mp3");
            outputStream = response.getOutputStream();
            mediaCommonService.outputAudioFileWithCipher(fileInputStream, outputStream);
        } catch (Exception e) {
            logger.error(">>>>>> 读写音频文件出错 {} <<<<<", e);
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resDto;
    }
}

