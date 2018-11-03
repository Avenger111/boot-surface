package org.code4everything.boot.starter.web;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.apache.log4j.Logger;
import org.code4everything.boot.starter.bean.MultipartFileBean;
import org.code4everything.boot.starter.service.FileService;
import org.code4everything.boot.xtool.bean.ResponseResult;
import org.code4everything.boot.xtool.constant.IntegerConsts;
import org.code4everything.boot.xtool.util.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

/**
 * 网络工具类
 *
 * @author pantao
 * @since 2018/11/2
 **/
public class HttpUtils {

    private static final Logger LOGGER = Logger.getLogger(HttpUtils.class);

    private HttpUtils() {}

    /**
     * 文件上传（无数据表）
     *
     * @param file 文件 {@link MultipartFile}
     * @param storagePath 文件存储路径，如：/root/boot/
     * @param digestBytes 是否计算文件的MD5码（大文件不建议计算，防止堆内存泄漏）
     * @param <T> 数据表类型
     *
     * @return 响应结果 {@link ResponseResult}。如果上传成功，{@link ResponseResult#getMsg()}返回文件的MD5文件名
     *
     * @since 1.0.0
     */
    public static <T extends Serializable> ResponseResult<T> upload(MultipartFile file, String storagePath,
                                                                    boolean digestBytes) {
        return upload(new FileService<T>() {}, file, storagePath, digestBytes, null);
    }

    /**
     * 文件上传
     *
     * @param fileService 文件服务 {@link FileService}
     * @param file 文件 {@link MultipartFile}
     * @param storagePath 文件存储路径，如：/root/boot/
     * @param digestBytes 是否计算文件的MD5码（大文件不建议计算，防止堆内存泄漏）
     * @param <T> 数据表类型
     *
     * @return 响应结果 {@link ResponseResult}。如果文件上传成功且最后得到的 {@link ResponseResult#getData()}为NULL，则{@link
     *         ResponseResult#getMsg()}将返回文件的MD5文件名
     *
     * @since 1.0.0
     */
    public static <T extends Serializable> ResponseResult<T> upload(FileService<T> fileService, MultipartFile file,
                                                                    String storagePath, boolean digestBytes) {
        return upload(fileService, file, storagePath, digestBytes, null);
    }

    /**
     * 文件上传
     *
     * @param fileService 文件服务 {@link FileService}
     * @param file 文件 {@link MultipartFile}
     * @param storagePath 文件存储路径，如：/root/boot/
     * @param digestBytes 是否计算文件的MD5码（大文件不建议计算，防止堆内存泄漏）
     * @param otherParam 附加参数，在自己实现的 {@link FileService}接口中使用
     * @param <T> 数据表类型
     *
     * @return 响应结果 {@link ResponseResult}。如果文件上传成功且最后得到的 {@link ResponseResult#getData()}为NULL，则{@link
     *         ResponseResult#getMsg()}将返回文件的MD5文件名
     *
     * @since 1.0.0
     */
    public static <T extends Serializable> ResponseResult<T> upload(FileService<T> fileService, MultipartFile file,
                                                                    String storagePath, boolean digestBytes,
                                                                    Map<String, Serializable> otherParam) {
        ResponseResult<T> result = new ResponseResult<>();
        MultipartFileBean fileBean = new MultipartFileBean();
        // 设置文件信息
        String ofn = file.getOriginalFilename();
        if (digestBytes) {
            try {
                // 设置MD5
                fileBean.setMd5(DigestUtil.md5Hex(file.getBytes()));
            } catch (Exception e) {
                LOGGER.error(StrUtil.format("get md5 of file[{}] failed, message -> {}", ofn, e.getMessage()));
                return result.setCode(IntegerConsts.FOUR_HUNDRED).setMsg(ofn + " upload failed");
            }
            fileBean.setFilename(fileBean.getMd5() + FileUtils.getSuffix(file.getOriginalFilename()));
        } else {
            fileBean.setFilename(file.getOriginalFilename());
        }
        fileBean.setOriginalFilename(ofn).setSize(file.getSize()).setOtherParams(otherParam);
        // 检测文件是否存在
        Boolean exists = fileService.exists(fileBean);
        boolean shouldWrite = false;
        T t = null;
        if (Validator.isNull(exists)) {
            t = fileService.getBy(fileBean);
            if (Validator.isNull(t)) {
                // 不存在时则可以写入磁盘
                shouldWrite = true;
            }
        } else if (!exists) {
            // 不存在时则可以写入磁盘
            shouldWrite = true;
        }
        if (shouldWrite) {
            try {
                // 写入磁盘
                file.transferTo(new File(storagePath + fileBean.getFilename()));
            } catch (Exception e) {
                LOGGER.error("upload file failed, message -> " + e.getMessage());
                return result.setCode(IntegerConsts.FOUR_HUNDRED).setMsg(ofn + " upload failed");
            }
            // 将数据写入数据库
            t = fileService.save(fileBean);
        }
        return Validator.isNull(t) ? result.setMsg(fileBean.getFilename()) : result.setData(t);
    }
}
