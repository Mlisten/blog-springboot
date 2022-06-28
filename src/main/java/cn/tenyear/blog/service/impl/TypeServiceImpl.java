package cn.tenyear.blog.service.impl;

import cn.tenyear.blog.mapper.TypeMapper;
import cn.tenyear.blog.modle.entity.TypeEntity;
import cn.tenyear.blog.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, TypeEntity> implements TypeService {
    @Resource
    private TypeMapper typeMapper;

    @Override
    public List<Map<String, Object>> listTypes() {

        return typeMapper.listTypes();
    }
}
