package cn.tenyear.blog.controller.show;

import cn.tenyear.blog.modle.entity.ArticleEntity;
import cn.tenyear.blog.service.ArticleService;
import cn.tenyear.blog.service.CommentService;
import cn.tenyear.blog.service.TagService;
import cn.tenyear.blog.service.TypeService;
import cn.tenyear.blog.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李胜旺
 * @date 2022/4/4
 * @email 804464376@qq.com
 */
@RestController
public class MainRightController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/allTags")
    public R allTags() {
        return tagService.listTags(true);
    }

    @GetMapping("/allTypes")
    public R allTypes() {
        List<Map<String, Object>> maps = typeService.listTypes();

        if (maps != null) {
            return R.success(maps);
        }
        return R.error();
    }

    @GetMapping("/allCount")
    public R allCount() {
        Map<String, Long> map = new HashMap<>();
        List<ArticleEntity> articles = articleService.list(new LambdaQueryWrapper<ArticleEntity>().eq(ArticleEntity::getOvert, 1));
        map.put("articlesCount", (long) articles.size());
        Long ac = 0L;
        for (ArticleEntity article : articles) {
            ac += article.getViews();
        }
        map.put("commentsCount", commentService.listCount(true));
        map.put("viewsCount", ac);
        return R.success(map);
    }

    @GetMapping("/allOrderByTime")
    public R allOrderByTime() {
        return articleService.allOrderByTime();
    }

    @GetMapping("/bestNewArticles")
    public R bestNewArticle() {
        //5篇最新创建的文章
        return orderBy(ArticleEntity::getUpdateTime);
    }

    @GetMapping("/bestViewsArticles")
    public R bestViewArticle() {
        //5篇最多浏览的文章
        return orderBy(ArticleEntity::getViews);
    }

    private R orderBy(SFunction<ArticleEntity, ?> function) {
        LambdaQueryWrapper<ArticleEntity> byDesc = new LambdaQueryWrapper<ArticleEntity>().select(ArticleEntity::getId, ArticleEntity::getTitle, ArticleEntity::getViews)
                .eq(ArticleEntity::getOvert, 1).orderByDesc(function);
        List<ArticleEntity> articles = articleService.page(Page.of(1, 5), byDesc).getRecords();
        if (articles != null) {
            return R.success(articles);
        }
        return R.error();
    }
}