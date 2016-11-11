package cc.logirl.pmms.service.impl;

import cc.logirl.pmms.dao.InfoDao;
import cc.logirl.pmms.dao.impl.InfoDaoImpl;
import cc.logirl.pmms.domain.Info;
import cc.logirl.pmms.page.Page;
import cc.logirl.pmms.service.InfoService;

import java.util.List;

/**
 * Created by xinxi on 2016/10/22.
 */
public class InfoServiceImpl implements InfoService {
    private InfoDao infoDao = new InfoDaoImpl();

    @Override
    public Page findPageReocrds(String pagenum) {
        int totalRecords = infoDao.getTotalRecords().intValue();
        Page<Info> page = new Page(Integer.parseInt(pagenum), totalRecords);
        List<Info> pageRecords = infoDao.findPageRecords(page.getStartIndex(), page.getPagesize());
        page.setRecords(pageRecords);
        return page;
    }

    @Override
    public Info getInfoByStudentNumber(Integer studentNumber) {
        return infoDao.findInfoByStudentNumber(studentNumber);
    }

    @Override
    public void addInfo(Info info) {
        infoDao.addInfo(info);
    }

    @Override
    public void deleteInfoById(String id) {
        infoDao.deleteInfoById(id);
    }

    @Override
    public Info getInfoById(String id){
        return infoDao.findInfoById(id);
    }

    @Override
    public void modifyInfo(Info info) {
        infoDao.updateInfo(info);
    }

    @Override
    public void batchDelete(String ids) {
        infoDao.batchDelete(ids);
    }


}
