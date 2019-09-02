package com.flexibleemployment.service;

import com.flexibleemployment.dao.entity.WhiteList;
import com.flexibleemployment.dao.mapper.WhiteListMapperExt;
import com.flexibleemployment.utils.BizException;
import com.flexibleemployment.utils.ConvertUtils;
import com.flexibleemployment.utils.file.ExcelUtils;
import com.flexibleemployment.vo.request.WhiteListDeleteReqVO;
import com.flexibleemployment.vo.request.WhiteListPageReqVO;
import com.flexibleemployment.vo.request.WhiteListReqVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ResultVO;
import com.flexibleemployment.vo.response.WhiteListRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WhiteListService extends BaseService<String, WhiteList, WhiteListMapperExt>{

    /**
     * 列表查询-分页
     *
     * @param
     * @return
     */
    public ResultVO<PageResponseVO<WhiteListRespVO>> queryListPage(WhiteListPageReqVO reqVO) {
        PageResponseVO<WhiteList> page = selectPage(reqVO, mapper::selectCount, mapper::selectList);
        PageResponseVO<WhiteListRespVO> result = ConvertUtils.convertPage(page, WhiteListRespVO.class);
        if (result != null && !CollectionUtils.isEmpty(result.getItems())) {
            return ResultVO.success(result);
        }
        return ResultVO.success(new PageResponseVO<WhiteListRespVO>());
    }


    /**
     * 新增
     *
     * @param reqVO
     * @return
     */
    @Transactional
    public Integer add(WhiteListReqVO reqVO) {
        WhiteList whiteList = ConvertUtils.convert(reqVO, WhiteList.class);
        whiteList.setCreatedAt(new Date());
        whiteList.setUpdatedAt(new Date());
        return mapper.insertSelective(whiteList);

    }

    /**
     * 详情查询
     *
     * @param mobile
     * @return
     */
    @Transactional
    public WhiteList queryByPrimaryKey(String mobile) {
        return mapper.selectByPrimaryKey(mobile);
    }

    /**
     * 修改
     *
     * @param reqVO
     * @return
     */
    @Transactional
    public ResultVO<Integer> update(WhiteListReqVO reqVO) {
        WhiteList whiteList = ConvertUtils.convert(reqVO, WhiteList.class);
        whiteList.setUpdatedAt(new Date());
        Integer result = mapper.updateByPrimaryKeySelective(whiteList);
        if (result == 0) {
            return ResultVO.validError("update is failed!");
        }
        return ResultVO.success(result);

    }

    /**
     * 删除
     *
     * @param reqVO
     * @return
     */
    @Transactional
    public ResultVO<Integer> delete(WhiteListDeleteReqVO reqVO) {
        Integer result = mapper.deleteByPrimaryKey(reqVO.getMobile());
        if (result == 0) {
            return ResultVO.validError("delete is failed!");
        }
        return ResultVO.success(result);
    }

    /**
     * 导入excel新增白名单
     *
     * @param file
     * @return
     */
    @Transactional
    public ResultVO<String> addByImportExcel(MultipartFile file) throws IOException {
        List<List<String>> whitelists = ExcelUtils.readRows(file.getInputStream());
        WhiteListReqVO reqVO = new WhiteListReqVO();
        int count = 0;
        int emptyCount = 0;
        for (int i=0;i<whitelists.size();i++){
            //判断是否空行，如为空行直接返回
            for (String whitelist:whitelists.get(i)
            ) {
                if (whitelist == null || whitelist.length() == 0) {
                    emptyCount++;
                }
            }
            if (emptyCount == whitelists.get(i).size()) {
                return ResultVO.success("success");
            }
            //取到第i行的数据，逐个赋值给VO
            String mobile = whitelists.get(i).get(0);
            String userName = whitelists.get(i).get(1);
            //如必填项为空，返回错误信息
            if ((mobile == null || mobile.length() == 0) ||  (userName == null || userName.length() == 0)){
                throw new BizException("第"+(i+1)+"行数据有误！");
            }
            reqVO.setMobile(mobile);
            reqVO.setUserName(userName);
            //非必填项
            String address = whitelists.get(i).get(2);
            if (address != null && address.length() != 0) {
                reqVO.setAddress(address);
            }
            //赋值好的VO插入数据库
            Integer result = add(reqVO);
            if (result!=1) {
                throw new BizException("第"+(i+1)+"行插入失败！");
            }
            count ++;
        }
        return ResultVO.success("success");
    }

}
