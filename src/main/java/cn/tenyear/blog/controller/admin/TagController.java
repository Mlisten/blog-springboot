package cn.tenyear.blog.controller.admin;
import cn.tenyear.blog.modle.entity.TagEntity;
import cn.tenyear.blog.service.TagService;
import cn.tenyear.blog.utils.Crud;
import cn.tenyear.blog.utils.DateUtil;
import cn.tenyear.blog.utils.R;
import cn.tenyear.blog.utils.Validated;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author 李胜旺
 * @date 2022/4/4
 * @email 804464376@qq.com
 */
@RestController
@RequestMapping("/admin/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/{name}")
    public R insert(@PathVariable String name){
        TagEntity tag = new TagEntity();
        tag.setName(name);
        if (!Validated.validate(tag)){
            return R.error("参数长度必须在 1-10 之内");
        }
        Crud.addDate(tag);
        LambdaQueryWrapper<TagEntity> wrapper = new LambdaQueryWrapper<TagEntity>().eq(TagEntity::getName, name);
        TagEntity one = tagService.getOne(wrapper);

        if (one==null){
            if (tagService.save(tag)) {
                return R.success();
            }
        }
        return R.error();
    }

    @DeleteMapping({"/{id}"})
    public R delete(@PathVariable Integer id){
        if (tagService.removeById(id)){
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }
    @PutMapping()
    public R update(@RequestBody TagEntity entity){
        LambdaUpdateWrapper<TagEntity> wrapper = new LambdaUpdateWrapper<TagEntity>().set(TagEntity::getName, entity.getName()).set(TagEntity::getUpdateTime, DateUtil.format()).eq(TagEntity::getId,entity.getId());

        if (tagService.update(entity, wrapper)){
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }

    @GetMapping
    public R list(@RequestParam(defaultValue = "1") Integer pageNum , @RequestParam (defaultValue = "5") Integer pageSize , @RequestParam(defaultValue = "",required = false) String search) {
        if (pageNum <= 0|| pageSize<=0){
            return R.success(tagService.list(),tagService.count());
        }
        Page<TagEntity> page = Page.of(pageNum, pageSize);
        LambdaQueryWrapper<TagEntity> wrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isEmpty(search)) {
            wrapper = Wrappers.<TagEntity>lambdaQuery().like(TagEntity::getName, search);
        }
        List<TagEntity> list = tagService.page(page, wrapper).getRecords();
        if (list != null) {
            return R.success(list, tagService.count());
        }
        return R.error();
    }
    @GetMapping("/{id}")
    public R getOne( @PathVariable Integer id){
        TagEntity one = tagService.getById(id);
        if (one != null) {
            return R.success(one);
        }
        return R.error();
    }

}
