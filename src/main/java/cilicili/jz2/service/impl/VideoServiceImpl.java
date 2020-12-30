package cilicili.jz2.service.impl;

import cilicili.jz2.dao.CollectionMapper;
import cilicili.jz2.dao.MyVideoMapper;
import cilicili.jz2.dao.VideoMapper;
import cilicili.jz2.pojo.Video;
import cilicili.jz2.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service ("videoService")
public class VideoServiceImpl implements IVideoService {
	private final VideoMapper videoMapper;
	private final MyVideoMapper myVideoMapper;
	private final CollectionMapper collectionMapper;

	@Autowired
	public VideoServiceImpl(VideoMapper videoMapper, MyVideoMapper myVideoMapper, CollectionMapper collectionMapper) {
		this.videoMapper = videoMapper;
		this.myVideoMapper = myVideoMapper;
		this.collectionMapper = collectionMapper;
	}

	@Override
	public Video findVideoById(Integer id) {
		return myVideoMapper.findById(id);
	}

	@Override
	public Video findVideoByUrl(String url) {
		return myVideoMapper.findByUrl(url);
	}

	@Override
	public void addVideo(Video video) {
		videoMapper.insert(video);
	}

	@Override
	public void updateVideo(Video video) {
		videoMapper.updateByPrimaryKeySelective(video);
	}

	@Override
	public List<Video> showVideos() {
		return myVideoMapper.findAllVideos();
	}

	@Override
	public List<Video> queryVideos(String keyword) {
		return myVideoMapper.queryVideo("%" + keyword + "%");
	}

	@Override
	public List<Video> showCollection(Integer userId) {
		return videoMapper.showcollections(userId);
	}
}
