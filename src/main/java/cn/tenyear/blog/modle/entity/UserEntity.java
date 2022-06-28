package cn.tenyear.blog.modle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Data
@Validated
@TableName("t_user")
@ApiModel(value = "用户实体类",description="类名:UserEntity,表名:t_user")
public class UserEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty("创建日期")
    private String createTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty("修改日期")
    private String updateTime;

    @ApiModelProperty("13位手机号码")
    @Pattern(regexp = "/0?(13|14|15|17|18)[0-9]{9}/",message = "手机号码格式有误")
    private BigInteger phone;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    @Length(min = 1,max = 20,message = "用户名长度不合法")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Length(min = 1,max = 255,message = "密码长度不合法")
    private String password;

    @Min(value = 1,message = "权限id有误")
    private Integer roleId;

    @ApiModelProperty("性别:1=男，2=女，3=未知")
    @Size(min = 1,max = 3,message = "性别参数不合法")
    private Integer sex;

    @ApiModelProperty("状态,1=启用，0=不启用")
    @Size(min = 0,max = 1,message = "状态参数不合法")
    private Integer status;
}
