package cn.tenyear.blog.service.impl;

import cn.tenyear.blog.mapper.MsgBoardMapper;
import cn.tenyear.blog.modle.entity.MsgBoardEntity;
import cn.tenyear.blog.service.MsgBoardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 李胜旺
 * @date 2022/4/6
 * @email 804464376@qq.com
 */
@Service
public class MsgBoardServiceImpl extends ServiceImpl<MsgBoardMapper, MsgBoardEntity> implements MsgBoardService {
}
