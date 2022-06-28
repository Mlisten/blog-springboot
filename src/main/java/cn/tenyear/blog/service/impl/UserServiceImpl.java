package cn.tenyear.blog.service.impl;

import cn.tenyear.blog.mapper.UserMapper;
import cn.tenyear.blog.modle.entity.UserEntity;
import cn.tenyear.blog.modle.vo.UserVO;
import cn.tenyear.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public ArrayList<UserVO> listUserVO(Integer pageIndex, Integer pageSize, String search) {
        return userMapper.listUserVO(pageIndex, pageSize, search);
    }

    @Override
    public ArrayList<UserVO> listSysUserVO(Integer pageIndex, Integer pageSize, String search) {
        return userMapper.listSysUserVO(pageIndex, pageSize, search);
    }

    @Override
    public Long countByUser() {
        return userMapper.countByUser();
    }

    @Override
    public Long countBySysUser() {
        return userMapper.countBySysUser();
    }

    @Override
    public UserVO getOneUserVO(Integer id) {
        return userMapper.getOneUserVO(id);
    }
}
