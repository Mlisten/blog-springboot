package cn.tenyear.blog.mapper;

import cn.tenyear.blog.modle.entity.UserEntity;
import cn.tenyear.blog.modle.vo.UserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {

    ArrayList<UserVO> listUserVO(Integer pageIndex , Integer pageSize, String search);
    ArrayList<UserVO> listSysUserVO(Integer pageIndex , Integer pageSize, String search);

    Long countByUser();
    Long countBySysUser();

    UserVO getOneUserVO(Integer id);
}
