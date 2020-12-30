package cilicili.jz2.service;

import cilicili.jz2.pojo.Collection;
import cilicili.jz2.pojo.Video;

import java.util.List;

public interface ICollectionService {
    void addcollection(Collection collection);
    List<Video> showCollection(Integer userId);
}
