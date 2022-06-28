package cn.tenyear.blog.service;

import cn.tenyear.blog.modle.entity.UserEntity;
import cn.tenyear.blog.modle.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Service
public interface UserService extends IService<UserEntity> {

    /**
     * 普通用户文章列表展示
     * @param pageIndex 起始页
     * @param pageSize  每页个数
     * @param search    查询条件
     * @return 网页所需的数据格式
     */
    ArrayList<UserVO> listUserVO(Integer pageIndex, Integer pageSize, String search);

    /**
     * 系统用户文章列表展示
     * @param pageIndex 起始页
     * @param pageSize  每页个数
     * @param search    查询条件
     * @return  网页所需的数据格式
     */
    ArrayList<UserVO> listSysUserVO(Integer pageIndex, Integer pageSize, String search);

    /**
     * 返回普通用户个数
     * @return
     */
    Long countByUser();

    /**
     * 返回系统用户个数
     * @return
     */
    Long countBySysUser();

    /**
     * 返回用户信息
     * @param id 用户id
     * @return 网页所需的数据格式
     */
    UserVO getOneUserVO(Integer id);
}
