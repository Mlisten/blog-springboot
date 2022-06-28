package cn.tenyear.blog.service.impl;

import cn.tenyear.blog.mapper.ArticleMapper;
import cn.tenyear.blog.mapper.TagMapper;
import cn.tenyear.blog.modle.entity.ArticleEntity;
import cn.tenyear.blog.modle.entity.TagEntity;
import cn.tenyear.blog.service.TagService;
import cn.tenyear.blog.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagEntity> implements TagService {
    @Resource
    private TagMapper tagMapper;
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public R listTags(boolean onlyOvert) {
        LambdaQueryWrapper<ArticleEntity> wrapper;
        List<Map<String,Object>> list = new ArrayList<>();
        if (onlyOvert){
            wrapper = new LambdaQueryWrapper<ArticleEntity>().select(ArticleEntity::getTagId,ArticleEntity::getId).eq(ArticleEntity::getOvert,1);
        }else {
            wrapper = new LambdaQueryWrapper<ArticleEntity>().select(ArticleEntity::getTagId,ArticleEntity::getId);
        }
        List<ArticleEntity> articleList = articleMapper.selectList(wrapper);
        List<TagEntity> tagList = tagMapper.selectList(null);
        if (tagList==null){
            return R.error();
        }
        //遍历所有的标签
        for (TagEntity tag:tagList){
            int count = 0;
            Map<String,Object> map = new HashMap<>();  //存储每个标签下的文章数量；标签自身id，自身name
            for (ArticleEntity article: articleList){
                //遍历每一个文章所拥有的标签id
                for (int tagId : article.getTagId()){
                    int id = -1;
                    String name = null;
                    //如果标签id == 单个文章下的标签id，则该标签下文章数量 +1
                    if (tag.getId() == tagId) {
                        count++;
                    }
                }
            }
            //如果该标签下文章存在，则存储
            if (count>0){
                map.put("count",count);
                map.put("id",tag.getId());
                map.put("name",tag.getName());
                list.add(map);
            }
        }
        return R.success(list);
    }
}
