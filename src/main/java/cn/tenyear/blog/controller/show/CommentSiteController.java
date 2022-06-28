package cn.tenyear.blog.controller.show;

import cn.tenyear.blog.modle.entity.CommentEntity;
import cn.tenyear.blog.service.CommentService;
import cn.tenyear.blog.utils.Crud;
import cn.tenyear.blog.utils.R;
import cn.tenyear.blog.utils.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李胜旺
 * @date 2022/4/4
 * @email 804464376@qq.com
 */
@RestController
@RequestMapping("/comment")
public class CommentSiteController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public R insert(@RequestBody CommentEntity comment) {
        System.out.println(comment);

        if (!Validated.validate(comment)) {
            return R.error("参数校验错误");
        }
        Crud.addDate(comment);
        if (commentService.save(comment)) {
            return R.success();
        }
        return R.error();
    }

    @GetMapping("/{articleId}")
    public R listCommentVOsByArticleId(@PathVariable Integer articleId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        return commentService.listCommentVOs(articleId, pageNum, pageSize);
    }

    @GetMapping
    public R listCommentVOs(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        return commentService.listCommentVOs(pageNum, pageSize, true);
    }
}
