package cn.tenyear.blog.controller.admin;

import cn.tenyear.blog.modle.entity.TypeEntity;
import cn.tenyear.blog.service.TypeService;
import cn.tenyear.blog.utils.Crud;
import cn.tenyear.blog.utils.DateUtil;
import cn.tenyear.blog.utils.R;
import cn.tenyear.blog.utils.Validated;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 李胜旺
 * @date 2022/4/4
 * @email 804464376@qq.com
 */
@RestController
@RequestMapping("/admin/type")
public class TypeController {
    @Resource
    private TypeService typeService;

    @PostMapping("/{name}")
    public R insert(@PathVariable String name) {
        TypeEntity type = new TypeEntity();
        type.setName(name);
        if (!Validated.validate(type)) {
            return R.error("参数长度必须在 1-10 之内");
        }
        Crud.addDate(type);
        LambdaQueryWrapper<TypeEntity> wrapper = new LambdaQueryWrapper<TypeEntity>().eq(TypeEntity::getName, name);
        TypeEntity one = typeService.getOne(wrapper);
        if (one == null) {
            if (typeService.save(type)) {
                return R.success();
            }
        }
        return R.error();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        if (typeService.removeById(id)) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    @PutMapping()
    public R update(@RequestBody TypeEntity entity) {
        LambdaUpdateWrapper<TypeEntity> wrapper = new LambdaUpdateWrapper<TypeEntity>().set(TypeEntity::getName, entity.getName()).set(TypeEntity::getUpdateTime, DateUtil.format()).eq(TypeEntity::getId, entity.getId());
        if (typeService.update(entity, wrapper)) {
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }

    @GetMapping
    public R list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "", required = false) String search) {
        Page<TypeEntity> page = Page.of(pageNum, pageSize);
        LambdaQueryWrapper<TypeEntity> wrapper = Wrappers.lambdaQuery();
        if (search.length() > 0) {
            wrapper = Wrappers.<TypeEntity>lambdaQuery().like(TypeEntity::getName, search);
        }

        List<TypeEntity> list = typeService.page(page, wrapper).getRecords();
        if (list != null) {
            return R.success(list, typeService.count());
        }
        return R.error();
    }

    @GetMapping("/{id}")
    public R getOne(@PathVariable Integer id) {
        TypeEntity one = typeService.getById(id);
        if (one != null) {
            return R.success(one);
        }
        return R.error();
    }
}
