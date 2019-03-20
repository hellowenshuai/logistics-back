package com.ansel.service.impl;

import com.ansel.bean.CityExpand;
import com.ansel.bean.Region;
import com.ansel.dao.ICityExpandDao;
import com.ansel.dao.IRegionDao;
import com.ansel.dao.IRouteInfoDao;
import com.ansel.service.ICityExpandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chenshuai
 * 线路相关操作
 */
@Transactional()
@Service(value = "cityExpandService")
public class CityExpandServiceImpl implements ICityExpandService {
    private static final Logger log = LoggerFactory.getLogger(CityExpandServiceImpl.class);

    @Autowired
    private ICityExpandDao cityExpandDao;

    @Autowired
    private IRegionDao regionDao;

    @Autowired
    private IRouteInfoDao routeInfoDao;

    /**
     * @return java.util.List<com.ansel.bean.Region>
     * @description 返回所有的城市信息
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<Region> findAllRegions() {
        List<Region> regions = regionDao.findAll();
        return regions;
    }

    /**
     * @return java.util.List<com.ansel.bean.Region>
     * @description 获取开始城市
     * @params []
     * @creator chenshuai
     * @date 2019/3/14 0014
     */
    @Override
    public List<Region> findLeftRegions() {
        return regionDao.findLeftRegions();
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.CityExpand>
     * @description 获取所有中转城市
     * @params [pageable]
     * @creator chenshuai
     * @date 2019/3/14 0014
     */
    @Override
    public Page<CityExpand> findAllExpands(Pageable pageable) {
        return cityExpandDao.findAll(pageable);
    }

    /**
     * @return boolean
     * @description 新增城市扩充信息
     * @params [cityExpand]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean addExpand(CityExpand cityExpand) {
        try {
            cityExpandDao.save(cityExpand);
            routeInfoDao.truncateTable();
            return true;
        } catch (Exception e) {
            log.error("城市扩充表添加失败 | 路线信息表清空失败");
            return false;
        }
    }

    /**
     * @return boolean
     * @description 删除一条城市扩充信息
     * @params [id]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean deleteExpand(int id) {
        try {
            CityExpand cityExpand = cityExpandDao.findById(id);
            cityExpandDao.delete(cityExpand);
            routeInfoDao.truncateTable();
            return true;
        } catch (Exception e) {
            log.error("城市扩充信息表 删除失败 | 路线信息表清空失败" + id);
            return false;
        }
    }

    /**
     * @return com.ansel.bean.CityExpand
     * @description 得到一条的城市范围信息
     * @params [id]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public CityExpand findById(int id) {
        return cityExpandDao.findById(id);
    }

    /**
     * @return boolean
     * @description 更新一条城市信息
     * @params [cityExpand]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean updateExpand(CityExpand cityExpand) {
        try {
            cityExpandDao.save(cityExpand);
            routeInfoDao.truncateTable();
            return true;
        } catch (Exception e) {
            log.error("城市扩充信息表更新失败 | 路线信息表清空失败");
            return false;
        }
    }

}
