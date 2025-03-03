//package com.artbite008.common.utils;
//
//import com.artbite008.starter.chaincontext.ChainContextConstants;
//import com.artbite008.starter.chaincontext.ChainRequestContext;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Objects;
//
//import static com.artbite008.starter.chaincontext.ChainContextConstants.MERCHANT_ID;
//
///**
// * @Author: ce.liu
// * @Date: 2022/8/21 21:35
// */
//@Slf4j
//public class MerchantContextUtils {
//
//	/**
//	 * 获取用户id
//	 */
//	public static String getMerchantId() {
//		String merchantId = ChainRequestContext.getCurrentContext().getString(MERCHANT_ID);
//		log.info("MERCHANT-ID:{}", merchantId);
//		if (StringUtils.isNotBlank(merchantId)) {
//			return merchantId;
//		}
//		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//		if (Objects.nonNull(requestAttributes)) {
//			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//			merchantId = request.getHeader(MERCHANT_ID);
//			if (Objects.isNull(merchantId)) {
//				return null;
//			}
//			log.info("MERCHANT-ID:{}", merchantId);
//			ChainRequestContext.getCurrentContext().put(MERCHANT_ID, merchantId);
//			return merchantId;
//		}
//		return null;
//	}
//}
