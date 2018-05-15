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
     * 下载音频文件接口，支持断点续传
     * @param request
     * @return
     */
    @RequestMapping("downloadAudioFile")
    @ResponseBody
    public PublicResponseDto downloadAudioFile(HttpServletRequest request, HttpServletResponse response) {
        PublicResponseDto resDto = new PublicResponseDto();
        OutputStream outputStream = null;
        try {
            File outFile = new File("E:\\data\\test_files\\GEM_paomo.mp3");
            FileInputStream fileInputStream = new FileInputStream(outFile);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=GEM_paomo.mp3");
            response.setHeader("Accept-Ranges", "bytes");
            Long partLength = Long.valueOf(0);
            Long fileLength = Long.valueOf(outFile.length());
            if (null != request.getHeader("Range")) {
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                partLength = Long.valueOf(request.getHeader("Range").replace("bytes=", "").replace("-", ""));
            }
            response.setHeader("Content-Length", String.valueOf(fileLength - partLength));
            if (partLength != 0) {
                StringBuffer strBuffer = new StringBuffer("bytes ");
                strBuffer.append(partLength).append("-");
                strBuffer.append(fileLength - 1).append("/");
                strBuffer.append(fileLength);
                response.setHeader("Content-Range", strBuffer.toString());
                fileInputStream.skip(partLength);
            }
            outputStream = response.getOutputStream();
            mediaCommonService.outputAudioFileRC4(fileInputStream, outputStream);
        } catch (Exception e) {
            logger.error(">>>>>> 读写音频文件出错 {} <<<<<", e);
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.flush();
                    //outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resDto;
    }
}

