package cilicili.jz2.controller;

import cilicili.jz2.pojo.Collection;

import java.io.Serializable;
import java.util.Map;

public interface ICollectionController {
    Map<String, Serializable> addcollection(Collection collection, String token);
    Map<String, Serializable> showcollection(Integer userId, Integer offset);

}
