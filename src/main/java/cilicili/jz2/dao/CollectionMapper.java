package cilicili.jz2.dao;

import cilicili.jz2.pojo.Collection;
import cilicili.jz2.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectionMapper {
    int insert(Collection collection);

    List<Video> showcollections(@Param("userId") Integer userId);
}
