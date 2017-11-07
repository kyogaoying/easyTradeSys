package org.sally.service;

import org.sally.dao.UserInfoDao;
import org.sally.entities.Condition;
import org.sally.entities.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	@Autowired
	private UserInfoDao userInfoDao;
	/**
	 * 全表查询
	 * @param page 要查的页数
	 * @return 查询出的结果
	 */
	public List<UserInfo> getUserInfos(int page){
		return userInfoDao.queryAll(page);
	}
	/**
     * 获取满足条件的总页数
     * @return 总页数
     */
	public double getTotalPagesAfterFindOne(String user_name) {
		return userInfoDao.getTotalPagesAfterFindOne(user_name);
	}
    /**
     * 获取总页数
     * @return 总页数
     */
	public double getTotalPages() {
		return userInfoDao.getTotalPages();
	}
	/**
	 * 查询指定记录
	 * @param user_name 查询条件
	 * @return 查询出的结果
	 */
	public List<UserInfo> getUser(String user_name,int page){	
		return userInfoDao.queryOne(user_name,page);
	}
	/**
	 * 保存
	 * @param userInfo 要保存的记录
	 */
	public void updateAndSaveUser(UserInfo userInfo) {
		userInfoDao.updateAndSave(userInfo);
	}
	
	/**
	 * 获取下一个编号
	 * @return 编号
	 */
	public String getNextVal() {
		int nextVal_int=userInfoDao.getMax()+1;
		
		return String.format("%05d", nextVal_int);
	}
	/**
	 * 删除记录
	 * @param userInfos 待删除的记录
	 * @return 删除的行数
	 */
	public int deleteUser(List<UserInfo> userInfos) {
		int count=0;
		for (UserInfo userInfo : userInfos) {
			count=count+userInfoDao.delete(userInfo); 
		}
		return count;
	}

	/**
	 * 查询所有符合条件的入库单
	 *
	 * @param currPage 当前页码
	 * @param conditions 条件集合
	 * @return 入库单集合
	 */
	public List<UserInfo> find(final int currPage, final List<Condition> conditions) throws Exception
	{
		return userInfoDao.find(currPage,conditions);
	}

	/**
	 * 获取查询数据的总行数
	 *
	 * @param conditions 条件集合
	 * @return 总行数
	 */
	public long getCount(final List<Condition> conditions)
	{
		return userInfoDao.getCount(conditions);
	}
}
