package cn.tenyear.blog.utils;

import cn.tenyear.blog.modle.entity.*;

import java.util.ArrayList;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 *
 * 验证 实体类中除 Integer 这种无法转化为基本类型的的字段，是否无空白
 * 验证 能转化为基本类型的字段是否在规定的范围之内
 * true : 合法 ; false : 不合法
 */
public class Validated {
    private Validated(){}
    public static boolean validate(Object obj){
        if (obj instanceof UserEntity user){
            return validateUser(user);
        }else if (obj instanceof ArticleEntity article){
            return validateArticle(article);
        }else if (obj instanceof CommentEntity comment){
            return validateComment(comment);
        }else if (obj instanceof TypeEntity type){
            return validateType(type);
        }else if (obj instanceof TagEntity tag){
            return validateTag(tag);
        }else if (obj instanceof RoleEntity role){
            return validateRole(role);
        } else {
            throw new  RuntimeException("自定义验证规则中，没有这种类型===>" + obj.getClass().getName());
        }
    }
    // 用户规则
    // phone字段必须正确匹配正则，与前端页面中的规则一致
    // 用户名，密码不得含有空格
    // roleId修改时，范围在[1,10]之间，sex范围必须在[1,3]之间，status范围必须是 1 或 0
    private static boolean validateUser(UserEntity user){
        boolean isUsername = false;
        boolean isPassword = false;
        boolean isPhone = false;
        boolean isRoleId = false;
        boolean isSex = false;
        boolean isStatus = false;
        if (user.getUsername() == null || !user.getUsername().matches("s+")){
            isUsername = true;
        }
        if (user.getPassword() == null || !user.getUsername().matches("s+") ){
            isPassword = true;
        }
        if (user.getPhone() == null || user.getPhone().toString().matches("0?(13|14|15|17|18)[0-9]{9}")){
            isPhone = true ;
        }
        if ( user.getRoleId() == null || (user.getRoleId() >= 1 && user.getRoleId() <= 10)) {
            isRoleId = true;
        }
        if (user.getSex() == null || user.getSex() == 1 || user.getSex() == 2 || user.getSex()== 3){
            isSex = true;
        }
        if (user.getStatus() == null ||  user.getStatus() == 1 || user.getStatus() == 0){
            isStatus = true;
        }
        return isUsername && isPassword && isPhone && isRoleId && isSex && isStatus;
    }
    // 文章规则
    // 用户ID必须有，文章标题不得有空格，文章primary(是否公开)，必须设置在[1,0]两个数之中，文章内容字符个数必须大于10
    private static boolean validateArticle(ArticleEntity article){
        boolean isUserId = false;
        boolean isTitle  = false;
        boolean isPrimary= false;
        boolean isContentHtml= false;
        if (article.getUserId()!=null){
            isUserId = true;
        }
        if (article.getTitle()!=null && !article.getTitle().matches("s+")){
            isTitle  = true;
        }
        if (article.getHtml() != null && article.getHtml().length()>10){
            isContentHtml = true;
        }
        if (article.getOvert() == 1 || article.getOvert() == 0){
            isPrimary = true;
        }
        return isUserId && isTitle && isContentHtml && isPrimary;
    }
    // 评论规则
    // 用户ID,文章ID，必须设置
    private static boolean validateComment(CommentEntity comment){
        boolean isUserId = false;
        boolean isArticleId  = false;
        if (comment.getUserId() == null || comment.getUserId() >= 0){
            isUserId = true;
        }
        if (comment.getArticleId()!=null && comment.getArticleId() > 0){
            isArticleId = true;
        }
        return isArticleId  && isUserId;
    }
    // 分类规则
    // name 字符长度应在[1,10]
    private static boolean validateType(TypeEntity type){
        return type.getName() != null && !type.getName().matches("s+")&& type.getName().length() > 0 && type.getName().length() <= 10;
    }
    // 标签规则
    // name 字符长度应在[1,10]
    private static boolean validateTag(TagEntity tag) {
        return tag.getName() != null && !tag.getName().matches("s+") && tag.getName().length() > 0 && tag.getName().length() <= 10;
    }
    // 权限规则
    // name:中文描述 字符长度应在[1,10],两个变量都不能有空格
    // api 必须以 / 开头
    private static boolean validateRole(RoleEntity role) {
        boolean b = role.getName() != null && !role.getName().matches("s+") && role.getName().length() > 0 && role.getName().length() <= 10;
        ArrayList<String> list = role.getApi();
        for (String str:list){
            if (str.indexOf("/admin/")!=0 || str.matches("s+")){
                return false;
            }
        }
        return  b;
    }
}
