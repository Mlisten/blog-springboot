package cn.tenyear.blog.controller.admin;

import cn.tenyear.blog.modle.entity.RoleEntity;
import cn.tenyear.blog.service.RoleService;
import cn.tenyear.blog.utils.R;
import cn.tenyear.blog.utils.Validated;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端 HTML 未写该接口
 * @author 李胜旺
 * @date 2022/4/4
 * @email 804464376@qq.com
 */

@RestController
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public R insert(@RequestBody RoleEntity entity){
        if (!Validated.validate(entity)) {
         return R.error("参数校验不通过");
        }

        if (roleService.save(entity)) {
            return R.success();
        }
        return R.error();
    }

    @DeleteMapping("/{id}")
    public R delete( @PathVariable Integer id){
        if (roleService.removeById(id)) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    @PutMapping()
    public R update(@RequestBody RoleEntity entity){
        if (Validated.validate(entity)){
            if (roleService.updateById(entity)){
                return R.success("修改成功");
            }
        }
        return R.error("修改失败");
    }

    @GetMapping
    public R list(@RequestParam(defaultValue = "1") Integer pageNum , @RequestParam (defaultValue = "5") Integer pageSize , @RequestParam(defaultValue = "",required = false) String search){
        if (pageNum <= 0|| pageSize<=0){
            return R.success(roleService.list(),roleService.count());
        }
        Page<RoleEntity> page = Page.of(pageNum,pageSize);
        LambdaQueryWrapper<RoleEntity> wrapper  =  Wrappers.lambdaQuery();
        if (search.length()>0){
            wrapper = Wrappers.<RoleEntity>lambdaQuery().like(RoleEntity::getName, search);
        }
        List<RoleEntity> list = roleService.page(page,wrapper).getRecords();
        if (list!=null){
            return R.success(list, roleService.count());
        }
        return R.error();
    }
    @GetMapping("/{id}")
    public R getOne(@PathVariable Integer id){

        RoleEntity one = roleService.getById(id);
        if (one!=null){
            return R.success(one);
        }
        return R.error();
    }
}
