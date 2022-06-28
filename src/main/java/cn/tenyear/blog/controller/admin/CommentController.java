package cn.tenyear.blog.controller.admin;

import cn.tenyear.blog.modle.entity.CommentEntity;
import cn.tenyear.blog.service.CommentService;
import cn.tenyear.blog.utils.Crud;
import cn.tenyear.blog.utils.DateUtil;
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
@RequestMapping("/admin/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public R insert(@RequestBody CommentEntity entity){
        Crud.addDate(entity);
        if (commentService.save(entity)) {
            return R.success();
        }
        return R.error();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id){
        if (commentService.removeById(id)) {
            return R.success();
        }
        return R.error();
    }
    @PutMapping
    public R update(@RequestBody CommentEntity entity){
        Crud.addDate(entity);
        entity.setUpdateTime(DateUtil.format());
        if (Validated.validate(entity)){
            if (commentService.updateById(entity)){
                return R.success("修改成功");
            }
        }
        return R.error("修改失败");
    }

    @GetMapping
    public R list(@RequestParam(defaultValue = "1") Integer pageNum , @RequestParam (defaultValue = "5") Integer pageSize){
        return commentService.listCommentVOs(pageNum,pageSize);
    }

    @GetMapping("/{id}")
    public R getOne(@PathVariable Integer id){
        CommentEntity one = commentService.getById(id);
        if (one!=null){
            return R.success(one);
        }
        return R.error();
    }
}
