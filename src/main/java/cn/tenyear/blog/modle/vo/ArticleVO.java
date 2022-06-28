package cn.tenyear.blog.modle.vo;

import cn.tenyear.blog.modle.entity.ArticleEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Data
public class ArticleVO implements Serializable {

    private Integer id;
    private Integer userId;
    private String username;
    private ArrayList<Integer> tagId;
    private ArrayList<String> tagNames;
    private Integer typeId;
    private String typeName;

    private String title;
    private String content;
    private String html;
    private Integer views;
    private Integer comments;
    private Integer overt;
    private String overtName;
    private String createTime;
    private String updateTime;

    public ArticleVO() {
    }

    public ArticleVO(Integer id, Integer userId, String username, ArrayList<Integer> tagId, ArrayList<String> tagNames, Integer typeId, String typeName, String title, String content, String html, Integer views, Integer comments, Integer overt,String overtName, String createTime, String updateTime) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.tagId = tagId;
        this.tagNames = tagNames;
        this.typeId = typeId;
        this.typeName = typeName;
        this.title = title;
        this.content = content;
        this.html = html;
        this.views = views;
        this.comments = comments;
        this.overt = overt;
        this.overtName = overtName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ArticleVO(ArticleEntity article, String username, ArrayList<String> tagNames, String typeName, String overtName, Integer comments){
        this.id = article.getId();
        this.userId = article.getUserId();
        this.username = username;
        this.tagId = article.getTagId();
        this.tagNames = tagNames;
        this.typeId = article.getTypeId();
        this.typeName = typeName;
        this.title = article.getTitle();
        this.content = article.getContent();
        this.html = article.getHtml();
        this.views = article.getViews();
        this.comments = comments;
        this.overt = article.getOvert();
        this.overtName = overtName;
        this.createTime = article.getCreateTime();
        this.updateTime = article.getUpdateTime();
    }
}
