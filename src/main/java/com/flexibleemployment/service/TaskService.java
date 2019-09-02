package com.flexibleemployment.service;

import com.flexibleemployment.dao.entity.Task;
import com.flexibleemployment.dao.entity.TaskAttachment;
import com.flexibleemployment.dao.mapper.TaskMapperExt;
import com.flexibleemployment.utils.BizException;
import com.flexibleemployment.utils.ConvertUtils;
import com.flexibleemployment.utils.SnowflakeIdWorker;
import com.flexibleemployment.utils.file.ExcelUtils;
import com.flexibleemployment.vo.request.*;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ResultVO;
import com.flexibleemployment.vo.response.TaskNameRespVO;
import com.flexibleemployment.vo.response.TaskRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TaskService extends BaseService<Long, Task, TaskMapperExt> {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private OrderService orderService;

    @Autowired
    TaskAttachmentService taskAttachmentService;

    /**
     * 列表查询-分页
     *
     * @return
     */
    public ResultVO<PageResponseVO<TaskRespVO>> queryListPage(TaskPageReqVO reqVO) {
        PageResponseVO<TaskRespVO> page = selectPage(reqVO, mapper::selectCount, mapper::selectList);
        if (page.getTotalCount() > 0) {
            for (TaskRespVO item : page.getItems()) {
                if (new Date().after(item.getDeliverTime())) {
                    Task task = new Task();
                    task.setTaskId(item.getTaskId());
                    task.setStatus((byte) 4);
                    item.setStatus((byte) 4);
                }
            }
            return ResultVO.success(page);
        }
        return ResultVO.success(new PageResponseVO<TaskRespVO>());
    }

    /**
     * 前端任务详情查询-任务ID
     *
     * @return
     */
    @Transactional
    public ResultVO<TaskRespVO> queryByTaskId(TaskAppReqVO reqVO) {
        TaskRespVO respVO = mapper.selectByTaskId(reqVO);
        if (respVO != null) {
            if (new Date().after(respVO.getDeliverTime())) {
                Task task = new Task();
                task.setTaskId(respVO.getTaskId());
                task.setStatus((byte) 4);
                mapper.updateByPrimaryKeySelective(task);
                respVO.setStatus((byte) 4);
            }
            return ResultVO.success(respVO);
        }

        return ResultVO.success(new TaskRespVO());
    }

    /**
     * 前端任务列表查询-项目ID
     *
     * @return
     */
    @Transactional
    public ResultVO<List<TaskNameRespVO>> queryByProjectId(TaskAppReqVO reqVO) {
        List<TaskNameRespVO> respVO = mapper.selectByProjectId(reqVO);
        if (respVO.size() == 0) {
            return ResultVO.success(new ArrayList<TaskNameRespVO>());
        }
        return ResultVO.success(respVO);
    }

    /**
     * 新增
     *
     * @return
     */
    @Transactional
    public Integer add(TaskReqVO reqVO) {
        Long taskId = reqVO.getTaskId();

        Task task = ConvertUtils.convert(reqVO, Task.class);
        if (taskId == null || taskId == 0) {
            task.setTaskId(snowflakeIdWorker.nextId());
        }
        task.setCreatedAt(new Date());
        task.setUpdatedAt(new Date());

        String mobile = reqVO.getMobile();
        if (mobile != null && mobile.length() != 0) {
            task.setStatus((byte) 2);
            OrderReqVO orderReqVO = new OrderReqVO();
            orderReqVO.setTaskId(task.getTaskId());
            orderReqVO.setMobile(mobile);
            orderService.add(orderReqVO);
        }
        return mapper.insertSelective(task);
    }

    /**
     * 更新
     *
     * @return
     */
    @Transactional
    public ResultVO<Integer> update(@RequestBody TaskReqVO reqVO) {
        if (reqVO.getStatus() != 1) {
            return ResultVO.validError("task is been received, can not be updated");
        }
        Task task = ConvertUtils.convert(reqVO, Task.class);
        task.setUpdatedAt(new Date());
        Integer result = mapper.updateByPrimaryKeySelective(task);
        if (result == 0) {
            return ResultVO.validError("update is failed!");
        }
        return ResultVO.success(result);
    }

    /**
     * 删除
     *
     * @return
     */
    @Transactional
    public ResultVO<Integer> delete(@RequestBody TaskDeleteReqVO reqVO) {
        if (reqVO.getStatus() != 1) {
            return ResultVO.validError("task is been received, can not be deleted");
        }
        //根据taskId查询附件表，并删除所有关联附件（逻辑删除）
        List<TaskAttachment> list = taskAttachmentService.queryByTaskId(reqVO.getTaskId());
        list.forEach(taskAttachment -> taskAttachmentService.delete(taskAttachment.getAttachmentId()));
        Integer result = mapper.delete(reqVO.getTaskId());
        if (result == 0) {
            return ResultVO.validError("delete is failed!");
        }
        return ResultVO.success(result);
    }

    /**
     * 导入excel新增任务
     *
     * @return
     */
    @Transactional
    public ResultVO<String> addByImportExcel(Long projectId, MultipartFile file) throws IOException, ParseException {
        List<List<String>> tasks = ExcelUtils.readRows(file.getInputStream());
        TaskReqVO reqVO = new TaskReqVO();
        int count = 0;
        int emptyCount = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        for (int i = 0; i < tasks.size(); i++) {
            //判断是否空行，如为空行直接返回
            for (String task : tasks.get(i)
            ) {
                if (task == null || task.length() == 0) {
                    emptyCount++;
                }
            }
            if (emptyCount == tasks.get(i).size()) {
                return ResultVO.success("success");
            }
            //取到第i行的数据，逐个赋值给VO
            String taskName = tasks.get(i).get(0);
            String taskGoal = tasks.get(i).get(1);
            String amountString = tasks.get(i).get(4);
            String deliverTime = tasks.get(i).get(5);
            String settlementTime = tasks.get(i).get(6);
            //如必填项为空，返回错误信息
            if ((taskName == null || taskName.length() == 0) || (taskGoal == null || taskGoal.length() == 0) || (amountString == null || amountString.length() == 0) || (deliverTime == null || deliverTime.length() == 0) || (settlementTime == null || settlementTime.length() == 0)) {
                throw new BizException("第" + (i + 1) + "行数据有误！");
            }
            reqVO.setTaskName(taskName);
            reqVO.setTaskGoal(taskGoal);
            //非必填项
            String taskRequest = tasks.get(i).get(2);
            if (taskRequest != null && taskRequest.length() != 0) {
                reqVO.setTaskRequest(taskRequest);
            }
            String taskDesc = tasks.get(i).get(3);
            if (taskDesc != null && taskDesc.length() != 0) {
                reqVO.setTaskDesc(taskRequest);
            }
            BigDecimal amount = new BigDecimal(amountString);
            if (!amount.equals(BigDecimal.ZERO)) {
                reqVO.setAmount(amount);
            }
            reqVO.setDeliverTime(dateFormat.parse(deliverTime));
            reqVO.setSettlementTime(dateFormat.parse(settlementTime));
            String mobile = tasks.get(i).get(7);
            if (mobile != null && mobile.length() != 0) {
                reqVO.setMobile(mobile);
            }
            reqVO.setProjectId(projectId);
            //赋值好的VO插入数据库
            Integer result = add(reqVO);
            if (result != 1) {
                throw new BizException("第" + (i + 1) + "行插入失败！");
            }
            count++;
        }
        return ResultVO.success("success");
    }

}
