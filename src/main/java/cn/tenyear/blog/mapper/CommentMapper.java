package cn.tenyear.blog.mapper;

import cn.tenyear.blog.modle.entity.CommentEntity;
import cn.tenyear.blog.modle.vo.CommentVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Repository
public interface CommentMapper extends BaseMapper<CommentEntity> {

    List<CommentVO> listCommentVOsByArticleId(int articleId, int index , int pageSize);

//    List<CommentVO> listCommentVOs(int index, int pageSize );
    List<CommentVO> listCommentVOsUseOnlyOvert(int index, int pageSize ,boolean onlyOvert);

    Integer listCount(boolean onlyOvert);
}
