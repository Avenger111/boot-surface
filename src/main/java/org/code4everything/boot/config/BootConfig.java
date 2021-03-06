package org.code4everything.boot.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import org.apache.log4j.Logger;
import org.code4everything.boot.base.FileUtils;
import org.code4everything.boot.bean.ConfigBean;
import org.code4everything.boot.encoder.FieldEncoder;
import org.code4everything.boot.interfaces.FileWatcher;
import org.code4everything.boot.log.AopLogUtils;
import org.code4everything.boot.module.redis.RedisTemplateUtils;
import org.code4everything.boot.web.mvc.BaseController;
import org.code4everything.boot.web.mvc.DefaultWebInterceptor;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.io.File;

/**
 * 工具配置类
 *
 * @author pantao
 * @since 2018/11/10
 */
public class BootConfig {

    private static final Logger LOGGER = Logger.getLogger(BootConfig.class);

    /**
     * 调试
     *
     * @since 1.0.0
     */
    private static boolean debug = false;

    /**
     * 最大文件上传大小
     *
     * @since 1.0.0
     */
    private static long maxUploadFileSize = Long.MAX_VALUE;

    /**
     * 是否对字段进行加密
     *
     * @since 1.0.0
     */
    private static boolean sealed = false;

    /**
     * 字段加密器
     *
     * @since 1.0.0
     */
    private static FieldEncoder fieldEncoder = new FieldEncoder();

    private BootConfig() {}

    /**
     * 监听工作路径下的 boot-config.json 配置文件
     *
     * @since 1.0.6
     */
    public static void watchBootConfig() {
        watchBootConfig(FileUtils.currentWorkDir() + File.separator + "boot-config.json");
    }

    /**
     * 监听配置文件
     *
     * @param bootConfigPath 配置文件路径
     *
     * @since 1.0.6
     */
    public static void watchBootConfig(String bootConfigPath) {
        FileUtils.watchFile(bootConfigPath, new FileWatcher() {
            @Override
            public void doSomething() {
                if (FileUtil.exist(bootConfigPath)) {
                    parseJson(JSONObject.parseObject(FileUtil.readString(bootConfigPath, CharsetUtil.UTF_8)));
                } else {
                    LOGGER.warn("boot config file [" + bootConfigPath + "] is not found");
                }
            }
        }, true);
    }

    /**
     * 解析JSON配置
     *
     * @param boot JSON 配置
     *
     * @since 1.0.6
     */
    public static void parseJson(JSONObject boot) {
        if (ObjectUtil.isNotNull(boot)) {
            Long maxUploadFileSize = boot.getLong("maxUploadFileSize");
            if (ObjectUtil.isNotNull(maxUploadFileSize)) {
                setMaxUploadFileSize(maxUploadFileSize);
            }
            Boolean debug = boot.getBoolean("debug");
            if (ObjectUtil.isNotNull(debug)) {
                setDebug(debug);
            }
            Boolean sealed = boot.getBoolean("sealed");
            if (ObjectUtil.isNotNull(sealed)) {
                setSealed(sealed);
            }
            Integer okCode = boot.getInteger("okCode");
            if (ObjectUtil.isNotNull(okCode)) {
                setOkCode(okCode);
            }
            LOGGER.info("boot config is changed >>> " + boot.toJSONString());
        }
    }

    /**
     * 设置指定日志缓存
     *
     * @param logCache 日志缓存
     *
     * @since 1.0.1
     */
    public static void setLogCache(Cache<String, ?> logCache) {
        AopLogUtils.setLogCache(logCache);
    }

    /**
     * 设置配置类
     *
     * @param configBean {@link ConfigBean}
     *
     * @since 1.0.2
     */
    public static void setConfigBean(ConfigBean configBean) {
        DefaultWebInterceptor.setConfigBean(configBean);
    }

    /**
     * 设置正确码
     *
     * @param okCode 正确码
     *
     * @since 1.0.5
     */
    public static void setOkCode(int okCode) {
        BaseController.setOkCode(okCode);
    }

    /**
     * 初始化 Redis 连接池
     *
     * @param hostName 主机
     * @param port 端口
     * @param database 数据库
     *
     * @since 1.0.0
     */
    public static void initRedisConnectionFactory(String hostName, Integer port, Integer database) {
        RedisTemplateUtils.initRedisConnectionFactory(hostName, port, database);
    }

    /**
     * 初始化 Redis 连接池
     *
     * @param hostName 主机
     * @param port 端口
     *
     * @since 1.0.0
     */
    public static void initRedisConnectionFactory(String hostName, Integer port) {
        RedisTemplateUtils.initRedisConnectionFactory(hostName, port);
    }

    /**
     * 初始化 Redis 连接池
     *
     * @param hostName 主机
     *
     * @since 1.0.0
     */
    public static void initRedisConnectionFactory(String hostName) {
        RedisTemplateUtils.initRedisConnectionFactory(hostName);
    }

    /**
     * 设置连接池
     *
     * @param redisConnectionFactory 连接池
     *
     * @since 1.0.4
     */
    public static void setRedisConnectionFactory(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplateUtils.setRedisConnectionFactory(redisConnectionFactory);
    }


    /**
     * 获取字段加密器
     *
     * @return 字段加密器
     *
     * @since 1.0.0
     */
    public static FieldEncoder getFieldEncoder() {
        return fieldEncoder;
    }

    /**
     * 设置字段加密器
     *
     * @param fieldEncoder 字段加密器
     *
     * @since 1.0.0
     */
    public static synchronized void setFieldEncoder(FieldEncoder fieldEncoder) {
        BootConfig.fieldEncoder = fieldEncoder;
    }

    /**
     * 是否对字段进行加密
     *
     * @return 是否对字段进行加密
     *
     * @since 1.0.0
     */
    public static boolean isSealed() {
        return sealed;
    }

    /**
     * 设置是否对字段进行加密
     *
     * @param sealed 是否对字段进行加密
     *
     * @since 1.0.0
     */
    public static synchronized void setSealed(boolean sealed) {
        BootConfig.sealed = sealed;
    }

    /**
     * 是否调试
     *
     * @return 是否调试
     *
     * @since 1.0.0
     */
    public static boolean isDebug() {
        return debug;
    }

    /**
     * 设置是否调试
     *
     * @param debug 是否调试
     *
     * @since 1.0.0
     */
    public static synchronized void setDebug(boolean debug) {
        BootConfig.debug = debug;
    }

    /**
     * 获取最大文件上传大小
     *
     * @return 最大文件上传大小
     *
     * @since 1.0.0
     */
    public static long getMaxUploadFileSize() {
        return maxUploadFileSize;
    }

    /**
     * 设置最大文件上传大小
     *
     * @param maxUploadFileSize 最大文件上传大小
     *
     * @since 1.0.0
     */
    public static synchronized void setMaxUploadFileSize(long maxUploadFileSize) {
        BootConfig.maxUploadFileSize = maxUploadFileSize;
    }
}
