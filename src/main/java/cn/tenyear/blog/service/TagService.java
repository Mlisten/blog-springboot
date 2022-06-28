package cn.tenyear.blog.service;

import cn.tenyear.blog.modle.entity.TagEntity;
import cn.tenyear.blog.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */

public interface TagService extends IService<TagEntity> {

    /**
     * 查询各标签下的文章数
     * @param onlyOvert 文章是否公开
     * @return  {count: (文章数),id: (标签id),name:(标签名称) }
     */
     R listTags(boolean onlyOvert);

}
