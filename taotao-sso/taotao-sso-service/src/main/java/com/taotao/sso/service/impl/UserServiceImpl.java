package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.dao.TbUserDao;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserQuery;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/12/30.
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserDao tbUserDao;

    @Override
    public TaotaoResult checkData(String data, int type) {
        TbUserQuery tbUserQuery = new TbUserQuery();
        TbUserQuery.Criteria criteria = tbUserQuery.createCriteria();
        //设置查询条件
        //1判断用户名是否可用
        if(type == 1) {
            criteria.andUsernameEqualTo(data);
        }
        //2判断手机号是否可用
        else if (type == 2) {
            criteria.andPhoneEqualTo(data);
        }
        //3判断邮箱是否可用
        else if (type == 3) {
            criteria.andEmailEqualTo(data);
        }
        //执行查询
        List<TbUser> users = tbUserDao.selectByExample(tbUserQuery);
        if(!CollectionUtils.isEmpty(users)) {
            //查询到数据，返回false
            return TaotaoResult.ok(false);
        }
        //数据可用
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult login(String username, String password) {





        return null;
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        return null;
    }

    @Override
    public TaotaoResult register(TbUser user) {
        //检查数据的有效性
        if(StringUtils.isEmpty(user.getUsername())) {
            return TaotaoResult.build(400, "用户名不能为空");
        }
        //检查数据是否可用
        TaotaoResult checkResult = checkData(user.getUsername(), 1);
        if(!(Boolean) checkResult.getData()) {
            return TaotaoResult.build(400, "用户名已存在");
        }
        //判断密码是否为空
        if(StringUtils.isEmpty(user.getPassword())) {
           return TaotaoResult.build(400, "密码不呢个为空");
        }
        //校验手机号
        if(StringUtils.isEmpty(user.getPhone())) {
            checkResult = checkData(user.getPhone(), 2);
            if(!(Boolean) checkResult.getData()) {
                return TaotaoResult.build(400, "手机号重复");
            }
        }
        //校验邮箱
        if(StringUtils.isEmpty(user.getEmail())) {
            checkResult = checkData(user.getEmail(), 3);
            if(!(Boolean) checkResult.getData()) {
                return TaotaoResult.build(400, "邮箱重复");
            }
        }
        //校验通过后，补全pojo
        user.setCreated(new Date());2
        user.setUpdated(new Date());
        //密码MD5加密
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        //插入数据
        tbUserDao.insert(user);
        return TaotaoResult.ok();
    }
}
