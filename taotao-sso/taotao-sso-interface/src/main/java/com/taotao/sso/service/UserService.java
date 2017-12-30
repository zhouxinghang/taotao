package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * Created by admin on 2017/12/30.
 */
public interface UserService {

    /**
     * 注册用户时，检查数据是否可用
     * @param data
     * @param type
     * @return
     */
    TaotaoResult checkData(String data, int type);
    TaotaoResult register(TbUser user);
    TaotaoResult login(String username, String password);
    TaotaoResult getUserByToken(String token);
}
