package com.ljyhust.media.service;

import com.ljyhust.utils.AESUtils;
import com.ljyhust.utils.CryptoCipherUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 多媒体处理业务类
 * Created by Administrator on 2018/5/5.
 */
@Service
public class MediaCommonService {

    private Logger logger = LoggerFactory.getLogger(MediaCommonService.class);

    @Value("${secret.rc4}")
    private String key;

    public void outputAudioFileWithAES(FileInputStream inputStream, OutputStream outputStream) throws IOException {
        FileChannel fsChannel = inputStream.getChannel();
        int capacity = 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        try {
            while (-1 != fsChannel.read(byteBuffer)) {
                byteBuffer.flip();
                outputStream.write(AESUtils.AESEncodeDefault(byteBuffer.array()));
                byteBuffer.clear();
            }
        } catch (IOException e) {
            logger.debug(">>>>>>> 文件输入输出错误 {} <<<<<<<", e);
            throw new IOException("audio stream write error");
        }

    }

    public void outputAudioFileWithCipher(InputStream inputStream, OutputStream outputStream) {
        CryptoCipherUtils.encode(inputStream, outputStream, this.key);
    }

    public void outputAudioFileRC4(InputStream inputStream, OutputStream outputStream) {
        CryptoCipherUtils.decodeWithRC4(inputStream, outputStream, this.key);
    }
}
