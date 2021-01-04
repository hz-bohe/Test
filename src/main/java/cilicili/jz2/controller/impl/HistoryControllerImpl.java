package cilicili.jz2.controller.impl;

import cilicili.jz2.controller.IHistoryController;
import cilicili.jz2.pojo.*;
import cilicili.jz2.service.impl.HistoryServiceImpl;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/history")
public class HistoryControllerImpl implements IHistoryController {
    private final HistoryServiceImpl historyService;
    private final UserServiceImpl userService;
    private  final VideoServiceImpl videoService;
    @Autowired
    public HistoryControllerImpl(HistoryServiceImpl historyService, UserServiceImpl userService, VideoServiceImpl videoService) {
        this.historyService = historyService;
        this.userService = userService;
        this.videoService = videoService;
    }
    @RequestMapping ("/add")
    @ResponseBody
    @Override
    public Map<String, Serializable> addhistory(History history, String token) {
        Map<String, Serializable> result = new HashMap<>();
        result.put("status", "failure");
        try {
            do {
                Token tokenCheck = TokenUtil.checkToken(token, TokenUtil.TokenUssage.DEFAULT);
                User user = userService.findUserById(tokenCheck.getUserId());
                if (user == null) {
                    throw new TokenUtil.TokenNotFound("用户不存在");
                }
                Video video = videoService.findVideoById(history.getVideoId());
                if (video == null) {
                    result.put("msg", "视频 id 不正确");
                    break;
                }
                LocalDate time=LocalDate.now();
                
                history.setWatchTime(time);
                history.setHistoryId(null);
                history.setUserId(user.getId());
                history.setVideoId(video.getId());
                try {
                	historyService.addhistory(history);
                    result.put("status","success");
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
    public Map<String, Serializable> showhistory( Integer userId, Integer offset) {
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
    
    @RequestMapping ("/delete")
	@ResponseBody
	@Override
	public Map<String, Serializable> deleteHistory(Integer id, String token){
    	Map<String, Serializable> result = new HashMap<>();
    	result.put("status", "failure");
    	try {
    		Token tokenCheck = TokenUtil.checkToken(token, TokenUtil.TokenUssage.DEFAULT);
    		User user = userService.findUserById(tokenCheck.getUserId());
    		History history = historyService.findHistoryById(id);
    		do {
    			if (history == null) {
					result.put("msg", "没有该历史记录");
					break;
				} else if (user == null) {
					throw new TokenUtil.TokenNotFound("用户不存在");
				} else if (!user.getId().equals(history.getUserId())) {
					throw new TokenUtil.TokenNotFound("非本人操作，拒绝授权");
				}
    			try {
					historyService.deleteHistory(id);
					result.put("status", "success");
				} catch (Exception e) {
					result.put("msg", "未知错误");
				}
    		} while (false);
    	}catch (TokenUtil.TokenExpired | TokenUtil.TokenNotFound | TokenUtil.TokenOverAuthed | TokenUtil.TokenUssageNotMatched tokenError) {
    		result.put("msg", tokenError.getMessage());
    	}
    	return result;
    }
	
	
    
    
}
