package com.sumscope.qpwb.ncd.web.controller;

import com.sumscope.qpwb.ncd.data.model.webserver.WebBondResponse;
import com.sumscope.qpwb.ncd.domain.*;
import com.sumscope.qpwb.ncd.service.service.NcdServiceI;
import com.sumscope.qpwb.ncd.web.condition.*;
import com.sumscope.service.webbond.common.web.response.ResponseData;
import com.sumscope.service.webbond.common.web.response.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpResponse;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Created by liu.yang on 2018/1/8.
 */
@Validated
@RestController
@RequestMapping(value = "${server.servlet.path.prefix}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class NcdController {

    @Autowired
    @Qualifier("ncdServer")
    private NcdServiceI ncdService;


    @ApiOperation(value = "添加或取消关注", notes = "添加或取消关注")
    @RequestMapping(value = "/attention", method = RequestMethod.POST)
    public ResponseData<AttentionDTO> attention(
            @ApiParam(value = "添加或取消关注的请求：消息体", required = true)
            @Valid @RequestBody AttentionCondition requestModel) {
        return ResponseUtil.ok(ncdService.setAttention(requestModel));
    }

    @ApiOperation(value = "获取用户关注列表", notes = "获取用户关注列表")
    @RequestMapping(value = "/attention", method = RequestMethod.GET)
    public ResponseData<AttentionDTO> getAttention(
            @ApiParam(value = "用户id")
            @NotBlank(message = "用户 id 不能为空")
            @RequestParam(name = "userId") String userId) {
        return ResponseUtil.ok(ncdService.getAttention(userId));
    }

    @ApiOperation(value = "获取matrix数据", notes = "获取matrix数据")
    @RequestMapping(value = "/quoteMatrix", method = RequestMethod.POST)
    public ResponseData<MatrixDTO> quoteMatrix(
            @ApiParam(value = "查询条件", required = true)
            @RequestBody QuoteMatrixCondition condition) {
        return ResponseUtil.ok(ncdService.getQuoteMatrix(condition));
    }

    @ApiOperation(value = "预定", notes = "预定")
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseData<NullDTO> order(
            @ApiParam(value = "用户预定请求体", required = true)
            @Valid @RequestBody OrderCondition orderCondition) {
        return ResponseUtil.ok(ncdService.order(orderCondition));
    }

    @ApiOperation(value = "获取报价历史时间", notes = "获取报价历史时间")
    @RequestMapping(value = "/timeDetail", method = RequestMethod.GET)
    public ResponseData<List<QuoteDetailDTO>> timeDetail(
            @ApiParam(value = "明细id", required = true)
            @NotBlank(message = "明细id 不能为空")
            @RequestParam(name = "detailId", required = false) String detailId,
            @ApiParam(value = "用户id", required = true)
            @NotBlank(message = "用户id 不能为空")
            @RequestParam(name = "userId", required = false) String userId) {
        return ResponseUtil.ok(ncdService.getTimeDetail(detailId, userId));
    }

    @ApiOperation(value = "获取银行报价详情", notes = "获取银行报价详情")
    @RequestMapping(value = "/quoteDetail", method = RequestMethod.GET)
    public ResponseData<List<IssuerDetailDTO>> quoteDetail(
            @ApiParam(value = "用户ID", required = true)
            @NotBlank(message = "用户 id 不能为空")
            @RequestParam(name = "userId") String userId,
            @ApiParam(value = "机构id", required = true)
            @NotBlank(message = "机构 id 不能为空")
            @RequestParam(name = "institutionCode") String institutionCode,
            @ApiParam(value = "评级", required = true)
            @NotBlank(message = "评级 不能为空")
            @RequestParam(name = "rate") String rate,
            @ApiParam(value = "发行日期", required = true)
            @NotBlank(message = "发行日期不能为空")
            @RequestParam(name = "quoteDate") String quoteDate) {
        return ResponseUtil.ok(ncdService.getQuoteDetail(userId, institutionCode, rate, quoteDate));
    }

    @ApiOperation(value = "获取broker查看的页面", notes = "获取broker查看的页面")
    @RequestMapping(value = "/reserve", method = RequestMethod.GET)
    public ResponseData<List<ReserveDTO>> reserve(
            @ApiParam(value = "用户id", required = true)
            @NotBlank(message = "用户 id 不能为空")
            @RequestParam(name = "userId", required = false) String userId) {
        return ResponseUtil.ok(ncdService.getReserve(userId));
    }

    @ApiOperation(value = "获取用户筛选条件", notes = "获取用户筛选条件")
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public ResponseData<FilterDTO> filter(
            @ApiParam(value = "用户ID", required = true)
            @NotBlank(message = "用户 id 不能为空")
            @RequestParam(name = "userId", required = false) String userId) {
        return ResponseUtil.ok(ncdService.filter(userId));
    }

    @ApiOperation(value = "保存用户筛选条件", notes = "保存用户筛选条件")
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseData<NullDTO> saveFilter(
            @ApiParam(value = "添加或取消关注的请求：消息体", required = true)
            @Valid @RequestBody FilterCondition filterCondition) {
        return ResponseUtil.ok(ncdService.saveFilter(filterCondition));
    }

    @ApiOperation(value = "获取页面基本信息", notes = "获取页面基本信息")
    @RequestMapping(value = "/pageBasic", method = RequestMethod.GET)
    public ResponseData<PageBasicDTO> pageBasic(
            @ApiParam(value = "用户ID", required = true)
            @NotBlank(message = "用户 id 不能为空")
            @RequestParam(name = "userId", required = false) String userId) {
        return ResponseUtil.ok(ncdService.getPageBasic(userId));
    }

    @ApiOperation(value = "获取经济商列表", notes = "获取经济商列表")
    @RequestMapping(value = "/brokers", method = RequestMethod.GET)
    public ResponseData<Collection<String>> brokers(
            @ApiParam(value = "用户ID", required = true)
            @NotBlank(message = "用户 id 不能为空")
            @RequestParam(name = "userId", required = false) String userId) {
        return ResponseUtil.ok(ncdService.getBrokers(userId));
    }


    @ApiOperation(value = "ncd订阅", notes = "ncd订阅")
    @RequestMapping(value = "/subscribeNcdQuoteByUserId", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<WebBondResponse> subscribeNcdQuoteByUserId(
            @ApiParam(value = "ncd订阅：消息体", required = true)
            @RequestBody NcdSubCondition requestBody) {
        return ResponseUtil.ok(ncdService.subscribeNcdFilter(requestBody));
    }

    @ApiOperation(value = "ncd取消订阅", notes = "ncd取消订阅")
    @RequestMapping(value = "/unSubscribeNcdQuoteByUserId", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<String> unSubscribeNcdQuoteByUserId(
            @ApiParam(value = "ncd取消订阅：消息体", required = true)
            @RequestBody NcdSubCondition requestBody) {
        ncdService.unSubscribeNcdFilter(requestBody);
        return ResponseUtil.ok("success");
    }
}
