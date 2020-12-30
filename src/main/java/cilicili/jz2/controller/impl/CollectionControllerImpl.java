package cilicili.jz2.controller.impl;

import cilicili.jz2.controller.ICollectionController;
import cilicili.jz2.pojo.*;
import cilicili.jz2.service.impl.CollectionServiceImpl;
import cilicili.jz2.service.impl.UserServiceImpl;
import cilicili.jz2.service.impl.VideoServiceImpl;
import cilicili.jz2.utils.TokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/collection")
public class CollectionControllerImpl implements ICollectionController {
    private final CollectionServiceImpl collectionService;
    private final UserServiceImpl userService;
    private  final VideoServiceImpl videoService;
    @Autowired
    public CollectionControllerImpl(CollectionServiceImpl collectionService, UserServiceImpl userService, VideoServiceImpl videoService) {
        this.collectionService = collectionService;
        this.userService = userService;
        this.videoService = videoService;
    }
    @RequestMapping ("/add")
    @ResponseBody
    @Override
    public Map<String, Serializable> addcollection(Collection collection, String token) {
        Map<String, Serializable> result = new HashMap<>();
        result.put("status", "failure");
        try {
            do {
                Token tokenCheck = TokenUtil.checkToken(token, TokenUtil.TokenUssage.DEFAULT);
                User user = userService.findUserById(tokenCheck.getUserId());
                if (user == null) {
                    throw new TokenUtil.TokenNotFound("用户不存在");
                }
                Video video = videoService.findVideoById(collection.getVideoId());
                if (video == null) {
                    result.put("msg", "视频 id 不正确");
                    break;
                }
                collection.setCollectionId(null);
                collection.setUserId(user.getId());
                collection.setVideoId(video.getId());
                try {
                    collectionService.addcollection(collection);
                    result.put("status","success");

                    /*videoService.addVideo(video);
                    result.put("status", "success");
                    video = videoService.findVideoByUrl(video.getUrl());
                    result.put("video", video);*/
                } catch (Exception e) {
                    result.put("msg", "未知错误");
                }
            } while (false);
        } catch (TokenUtil.TokenExpired | TokenUtil.TokenNotFound | TokenUtil.TokenOverAuthed | TokenUtil.TokenUssageNotMatched tokenError) {
            result.put("msg", tokenError.getMessage());
        }
        return result;
    }
    @RequestMapping ("/show")
    @ResponseBody
    @Override
    public Map<String, Serializable> showcollection(@RequestParam("userId") Integer userId,@RequestParam("offset") Integer offset) {
        Map<String, Serializable> result = new HashMap<>();
        if (offset == null) {
            offset = 0;
        }
        PageHelper.startPage(offset, 12);
        ArrayList<Video> videos = (ArrayList<Video>) videoService.showCollection(userId);
        PageInfo<Video> pageInfo = new PageInfo<>(videos);
        result.put("page", pageInfo);
        return result;
    }
}
