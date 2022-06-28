package cn.tenyear.blog.modle.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Data
public class CommentVO implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer articleId;
    private String title;
    private String content;
    private String username;
    private String createTime;
    private String updateTime;
}
