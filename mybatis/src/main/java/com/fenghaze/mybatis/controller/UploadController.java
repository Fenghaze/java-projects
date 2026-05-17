package com.fenghaze.mybatis.controller;

import com.fenghaze.mybatis.oss.OssService;
import com.fenghaze.mybatis.response.Result;
import com.fenghaze.mybatis.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/api")
public class UploadController {

    @Autowired
    private OssService ossService;

    @Autowired
    private EmpService empService;

    @PostMapping("/uploadAvatar")
    public Result cloudUploadAvatar(@RequestParam Integer empId,
                                    @RequestParam MultipartFile image) {
        log.info("上传员工头像, empId: {}", empId);
        try {
            // 获取原始文件名后缀
            String originFileName = image.getOriginalFilename();
            String suffix = originFileName.substring(originFileName.lastIndexOf("."));

            // 文件名格式：emp_{empId}_{timestamp}.{ext}，与员工信息对应
            String timestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
            String objectKey = "imgs/avatars/emp_" + empId + "_" + timestamp + suffix;

            // 保存到本地临时文件
            String tempFilePath = System.getProperty("java.io.tmpdir") + "emp_" + empId + "_" + timestamp + suffix;
            image.transferTo(new File(tempFilePath));

            // 上传到 OSS
            ossService.uploadFile(objectKey, tempFilePath);

            // 删除临时文件
            new File(tempFilePath).delete();

            // 构造图片的公开访问 URL
            String avatarUrl = ossService.getObjectUrl(objectKey);

            // 将 URL 保存到员工数据表的 avatar 字段
            empService.updateAvatar(empId, avatarUrl);

            log.info("头像上传成功, empId: {}, url: {}", empId, avatarUrl);
            return Result.success(avatarUrl);
        } catch (Exception e) {
            log.error("上传头像失败, empId: {}", empId, e);
            return Result.error("上传失败");
        }
    }
}
