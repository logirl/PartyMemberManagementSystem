package cc.logirl.pmms.dao.impl;

import cc.logirl.dbhelper.DBHelper;
import cc.logirl.dbhelper.handler.BeanHandler;
import cc.logirl.dbhelper.handler.BeanListHandler;
import cc.logirl.dbhelper.handler.ScalarHandler;
import cc.logirl.pmms.dao.InfoDao;
import cc.logirl.pmms.domain.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by xinxi on 2016/10/21.
 */
public class InfoDaoImpl implements InfoDao {
    private DBHelper dh = DBHelper.getInstance();
    static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public Long getTotalRecords() {
        logger.debug("查找Info数量");
        return dh.query("select count(*) from info", null, new ScalarHandler());
    }


    @Override
    public List<Info> findPageRecords(int startIndex, int pagesize) {
        logger.debug("分页查找Info：[开始索引 {}，每页条数 {}]", startIndex, pagesize);
        return dh.query("select * from info limit ?,?",
                new Object[]{startIndex, pagesize}, new BeanListHandler(Info.class));
    }

    @Override
    public Info findInfoByStudentNumber(Integer studentNumber) {
        logger.debug("根据学号查找Info：[{}]", studentNumber);
        return dh.query("select * from info where studentNumber=?",
                new Object[]{studentNumber}, new BeanHandler(Info.class));
    }

    @Override
    public void addInfo(Info info) {
        logger.debug("添加Info：[{}]", info);
        dh.update("insert into info(studentNumber,name,activistTime,developTime,status,score,batch) values(?,?,?,?,?,?,?)", new Object[]{
                info.getStudentNumber(), info.getName(), info.getActivistTime(), info.getDevelopTime(), info.getStatus(), info.getScore(), info.getBatch()
        });
    }

    @Override
    public void deleteInfoById(String id) {
        logger.debug("根据id删除Info：[{}]", id);
        dh.update("delete from info where id = ?", new Object[]{id});
    }

    @Override
    public Info findInfoById(String id) {
        logger.debug("根据id查找Info：[{}]", id);
        return dh.query("select * from info where id = ?", new Object[]{id}, new BeanHandler(Info.class));
    }

    @Override
    public void updateInfo(Info info) {
        logger.debug("修改Info：[{}]", info);
        dh.update("update info set studentNumber=?,name=?,activistTime=?,developTime=?,status=?,score=?,batch=? where id=?",
                new Object[]{info.getStudentNumber(), info.getName(), info.getActivistTime(), info.getDevelopTime(), info.getStatus(), info.getScore(), info.getBatch(), info.getId()});
    }

    @Override
    public void batchDelete(String ids) {
        logger.debug("批量删除Info：[{}]", ids);
        dh.update("delete from info where id in (" + ids.replaceAll("\\d+", "?") + ")", ids.split(","));
    }
}
