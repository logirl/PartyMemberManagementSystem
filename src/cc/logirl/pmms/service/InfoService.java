package cc.logirl.pmms.service;

import cc.logirl.pmms.domain.Info;
import cc.logirl.pmms.ioc.Transactional;
import cc.logirl.pmms.page.Page;

/**
 * Created by xinxi on 2016/10/22.
 */
public interface InfoService {
    /**
     * 根据用户要看的页码返回封装了分页有关数据的Page对象
     *
     * @param pagenum 页码
     * @return 分页有关数据
     */
    Page findPageReocrds(String pagenum);

    /**
     * 根据学号查询Info信息
     *
     * @param studentNumber 学号
     * @return
     */
    Info getInfoByStudentNumber(Integer studentNumber);

    /**
     * 添加党员信息
     *
     * @param info
     */
    @Transactional
    void addInfo(Info info);

    /**
     * 删除党员信息
     *
     * @param id
     */
    @Transactional
    void deleteInfoById(String id);

    /**
     * 根据id获取党员信息
     *
     * @param id
     * @return
     */
    Info getInfoById(String id);

    /**
     * 修改党员信息
     *
     * @param info
     */
    @Transactional
    void modifyInfo(Info info);

    /**
     * 批量删除
     *
     * @param ids
     */
    @Transactional
    void batchDelete(String ids);
}
