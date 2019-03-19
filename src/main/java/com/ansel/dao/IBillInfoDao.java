package com.ansel.dao;

import com.ansel.bean.BillInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author chenshuai
 */
public interface IBillInfoDao extends JpaRepository<BillInfo, Long> {
    /**
     * @return com.ansel.bean.BillInfo
     * @description 查看某个票据详情
     * @params [billCode]
     * @creator chenshuai
     * @date 2019/3/19 0019
     */
    public BillInfo findByBillCode(String billCode);

    /**
     * @return
     * @description 1.先查询运单发表表中的表单号，2.所有货运单号，除去已经发布的单号
     * @params pageable 分页条件
     * @creator chenshuai
     * @date 2019/3/19 0019
     */
    @Query(value = "select * from billinfo where bill_state = '已填' and bill_type = '货运单' and bill_code not in (select b.bill_code from billrelease b)", nativeQuery = true)
    public Page<BillInfo> findNotRelease(Pageable pageable);


}
