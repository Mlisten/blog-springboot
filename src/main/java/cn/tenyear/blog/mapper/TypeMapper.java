package cn.tenyear.blog.mapper;

import cn.tenyear.blog.modle.entity.TypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Repository
public interface TypeMapper extends BaseMapper<TypeEntity> {
    List<Map<String,Object>> listTypes();
}
