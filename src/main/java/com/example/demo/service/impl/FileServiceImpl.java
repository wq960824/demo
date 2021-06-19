package com.example.demo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.File;
import com.example.demo.mapper.FileMapper;
import com.example.demo.service.FileService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统文件库（图片、视频等） 服务实现类
 * </p>
 *
 * @author wq
 * @since 2019-08-02
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

}
