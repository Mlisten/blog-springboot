package cn.tenyear.blog.service.impl;

import cn.tenyear.blog.mapper.CommentMapper;
import cn.tenyear.blog.modle.entity.CommentEntity;
import cn.tenyear.blog.modle.vo.CommentVO;
import cn.tenyear.blog.service.CommentService;
import cn.tenyear.blog.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentEntity> implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public R listCommentVOs(int articleId, int pageNum , int pageSize){
        int index = (pageNum-1)*pageSize;
        List<CommentVO> list =  commentMapper.listCommentVOsByArticleId(articleId,index,pageSize);
        list.forEach(item->{
            if (item.getUsername()==null){
                item.setUsername("游客");
            }
        });
        Long count = commentMapper.selectCount(new LambdaQueryWrapper<CommentEntity>().eq(CommentEntity::getArticleId, articleId));
        if (list!=null) {
           return R.success(list, count);
        }
        return R.error();
    }

    @Override
    public R listCommentVOs(int pageNum, int pageSize){
        return listCommentVOs(pageNum, pageSize,false);
    }
    @Override
    public R listCommentVOs(int pageNum, int pageSize, boolean onlyOvert){
        int index =  (pageNum-1)*pageSize;
        List<CommentVO> list = commentMapper.listCommentVOsUseOnlyOvert(index,pageSize,onlyOvert);

        list.forEach(item->{
            if (item.getUsername()==null){
                item.setUsername("游客");
            }
        });
        //全部评论的数量
        long count = count();
        if (list!=null){
            return R.success(list, count);
        }
        return R.error();
    }

    @Override
    public long listCount(boolean onlyOvert) {
        return commentMapper.listCount(onlyOvert);
    }
}
