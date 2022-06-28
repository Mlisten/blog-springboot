package cn.tenyear.blog.service;

import cn.tenyear.blog.modle.entity.CommentEntity;
import cn.tenyear.blog.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
public interface CommentService extends IService<CommentEntity> {

    /**
     * 前台页面 单个文章下的评论
     * @param articleId 文章id
     * @return CommentVO 对象集合
     */
    R listCommentVOs(int articleId, int pageNum , int pageSize);

    /**
     * 后台页面展示数据
     * @param pageSize 查询出来的个数
     * @return pageSize长度 CommentVO对象集合
     */
    R listCommentVOs(int pageNum,int pageSize);

    R listCommentVOs(int pageNum, int pageSize, boolean onlyOvert);

    long listCount(boolean onlyOvert);
}
