package com.jeeplus.abapp.web;

import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.abapp.service.AbCardBagService;
import com.jeeplus.abapp.utils.login.CheckUser;
import com.jeeplus.abapp.vo.CardBagParam;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.web.BaseController;

/**
 * 卡包管理Controller
 * @author liuwei
 * @version 2018-01-23
 */
@Controller
@RequestMapping(value = "${abpaiPath}")
public class AbCardBagController extends BaseController {
	
	@Autowired
	private AbCardBagService abCardBagService;

	/**
	 * 添加卡包
	 * @param cardBagParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "cardbag/add", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson addCardBag(
			@RequestBody CardBagParam cardBagParam,
			HttpServletRequest request
			) {
		logger.info(request.getRequestURI() + ", 卡包添加。。。。。");
		AjaxJson ajaxJson = new AjaxJson();
		String userId = CheckUser.getUserIdFromToken(request);
		String bankcard = cardBagParam.getBankcard();
		String userName = cardBagParam.getUserName();
		String mobile = cardBagParam.getMobile();
		try {
			String message = abCardBagService.insertCardBag(userId, bankcard, userName, mobile);
			if (!"success".equals(message)) {
				ajaxJson.setMsg(message);
				ajaxJson.setSuccess(false);
				ajaxJson.setResponseCode("403");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("系统异常");
			ajaxJson.setResponseCode("500");
		}
		return ajaxJson;
	}
	
	/**
	 * 分润结算卡添加
	 * @param cardBagParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "earnbankcard/add", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson addEarnbankcard(
			@RequestBody CardBagParam cardBagParam,
			HttpServletRequest request
			) {
		logger.info(request.getRequestURI() + ", 分润结算款添加。。。。。");
		AjaxJson ajaxJson = new AjaxJson();
		String userId = CheckUser.getUserIdFromToken(request);
		String bankcard = cardBagParam.getBankcard();
		String userName = cardBagParam.getUserName();
		String mobile = cardBagParam.getMobile();
		try {
			String message = abCardBagService.addEarnbankcard(userId, bankcard, userName, mobile);
			if (!"success".equals(message)) {
				ajaxJson.setMsg(message);
				ajaxJson.setSuccess(false);
				ajaxJson.setResponseCode("403");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("系统异常");
			ajaxJson.setResponseCode("500");
		}
		return ajaxJson;
	}
	
	/**
	 * 获取我的卡包消息
	 * @param cardBelong
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "cardbag/query", method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson getMyCardBagInfo(
			@RequestParam (value = "cardType", required = true) String cardType,
			HttpServletRequest request
			) {
		logger.info(request.getRequestURI() + ", 获取我的卡包消息。。。。。。。。。");
		AjaxJson ajaxJson = new AjaxJson();
		String userId = CheckUser.getUserIdFromToken(request);
		try {
			LinkedHashMap<String, Object> respMap = abCardBagService.getMyCardBag(userId, cardType);
			ajaxJson.setBody(respMap);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("系统异常");
			ajaxJson.setResponseCode("500");
		}
		return ajaxJson;
	}
	
	/**
	 * 删除卡包（逻辑删除）
	 * @param cardBagParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "cardbag/remove", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson removeCardBag(
			@RequestBody CardBagParam cardBagParam,
			HttpServletRequest request
			) {
		logger.info(request.getRequestURI() + ", 逻辑删除卡包...。。。。。。");
		AjaxJson ajaxJson = new AjaxJson();
		String userId = CheckUser.getUserIdFromToken(request);
		String cardId = cardBagParam.getCardId();
		try {
			String message = abCardBagService.removeCardbag(userId, cardId);
			if (!"success".equals(message)) {
				ajaxJson.setMsg(message);
				ajaxJson.setSuccess(false);
				ajaxJson.setResponseCode("401");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setMsg("系统异常");
			ajaxJson.setResponseCode("500");
			ajaxJson.setSuccess(false);
		}
		return ajaxJson;
	}
	
	/**
	 * 判断银行卡是否是本人卡
	 * @param cardBagParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "card/cardIsSelf", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson bankcardIsSelf(
			@RequestBody CardBagParam cardBagParam,
			HttpServletRequest request
			) {
		logger.info(request.getRequestURI() + ", 判断银行卡是否是本人卡。。。。。。");
		AjaxJson ajaxJson = new AjaxJson();
		String userId = CheckUser.getUserIdFromToken(request);
		String bankcard = cardBagParam.getBankcard();
		try {
			String message = abCardBagService.checkcardIsSelf(userId, bankcard);
			if (null != message && !"success".equals(message)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg(message);
				ajaxJson.setResponseCode("403");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setMsg("系统异常");
			ajaxJson.setSuccess(false);
			ajaxJson.setResponseCode("500");
		}
		return ajaxJson;
	}
	
	/**
	 * 修改默认结算卡
	 * @param cardBagParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "cardbag/edit", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updCardbagDefset(
			@RequestBody CardBagParam cardBagParam,
			HttpServletRequest request
			) {
		logger.info(request.getRequestURI() + ", 设置默认结算卡.。。。。。。。");
		AjaxJson ajaxJson = new AjaxJson();
		String userId = CheckUser.getUserIdFromToken(request);
		String cardNo = cardBagParam.getBankcard();
		if (null == cardNo || cardNo.length() <= 13) {
			ajaxJson.setMsg("卡号错误");
			ajaxJson.setResponseCode("403");
			ajaxJson.setSuccess(false);
		} else {
			try {
				String message = abCardBagService.updCardbadDefSet(userId, cardNo);
				if (!"success".equals(message)) {
					ajaxJson.setMsg("设置默认结算卡失败");
					ajaxJson.setSuccess(false);
					ajaxJson.setResponseCode("403");
				}
			} catch (Exception e) {
				e.printStackTrace();
				ajaxJson.setMsg("系统错误");
				ajaxJson.setResponseCode("500");
				ajaxJson.setSuccess(false);
			}
		}
		return ajaxJson;
	}
	
	/**
	 * 获取分润结算卡信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "drawcard/cardinfo", method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson getDrawbankinfo(
			HttpServletRequest request
			) {
		logger.info(request.getRequestURI() + ", 获取分润结算卡信息。。。。。");
		AjaxJson ajaxJson = new AjaxJson();
		String userId = CheckUser.getUserIdFromToken(request);
		try {
			LinkedHashMap<String, Object> respMap = abCardBagService.getDrawbankinfo(userId);
			if (null != respMap) {
				ajaxJson.setBody(respMap);
			} else {
				ajaxJson.setMsg("无分润结算卡");
				ajaxJson.setResponseCode("403");
				ajaxJson.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setMsg("系统异常");
			ajaxJson.setResponseCode("500");
			ajaxJson.setSuccess(false);
		}
		return ajaxJson;
	}
	
	/**
	 * 获取用户的默认消费卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "tradecard/query", method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson getDefTradeCard(
			HttpServletRequest request
			) {
		logger.info(request.getRequestURI() + ", 获取用户的默认消费卡。。。。");
		AjaxJson ajaxJson = new AjaxJson();
		String userId = CheckUser.getUserIdFromToken(request);
		try {
			LinkedHashMap<String, Object> respMap = abCardBagService.getDeftradecard(userId);
			ajaxJson.setBody(respMap);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setMsg("系统异常");
			ajaxJson.setSuccess(false);
			ajaxJson.setResponseCode("500");
		}
		return ajaxJson;
	}
	
	/**
	 * 根据卡号查询卡信息
	 * @param cardno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "cardbag/querybyno", method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson addtradelimit(
			@RequestParam(value = "cardno", required = true) String cardno,
			HttpServletRequest request
			) {
		AjaxJson ajaxJson = new AjaxJson();
		String userId = CheckUser.getUserIdFromToken(request);
		try {
			LinkedHashMap<String, Object> respMap = abCardBagService.getCardinfoBycardno(userId, cardno);
			if (null != respMap) {
				ajaxJson.setBody(respMap);
			} else {
				ajaxJson.setMsg("暂无数据");
				ajaxJson.setResponseCode("401");
				ajaxJson.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setMsg("系统异常");
			ajaxJson.setResponseCode("500");
			ajaxJson.setSuccess(false);
		}
		return ajaxJson;
	}
	
	/**
	 * 修改卡包中卡的手机号
	 * @param cardBagParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "cardbag/updMobile", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson addtradelimit(
			@RequestBody CardBagParam cardBagParam,
			HttpServletRequest request
			) {
		AjaxJson ajaxJson = new AjaxJson();
		String userId = CheckUser.getUserIdFromToken(request);
		String cardId = cardBagParam.getCardId();
		String mobile = cardBagParam.getMobile();
		try {
			String message = abCardBagService.updCardbag(userId, cardId, mobile);
			if (!"success".equals(message)) {
				ajaxJson.setMsg(message);
				ajaxJson.setResponseCode("401");
				ajaxJson.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setMsg("系统异常");
			ajaxJson.setSuccess(false);
			ajaxJson.setResponseCode("500");
		}
		return ajaxJson;
	}

}