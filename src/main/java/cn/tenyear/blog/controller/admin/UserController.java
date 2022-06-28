package cn.tenyear.blog.controller.admin;

import cn.tenyear.blog.modle.entity.UserEntity;
import cn.tenyear.blog.modle.vo.UserVO;
import cn.tenyear.blog.service.UserService;
import cn.tenyear.blog.utils.Crud;
import cn.tenyear.blog.utils.DateUtil;
import cn.tenyear.blog.utils.R;
import cn.tenyear.blog.utils.Validated;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
/**
 * 包含普通用户(/user) 和 系统用户(/sys-user)
 * @author 李胜旺
 * @date 2022/4/4
 * @email 804464376@qq.com
 */
@RestController
@RequestMapping("/admin")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping({"/user"})
    public R insertUser(@RequestBody UserEntity user){
        Crud.addDate(user);
        user.setRoleId(10);
        return getR(user);
    }
    @PostMapping({"/sys-user"})
    public R insertSysUser(@RequestBody UserEntity user){
        Crud.addDate(user);
        return getR(user);
    }

    @DeleteMapping({"/user/{id}", "/sys-user/{id}"})
    public R delete(@PathVariable Integer id){

        if (userService.removeById(id)){
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }
    @PutMapping({"/user","/sys-user"})
    public R update(@RequestBody UserEntity entity){
        entity.setUpdateTime(DateUtil.format());
        if (userService.updateById(entity)){
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    @GetMapping("/user")
    public R listUser(@RequestParam(defaultValue = "1") Integer pageNum , @RequestParam (defaultValue = "5") Integer pageSize , @RequestParam(defaultValue = "",required = false) String search){
        // 权限大于等于10的为普通用户
        int pageIndex = (pageNum-1) * pageSize ;
        ArrayList<UserVO> listUserVO = userService.listUserVO(pageIndex, pageSize ,search );
        if (listUserVO!=null){
            return R.success(listUserVO,userService.countByUser());
        }
        return R.error("查询失败");
    }
    @GetMapping("/sys-user")
    public R listSysUser(@RequestParam(defaultValue = "1") Integer pageNum , @RequestParam (defaultValue = "5") Integer pageSize , @RequestParam(defaultValue = "",required = false) String search){
        // 权限小于10的为系统用户
        int pageIndex = (pageNum-1) * pageSize ;
        ArrayList<UserVO> listUserVO = userService.listSysUserVO(pageIndex, pageSize ,search);
        if (listUserVO!=null){
            return R.success(listUserVO,userService.countBySysUser());
        }
        return R.error("查询失败");
    }

    @GetMapping({"/user/{id}","/sys-user/{id}"})
    public R getOne(@PathVariable Integer id){
        UserVO userVO = userService.getOneUserVO(id);
        if (userVO!=null){
            return R.success(userVO);
        }
      return R.error("查询失败");
    }
    private R getR(@RequestBody UserEntity user) {
        if (Validated.validate(user)){
            LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getPhone,user.getPhone());

            UserEntity one = userService.getOne(wrapper);
            if (one!=null){
                return R.error("该账号已存在");
            }
            userService.save(user);
            return R.success();
        }
        return R.error("参数校验未通过");
    }
}
