package cn.tenyear.blog.controller.show;

import cn.tenyear.blog.modle.entity.ArticleEntity;
import cn.tenyear.blog.modle.vo.ArticleVO;
import cn.tenyear.blog.service.ArticleService;
import cn.tenyear.blog.utils.R;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李胜旺
 * @date 2022/4/4
 * @email 804464376@qq.com
 */
@RestController
@RequestMapping
public class IndexController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/allArticles")
    public R list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        List<ArticleVO> voList = articleService.listArticleVO(pageNum, pageSize, null, true);
        if (voList != null) {
            Long count = articleService.count(true);
            return R.success(voList, count);
        }
        return R.error();
    }

    @GetMapping("/article/{id}")
    public R getOne(@PathVariable Integer id) {
        ArticleEntity one = articleService.getById(id);
        if (one != null) {
            Wrapper<ArticleEntity> eq = new LambdaUpdateWrapper<ArticleEntity>().set(ArticleEntity::getViews, one.getViews() + 1).eq(ArticleEntity::getId, id);
            articleService.update(one, eq);
            ArticleVO vo = articleService.getOneArticleVO(id);
            return R.success(vo);
        }
        return R.error();
    }
}
