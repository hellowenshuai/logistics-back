package com.ansel.service.impl;

import com.ansel.bean.CityExpand;
import com.ansel.bean.Region;
import com.ansel.bean.RouteInfo;
import com.ansel.dao.ICityExpandDao;
import com.ansel.dao.IRegionDao;
import com.ansel.dao.IRouteInfoDao;
import com.ansel.service.IRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * @author chenshuai
 */
@Service(value = "routeService")
@Transactional(rollbackFor = Exception.class)
public class RouteServiceImpl implements IRouteService {

    private static final Logger log = LoggerFactory.getLogger(RouteServiceImpl.class);

    @Autowired
    private IRegionDao regionDao;

    @Autowired
    private ICityExpandDao cityExpandDao;

    @Autowired
    private IRouteInfoDao routeInfoDao;

    private List<List<Integer>> routeList;

    /**
     * @return void
     * @description 新增城市扩充信息
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public void generateRoute() {

        routeList = new LinkedList<>();
        List<Region> list = regionDao.findAll();
        for (Region region : list) {
            List<Integer> temp = new LinkedList<>();
            int cityId = region.getId();
            temp.add(cityId);
            // dfs算法
            dfs(cityId, temp);
        }
        for (List<Integer> route : routeList) {
            RouteInfo routeInfo = new RouteInfo();
            routeInfo.setStartStation(route.get(0));
            routeInfo.setEndStation(route.get(route.size() - 1));
            String passStation = "";
            for (int i = 1; i < route.size() - 1; i++) {
                passStation += (i==1 ? "" : ",");
                passStation += route.get(i);
            }
            routeInfo.setPassStation(passStation);
            routeInfo.setDistance((route.size() - 1) * 100);
            routeInfo.setFetchTime(route.size() - 1);
            routeInfoDao.save(routeInfo);
        }

    }

    /**
     * @return void
     * @description 深度递归，计算距离
     * @params [id, temp]
     * @creator chenshuai
     * @date 2019/3/14 0014
     */
    public void dfs(int id, List<Integer> temp) {
        // TODO 这里再研究
        CityExpand cityExpand = cityExpandDao.findByCityId(id);
        if (cityExpand==null) {
            return;
        }
        String[] rangeCity = cityExpand.getRangeCity().split(",");
        for (String string : rangeCity) {
            Integer rangeCityId = Integer.valueOf(string);
            if (temp.contains(rangeCityId)) {
                continue;
            }
            temp.add(rangeCityId);
            dfs(rangeCityId, temp);
            routeList.add(new LinkedList<>(temp));
            temp.remove(rangeCityId);
        }
        return;
    }

    /**
     * @return java.util.List<com.ansel.bean.RouteInfo>
     * @description 得到所有的线路信息
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<RouteInfo> findAllRouteInfos() {
        return routeInfoDao.findAll();
    }

}
