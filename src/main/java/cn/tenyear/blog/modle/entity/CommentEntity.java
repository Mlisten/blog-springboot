package cn.tenyear.blog.modle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Data
@Validated
@TableName("t_comment")
@ApiModel(value = "评论实体类",description="类名:CommentEntity,表名:t_comment")
public class CommentEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty("创建日期")
    private String createTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty("修改日期")
    private String updateTime;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("文章ID")
    private Integer articleId;

    @ApiModelProperty("评论的内容")
    private String content;
}
