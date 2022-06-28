package cn.tenyear.blog.service;

import cn.tenyear.blog.modle.entity.ArticleEntity;
import cn.tenyear.blog.modle.vo.ArticleVO;
import cn.tenyear.blog.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
public interface ArticleService extends IService<ArticleEntity> {

    R allOrderByTime();

    R selectByTypeId(int id,int pageSize,int pageNum);
    R selectByTagId(int id, int pageSize, int pageNum);
    R selectByCreateTime(String time,int pageSize,int pageNum);
    R selectByTitle(String title,int pageSize,int pageNum);

    ArticleVO getOneArticleVO(Integer id);

    ArticleVO getOneArticleVO(Integer id, boolean onlyOvert ,boolean isSimple );

    List<ArticleVO> listArticleVO(Integer pageNum, Integer pageSize, String search, boolean isOvert);

    Long count(boolean onlyOvert);
}
