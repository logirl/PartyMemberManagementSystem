package cc.logirl.pmms.dao;

import cc.logirl.pmms.domain.Info;

import java.util.List;

/**
 * Created by xinxi on 2016/10/21.
 */
public interface InfoDao {
    /**
     * 查询记录的总条数
     *
     * @return
     */
    Long getTotalRecords();

    /**
     * 查询分页数据
     *
     * @param startIndex 每页开始记录的索引编号
     * @param pagesize   每页显示的记录条数
     * @return
     */
    List<Info> findPageRecords(int startIndex, int pagesize);

    /**
     * 根据学号查询Info信息
     *
     * @param studentNumber
     * @return
     */
    Info findInfoByStudentNumber(Integer studentNumber);

    /**
     * 添加党员信息
     *
     * @param info
     */
    void addInfo(Info info);

    /**
     * 删除党员信息
     *
     * @param id
     */
    void deleteInfoById(String id);

    /**
     * 根据id获取党员信息
     *
     * @param id
     * @return
     */
    Info findInfoById(String id);

    /**
     * 修改党员信息
     *
     * @param info
     */
    void updateInfo(Info info);

    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDelete(String ids);
}
