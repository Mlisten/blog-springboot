package cn.tenyear.blog.mapper;

import cn.tenyear.blog.modle.entity.ArticleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
public interface ArticleMapper extends BaseMapper<ArticleEntity> {
    List<Map<String,Object>> allOrderByTime();
}
