package com.github.readcnreceiptcount.schedule;

import com.alibaba.fastjson.JSONObject;
import com.github.readcnreceiptcount.constant.CommonConstant;
import com.github.readcnreceiptcount.util.RedisUtils;
import com.github.readcnreceiptcount.websocket.ReadRedisServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
@Configuration
@EnableScheduling
@EnableAsync
public class SendCaiNiaoCountTask {

    private static final Log logger = LogFactory.getLog(SendCaiNiaoCountTask.class);

    private static final long FIXED_DELAY = 2000;


    @Async
    @Scheduled(fixedDelay = FIXED_DELAY)
    public void sendAllMessage() {
        Calendar calendar = Calendar.getInstance();
        String now = CommonConstant.DATE_TIME_WITH_MIS_FORMAT.format(calendar.getTime());
        long ceb622SuccessCount = RedisUtils.getSendMessageCount(true);
        long ceb622FailCount = RedisUtils.getSendMessageCount(false);
        JSONObject result = new JSONObject();
        result.put("time", now);
        result.put("ceb622SuccessCount", ceb622SuccessCount);
        result.put("ceb622FailCount", ceb622FailCount);
        String resultStr = result.toJSONString();
        logger.info("result: [" + resultStr + "]");

        for (ReadRedisServer rrs: ReadRedisServer.getWebSocketSet()) {
            try {
                rrs.sendMessage(resultStr);
            } catch (IOException e) {
                logger.error("send message error", e);
            }
        }
    }
}
