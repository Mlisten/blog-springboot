package cn.tenyear.blog.modle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Data
@Validated
@TableName("t_type")
@ApiModel(value = "分类实体类",description="类名:TypeEntity,表名:t_type")
public class TypeEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty("创建日期")
    private String createTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty("修改日期")
    private String updateTime;

    @NotBlank(message = "分类名称不能为空")
    @ApiModelProperty("分类名称")
    private String name;
}
