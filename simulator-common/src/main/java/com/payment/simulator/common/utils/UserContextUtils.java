//package com.artbite008.common.utils;
//
//
//import java.util.Objects;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.artbite008.starter.chaincontext.ChainContextConstants;
//import com.artbite008.starter.chaincontext.ChainRequestContext;
//
//import lombok.extern.slf4j.Slf4j;
//
//import static com.artbite008.starter.chaincontext.ChainContextConstants.USER_ID;
//
//@Slf4j
//public class UserContextUtils {
//
//    /**
//     * admin用户id
//     */
//    public static final String ADMIN_OPERATOR_ID = "operator";
//
//    /**
//     * 获取用户id
//     */
//    public static String getUserId() {
//        String userId = ChainRequestContext.getCurrentContext().getString(USER_ID);
//        log.info("X-USER-ID:{}", userId);
//        if (StringUtils.isNotBlank(userId)) {
//            return userId;
//        }
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (Objects.nonNull(requestAttributes)) {
//            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//            userId = request.getHeader(USER_ID);
//            if (Objects.isNull(userId)) {
//                return null;
//            }
//            log.info("X-USER-ID:{}", userId);
//            ChainRequestContext.getCurrentContext().put(USER_ID, userId);
//            return userId;
//        }
//        return null;
//    }
//
//    /**
//     * 获取admin端用户id
//     */
//    public static String getAdminUserId() {
//        String userId = ChainRequestContext.getCurrentContext().getString(ADMIN_OPERATOR_ID);
//        log.info("ADMIN_OPERATOR_ID:{}", userId);
//        if (StringUtils.isNotBlank(userId)) {
//            return userId;
//        }
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (Objects.nonNull(requestAttributes)) {
//            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//            userId = request.getHeader(ADMIN_OPERATOR_ID);
//            if (Objects.isNull(userId)) {
//                return null;
//            }
//            log.info("ADMIN_OPERATOR_ID:{}", userId);
//            ChainRequestContext.getCurrentContext().put(ADMIN_OPERATOR_ID, userId);
//            return userId;
//        }
//        return null;
//    }
//}
