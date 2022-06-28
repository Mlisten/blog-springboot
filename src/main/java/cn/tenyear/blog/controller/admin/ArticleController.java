package cn.tenyear.blog.controller.admin;

import cn.tenyear.blog.modle.entity.ArticleEntity;
import cn.tenyear.blog.modle.vo.ArticleVO;
import cn.tenyear.blog.service.ArticleService;
import cn.tenyear.blog.utils.Crud;
import cn.tenyear.blog.utils.DateUtil;
import cn.tenyear.blog.utils.R;
import cn.tenyear.blog.utils.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李胜旺
 * @date 2022/4/4
 * @email 804464376@qq.com
 */
@RestController
@RequestMapping("/admin/article")
@Api(tags = "后台文章控制器")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping()
    @ApiOperation(value = "新增一篇文章")
    public R insert(@RequestBody ArticleEntity article) {
        Crud.addDate(article);
        System.out.println(article);
        if (articleService.save(article)) {
            return R.success();
        }
        return R.error();
    }

    @DeleteMapping({"/{id}"})
    public R delete(@PathVariable Integer id) {
        if (articleService.removeById(id)) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    @PutMapping()
    public R update(@RequestBody ArticleEntity entity) {
        entity.setUpdateTime(DateUtil.format());
        if (Validated.validate(entity)) {
            if (articleService.removeById(entity)) {
                return R.success("修改成功");
            }
        }
        return R.error("修改失败");
    }

    @GetMapping
    public R list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "", required = false) String search) {

        List<ArticleVO> list = articleService.listArticleVO(pageNum, pageSize, search, false);
        if (list != null) {
            Long count = articleService.count(false);
            return R.success(list, count);
        }
        return R.error();
    }

    @GetMapping("/{id}")
    public R getOne(@PathVariable Integer id) {
        ArticleVO oneArticleVO = articleService.getOneArticleVO(id);

        if (oneArticleVO != null) {
            return R.success(oneArticleVO);
        }
        return R.error();
    }
}
