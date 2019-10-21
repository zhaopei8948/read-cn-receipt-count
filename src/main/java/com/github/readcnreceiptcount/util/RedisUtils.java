package com.github.readcnreceiptcount.util;

import com.github.readcnreceiptcount.constant.CommonConstant;
import com.github.readcnreceiptcount.constant.MessageTypeEnum;
import com.github.readcnreceiptcount.constant.RedisConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class RedisUtils {

    private static Log logger = LogFactory.getLog(RedisUtils.class);

    private static StringRedisTemplate stringRedisTemplate;

    private static ValueOperations<String, String> valueOperations;

    private static ListOperations<String, String> listOperations;

    private static RedisOperations<String, String> redisOperations;

    public static void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        RedisUtils.stringRedisTemplate = stringRedisTemplate;
        valueOperations = stringRedisTemplate.opsForValue();
        listOperations = stringRedisTemplate.opsForList();
        redisOperations = valueOperations.getOperations();
    }

    public static void setSendMessageCount(MessageTypeEnum messageTypeEnum, boolean isSuccess) {
        try {
            String key = isSuccess ? getSuccessMsgCntKey(messageTypeEnum) : getFailMsgCntKey(messageTypeEnum);
            valueOperations.increment(key);
            setExpire(key);
        } catch (Exception e) {
            logger.error("set send message count error", e);
        }
    }

    public static long getSendMessageCount(boolean isSuccess) {
        try {
            MessageTypeEnum messageTypeEnum = MessageTypeEnum.MESSAGE_TYPE_DB_CEB622;
            String key = isSuccess ? getSuccessMsgCntKey(messageTypeEnum) : getFailMsgCntKey(messageTypeEnum);
            String sr = valueOperations.get(key);
            if (StringUtils.isEmpty(sr)) {
                return 0;
            } else {
                return Long.valueOf(sr);
            }
        } catch (Exception e) {
            logger.error("get send message count error", e);
        }

        return 0;
    }

    public static void setFailMsg(String message) {
        try {
            String key = getFailMsgListKey();
            listOperations.leftPush(key, message);
            setExpire(key);
        } catch (Exception e) {
            logger.error("set fail msg error", e);
        }
    }

    private static void setExpire(String key) {
        if (redisOperations.getExpire(key) == -1) {
            redisOperations.expire(key, RedisConstant.KEY_EXPIRE_DASY, TimeUnit.DAYS);
        }
    }

    private static String getFailMsgListKey() {
        Calendar calendar = Calendar.getInstance();
        return RedisConstant.KEY_PREFIX + CommonConstant.DATE_FORMAT.format(calendar.getTime())
                + RedisConstant.KEY_FAIL_MSG_LIST;
    }

    private static String getMessageCountKey(MessageTypeEnum messageTypeEnum) {
        Calendar calendar = Calendar.getInstance();
        return RedisConstant.KEY_PREFIX + CommonConstant.DATE_FORMAT.format(calendar.getTime())
                + "_" + messageTypeEnum.toString();
    }

    private static String getSuccessMsgCntKey(MessageTypeEnum messageTypeEnum) {
        return getMessageCountKey(messageTypeEnum) + RedisConstant.KEY_SUCCESS_SUFFIX;
    }

    private static String getFailMsgCntKey(MessageTypeEnum messageTypeEnum) {
        return getSuccessMsgCntKey(messageTypeEnum) + RedisConstant.KEY_FAIL_SUFFIX;
    }
}
