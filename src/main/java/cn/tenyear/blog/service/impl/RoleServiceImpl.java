package cn.tenyear.blog.service.impl;

import cn.tenyear.blog.mapper.RoleMapper;
import cn.tenyear.blog.modle.entity.RoleEntity;
import cn.tenyear.blog.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Resource
    private RoleMapper roleMapper;
}
