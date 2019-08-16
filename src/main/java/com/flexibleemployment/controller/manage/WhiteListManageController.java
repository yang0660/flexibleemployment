package com.flexibleemployment.controller.manage;

import com.flexibleemployment.dao.entity.WhiteList;
import com.flexibleemployment.service.WhiteListService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.utils.ConvertUtils;
import com.flexibleemployment.utils.SnowflakeIdWorker;
import com.flexibleemployment.vo.request.WhiteListDeleteReqVO;
import com.flexibleemployment.vo.request.WhiteListPageReqVO;
import com.flexibleemployment.vo.request.WhiteListReqVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ResultVO;
import com.flexibleemployment.vo.response.WhiteListRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Slf4j
@RequestMapping("/manage/whitelist")
@Api(tags = "会员管理", description = "/manage/whitelist")
@AuthIgnore
public class WhiteListManageController {

    @Autowired
    WhiteListService whiteListService;

    /**
     * 列表查询-分页
     *
     * @param
     * @return
     */
    @PostMapping(value = "/queryListPage")
    @ApiOperation("列表查询-分页")
    public ResultVO<PageResponseVO<WhiteListRespVO>> queryListPage(@RequestBody WhiteListPageReqVO reqVO) {
        PageResponseVO<WhiteListRespVO> page = whiteListService.queryPageList(reqVO);

        if (page != null && !CollectionUtils.isEmpty(page.getItems())) {
            return ResultVO.success(page);
        }
        return ResultVO.success(new PageResponseVO<WhiteListRespVO>());
    }


    /**
     * 新增
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("新增")
    public ResultVO<Integer> add(@RequestBody WhiteListReqVO reqVO) {
        synchronized (this) {

            WhiteList whiteList = ConvertUtils.convert(reqVO, WhiteList.class);
            Date now = new Date();
            whiteList.setCreatedAt(now);
            whiteList.setUpdatedAt(now);
            Integer result = whiteListService.insertSelective(whiteList);
            if (result == 0) {
                return ResultVO.validError("save is failed!");
            }
            return ResultVO.success(result);
        }
    }

    /**
     * 修改
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/update")
    @ApiOperation("修改")
    public ResultVO<Integer> update(@RequestBody WhiteListReqVO reqVO) {

        WhiteList whiteList = ConvertUtils.convert(reqVO, WhiteList.class);
        Date now = new Date();
        whiteList.setUpdatedAt(now);
        Integer result = whiteListService.updateByPrimaryKeySelective(whiteList);
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
    @PostMapping(value = "/delete")
    @ApiOperation("删除")
    public ResultVO<Integer> delete(@RequestBody WhiteListDeleteReqVO reqVO) {
        Integer result = whiteListService.deleteByPrimaryKey(reqVO.getMobile());
        if (result == 0) {
            return ResultVO.validError("delete is failed!");
        }
        return ResultVO.success(result);
    }

}
