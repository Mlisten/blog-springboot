package cn.tenyear.blog.service;

import cn.tenyear.blog.modle.entity.TypeEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Service
public interface TypeService extends IService<TypeEntity> {

     /**
      * 列表展示分类
      * @return
      */
     List<Map<String,Object>> listTypes();
}
