package com.wj.spc.demo_1203.commons.util;

import com.wj.spc.demo_1203.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
public class TokenUtils {

    /**
     * 访问者类型_未知
     */
    private final String AUDIENCE_UNKNOWN = "unknown";
    /**
     * 访问者类型_浏览器
     */
    private final String AUDIENCE_WEB = "web";
    /**
     * 访问者类型_手机
     */
    private final String AUDIENCE_MOBILE = "mobile";
    /**
     * 访问者类型_平板电脑
     */
    private final String AUDIENCE_TABLET = "tablet";


    /**
     * 加密密码
     */
    @Value("${itslaw.token.secret}")
    private String secret;

    /**
     * 过期时间
     */
    @Value("${itslaw.token.expiration}")
    private Long expiration;


    /**
     * 从token中获取用户id(uId)
     *
     * @param token token
     * @return 用户id
     */
    public Integer getIDFromToken(String token) {
        Integer id;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            id = (Integer) claims.get("uId");
        } catch (Exception e) {
            id = null;
        }
        return id;
    }

    /**
     * 从token中获取用户名(sub)
     *
     * @param token token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从token中获取roleId
     *
     * @param token token
     * @return roleId
     */
    public Integer getRoleIdFromToken(String token) {
        Integer roleId;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            roleId = (Integer) claims.get("roleId");
        } catch (Exception e) {
            roleId = null;
        }
        return roleId;
    }

    /**
     * 从token中获取创建日期(created)
     *
     * @param token token
     * @return 创建日期
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            created = new Date((Long) claims.get("created"));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * 从token中获取过期时间(exp)
     *
     * @param token token
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        Date exp = null;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            exp = claims.getExpiration();
        } catch (Exception e) {
            exp = null;
        }
        return exp;
    }

    /**
     * 从token中获取访问者类型(audience)
     *
     * @param token token
     * @return 访问者类型
     */
    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            audience = (String) claims.get("audience");
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    /**
     * 从token中获取Claims
     *
     * @param token token
     * @return Claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 取得当前日期
     *
     * @return 当前日期
     */
    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 取得过期日期
     *
     * @return 过期日期
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.expiration * 1000);
    }

    /**
     * 判断token是否过期
     *
     * @param token token
     * @return Boolean
     */
    private Boolean isTokenExpired(String token) {
        final Date exp = this.getExpirationDateFromToken(token);
        return exp.before(this.generateCurrentDate());
    }

    /**
     * 判断token是否在密码变更后重新生成
     *
     * @param created           token创建日期
     * @param lastPasswordReset 密码变更日期
     * @return Boolean
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * 返回访问者类型
     *
     * @param device Device
     * @return 访问者类型
     */
    private String generateAudience(Device device) {
        String audience = this.AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = this.AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    /**
     * 判断是否忽略过期时间
     *
     * @param token token
     * @return Boolean
     */
    private Boolean ignoreTokenExpiration(String token) {
        String audience = this.getAudienceFromToken(token);
        return (this.AUDIENCE_TABLET.equals(audience) || this.AUDIENCE_MOBILE.equals(audience));
    }

    /**
     * 生成token
     *
     * @param userDetails ItslawUser
     * @param device      Device
     * @return token
     */
    public String generateToken(User userDetails, Device device) {
        Map<String, Object> claims = new HashMap<String, Object>();

        claims.put("sub", userDetails.getUsername());
        claims.put("audience", this.generateAudience(device));
        claims.put("created", this.generateCurrentDate());

        //public
        claims.put("uId", userDetails.getId());
        //claims.put("roleId", userDetails.getRoleId());

        return this.generateToken(claims);
    }

    /**
     * 生成token
     *
     * @param claims Map
     * @return token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(this.generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    /**
     * 判断是否可以刷新token
     *
     * @param token             token
     * @param lastPasswordReset 密码变更日期
     * @return Boolean
     */
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = this.getCreatedDateFromToken(token);
        return (!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
                && (!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token)));
    }

    /**
     * 刷新token
     *
     * @param token 旧token
     * @return 新token
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            claims.put("created", this.generateCurrentDate());
            refreshedToken = this.generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证token
     *
     * @param token       token
     * @param userDetails UserDetails
     * @return Boolean
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = this.getUsernameFromToken(token);
        return (username.equals(user.getUsername())
                && !(this.isTokenExpired(token)));
    }

}
