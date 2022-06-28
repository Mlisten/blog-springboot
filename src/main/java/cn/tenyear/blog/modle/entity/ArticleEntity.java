package cn.tenyear.blog.modle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Data
@Validated
@TableName ( value="t_article",autoResultMap = true)
@ApiModel(value="文章实体类",description="类名:ArticleEntity,表名:t_article")
public class ArticleEntity implements Serializable {
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

    @ApiModelProperty("标题")
    private String title;
    /**
     * wangEditor 编辑文章用到的json格式内容
     */
    @ApiModelProperty("Json格式内容")
    private String content;
    /**
     * 文章内容的HTML代码
     */
    @ApiModelProperty("HTML代码")
    private String html;
    @ApiModelProperty("浏览数")
    private Integer views;
    /**
     * 文章是否公开
     */
    @ApiModelProperty("1=公开，0=不公开")
    private Integer overt;
    @ApiModelProperty("分类ID")
    private Integer typeId;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty("所有的标签ID")
    private ArrayList<Integer> tagId;
}
