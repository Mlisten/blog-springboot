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

/**
 * @author 李胜旺
 * @date 2022/4/6
 * @email 804464376@qq.com
 */
@Data
@Validated
@TableName("t_msgboard")
@ApiModel(value = "留言实体类", description = "类名:MsgBoardEntity,表名:t_msgboard")
public class MsgBoardEntity {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty("日期")
    private String datetime;

    @ApiModelProperty(value = "昵称,非必要项")
    private String nickname;

    @ApiModelProperty(value = "消息")
    @NotBlank(message = "内容不能为空")
    private String msg;
}
