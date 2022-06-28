package cn.tenyear.blog.service.impl;


import cn.tenyear.blog.mapper.*;
import cn.tenyear.blog.modle.entity.ArticleEntity;
import cn.tenyear.blog.modle.entity.CommentEntity;
import cn.tenyear.blog.modle.vo.ArticleVO;
import cn.tenyear.blog.service.ArticleService;
import cn.tenyear.blog.utils.DateUtil;
import cn.tenyear.blog.utils.R;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private TagMapper tagMapper ;
    @Resource
    private CommentMapper commentMapper;

    /**
     * 根据 年-月-日 排序,并各自包含总数
     * @return R
     */
    @Override
    public R allOrderByTime() {

        List<Map<String,Object>> list =  articleMapper.allOrderByTime();
        long count = 0;
        if (list!=null){
            for (Map<String,Object> map:list){
                count += (long) map.get("count");
            }
            return R.success(list,count);
        }
        return R.error();
    }

    /**
     * 不含文字内容的VO对象，R对象 包裹
     * @param id 分类id
     * @param pageSize 页数
     * @param pageNum 页码
     * @return R
     */
    @Override
    public R selectByTypeId(int id, int pageSize, int pageNum) {
        int index = (pageNum-1)*pageSize;
        LambdaQueryWrapper<ArticleEntity> eq = new LambdaQueryWrapper<ArticleEntity>()
                .select(ArticleEntity::getId,ArticleEntity::getTypeId,ArticleEntity::getTitle,ArticleEntity::getTagId,ArticleEntity::getUserId,ArticleEntity::getViews,ArticleEntity::getCreateTime)
                .eq(ArticleEntity::getTypeId ,id).eq(ArticleEntity::getOvert,1);
        List<ArticleEntity> list = articleMapper.selectPage(Page.of(index, pageSize), eq).getRecords();
        if (list!= null){
            List<ArticleVO> articleVOList = transArticleByIds(list);
            return R.success(articleVOList,list.size());
        }
        return R.error();
    }

    /**
     * 不含文字内容的VO对象，R对象 包裹,文章设置公开的
     * @param id 标签id
     * @param pageSize 页数
     * @param pageNum 页码
     * @return R
     */
    @Override
    public R selectByTagId(int id, int pageSize, int pageNum) {
        int index = (pageNum-1)*pageSize;
        List<ArticleVO> list = new ArrayList<>();
        LambdaQueryWrapper<ArticleEntity> wrapper = new LambdaQueryWrapper<ArticleEntity>().select(ArticleEntity::getTagId,ArticleEntity::getId).eq(ArticleEntity::getOvert,1);
        //查询所有文章的id，tag_id
        List<ArticleEntity> articleList = articleMapper.selectList(wrapper);
        if (articleList==null){
            return R.error("无文章");
        }
        for (ArticleEntity article : articleList) {
            ArrayList<Integer> tagIds = article.getTagId();
            // 遍历tagIds数组中每一个id
            for (int j = 0; index <= j && j < index + pageSize && j < tagIds.size(); j++) {
                Integer integer = tagIds.get(j);
                if (integer != null && integer == id) {
                    list.add(getOneArticleVO(article.getId(), false, true));
                }
            }
        }
        return R.success(list,(long)list.size());
    }

    /**
     * 不含文字内容的VO对象，R对象 包裹
     * @param time 文章的创建时期 年-月-日
     * @param pageSize 页数
     * @param pageNum 页面
     * @return R
     */
    @Override
    public R selectByCreateTime(String time, int pageSize, int pageNum) {
        String checkedTime = DateUtil.validatedDateTime(time, DateUtil.Pattern.CHINESE_DATE,false,true);
        if (checkedTime != null) {
            int index = (pageNum-1)*pageSize;
            LambdaQueryWrapper<ArticleEntity> eq = new LambdaQueryWrapper<ArticleEntity>().select(ArticleEntity::getId).likeRight(ArticleEntity::getCreateTime,checkedTime).eq(ArticleEntity::getOvert,1);
            List<ArticleEntity> list = articleMapper.selectPage(Page.of(index, pageSize), eq).getRecords();
            if (list!= null && list.size() >0){
                List<ArticleVO> articleVOS = transArticleByIds(list);
                return R.success(articleVOS,list.size());
            }
            return R.error();
        }
        return R.error("时间格式不正确");
    }

    @Override
    public R selectByTitle(String title, int pageSize, int pageNum) {
        int index = (pageNum-1)*pageSize;
        LambdaQueryWrapper<ArticleEntity> eq = new LambdaQueryWrapper<ArticleEntity>().select(ArticleEntity::getId).like(ArticleEntity::getTitle,title).eq(ArticleEntity::getOvert,1);
        List<ArticleEntity> list = articleMapper.selectPage(Page.of(index, pageSize), eq).getRecords();
        if (list!= null){
            List<ArticleVO> articleVOS = transArticleByIds(list);
            return R.success(articleVOS,list.size());
        }
        return R.error();
    }

    /**
     * 完整单个的ArticleVO信息
     * @param id 文章id
     * @return ArticleVO
     */
    @Override
    public ArticleVO getOneArticleVO(Integer id ){
       return getOneArticleVO(id,false ,false);
    }

    /**
     *
     * @param id 文章id
     * @param onlyOvert 文章是否公开 true：公开，false：不公开
     * @param isSimple 文章信息是否完整查询，true：包含content，html字段， false：不包含
     * @return ArticleVO
     */
    @Override
    public ArticleVO getOneArticleVO(Integer id, boolean onlyOvert , boolean isSimple){
        ArticleEntity article;
        //根据文章id查询的对象转化为VO对象
        if (isSimple){
            LambdaQueryWrapper<ArticleEntity> eq = new LambdaQueryWrapper<ArticleEntity>()
                    .select(ArticleEntity::getId, ArticleEntity::getViews, ArticleEntity::getOvert, ArticleEntity::getTitle, ArticleEntity::getUserId, ArticleEntity::getTagId, ArticleEntity::getTypeId, ArticleEntity::getCreateTime)
                    .eq(ArticleEntity::getId, id);
            article = articleMapper.selectOne(eq);
        }else {
            article = articleMapper.selectById(id);
        }

        if (onlyOvert){
            if (article.getOvert()!=1){
                return null;
            }
        }
        String username = userMapper.selectById(article.getUserId()).getUsername();
        String typeName = typeMapper.selectById(article.getTypeId()).getName();
        String overtName = article.getOvert() == 1 ? "公开" :"私密";
        ArrayList<String> tagNames = new ArrayList<>();
        ArrayList<Integer> tagId = article.getTagId();

        for (Integer i : tagId){
            String name = tagMapper.selectById(i).getName();
            tagNames.add(name);
        }
        Wrapper<CommentEntity> wrapper = new LambdaQueryWrapper<CommentEntity>().select(CommentEntity::getArticleId).eq(CommentEntity::getArticleId,article.getId());
        Integer comments = Math.toIntExact(commentMapper.selectCount(wrapper));
        return  new ArticleVO(article, username, tagNames, typeName, overtName,comments);

    }

    /**
     *
     * @param pageNum 页码
     * @param pageSize 页数
     * @param search 文章标题
     * @param onlyOvert 文章是否公开  true：公开，false：不公开
     * @return ArticleVO对象集合
     */
    @Override
    public List<ArticleVO> listArticleVO(Integer pageNum, Integer pageSize, String search,boolean onlyOvert){
        LambdaQueryWrapper<ArticleEntity> wrapper= new LambdaQueryWrapper<>();

        List<ArticleVO> voList = new ArrayList<>(pageSize);
        List<ArticleEntity> list;
        if (onlyOvert){
            //只返回 overt=1 的文章（公开）
            wrapper.eq(ArticleEntity::getOvert, 1);
        }
        if (!StringUtils.isEmpty(search)){
            wrapper.like(ArticleEntity::getTitle, search);
        }
        //获取对应位置的 各文章id
        list = articleMapper.selectPage(Page.of(pageNum, pageSize), wrapper.select(ArticleEntity::getId)).getRecords();
        for (ArticleEntity article : list){
            voList.add(this.getOneArticleVO(article.getId()));
        }
        return voList;
    }

    /**
     *
     * @param onlyOvert 文章是否公开
     * @return 查询文章的数量
     */
    @Override
    public Long count(boolean onlyOvert){
        LambdaQueryWrapper<ArticleEntity> wrapper = new LambdaQueryWrapper<ArticleEntity>().eq(ArticleEntity::getOvert, 1);
        if(onlyOvert){
            return articleMapper.selectCount(wrapper);
        }
        return articleMapper.selectCount(null);
    }

    /**
     * 根据多个id查询ArticleVO的集合
     * @param articleList 含文章id的集合
     * @return 不含内容的ArticleVO的集合
     */
    private List<ArticleVO> transArticleByIds(List<ArticleEntity> articleList){
        List<ArticleVO> list =  new ArrayList<>(articleList.size());
        for (ArticleEntity article:articleList){
            ArticleVO vo = getOneArticleVO(article.getId(),false, true);
            list.add(vo);
        }
        return list;
    }
}
