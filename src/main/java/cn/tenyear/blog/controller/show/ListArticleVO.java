package cn.tenyear.blog.controller.show;

import cn.tenyear.blog.service.ArticleService;
import cn.tenyear.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李胜旺
 * @date 2022/4/4
 * @email 804464376@qq.com
 */
@RestController
@RequestMapping("/listArticleVO")
public class ListArticleVO {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/byTypeId/{id}")
    public R byTypeId(@PathVariable Integer id, @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum) {
        return articleService.selectByTypeId(id, pageSize, pageNum);
    }

    @GetMapping("/byTagId/{id}")
    public R byTagId(@PathVariable Integer id, @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum) {
        return articleService.selectByTagId(id, pageSize, pageNum);
    }

    @GetMapping("/byTime/{time}")
    public R byTime(@PathVariable String time, @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum) {
        return articleService.selectByCreateTime(time, pageSize, pageNum);
    }

    @GetMapping("/byTitle/{title}")
    public R byTitle(@PathVariable String title, @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum) {
        return articleService.selectByTitle(title, pageSize, pageNum);
    }
}
