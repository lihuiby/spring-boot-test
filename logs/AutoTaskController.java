package com.yonyoucloud.fi.arap.controller;

import com.alibaba.fastjson.JSONObject;
import com.yonyoucloud.fi.arap.account.AccountContext;
import com.yonyoucloud.fi.arap.account.AccountMatterType;
import com.yonyoucloud.fi.arap.account.CheckResultVO;
import com.yonyoucloud.fi.arap.account.CheckStatus;
import com.yonyoucloud.fi.arap.service.account.IArapSettleAccountService;
import com.yonyoucloud.uretail.core.AppContext;
import com.yonyoucloud.uretail.dto.ResponseDto;
import com.yonyoucloud.uretail.dto.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @Description: 定时任务调度接口
 * @author: li
 * @Date: Created in 2019/10/09
 */
@RestController
public class AutoTaskController {

	private static final Logger log = LoggerFactory.getLogger(AutoTaskController.class);

	@Autowired
	private IArapSettleAccountService settleAccountService;

	/**
	 * 定时任务
	 * 包含 应收结账检查, 应收结账
	 * 		应付结账检查, 应付结账
	 * 异步执行
	 */
	@RequestMapping("/fiarap/task/settle")
	public JSONObject settleTaskPay(@RequestBody JSONObject params) {
		JSONObject resp = new JSONObject();
		try {
			CompletableFuture.supplyAsync(() -> {
				return settleAccountService.settleAutoTask(params);
			}).thenAcceptAsync((result) -> {
				log.info("Async settleTaskPay Result = {}", result);
			}).exceptionally((e) -> {
				log.error("Async settleTaskPay Error.", e);
				return null;
			});
			resp.put("asynchronized", Boolean.TRUE);
		}catch (Exception e) {
			log.error("settleTaskPay Error. ", e);
			resp.put("errInfo", e.getMessage());
		}
		return resp;
	}


	/**
	 * 定时任务 TODO
	 * 应付的 结账检查, 和结账, 异步执行
	 */
	@RequestMapping("/fiarap/task/settle/receive")
	public ResponseDto settleCheckTaskReceive(AccountContext accountContext) {

		accountContext.setAccountMatterType(AccountMatterType.RECEIVE);
		//结账检查 和 结账
		return autoCommonAccount(accountContext);
	}

	/**
	 * 结账检查和结账
	 * TODO 异常情况是否发送邮件或短信通知
	 * @param accountContext
	 */
	private ResponseDto autoCommonAccount(AccountContext accountContext){
		log.info("tenantId = {}, paramTenantId = {}", AppContext.getTenantId(), accountContext.getTenantid());
		/*if(StringUtils.isEmpty(accountContext.getTenantid())) {
		}*/
		accountContext.setTenantid(String.valueOf(AppContext.getTenantId()));

		CheckResultVO checkResultVO = new CheckResultVO();
		CheckStatus checkStatus;
		try {
			checkStatus = settleAccountService.settleCheck(accountContext, checkResultVO);
		} catch (Exception e) {
			log.error("settleCheck error. accountContext={}. ", accountContext, e);
			return new ResponseDto(ResponseStatus.Failed, e.getMessage());
		}

		//结账检查失败
		if(checkStatus.equals(CheckStatus.error)) {
			return new ResponseDto(ResponseStatus.Failed, checkResultVO.getItemResultList());
		}

		//结账
		try {
			Map<String, Object> resultMap = settleAccountService.settle(accountContext);
			log.info("auto settle. resultMap={}", resultMap);
			return new ResponseDto(ResponseStatus.Ok, resultMap);
		} catch (Exception e) {
			log.error("settle error. accountContext={} checkStatus={}. ", accountContext, checkStatus, e);
			return new ResponseDto(ResponseStatus.Failed, e.getMessage());
		}
	}
}
