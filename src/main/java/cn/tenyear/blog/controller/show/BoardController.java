package cn.tenyear.blog.controller.show;


import cn.tenyear.blog.modle.entity.MsgBoardEntity;
import cn.tenyear.blog.service.MsgBoardService;
import cn.tenyear.blog.utils.DateUtil;
import cn.tenyear.blog.utils.R;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李胜旺
 * @date 2022/4/6
 * @email 804464376@qq.com
 */
@RestController
@RequestMapping("/msg-board")
@Api(value = "留言板控制器")
public class BoardController {
    @Autowired
    private MsgBoardService msgBoardService;

    @PostMapping
    public R insert(@RequestBody MsgBoardEntity entity) {
        if (StringUtils.isBlank(entity.getMsg())) {
            return R.error("内容不能为空");
        }
        entity.setDatetime(DateUtil.format());
        if (StringUtils.isBlank(entity.getNickname())) {
            entity.setNickname("游客");
        }
        if (msgBoardService.save(entity)) {
            return R.success();
        }
        return R.error();
    }

    @PostMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        if (msgBoardService.removeById(id)) {
            return R.success();
        }
        return R.error();
    }

    @GetMapping
    public R list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        IPage<MsgBoardEntity> iPage = new Page<>();
        iPage.setSize(pageSize);
        iPage.setCurrent(pageNum);
        IPage<MsgBoardEntity> boardEntityIPage = msgBoardService.page(iPage);
        List<MsgBoardEntity> list = boardEntityIPage.getRecords();
        return R.success(list, boardEntityIPage.getTotal());
    }
}
