package cilicili.jz2.service.impl;

import cilicili.jz2.dao.CollectionMapper;
import cilicili.jz2.dao.VideoMapper;
import cilicili.jz2.pojo.Collection;
import cilicili.jz2.pojo.Video;
import cilicili.jz2.service.ICollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("collectionService")
public class CollectionServiceImpl implements ICollectionService {
    private final VideoMapper videoMapper;
    private final CollectionMapper collectionMapper;
    @Autowired
    public CollectionServiceImpl(VideoMapper videoMapper, CollectionMapper collectionMapper) {
        this.videoMapper = videoMapper;
        this.collectionMapper = collectionMapper;
    }
    @Override
    public void addcollection(Collection collection) {
        collectionMapper.insert(collection);
    }

    @Override
    public List<Video> showCollection(Integer userId) {
        return collectionMapper.showcollections(userId);
    }
}
