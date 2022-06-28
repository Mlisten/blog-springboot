package cn.tenyear.blog.modle.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Data
public class UserVO implements Serializable {
    private BigInteger phone;
    private String username;
    private String password;
    private Integer roleId;
    private Integer sex;
    private Integer status;

    private String roleName;

    private Integer id;
    private String createTime;
    private String updateTime;
}
