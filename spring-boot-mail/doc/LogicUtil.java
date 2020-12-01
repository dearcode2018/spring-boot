package com.cc.ovp.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.zip.Deflater;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.red5.io.ITag;
import org.red5.io.flv.impl.FLVReader;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.cc.ovp.OVPContext;
import com.cc.ovp.dao.Cata_db;
import com.cc.ovp.dao.Index_db;
import com.cc.ovp.dao.Video_pool_db;
import com.cc.ovp.domain.VideoPool.VideoPoolExt;
import com.cc.ovp.logic.BW;
import com.cc.ovp.logic.Cata_logic;
import com.cc.ovp.logic.EncodeTask_logic;
import com.cc.ovp.service.DfsService;
import com.cc.ovp.util.sms.Client;
import com.cc.ovp.web.video.UserJson;
import com.cc.ovp.web.video.VideoJson;
import com.cc.ovp.web.video.VideoXML;

import ayou.system.Command;
import ayou.util.DOC;
import ayou.util.EncryptUtil;
import ayou.util.FileUtil;
import ayou.util.IntUtil;
import ayou.util.ParamUtil;
import ayou.util.StringUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

import static com.cc.ovp.util.RedisUtils.getPool;

/**
 * TODO 类太大了，需要拆分
 */
public class LogicUtil {

    // private static TimeCacheMap<String,String> accessKeys = new TimeCacheMap<String,String>(300);
    private static TimeCacheMap<String, String> local_cache = new TimeCacheMap<String, String>(3600);
    private static TimeCacheMap<String, Integer> mobile_send_count_cache = new TimeCacheMap<String, Integer>(3600);
    private static final Logger logger = Logger.getLogger(LogicUtil.class);
    public static final String PPT_DIR = "/data/htmlfile/ppt/";
    private static MailSender mailSender = (MailSender) OVPContext.getBean("mailSender");
    private static VideoJson videoJson = (VideoJson) OVPContext.getBean("videoJson");
    private static UserJson userJson = (UserJson) OVPContext.getBean("userJson");
    private static VideoXML videoXML = (VideoXML) OVPContext.getBean("videoXML");
    private static String REDIS_BANNNED_IP_KEY = "redis_bannedIp";
    private static String REDIS_BANNNED_PHONE_KEY = "redis_bannedPhone";
    
    private static final List<String> VIDEO_JSON_URLS = Arrays.asList("http://player.polyv.net/videojson/%s.json",
            "http://player.polyv.net/videojson/%s.js");

    private static final String IMAGE_PATH = "%svideo_image/%s/%s_%s.jpg"; // 截图（小图）的路径，完整的例如
                                                                           // /data/htmlfile/video_image/e/e6b23c6f51/2/e6b23c6f51350f106556806a576b1942_0.jpg
    private static final String IMAGE_BIG_PATH = "%svideo_image/%s/%s_%s_b.jpg"; // 截图（大图）的路径，完整的例如
                                                                                 // /data/htmlfile/video_image/e/e6b23c6f51/2/e6b23c6f51350f106556806a576b1942_0_b.jpg
    private static final String CUT_IMAGE_COMMAND = "/bin/bash %sWEB-INF/sh/video_getimage.sh %s %s %s %s %sx%s"; // 截图的命令

    private static DfsService dfsService = (DfsService) OVPContext.getBean("dfsService");

    public static void putLocalCache(String key, String value) {
        local_cache.put(key, value);

    }

    public static void removeLocalCache(String key) {
        local_cache.remove(key);
    }

    public static String getLocalCache(String key) {
        return local_cache.get(key);
    }

    public static void sendMail(String title, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("service@polyv.net");
        msg.setTo("service@polyv.net");

        msg.setText(body);
        msg.setSubject(title);
        try {
            mailSender.send(msg);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static String getSourceDomainByGroupName(String groupname) {
        String seg1domain = "segment.polyv.net";
        String seg4domain = "ws4s.videocc.net";
        int groupNumber = Integer.parseInt(groupname.replaceAll("group", ""));
        //
        if (groupNumber == 4) {
            return seg4domain;
        }
        if (groupNumber < 8) {
            return seg1domain;
        }
        return "ws" + groupNumber + "s.videocc.net";
    }

    /*
     * public static String getSourceDomainByGroupName(String groupname){
     * String seg1domain = "segment.polyv.net";
     * String seg4domain = "ws4s.videocc.net";
     * 
     * //======
     * String seg8domain = "ws8s.videocc.net";
     * String seg9domain = "ws9s.videocc.net";
     * String seg10domain = "ws10s.videocc.net";
     * String seg11domain = "ws11s.videocc.net";
     * String seg12domain = "ws12s.videocc.net";
     * String seg13domain = "ws13s.videocc.net";
     * String seg14domain = "ws14s.videocc.net";
     * String seg15domain = "ws15s.videocc.net";
     * 
     * 
     * String domain = seg1domain;
     * if("group4".equals(groupname)){
     * domain = seg4domain;
     * }
     * if("group8".equals(groupname)){
     * domain = seg8domain;
     * }
     * if("group9".equals(groupname)){
     * domain = seg9domain;
     * }
     * if("group10".equals(groupname)){
     * domain = seg10domain;
     * }
     * if("group11".equals(groupname)){
     * domain = seg11domain;
     * }
     * if("group12".equals(groupname)){
     * domain = seg12domain;
     * }
     * if("group13".equals(groupname)){
     * domain = seg13domain;
     * }
     * if("group14".equals(groupname)){
     * domain = seg14domain;
     * }
     * if("group15".equals(groupname)){
     * domain = seg15domain;
     * }
     * 
     * return domain;
     * }
     */
    public static String vidFromVideoPoolId(String video_pool_id) {
        if (video_pool_id.indexOf("_") != -1) {
            return video_pool_id;
        }
        return video_pool_id + "_" + video_pool_id.charAt(0);
    }

    public static String videoPoolIdFromVid(String vid) {
        if (vid.indexOf("_") == -1) {
            return vid;
        }
        return vid.substring(0, vid.indexOf("_"));
    }

    public static void cleanAllVxml(String userid) {
        cleanUserRedisVideoObject(userid);
        String url = Config.get("delCacheFile2") + "?userid=" + userid;
        Util.getUrl(url);
    }
    
    /**
     * 清理 videoxml 和 videojson 的缓存
     */
    public static void cleanVXML(String video_pool_id) {
        reloadRedisVideoJson(video_pool_id);
        reloadRedisVideoXML(video_pool_id);
        String vid = vidFromVideoPoolId(video_pool_id);
        try {
            //Redis2Utils.del("videojson_" + vidFromVideoPoolId(video_pool_id));
            Redis2Utils.del("videoInfo_" + vidFromVideoPoolId(video_pool_id));
            
            // 调用新版点播接口，刷新 videojson 的 CDN 缓存
            List<String> urls = new ArrayList<String>();
            for (String url : VIDEO_JSON_URLS) {
                urls.add(String.format(url, vid));
            }
            cleanAliyunUrl(org.apache.commons.lang3.StringUtils.join(urls, ","), false);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        IPUtil.delVxml(vid);
    }

    /*
     * 不重载(删除)videojson，避免编码机代码旧版引起json版本不更新
     */
    public static void cleanVXMLAndDelVideoJson(String video_pool_id) {
        cleanRedisVideoJson(video_pool_id);
        cleandRedisVideoXML(video_pool_id);
        try {
            //Redis2Utils.del("videojson_" + vidFromVideoPoolId(video_pool_id));
            Redis2Utils.del("videoInfo_" + vidFromVideoPoolId(video_pool_id));
        } catch (Exception e) {
            logger.error("clenvxmlandvideojson error, vid="+video_pool_id, e);
        }
        // IPUtil.delCacheFile(LogicUtil.parseXmlPath(video_pool_id.substring(0,1), video_pool_id));
        IPUtil.delVxml(LogicUtil.vidFromVideoPoolId(video_pool_id));

    }

    public static void cleanUXML(String userid) {
        reloadRedisUserJson(userid);
        reloadRedisUserXML(userid);
        try {
            logger.info("delete userid key:" + "CcUserServiceImpl#getByUserId_" + userid);
            Redis2Utils.del("CcUserServiceImpl#getByUserId_" + userid);
            logger.info("clean user cdn cache. userid=" + userid);
            cleanAliyunUrl("http://static.polyv.net/uxml/" + userid + ".xml", false);
            cleanAliyunUrl("http://static.polyv.net/ux/" + userid, false);
        } catch (Exception e) {
            logger.error("cleanUXML error, userid="+userid, e);
        }
        IPUtil.delCacheFile(LogicUtil.parseUserXmlPath(userid));
    }

    private static void reloadRedisUserJson(String userid) {
        DOC udoc = userJson.userJson(userid);
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            String jsonkey = "userjson_" + userid;
            jedis.set(jsonkey, JsonUtil.docToJson(udoc));

        } catch (Exception e) {
            logger.error("reloadRedisUserJson error, userid="+userid, e);
        } finally {
            if (null != jedis) {
                getPool().returnResource(jedis);
            }
        }
    }

    public static void delRedisUserSecretkey(String userid) {
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            String secretkey = "secretkey_" + userid;
            jedis.del(secretkey);

        } catch (Exception e) {
            logger.error("delRedisUserSecretkey error, userid="+userid, e);
        } finally {
            if (null != jedis) {
                getPool().returnResource(jedis);
            }
        }
    }

    /**
     * 
     * @param ip
     * @param type ip/phone
     */
    public static void addBannedIP(String ip, String type) {

        logger.info("addBannedIP.." + ip);
        long score = System.currentTimeMillis();
        Jedis jedis = null;
        try {

            jedis = getPool().getResource();
            if (type.equals("ip")) {
                jedis.zadd(REDIS_BANNNED_IP_KEY, score, ip);
            } else {
                jedis.zadd(REDIS_BANNNED_PHONE_KEY, score, ip);

            }

        } catch (Exception e) {
            logger.error("addBannedIP.." + ip + " failed", e);
        } finally {
            if (null != jedis) {
                getPool().returnResource(jedis);
            }
        }
    }

    public static Iterator<Tuple> listBannedIP(String type) {
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            String key = REDIS_BANNNED_IP_KEY;
            if (!type.equals("ip")) {
                key = REDIS_BANNNED_PHONE_KEY;
            }
            Set<Tuple> ipset = jedis.zrangeWithScores(key, 0, -1);
            ;
            Iterator<Tuple> it = ipset.iterator();

            return it;
        } catch (Exception e) {
            logger.error("list..bannedIp" + " failed", e);
        } finally {
            if (null != jedis) {
                getPool().returnResource(jedis);
            }
        }
        return null;
    }

    public static void delBannedIp(String ip, String type) {
        logger.info("delBannedIp.." + ip);
        Jedis jedis = null;
        try {

            jedis = getPool().getResource();
            String key = REDIS_BANNNED_IP_KEY;
            if (!type.equals("ip")) {
                key = REDIS_BANNNED_PHONE_KEY;
            }
            jedis.zrem(key, ip);

        } catch (Exception e) {
            logger.error("delBannedIp.." + ip + " failed", e);
        } finally {
            if (null != jedis) {
                getPool().returnResource(jedis);
            }
        }
    }

    public static void cleanRedisVideoJson(String video_pool_id) {
        logger.info("cleanRedisVideoJson.." + video_pool_id);
        String vid = vidFromVideoPoolId(video_pool_id);
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            String jsonkey = "videojson_" + vid;
            jedis.del(jsonkey);

        } catch (Exception e) {
            logger.error("cleanRedisVideoJson.." + video_pool_id + " failed", e);
        } finally {
            if (null != jedis) {
                getPool().returnResource(jedis);
            }
        }
    }

    public static String reloadRedisVideoJson(String video_pool_id) {
        logger.info("reloadRedisVideoJson.." + video_pool_id);
        String vid = vidFromVideoPoolId(video_pool_id);
        DOC vdoc = videoJson.videoJson(vid);
        Jedis jedis = null;
        try {
            String jsonbody = JsonUtil.docToJson(vdoc);
            jedis = getPool().getResource();
            String jsonkey = "videojson_" + vid;
            String result = jedis.set(jsonkey, jsonbody);
            logger.info(String.format("reloadRedisVideoJson finish..vid=%s, docSize=%s, result=%s", vid, vdoc.size(),
                    result));
            return jsonbody;

        } catch (Exception e) {
            logger.error("reloadRedisVideoJson.." + video_pool_id + " failed", e);
        } finally {
            if (null != jedis) {
                getPool().returnResource(jedis);
            }
        }
        return "{}";
    }

    public static void reloadRedisUserXML(String userid) {
        String userxml = videoXML.uxmlForRedis(userid);
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            String str = "polyvk" + userid;
            String jsonkey = "videoxml_" + userid;
            userxml = AESCryptoUtils.encryptToBase64(userxml, str.getBytes());
            jedis.set(jsonkey, userxml);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                getPool().returnResource(jedis);
            }
        }
    }

    public static void cleandRedisVideoXML(String video_pool_id) {
        String vid = vidFromVideoPoolId(video_pool_id);
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            String jsonkey = "videoxml_" + vid;
            jedis.del(jsonkey);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                getPool().returnResource(jedis);
            }
        }
    }

    public static void reloadRedisVideoXML(String video_pool_id) {
        String vid = vidFromVideoPoolId(video_pool_id);
        String vxml = videoXML.vxmlForRedis(vid);
        Jedis jedis = null;
        try {
            String userid = vid.substring(0, 10);
            String str = "polyvk" + userid;
            jedis = getPool().getResource();
            String jsonkey = "videoxml_" + vid;
            vxml = AESCryptoUtils.encryptToBase64(vxml, str.getBytes());
            jedis.set(jsonkey, vxml);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                getPool().returnResource(jedis);
            }
        }
    }
    
    /**
     * 删除一个用户的全部 videojson, videoxml 缓存。耗时较长，会开新线程执行
     */
    private static void cleanUserRedisVideoObject(final String userid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Jedis jedis = null;
                try {
                    jedis = getPool().getResource();
                    String keyPattern = "videojson_" + userid + "*";
                    String xmlPattern = "videoxml_" + userid + "*";
        
                    Set<String> keys = RedisUtils.scan(keyPattern);
                    Set<String> xmlKeys = RedisUtils.scan(xmlPattern);
        
                    // TODO 如果要处理的key数量较多，这样处理会不会有问题？
                    Pipeline p = jedis.pipelined();
                    Iterator<String> keyIter = keys.iterator();
                    while (keyIter.hasNext()) {
                        String key = keyIter.next();
                        p.del(key);
                    }
                    Iterator<String> xmlKeyIter = xmlKeys.iterator();
                    while (xmlKeyIter.hasNext()) {
                        String key = xmlKeyIter.next();
                        p.del(key);
            
                    }
                    p.sync();
        
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    if (null != jedis) {
                        getPool().returnResource(jedis);
                    }
                }
        
            }
        }).start();
    }

    public static void putAccessKey(String hashkey) {
        // accessKeys.put(hashkey, hashkey);

        Redis2Utils.setString("accessHash_" + hashkey, hashkey, 300);

    }

    public static boolean isAccessKeyExists(String hashkey, boolean removeDelay) {
        String key = Redis2Utils.getString("accessHash_" + hashkey);
        boolean exists = hashkey.equals(key);
        // System.out.println("hashkey:"+hashkey + " exists:"+exists);
        if (exists) {
            if (removeDelay) {
                removeAccessKeyDelay(hashkey, 3);
            } else {
                removeAccessKey(hashkey);
            }
        }
        return exists;

    }

    public static int getMobileSentCount(String mobileip) {
        System.out.println(mobileip);
        String value = Redis2Utils.getString("send_" + mobileip);;
        int times = 0;

        try {
            times = Integer.parseInt(value);
        } catch (Exception e) {

        }
        return times;
    }

    public static void putMobile(String mobile, String mobileip, String code) {

        try {
            int exp = 180;
            Redis2Utils.setString("mobile_" + mobile, code, exp);

            // 设置同一个ip发送次数
            int counter = getMobileSentCount(mobileip);
            counter = counter + 1;

            Redis2Utils.setString("send_" + mobileip, counter + "", 3600 * 24);

        } catch (Exception e) {
            logger.error("set Mobile_ error", e);
        }

    }

    public static String getMobile(String mobile) {

        return Redis2Utils.getString("mobile_" + mobile);

    }

    public static void removeAccessKey(String hashkey) {
        Redis2Utils.del("accessHash_" + hashkey);
    }

    public static void removeAccessKeyDelay(final String hashkey, final int secs) {
        new Thread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(secs * 1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.getMessage(), e);
                }
                // accessKeys.remove(hashkey);
                removeAccessKey(hashkey);
            }
        }).start();

        // System.out.println("remove:"+hashkey);
    }

    public static String getPPTImageByIndex(String vid, int index, String hash) {
        String hostid = vid.substring(0, 1);
        String userid = vid.substring(0, 10);
        // String images_target_file = LogicUtil.PPT_DIR+"/"+hostid+"/"+userid+"/"+vid+"-"+index+".jpg";
        String images_target_file = "/doc/" + hash + "/" + hostid + "/" + userid + "/" + vid + "-" + index + ".jpg";
        return images_target_file;

    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    public static String getPPTThumbImageByIndex(String vid, int index, String hash) {
        String hostid = vid.substring(0, 1);
        String userid = vid.substring(0, 10);
        // String images_target_file = LogicUtil.PPT_DIR+"/"+hostid+"/"+userid+"/"+vid+"-"+index+".jpg";
        String images_target_file = "/doc/" + hash + "/" + hostid + "/" + userid + "/" + vid + "-" + index + "_t.jpg";
        return images_target_file;

    }

    public static DfsService getDfsService() {
        return (DfsService) OVPContext.getBean("dfsService");
    }

    public static String parseUrl(String type, String hostid, String url, String protocol) {
        if (!StringUtil.isFine(url) && !type.equals("srt")) {
            // return "/file/themes/default/images/video_blank.png";
            return "/img/videodefault.png";
        } else if (url.startsWith("http://")) {
            if ("https".equals(protocol)) {
                return url.replaceAll("http://", "https://");
            } else {
                return url;
            }

        } else {
            if (type.equals("video")) {
                return protocol + "://" + Config.get("hadoopdomain") + "/" + url;
            }
            if (type.equals("image")) {
                return protocol + "://" + Config.get("imagesdomain") + "/u" + type + "/" + hostid + "/" + url;
            }

            if (type.equals("srt")) {
                return protocol + "://" + Config.get("staticdomain") + "/u" + type + "/" + hostid + "/" + url;
            }
            return "/u" + type + "/" + hostid + "/" + url;
        }
    }

    // video_logic 得到访问路径
    public static String parseUrl(String type, String hostid, String url) {
        return parseUrl(type, hostid, url, "http");
    }

    // video_logic
    public static String getWatetPicUrl(String url) {
        if (StringUtil.isFine(url)) {
            return url.replaceAll("/html/", Config.get("htmlPath") + "html/");
        }
        return Config.get("htmlPath");
    }

    public static String parseUrlByUserid(String type, String userid, String url) {
        String hostid = "";
        if (StringUtil.isFine(userid)) {
            hostid = userid.substring(0, 1);
        }
        return parseUrl(type, hostid, url);
    }

    /**
     * 得到视频的接口xml路径
     * @param hostid
     * @param video_pool_id
     * @return
     */
    public static String parseXmlPath(String hostid, String video_pool_id) {
        if (StringUtil.isFine(video_pool_id)) {
            // return "xml/"+hostid+"/"+video_pool_id.substring(video_pool_id.length() - 2)+"/"+video_pool_id+".xml";
            return "vxml/" + video_pool_id + "_" + hostid + ".xml";
        }
        return "";
    }

    public static String parsePPTXmlPath(String hostid, String video_pool_id) {
        if (StringUtil.isFine(video_pool_id)) {
            // return "xml/"+hostid+"/"+video_pool_id.substring(video_pool_id.length() - 2)+"/"+video_pool_id+".xml";
            return "pptxml/" + video_pool_id + "_" + hostid + ".xml";
        }
        return "";
    }

    public static String parseXmlPathByVid(String vid) {
        if (StringUtil.isFine(vid)) {
            return "vxml/" + vid + ".xml";
        }
        return "";
    }

    /**
     * 得到视频的接口xml路径
     * @param hostid
     * @param video_pool_id
     * @return
     */
    public static String parsePlaylistXmlPath(String hostid, long video_id) {
        return "pxml/" + video_id + "_" + hostid + ".xml";
    }

    /**
     * 得到视频的用户接口xml路径
     * @param hostid
     * @param video_pool_id
     * @return
     */
    public static String parseUserXmlPath(String userid) {
        if (StringUtil.isFine(userid)) {
            return "uxml/" + userid + ".xml";
        }
        return "";
    }

    /**
     * 得到推荐视频的用户接口xml路径
     * @param hostid
     * @param video_pool_id
     * @return
     */
    public static String parseRecommendXmlPath(String userid) {
        if (StringUtil.isFine(userid)) {
            return "rxml/" + userid + ".xml";
        }
        return "";
    }

    public static void removeUserVxml(String userid) {
        if (StringUtil.isFine(userid)) {
            try {

                File directory = new File(Config.get("staticxml") + "vxml/");
                for (File f : directory.listFiles()) {
                    if (f.getName().startsWith(userid)) {
                        f.delete();
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 得到视频的接口xml URL
     * @param hostid
     * @param video_pool_id
     * @return
     */
    public static String parseXmlUrl(String hostid, String video_pool_id) {
        if (StringUtil.isFine(video_pool_id)) {
            return "http://" + Config.get("domain") + "/uc/video/settings/xml?vid=" + video_pool_id + "_" + hostid;
        }
        return "";
    }

    public static void cleanAliyunUrl(String url, boolean isDir) throws Exception {
        String interface_url = "http://my.polyv.net/v2/cache/clean-resources";
        long ptime = System.currentTimeMillis();

        String innorSign = EncryptUtil.getMD5("LOGININNOR" + ptime).toUpperCase();

        interface_url = interface_url + "?ptime=" + ptime + "&innor=" + innorSign + "&url="
                + URLEncoder.encode(url, "utf-8") + "&isDir=" + isDir;
        String result = WgetUtil.wgetDocument(interface_url, "utf-8");
        logger.info(String.format("cleanAlyunUrl, url=%s, isDir=%s, result=[%s]", url, isDir, result));
    }

    /**
     * 根据当前视频状态及用户权限获取下一个视频状态
     * @param video_pool_status 当前视频状态
     * @param ugrade 当前用户权限
     * @return
     */
    public static int getVideoNextStatus(int video_pool_status, int ugrade) {
        int status;
        /*************** 更新状态 *****/
        if (ugrade == Permiss.ADMIN || ugrade == Permiss.COMPANY) {
            status = VideoStatus.video_publish_ok;// 发布
        } else {
            // 已经审核过了就不用再审核
            if (video_pool_status == VideoStatus.video_deal_again1
                    || video_pool_status == VideoStatus.video_deal_again0) {
                status = VideoStatus.video_publish_ok;// 发布
            } else {
                status = VideoStatus.video_appr_waite;// 审核
            }
        }
        return status;
    }

    public static String parseUrlBySource(String stype, String hostid, String url) {
        if (!StringUtil.isFine(url)) {
            // return "/file/themes/default/images/video_blank.png";
            return "/img/videodefault.png";
        } else if (url.startsWith("http://")) {
            return url;
        } else {
            return "/" + stype + "/" + hostid + "/" + url;
        }
    }

    public static String parseUrl(String type, String hostid) {

        return "/u" + type + "/" + hostid + "/";

    }

    // cata_logic
    public static String parseCataMenu(String hostid, String catatree, String catatype) {
        String output = "";
        if (StringUtils.isBlank(catatree)) {
            return output;
        }
        String[] cataid_list = catatree.split(",");
        Cata_db cdb = new Cata_db(hostid);
        for (int i = 0; cataid_list != null && i < cataid_list.length; i++) {
            DOC doc = cdb.getOne(IntUtil.s2l(cataid_list[i]));
            if (doc != null) {
                output += "<a href='/uc/" + catatype + "/list?cataid=" + doc.get("cataid")
                        + "' target='navTab' rel='cata_" + (cataid_list.length > 1 ? cataid_list[1] : cataid_list[0])
                        + "'>" + doc.get("cataname") + "</a> >> ";
            }
        }
        return output;
    }

    public static String getCataNameList(String hostid, long objid, long cataid) {
        return getCataNameList(hostid, objid, cataid, 1);
    }

    public static void changeCata(String hostid, String video_pool_id, long cataid, DOC pcata) {
        try {
            Video_pool_db vpd = new Video_pool_db(hostid);
            Cata_db cdb = new Cata_db(hostid);
            DOC vdoc = vpd.getOne(video_pool_id);
            String precatatree = "";
            if (vdoc != null) {
                precatatree = vdoc.get("catatree");
            }
            // System.out.println("precatatree:"+precatatree);
            // String precatatree =
            vpd.updateLong(video_pool_id, "cataid", cataid);
            vpd.update(video_pool_id, "catatree", pcata.get("catatree"));
            // System.out.println(video_pool_id );

            String catastr = pcata.get("catatree");
            // 更新父目录条数
            if (catastr != null) {
                String[] catas = catastr.split(",");
                for (int i = 0; i < catas.length; i++) {
                    try {
                        long pcataid = Long.parseLong(catas[i]);
                        if (pcataid != 1) {
                            DOC tmp = cdb.getOne(pcataid);
                            if (tmp != null) {
                                int count = vpd.getCountByCatatree(tmp.get("catatree"));
                                cdb.updateNum(pcataid, "articles", count);
                            }
                        }

                    } catch (Exception ex) {

                    }

                }
            }
            // 更新旧父目录条数
            if (precatatree.length() > 0) {
                String[] catas = precatatree.split(",");
                for (int i = 0; i < catas.length; i++) {
                    try {
                        long pcataid = Long.parseLong(catas[i]);
                        if (pcataid != 1) {
                            DOC tmp = cdb.getOne(pcataid);
                            if (tmp != null) {
                                int count = vpd.getCountByCatatree(tmp.get("catatree"));
                                cdb.updateNum(pcataid, "articles", count);
                            }
                        }

                    } catch (Exception ex) {

                    }

                }

            }
            LogicUtil.cleanVXML(video_pool_id);
            // IPUtil.delCacheFile(LogicUtil.parseXmlPath(hostid, video_pool_id));
        } catch (Exception e) {

        }
    }

    public static String getCataNameById(String userid, long cataid) {
        String hostid = "";
        if (StringUtil.isFine(userid)) {
            hostid = userid.substring(0, 1);
        }
        return Cata_logic.getCataname(hostid, cataid);
    }

    public static String getCataNameList(String hostid, long objid, long cataid, int catatype) {
        if (objid == 0) {
            return Cata_logic.getCataname(hostid, cataid);
        }
        String catanames = "";
        Index_db idb = new Index_db(hostid);
        DOC[] index_docs = idb.getDataByObjid(objid);
        for (int i = 0; index_docs != null && i < index_docs.length; i++) {
            Cata_db cdb = new Cata_db(hostid);
            DOC cata = cdb.getOne(index_docs[i].getl("cataid"));
            if (cata == null) {
                continue;
            }
            if (StringUtil.isFine(cata.get("cataname"))
                    && IntUtil.s2i(cata.get("catatree").substring(0, 1)) == catatype) {
                if (i != 0) {
                    catanames += ",";
                }
                catanames += cata.get("cataname");
            }
        }
        catanames = catanames.replaceAll(",+", ",");
        if (!StringUtil.isFine(catanames)) {
            return Cata_logic.getCataname(hostid, cataid);
        }
        return catanames;
    }

    public static String playURL(String video_pool_id) {
        return new StringBuilder("/uc/video/viewflash?videopoolid=").append(video_pool_id).toString();
    }

    /**
     * 截图
     * @param ext ext必须包含duration时长，source_file源文件地址参数。会更新字段：images, images_b, first_image, first_image_b
     * @param imageCount 截图数量
     * @param videoPath 视频文件路径
     */
    @SuppressWarnings("unchecked")
    public static void cutImage(DOC ext, String hostid, int imageCount, String videoPath, int width, int height) {
        try {
            // 获取视频时长
            String[] durationTimes = ext.get("duration").split(":");

            int hour = 0, minute = 0;
            double second = 0;
            try {
                hour = IntUtil.s2i(durationTimes[0]);
                minute = IntUtil.s2i(durationTimes[1]);
                second = IntUtil.s2d(durationTimes[2]);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

            double sumSeconds = hour * 3600 + minute * 60 + second;

            DecimalFormat df = new DecimalFormat("0.00");
            ArrayList<String> extImages = new ArrayList<String>(); // 存放到 ext 中的截图的相对路径，例如
            // e6b23c6f51/2/e6b23c6f51350f106556806a576b1942_0.jpg
            ArrayList<String> extImagesBig = new ArrayList<String>();

            StringWriter writer = new StringWriter();
            List<String> imagePaths = new ArrayList<String>(); // 截图的完整路径，例如 /data/htmlfile/video_image/e/e6b23c6f51
            // /2/e6b23c6f51350f106556806a576b1942_0.jpg
            List<String> imageBigPaths = new ArrayList<String>();
            String filename = ext.get("source_file").split("\\.")[0]; // 例如 e6b23c6f51/2
            // /e6b23c6f51350f106556806a576b1942_0

            // 进行截图，获取图片路径
            String htmlDir = Config.get("htmldir");
            for (int i = 0; i < imageCount; i++) {
                String cutImageSecond = df.format(i == 0 ? 2 : (sumSeconds / imageCount * i)); // 进行截图的秒数，第一张图固定为第2秒

                // /data/htmlfile/video_image/e/e6b23c6f51/2/e6b23c6f51350f106556806a576b1942_0.jpg
                String imagePath = String.format(IMAGE_PATH, htmlDir, hostid, filename, i);
                String imageBigPath = String.format(IMAGE_BIG_PATH, htmlDir, hostid, filename, i);

                String command = String.format(CUT_IMAGE_COMMAND, Config.get("svndir"), videoPath, cutImageSecond,
                        imagePath, imageBigPath, width, height);
                logger.info(">>>>>>>>cut img start:" + command);
                Command.run(command, writer);
                logger.info(">>>>>>>>cut img end:" + writer.getBuffer().toString());

                extImages.add(filename + "_" + i + ".jpg");
                extImagesBig.add(filename + "_" + i + "_b.jpg");

                imagePaths.add(imagePath);
                imageBigPaths.add(imageBigPath);
            }

            // TODO 进行这个处理的原因是？
            // 如果第一张截图不存在，且截图文件数大于1，则复制第二张截图到第一张截图的路径
            if (!ayou.util.FileUtil.exists(imagePaths.get(0)) && imagePaths.size() > 1) {
                ayou.util.FileUtil.copy(imagePaths.get(1), imagePaths.get(0), true);
            }
            if (!ayou.util.FileUtil.exists(imageBigPaths.get(0)) && imageBigPaths.size() > 1) {
                ayou.util.FileUtil.copy(imageBigPaths.get(1), imageBigPaths.get(0), true);
            }

            // 批量上传截图到hdfs
            UploadService.uploadFiles(imagePaths);
            UploadService.uploadFiles(imageBigPaths);

            // 更新 ext
            ext.put("images", extImages);
            ext.put("images_b", extImagesBig);

            // 如果原来不存在封面图，则正常更新。否则，不更新，保留原有封面图
            if (StringUtils.isEmpty(ext.get("first_image")) || ext.get("first_image").indexOf("default") != -1) {
                ext.put("first_image", extImages.get(0));
                ext.put("first_image_b", extImagesBig.get(0));
            } else {
                logger.info("first_image exists. vid=" + ext.get("video_pool_id") + ", first_image="
                        + ext.get("first_image"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * 视频截图
     * @param cover_images_count 需要截图的数量
     * @param videofile 视频文件路径
     * @param width 宽度
     * @param height 高度
     */
    public static void cutImage(String hostid, String video_pool_id, int cover_images_count, String videofile,
            int width, int height) {
        Video_pool_db vpdb = new Video_pool_db(hostid);
        DOC video_pool = vpdb.getOne(video_pool_id);
        if (video_pool == null) {
            logger.info(video_pool_id + " is null");
            return;
        }
        DOC ext = JsonUtil.strToDOC(video_pool.get("ext"));
        cutImage(ext, hostid, cover_images_count, videofile, width, height);

        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("images", ext.geto("images"));
        map.put("images_b", ext.geto("images_b"));
        map.put("first_image", ext.geto("first_image"));
        map.put("first_image_b", ext.geto("first_image_b"));
        vpdb.updateExt(video_pool_id, map);
    }

    /**
     * 生产视频源和转码后的文件存放的共同路径，不包括后缀
     * @param video_pool_id
     * @param userid
     * @return
     */
    public static String getVideoPublicFilePath(String video_pool_id, String userid) {
        StringBuffer sb = new StringBuffer();
        // ***userid/1/video_pool_id
        sb.append(userid);
        sb.append("/");
        sb.append(video_pool_id.charAt(video_pool_id.length() - 1));
        ;
        sb.append("/");
        sb.append(video_pool_id);
        return sb.toString();
    }

    public static String getSourceFilePath(String source_file, String hostid) {
        StringBuffer sb = new StringBuffer();
        // ***htmldir/x/video_source/
        sb.append(Config.get("htmldir"));
        sb.append(hostid);
        sb.append("/video_source/");
        sb.append(source_file);
        return sb.toString();
    }

    public static String getTargetFilePath(String source_file, String hostid) {
        StringBuffer sb = new StringBuffer();
        // ***htmldir/x/video_target/
        sb.append(Config.get("htmldir"));
        sb.append(hostid);
        sb.append("/video_target/");
        sb.append(source_file);
        return sb.toString();
    }

    public static String getFullTargetFilePath(String video_pool_id, String hostid, int index) {
        StringBuffer sb = new StringBuffer();
        int length = video_pool_id.length();
        sb.append(Config.get("htmldir"));
        sb.append(hostid);
        sb.append("/video_target/");
        sb.append(video_pool_id.substring(0, 10) + "/");
        sb.append(video_pool_id.charAt(length - 1) + "/");
        sb.append(video_pool_id + "_" + index + ".mp4");
        return sb.toString();
    }

    public static String videoStatusDesc(int status) {
        return VideoStatus.desc(status);
    }

    public static String videoStatusDesc(String status) {
        try {
            return videoStatusDesc(Integer.valueOf(status));
        } catch (Exception e) {
            return "未知";
        }

    }

    /**
     * 
     * @param original_definition 分辨率
     * @param original_br 码率
     * @return
     */
    public static DOC cal_video(String original_definition, int original_br, int maxBr) {
        ArrayList<BW> taskList = EncodeTask_logic.getTask(original_br);
        // 指定最大码率个数，则去掉多余的码率
        while (taskList.size() > maxBr) {
            taskList.remove(taskList.size() - 1);
        }

        DOC cal = new DOC();
        int width = Integer.parseInt(original_definition.split("x")[0]);
        int height = Integer.parseInt(original_definition.split("x")[1]);
        StringBuffer brbuffer = new StringBuffer();
        StringBuffer dnbuffer = new StringBuffer();
        for (int i = 0; i < taskList.size(); i++) {
            BW bw = taskList.get(i);
            brbuffer.append(bw.bitrate);
            // cal.put("level_status_"+(i+1), "0");
            if (i != taskList.size() - 1) {
                brbuffer.append(",");
            }
            int outwidth = bw.width;
            int outheight = Integer.parseInt("" + outwidth * height / width);
            dnbuffer.append(outwidth + "x" + outheight);
            if (i != taskList.size() - 1) {
                dnbuffer.append(",");
            }
        }
        cal.put("out_br", brbuffer.toString());
        cal.put("resolution", dnbuffer.toString());
        return cal;
    }

    /**
     * 根据相关信息，获取输出码率列表、分辨率列表
     * @param taskList 编码任务列表
     * @param original_definition 源视频分辨率
     * @param maxBr 最大码率级别，例如 3
     * @return Map 包含两个字段，例如 {out_br: "256,512,1228", resolution: "480x270,848x477,1280x720"}<br/>
     *         为了方便后续更新 ext，而使用这样的返回类型
     */
    public static Map<String, Object> cal_video(List<BW> taskList, String original_definition, int maxBr) {
        // 指定最大码率个数，则去掉多余的码率
        while (taskList.size() > maxBr) {
            taskList.remove(taskList.size() - 1);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        int width = Integer.parseInt(original_definition.split("x")[0]);
        int height = Integer.parseInt(original_definition.split("x")[1]);
        StringBuffer brbuffer = new StringBuffer();
        StringBuffer dnbuffer = new StringBuffer();
        for (int i = 0; i < taskList.size(); i++) {
            BW bw = taskList.get(i);
            brbuffer.append(bw.bitrate);
            if (i != taskList.size() - 1) {
                brbuffer.append(",");
            }
            int outwidth = bw.width;
            int outheight = Integer.parseInt("" + outwidth * height / width);
            dnbuffer.append(outwidth).append("x").append(outheight);
            if (i != taskList.size() - 1) {
                dnbuffer.append(",");
            }
        }
        result.put("out_br", brbuffer.toString());
        result.put("resolution", dnbuffer.toString());
        return result;
    }

    public static String postSingleVideoToCDN(Map<String, String> vdoc) {
        String url = "http://mvodapi.ovp.ccgslb.com.cn/portal/mds/service.jsp";
        // String url = "http://220.181.49.195/uc/video/settings/getcdnxml";
        String op = "publish";
        String context = getSingleVideoCDNXml(vdoc);
        System.out.println("预推到cdn>>>>>>>>>>>>>>>>>>>" + url + "?op=" + op + "&context=" + context);
        NameValuePair[] data = { new NameValuePair("op", op), new NameValuePair("context", context) };
        String response = PostHttp.methodPost(url, data);
        return response;
    }

    /**
     * 上传到cdn
     * @param vdoc
     * @param hostid
     */
    public static String postVideoToCDN(DOC vdoc, String hostid) {
        String url = "http://mvodapi.ovp.ccgslb.com.cn/portal/mds/service.jsp";
        // String url = "http://220.181.49.195/uc/video/settings/getcdnxml";
        String op = "publish";
        String context = getCDNXML(vdoc, hostid);
        System.out.println("预推到cdn>>>>>>>>>>>>>>>>>>>" + url + "?op=" + op + "&context=" + context);
        NameValuePair[] data = { new NameValuePair("op", op), new NameValuePair("context", context) };
        String response = PostHttp.methodPost(url, data);
        return response;
    }

    public static int getGroupNumer(String groupName) {
        int groupNumber = 1;
        try {
            groupNumber = Integer.parseInt(groupName.replaceAll("group", ""));
        } catch (Exception e) {

        }
        return groupNumber;
    }

    // 根据用户配置网宿，快网，以及当前文件所在group来生成域名
    public static String getCdnUrl(String cdnurl, String groupName) {

        // 只有group4,8一组实现逻辑
        /*
         * if("group4".equals(groupName) || "group8".equals(groupName) || "group9".equals(groupName) || "group10".equals(groupName)
         * || "group11".equals(groupName) || "group12".equals(groupName) || "group13".equals(groupName) || "group14".equals(groupName)||
         * "group15".equals(groupName)){
         * String groupNum = groupName.replaceAll("group", "");
         * if("seg1.videocc.net".equals(cdnurl)){
         * return "ws"+groupNum + ".videocc.net";
         * }
         * 
         * if("seg2.videocc.net".equals(cdnurl)){
         * return "kw"+groupNum + ".videocc.net";
         * }
         * 
         * }
         * return cdnurl;
         */

        int groupNumber = 1;
        try {
            groupNumber = Integer.parseInt(groupName.replaceAll("group", ""));
        } catch (Exception e) {

        }

        //
        if (groupNumber == 4 || groupNumber == 8 || groupNumber >= 9) {
            if ("seg1.videocc.net".equals(cdnurl)) {
                return "ws" + groupNumber + ".videocc.net";
            }

            if ("seg2.videocc.net".equals(cdnurl)) {
                return "kw" + groupNumber + ".videocc.net";
            }
        }

        return cdnurl;
    }

    /**
     * 网宿清目录
     **/
    public static void cleanWangSuByUrl(final String url) {

        new Thread(new Runnable() {

            public void run() {
                try {
                    String userid = Config.get("ws_custid");// 这里cust_id==userid
                    // cdn提供的用户密码
                    String passwd = Config.get("ws_cdnpw");

                    String md5passwd = MD5Util.getMD5String(userid + passwd + url);
                    String hosturl = "http://wscp.lxdns.com:8080/wsCP/servlet/contReceiver";

                    NameValuePair[] data = { new NameValuePair("username", userid),
                            new NameValuePair("passwd", md5passwd), new NameValuePair("url", url) };
                    String result = PostHttp.methodPost(hosturl, data);
                    logger.info("clean wangsu cache:" + url + " result:" + result);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    logger.error(e.getMessage(), e);
                }

            }
        }).start();

        // System.out.println(response);

    }

    /**
     * 预推到网宿
     **/
    public static void postVideoToCDN2(DOC vdoc, String hostid, int level) {
        // 预推http://wscp.lxdns.com:8080/wsCP/servlet/contReceiver?username=easyfun&passwd=7e290100ba58fa285b9f02dded334e6d&url=http://v2.polyv.net/mp4/0bb65aeb75/b/0bb65aeb75f68379fea52bde61e67a8b.mp4&isfetch=Y

        String userid = Config.get("ws_custid");// 这里cust_id==userid
        // cdn提供的用户密码
        String passwd = Config.get("ws_cdnpw");

        String relativePath = vdoc.get("swf_link" + level);
        String sourcePath = "http://plvod01.videocc.net/mp4/" + relativePath;
        String md5passwd = MD5Util.getMD5String(userid + passwd + sourcePath);
        String url = "http://wscp.lxdns.com:8080/wsCP/servlet/contReceiver";

        NameValuePair[] data = { new NameValuePair("username", userid), new NameValuePair("passwd", md5passwd),
                new NameValuePair("url", sourcePath), new NameValuePair("isfetch", "N") };
        String response = PostHttp.methodPost(url, data);

        relativePath = vdoc.get("mp4_link" + level);
        sourcePath = "http://mpv.videocc.net/mp4/" + relativePath;
        md5passwd = MD5Util.getMD5String(userid + passwd + sourcePath);

        NameValuePair[] data2 = { new NameValuePair("username", userid), new NameValuePair("passwd", md5passwd),
                new NameValuePair("url", sourcePath), new NameValuePair("isfetch", "N") };
        response = PostHttp.methodPost(url, data2);
        System.out.println("mpv precache:" + response);

    }

    /**
     * 预推到帝联
     **/
    public static void postVideoToDnionCDN(DOC vdoc, String hostid, int level) {

        String userid = "yifangkeji";
        // cdn提供的用户密码
        String passwd = "Qy7ttvj7vg";

        System.out.println("Dnion precache...");
        String relativePath = vdoc.get("swf_link" + level);
        String sourcePath = "http://dn01.videocc.net/" + relativePath;
        // String url = "http://pushdx.dnion.com/preCache.do";
        // 不再预推，只清缓存
        String url = "http://pushdx.dnion.com/cdnUrlPush.do";

        NameValuePair[] data = { new NameValuePair("username", userid), new NameValuePair("password", passwd),
                new NameValuePair("url", sourcePath) };
        String response = PostHttp.methodPost(url, data);
        System.out.println(response);

    }

    public static String postAdvVideoToCDN(Map<String, String> videoInfo) {
        String url = "http://mvodapi.ovp.ccgslb.com.cn/portal/mds/service.jsp";
        String op = "publish";
        String context = getAdvVideoCDNXml(videoInfo);
        System.out.println("预推到cdn>>>>>>>>>>>>>>>>>>>" + url + "?op=" + op + "&context=" + context);
        NameValuePair[] data = { new NameValuePair("op", op), new NameValuePair("context", context) };
        String response = PostHttp.methodPost(url, data);
        return response;
    }

    /**
     * 更新cdn同名视频,原理是cdn马上发出回源请求,即时的
     * @param vdoc
     * @param hostid
     */
    public static String updateVideoToCDN(DOC vdoc, String hostid) {
        String url = "http://mvodapi.ovp.ccgslb.com.cn/portal/mds/service.jsp";
        String op = "update";
        String context = getCDNXML(vdoc, hostid);
        System.out.println("预推到cdn>>>>>>>>>>>>>>>>>>>" + url + "?op=" + op + "&context=" + context);
        NameValuePair[] data = { new NameValuePair("op", op), new NameValuePair("context", context) };
        String response = PostHttp.methodPost(url, data);
        return response;
    }

    public static String updateAdvVideoToCDN(Map<String, String> videoInfo) {
        String url = "http://mvodapi.ovp.ccgslb.com.cn/portal/mds/service.jsp";
        String op = "update";
        String context = getAdvVideoCDNXml(videoInfo);
        System.out.println("预推到cdn>>>>>>>>>>>>>>>>>>>" + url + "?op=" + op + "&context=" + context);
        NameValuePair[] data = { new NameValuePair("op", op), new NameValuePair("context", context) };
        String response = PostHttp.methodPost(url, data);
        return response;
    }

    /**
     * 清除cdn,原理是cdn删除各个服务器上的同名视频,再回源,时间大概是要2-3分钟
     * @param vdoc
     * @param hostid
     */
    public static String cleanCDNForVideo(DOC vdoc, String hostid) {
        try {
            String url = "http://mvodapi.ovp.ccgslb.com.cn/portal/mds/service.jsp";
            String op = "delete";
            String context = deleteCDNXML(vdoc, hostid);
            System.out.println("从cdn上清除>>>>>>>>>>>>>>>>>>>" + url + "?op=" + op + "&context=" + context);
            NameValuePair[] data = { new NameValuePair("op", op), new NameValuePair("context", context) };
            String response = PostHttp.methodPost(url, data);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 清除前端static缓存的MP4
     * @param vdoc
     * @param hostid
     */
    public static void cleanStaticVideo(String out_br, String target_file1, String hostid) {
        if (StringUtil.isFine(out_br)) {
            String filename = target_file1.split("_")[0];
            String[] brs = out_br.split(",");
            for (int i = 1; i < brs.length + 1; i++) {
                String target_file = "mp4/" + filename + "_" + i + ".mp4";
                IPUtil.delCacheFile(target_file);
            }
        }
    }

    /**
     * 清空cdn XML 代码
     * @param vdoc
     * @param hostid
     * @return
     */
    public static String deleteOneCDNXML(String publish_url, String video_pool_id, int level) {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            sb.append("<ccsc>");
            // cust_id由cc提供
            String userid = Config.get("custid");// 这里cust_id==userid
            sb.append("<cust_id>").append(userid).append("</cust_id>");
            // cdn提供的用户密码
            String passwd = Config.get("cdnpw");
            String item_id = video_pool_id + "_" + level;
            // 加密算法
            String md5passwd = MD5Util.getMD5String(item_id + userid + "chinacache" + passwd);
            sb.append("<passwd>").append(md5passwd).append("</passwd>");
            // 发布记录的唯一标识
            sb.append("<item_id value=\"" + item_id + "\">");
            // cdn地址
            sb.append("<publish_path>").append(publish_url).append("</publish_path>");
            sb.append("</item_id>");
            sb.append("</ccsc>");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return sb.toString();
    }

    /**
     * 组装ext,从旧video_pool及pu里替换组装新的ext。更新视频时使用
     * @param pu
     * @param video_pool
     * @return ext
     */
    public static DOC changeExt(ParamUtil pu, DOC video_pool) {
        DOC ext = JsonUtil.strToDOC(video_pool.get("ext"));
        String userid = video_pool.get("userid");
        String hostid = userid.substring(0, 1);
        // 取出Ext所有的字段
        String[] fields = VideoPoolExt.getExtFields();
        StringBuffer fieldssb = new StringBuffer();
        for (int i = 0; i < fields.length; i++) {
            fieldssb.append("'" + fields[i] + "'");
            if (i != fields.length - 1) {
                fieldssb.append(",");
            }
        }
        String fieldstr = fieldssb.toString();
        for (int i = 0; i < pu.getNames().length; i++) {
            String name = pu.getNames()[i];
            String valueString = pu.getString(name);
            putExt(video_pool, ext, hostid, fieldstr, name, valueString);
        }
        return ext;
    }

    /**
     * 组装cdn XML 代码
     * @param vdoc
     * @param hostid
     * @return
     */
    public static String deleteCDNXML(DOC vdoc, String hostid) {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            sb.append("<ccsc>");
            // cust_id由cc提供
            String userid = Config.get("custid");// 这里cust_id==userid
            sb.append("<cust_id>").append(userid).append("</cust_id>");
            String first_item = "";
            // cdn提供的用户密码
            String passwd = Config.get("cdnpw");
            String out_br = vdoc.get("out_br");
            String video_pool_id = vdoc.get("video_pool_id");
            if (StringUtil.isFine(out_br)) {
                String[] brs = out_br.split(",");
                for (int i = 1; i < brs.length + 1; i++) {
                    String item_id = video_pool_id + "_" + i;
                    if (i == 1) {
                        // 加密算法
                        first_item = item_id;
                        String md5passwd = MD5Util.getMD5String(first_item + userid + "chinacache" + passwd);
                        sb.append("<passwd>").append(md5passwd).append("</passwd>");
                    }
                    // 发布记录的唯一标识
                    sb.append("<item_id value=\"" + item_id + "\">");
                    String swf_link = LogicUtil.parseUrl("video", hostid, vdoc.get("swf_link" + i));
                    // 视频文件最后发布的链接
                    String publish_path = swf_link;
                    sb.append("<publish_path>").append(publish_path).append("</publish_path>");
                    sb.append("</item_id>");
                }
            }
            sb.append("</ccsc>");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return sb.toString();
    }

    /**
     * 组装cdn XML 代码
     * @param vdoc
     * @param hostid
     * @return
     */
    public static String getCDNXML(DOC vdoc, String hostid) {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            sb.append("<ccsc>");
            // cust_id由cc提供
            String userid = Config.get("custid");// 这里cust_id==userid
            sb.append("<cust_id>").append(userid).append("</cust_id>");
            String first_item = "";
            // cdn提供的用户密码
            String passwd = Config.get("cdnpw");
            String out_br = vdoc.get("out_br");
            String video_pool_id = vdoc.get("video_pool_id");
            if (StringUtil.isFine(out_br)) {
                String[] brs = out_br.split(",");
                for (int i = 1; i < brs.length + 1; i++) {
                    String item_id = video_pool_id + "_" + i;
                    if (i == 1) {
                        // 加密算法
                        first_item = item_id;
                        String md5passwd = MD5Util.getMD5String(first_item + userid + "chinacache" + passwd);
                        sb.append("<passwd>").append(md5passwd).append("</passwd>");
                    }
                    // 发布记录的唯一标识
                    sb.append("<item_id value=\"" + item_id + "\">");

                    String relativePath = vdoc.get("swf_link" + i);
                    String sourcePath = "http://v2.polyv.net/mp4/" + relativePath;

                    // String swf_link = LogicUtil.parseUrl( "video", hostid, vdoc.get("swf_link"+i));
                    sb.append("<source_path>").append(sourcePath).append("</source_path>");

                    // 视频文件最后发布的链接
                    String publishPath = "http://freeovp.videocc.net/" + relativePath;
                    sb.append("<publish_path>").append(publishPath).append("</publish_path>");
                    // String md5file = MD5Util.getMD5String(item_id);
                    // md5说明：op=check,通过md5检验分发设备上的文件,op=publish时下载完文件比较md5,如果没有此项则只检验文件长度，当op=delete时此项无效

                    String md5file = "";
                    String dfsSrc = "/data/htmlfile/" + hostid + "/video_target/" + relativePath;
                    String localFile = "/tmp/" + relativePath.substring(relativePath.lastIndexOf("/") + 1);
                    try {
                        getDfsService().downloadFile(dfsSrc, localFile);
                        md5file = MD5Util.md5sum(localFile);
                        new File(localFile).delete();
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }

                    sb.append("<md5>").append(md5file).append("</md5>");
                    sb.append("</item_id>");
                }
            }
            sb.append("</ccsc>");
            System.out.println("上传到cdn：>>>>>>>>>>>>>>>>>>>" + sb.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return sb.toString();
    }

    /**
     * 从flv解密输出的mp4预推
     **/
    public static String getSingleVideoCDNXml(Map<String, String> videoInfo) {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            sb.append("<ccsc>");
            // cust_id由cc提供
            String userid = Config.get("custid");// 这里cust_id==userid
            sb.append("<cust_id>").append(userid).append("</cust_id>");

            // cdn提供的用户密码
            String passwd = Config.get("cdnpw");
            String videoid = videoInfo.get("videoid");
            String path = videoInfo.get("path");
            String md5passwd = MD5Util.getMD5String(videoid + userid + "chinacache" + passwd);
            sb.append("<passwd>").append(md5passwd).append("</passwd>");

            // 发布记录的唯一标识
            sb.append("<item_id value=\"" + videoid + "\">");

            String source_file = videoInfo.get("source_file");
            String sourcePath = "http://v2.polyv.net/mp4/" + source_file;

            // String swf_link = LogicUtil.parseUrl( "video", hostid, vdoc.get("swf_link"+i));
            sb.append("<source_path>").append(sourcePath).append("</source_path>");

            // 视频文件最后发布的链接
            String publishPath = "http://freeovp.videocc.net/" + source_file;
            sb.append("<publish_path>").append(publishPath).append("</publish_path>");
            String md5file = MD5Util.md5sum(path);
            // md5说明：op=check,通过md5检验分发设备上的文件,op=publish时下载完文件比较md5,如果没有此项则只检验文件长度，当op=delete时此项无效
            sb.append("<md5>").append(md5file).append("</md5>");
            sb.append("</item_id>");

            sb.append("</ccsc>");
            System.out.println(">>>>>>>>>>>>.cdnXML：>>>>>>>>>>>>>>>>>>>" + sb.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return sb.toString();

    }
    
    /**
     * 判断能否由GPU进行编码
     */
    public static boolean isGPUTask(DOC ext, DOC playProfileExt) {
        // 如果设置了禁用GPU编码，则直接返回 false
        if ("1".equals(playProfileExt.get("disable_gpu"))) {
            return false;
        }
        
        // 音频，或者非 yuv420p，不能由GPU编码
        boolean isAudio = "1".equals(ext.get("isAudio"));
        boolean isYuv420p = "yuv420p".equals(ext.get("original_pix_fmt"));
        if(isAudio || !isYuv420p) {
            return false;
        }
    
        // 源视频宽度大于2032，不能由GPU编码
        int width = 0;
        String originalDefinition = ext.get("original_definition");
        if (StringUtils.contains(originalDefinition, "x")) {
            width = Integer.parseInt(originalDefinition.split("x")[0]);
            if (width >= 2032) {
                return false;
            }
        }
        
        // 源视频宽度大于等于1080，且帧率大于30，不能由GPU编码
        String originalFpsStr = ext.get("original_fps");
        if (NumberUtils.isNumber(originalFpsStr) && Integer.parseInt(originalFpsStr) > 30 && width >= 1080) {
            return false;
        }
    
        // 判断是否 nvidia gpu 支持的解码格式
        String vcodec = ext.get("original_encoding");
        List<String> gpuCodecList = Arrays.asList("h264", "hevc", "mjpeg", "mpeg1video", "mpeg2video", "mpeg4", "vc1",
                "vp8", "vp9");
        return gpuCodecList.contains(vcodec);
    }
    
    public static String[] extractOutLineFromPDF(String pdf_file) throws Exception {
        String command = "/usr/bin/perl " + Config.get("svndir") + "WEB-INF/sh/pdf_outline.pl " + pdf_file;
        System.out.println(command);

        ByteArrayOutputStream writer = new ByteArrayOutputStream();

        Command.run2(command, writer);
        String outline = writer.toString("utf-8");
        String[] items = outline.split("///");

        return items;
    }

    public static String getAdvVideoCDNXml(Map<String, String> videoInfo) {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            sb.append("<ccsc>");
            // cust_id由cc提供
            String userid = Config.get("custid");// 这里cust_id==userid
            sb.append("<cust_id>").append(userid).append("</cust_id>");
            String first_item = "";
            // cdn提供的用户密码
            String passwd = Config.get("cdnpw");
            String advID = videoInfo.get("advID");
            String path = videoInfo.get("path");
            String md5passwd = MD5Util.getMD5String(advID + userid + "chinacache" + passwd);
            sb.append("<passwd>").append(md5passwd).append("</passwd>");

            // 发布记录的唯一标识
            sb.append("<item_id value=\"" + advID + "\">");

            String matterurl = videoInfo.get("matterurl");
            String sourcePath = "http://v2.polyv.net/mp4" + matterurl;

            // String swf_link = LogicUtil.parseUrl( "video", hostid, vdoc.get("swf_link"+i));
            sb.append("<source_path>").append(sourcePath).append("</source_path>");

            // 视频文件最后发布的链接
            String publishPath = "http://freeovp.videocc.net" + matterurl;
            sb.append("<publish_path>").append(publishPath).append("</publish_path>");
            String md5file = MD5Util.md5sum(path);
            // md5说明：op=check,通过md5检验分发设备上的文件,op=publish时下载完文件比较md5,如果没有此项则只检验文件长度，当op=delete时此项无效
            sb.append("<md5>").append(md5file).append("</md5>");
            sb.append("</item_id>");

            sb.append("</ccsc>");
            System.out.println(">>>>>>>>>>>>.广告cdnXML：>>>>>>>>>>>>>>>>>>>" + sb.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return sb.toString();

    }

    /**
     * 组装ext,从旧video_pool及pu里替换组装新的ext。更新视频时使用
     * @param doc
     * @param video_pool
     * @return ext
     */
    public static DOC changeExt(DOC doc, DOC video_pool) {
        DOC ext = JsonUtil.strToDOC(video_pool.get("ext"));
        String userid = video_pool.get("userid");
        String hostid = userid.substring(0, 1);
        // 取出Ext所有的字段
        String[] fields = VideoPoolExt.getExtFields();
        StringBuffer fieldssb = new StringBuffer();
        for (int i = 0; i < fields.length; i++) {
            fieldssb.append("'" + fields[i] + "'");
            if (i != fields.length - 1) {
                fieldssb.append(",");
            }
        }
        String fieldstr = fieldssb.toString();
        for (Iterator iter = doc.entrySet().iterator(); iter.hasNext();) {
            Entry entry = (Entry) iter.next();
            String name = (String) entry.getKey();// 返回与此项对应的键
            try {

                if (entry.getValue() instanceof String) {
                    String valueString = (String) entry.getValue();// 返回与此项对应的值
                    putExt(video_pool, ext, hostid, fieldstr, name, valueString);
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        }
        return ext;
    }

    /**
     * 组装ext的方法片段，包括特殊处理
     * @param video_pool
     * @param ext
     * @param hostid
     * @param fieldstr
     * @param name
     * @param valueString
     */
    public static void putExt(DOC video_pool, DOC ext, String hostid, String fieldstr, String name,
            String valueString) {
        // 图片特殊处理
        if (name.equals("first_image")) {
            valueString = valueString.replace("/uimage/" + hostid + "/", "");
        }
        if (name.equals("first_image_b")) {
            valueString = valueString.replace("/uimage/" + hostid + "/", "");
        }
        // 特殊处理:默认播放比率修改，要同时修改默认MP4路径
        if (name.equals("my_br")) {
            String resolution = video_pool.get("resolution");
            if (StringUtil.isFine(valueString)) {
                String default_videolink = "";
                String[] resolutions = resolution.split(",");
                // 如果用户首选用高清播放，但上传的视频又没有高清码率，则退而求次之
                if (resolutions.length < Integer.parseInt(valueString)) {
                    default_videolink = video_pool.get("swf_link" + resolutions.length);
                } else {
                    default_videolink = video_pool.get("swf_link" + valueString);
                }
                ext.put("default_videolink", default_videolink);
            }
        }
        // 只更新ext已有的属性
        // if(StringUtil.isFine(valueString) && fieldstr.indexOf("'"+name+"'")>-1){
        // 修改valueString允许为空
        if (valueString != null && fieldstr.indexOf("'" + name + "'") > -1) {
            ext.put(name, valueString);
        }
    }

    /**
     * 解析视频：
     * 
     * @param file
     * @return 返回writer out: $DUR/$DEFINITION/$BR/$FPS/$TOT_FR/$ENCODING: 时长、分辨率、码率、帧率、总帧数、原编码
     */
    public static DOC analysisVideo(final String file) {
        DOC doc = new DOC();
        try {
            StringWriter writer = new StringWriter();
            String command = "/bin/bash " + Config.get("svndir") + "/WEB-INF/sh/video_info.sh " + file;
            // writer out: $DUR/$DEFINITION/$BR/$FPS/$TOT_FR/$ENCODING: 时长、分辨率、码率、帧率、总帧数、原编码
            System.out.println(command);
            Command.run(command, writer);
            String outduration = writer.toString().trim();
            System.out.println("::::::::分析视频:==" + command + " >>>>>" + outduration);
            String video_info[] = outduration.split("\\|");
            doc.put("original_duration", video_info[0]);// 原视频时长
            String original_definition = video_info[1];// 原视频分辨率
            if (StringUtils.isBlank(original_definition)) {
                logger.info("original_definition is blank, use 720p");
                original_definition = "1280x720";
            }
            doc.put("original_definition", video_info[1]);
            doc.put("width", original_definition.split("x")[0]);
            doc.put("height", original_definition.split("x")[1]);
            int original_br = 0;
            try {
                // 原始码率有可能为N/A
                original_br = Integer.parseInt(video_info[2]);
            } catch (Exception e) {
                original_br = 256;
            }
            doc.put("original_br", original_br + "");
            ;// 原视频码率
            doc.put("original_fps", video_info[3]);// 原视频帧率
            String original_tot_fr = "";
            try {
                original_tot_fr = video_info[4];
                if (!StringUtil.isFine(original_tot_fr)) {
                    original_tot_fr = "10";
                }
            } catch (Exception e) {
                original_tot_fr = "10";
            }
            doc.put("original_tot_fr", original_tot_fr);// 原视频总帧数
            doc.put("original_encoding", video_info[5]);// 原视频编码
            // try catch 是因为在|| split(|) 会 java.lang.ArrayIndexOutOfBoundsException
            String original_audioft = "";
            try {
                original_audioft = video_info[6];// 原视频声音格式mp3,wmav2,aac等
                if (!StringUtil.isFine(original_audioft)) {
                    original_audioft = "aac";
                }
            } catch (Exception e) {
                original_audioft = "aac";
            }

            String original_ar = "";// 原视频声音频率-ar:44100
            try {
                original_ar = video_info[7];
                if (!StringUtil.isFine(original_ar)) {
                    original_ar = "44100";
                }
            } catch (Exception e) {
                original_ar = "44100";
            }

            String original_ac = "2";// ac:stereo,mono,5.1,2,s16

            try {
                if ("5.1".equals(video_info[8])) {
                    original_ac = "6";
                }
            } catch (Exception e) {
                original_ac = "2";
            }
            String original_ab = "";// 原视频声音码率:32k
            try {
                original_ab = video_info[9];// 原视频声音码率:32k
                if (!StringUtil.isFine(original_ab)) {
                    original_ab = "32";
                }
            } catch (Exception e) {
                original_ab = "32";
            }
            System.out.println("============222222222====== original_audioft=" + original_audioft + " original_ar="
                    + original_ar + " original_ac=" + original_ac + " original_ab=" + original_ab);
            doc.put("original_audioft", original_audioft);
            ;// 原视频声音格式mp3,wmav2,aac等
            doc.put("original_ar", original_ar);// 原视频声音频率-ar
            doc.put("original_ac", original_ac);// 原视频声音频道数-ac
            doc.put("original_ab", original_ab);// 原视频声音声速-ab
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return doc;
    }

    public static DOC analysisVideoByJsonContent(String content) {
        DOC doc = new DOC();
        try {

            System.out.println("::::::::分析视频:==" + content);

            JSONObject j = new JSONObject(content);
            if (j != null) {
                JSONObject format = j.getJSONObject("format");
                // 整个视频码率
                String format_bitrate = format.getString("bit_rate");
                int durationtotal = (int) format.getDouble("duration");
                doc.put("original_duration", secToTime(durationtotal));

                JSONArray streams = j.getJSONArray("streams");
                for (int i = streams.length() - 1; i >= 0; i--) {
                    JSONObject stream = (JSONObject) streams.get(i);
                    // stream 0: video
                    int duration = 0;
                    if ("video".equals(stream.getString("codec_type"))) {
                        try {
                            JSONObject tags = (JSONObject) stream.getJSONObject("tags");

                            if (tags != null && tags.length() > 0) {
                                String rotate = tags.getString("rotate");
                                if (StringUtil.isFine(rotate)) {
                                    doc.put("rotate", rotate);
                                }
                                // System.out.println("got rotate:"+rotate);
                            }
                        } catch (Exception e) {
                            // e.printStackTrace();
                        }

                        try {
                            duration = (int) stream.getDouble("duration");
                        } catch (Exception e) {

                        }
                        //视频比音频短，则采总数
                        if (duration == 0 || duration<durationtotal) {
                            duration = durationtotal;
                        }
                        doc.put("original_duration", secToTime(duration));// 原视频时长
                        doc.put("original_definition", stream.getString("width") + "x" + stream.getString("height"));
                        doc.put("width", stream.getString("width"));
                        doc.put("start_time", stream.getString("start_time"));
                        doc.put("height", stream.getString("height"));
                        String display_aspect_ratio = stream.getString("display_aspect_ratio");
                        // mjpeg audio dar is wrong
                        if (StringUtil.isFine(display_aspect_ratio)
                                && !"mjpeg".equals(stream.getString("codec_name"))) {
                            doc.put("display_aspect_ratio", display_aspect_ratio);
                        }
                        int original_br = 0;
                        try {
                            // stream 0没有码率，则采用整个视频的码率
                            if (!StringUtil.isFine(stream.getString("bit_rate"))) {
                                original_br = Integer.parseInt(format_bitrate);
                            } else {
                                // 原始码率有可能为N/A
                                original_br = Integer.parseInt(stream.getString("bit_rate"));
                            }

                            // 根据视频实际比较，计算是除以1000，而非1024
                            original_br = original_br / 1000;
                        } catch (Exception e) {
                            original_br = 256;
                        }
                        doc.put("original_br", original_br + "");// 原视频码率

                        if (stream.getString("avg_frame_rate") != null) {
                            if (stream.getString("avg_frame_rate").indexOf("/") != -1) {
                                String[] streamsframes = stream.getString("avg_frame_rate").split("/");
                                try {
                                    int r_frame_rate = Integer.parseInt(streamsframes[0])
                                            / Integer.parseInt(streamsframes[1]);
                                    if (r_frame_rate > 60) {
                                        r_frame_rate = 60;
                                    }
                                    doc.put("original_fps", "" + r_frame_rate);// 原视频帧率
                                } catch (Exception ss) {
                                    doc.put("original_fps", "25");
                                }
                            } else {
                                doc.put("original_fps", stream.getString("avg_frame_rate"));
                            }
                        }

                        doc.put("original_encoding", stream.getString("codec_name"));// 原视频编码

                        String original_tot_fr;
                        try {
                            original_tot_fr = stream.getString("nb_frames");
                            if (!StringUtil.isFine(original_tot_fr)) {
                                int original_fps_i = doc.geti("original_fps");
                                original_tot_fr = "" + original_fps_i * duration;
                            }
                            if ("0".equals(original_tot_fr)) { // 曾出现过，分析得到的总帧数为0的情况，会导致编码失败。此时要给个默认值。
                                original_tot_fr = "1000";
                            }
                        } catch (Exception e) {
                            original_tot_fr = "10";
                        }
                        doc.put("original_tot_fr", original_tot_fr);

                        doc.put("original_pix_fmt", stream.getString("pix_fmt"));
                        doc.put("original_profile", stream.getString("profile"));

                    }
                    if ("audio".equals(stream.getString("codec_type"))) {

                        if (duration == 0 && durationtotal == 0) {
                            try {
                                duration = (int) stream.getDouble("duration");
                            } catch (Exception e) {

                            }
                            System.out.println("audio duration:" + duration);
                            doc.put("original_duration", secToTime(duration));
                        }
                        String original_audioft = "";
                        try {
                            original_audioft = stream.getString("codec_name");// 原视频声音格式mp3,wmav2,aac等
                            if (!StringUtil.isFine(original_audioft)) {
                                original_audioft = "unknown"; // 没拿到codec_name，不能当成aac，避免被复用
                            }
                        } catch (Exception e) {
                            original_audioft = "unknown";// 没拿到codec_name，不能当成aac，避免被复用
                        }
                        String original_ar = "";// 原视频声音频率-ar:44100
                        try {
                            original_ar = stream.getString("sample_rate");
                            if (!StringUtil.isFine(original_ar)) {
                                original_ar = "44100";
                            }
                        } catch (Exception e) {
                            original_ar = "44100";
                        }

                        String original_ac = stream.getString("channels");// ac:stereo,mono,5.1,2,s16

                        int original_ab = 32;
                        try {
                            original_ab = Integer.parseInt(stream.getString("bit_rate"));
                            // 根据视频实际比较，计算是除以1000，而非1024
                            original_ab = original_ab / 1000;
                        } catch (Exception e) {
                            original_ab = 32;
                        }
                        // System.out.println("dar1:"+display_aspect_ratio);
                        int original_br = 0;
                        try {

                            // 原始码率有可能为N/A
                            original_br = Integer.parseInt(stream.getString("bit_rate"));

                            // 根据视频实际比较，计算是除以1000，而非1024
                            original_br = original_br / 1000;
                        } catch (Exception e) {
                            original_br = 256;
                        }
                        // 没有从视频轨道获取到码率，则设置音频码率
                        if (!StringUtil.isFine(doc.get("original_br"))) {
                            doc.put("original_br", original_br + "");
                            ;// 原视频码率
                        }

                        doc.put("original_audioft", original_audioft);
                        ;// 原视频声音格式mp3,wmav2,aac等
                        doc.put("original_ar", original_ar);// 原视频声音频率-ar
                        doc.put("original_ac", original_ac);// 原视频声音频道数-ac
                        doc.put("original_ab", original_ab + "");// 原视频声音声速-ab

                        doc.put("original_audio_profile", stream.getString("profile"));
                        doc.put("original_audio_duration", stream.getString("duration"));

                    }

                }

            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
                
        Iterator<String> itr = doc.keySet().iterator();
        while (itr.hasNext()) {
            String key = (String)itr.next();
            logger.info(key + ":" + doc.get(key));
        }
        
        
        /*System.out.println("video_info:->duration:" + doc.get("original_duration") + "\n bit_rite:"
                + doc.get("original_br") + "\n original_definition:" + doc.get("original_definition")
                + "\n original_tot_fr:" + doc.get("original_tot_fr") + "\n original_fps:" + doc.get("original_fps")
                + "\n original_encoding:" + doc.get("original_encoding") + "\n original_audioft:"
                + doc.get("original_audioft") + "\n original_ar:" + doc.get("original_ar") + "\n original_ac:"
                + doc.get("original_ac") + "\n original_ab:" + doc.get("original_ab"));*/
        
        return doc;
    }

    public static boolean process(String command, Writer out) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command);
            InputStreamReader rd = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(rd);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (out != null) {
                    out.write(line);
                    out.write('\n');
                }
            }
            process.destroy();
            br.close();
            rd.close();
            br = null;
            rd = null;
            process = null;
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public static final String replaceChinese(String s) {
        char[] chars = s.toCharArray();
        StringBuffer result = new StringBuffer("");

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] <= 255) {
                result.append(chars[i]);
            }
        }
        return result.toString();
    }

    /**
     * 解析视频,使用json格式：
     * 
     * @param file
     * @return 返回writer out: {}
     * @throws UnsupportedEncodingException
     */
    public static DOC analysisVideoByJsonFormat(final String file) {
        StringWriter writer = new StringWriter();
        String command = "/bin/bash " + Config.get("svndir") + "/WEB-INF/sh/videojson_info.sh " + file;

        System.out.println(command);
        /*
         * Command.run2(command, writer);
         * //process(command, writer);
         * String outduration = writer.toString().trim();
         */

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Command.run2(command, baos);
        String outduration = null;
        try {
            outduration = baos.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            outduration = baos.toString();
        }

        // outduration = replaceChinese(outduration);
        DOC doc = analysisVideoByJsonContent(outduration);
        if (doc == null || doc.size() == 0) {
            doc = analysisVideo(file);
        }
        return doc;
    }

    public static DOC upBr(int bitrate, int new_br, String new_fbl, DOC video_pool) {
        DOC doc = new DOC();
        String out_br = video_pool.get("out_br");// 码率
        String resolution = video_pool.get("resolution");// 分辨率
        StringBuffer new_out_br = new StringBuffer();
        StringBuffer new_resolution = new StringBuffer();
        // 更新码率,分辨率
        if (StringUtil.isFine(out_br)) {
            String[] brs = out_br.split(",");
            String[] resolutions = resolution.split(",");
            for (int i = 0; i < brs.length; i++) {
                if (bitrate == Integer.parseInt(brs[i])) {
                    brs[i] = new_br + "";
                    resolutions[i] = new_fbl;
                }
                new_out_br.append(brs[i]);
                new_resolution.append(resolutions[i]);
                if (i != brs.length - 1) {
                    new_out_br.append(",");
                    new_resolution.append(",");
                }
            }
            doc.put("out_br", new_out_br.toString());
            doc.put("resolution", new_resolution.toString());
        }
        return changeExt(doc, video_pool);
    }

    /**
     * 删除缓存文件专用方法,因164-169编码机器不通外网，临时使用
     * @return
     */
    public static boolean delCacheFile(String hostid, String video_pool_id) {
        String url = "http://v.polyv.net/uc/services/delcachefile?hostid=" + hostid + "&video_pool_id=" + video_pool_id;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        boolean flag = false;
        try {
            System.out.println(">>>>>del file url = " + url);
            client.executeMethod(method);
            System.out.println(method.getStatusLine());
            flag = true;
        } catch (HttpException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return flag;

    }

    /**
     * 上传视频到优酷:纯数据方式上传接口
     * @param partnerid YOUKU合作ID
     * @param catIds 视频分类
     * @param Filedata 视频数据
     */
    public static String postVideoToYOUKU(String title, String tag, String partnerid, String catIds, String Filedata) {
        if (!StringUtil.isFine(partnerid)) {
            partnerid = "XOA==";
        }
        if (!StringUtil.isFine(catIds)) {
            catIds = "106";// 106 其他
        }
        String url = "http://api.youku.com/upload/uploadPackage";
        if (title == null || title.length() < 2) {
            title = "标题太短";
        }
        if (tag == null || tag.length() < 2) {
            tag = "ovp";
        }
        String sourceType = "0";// 版权所有 0. 转载 1. 原创
        String publicType = "0";// 视频权限 0. 公开
        String memo = "";// 视频描述
        String folderIds = "";// 上传时加入专辑 多个专辑ID用逗号分隔
        String username = "dklich@163.com";// 上传的用户名
        String password = "ovpyuiop";// 上传的用户密码 用户密码如果为32位,按md5来验证,否则按照明码验证
        System.out.println(">>>>>>>youku>>username:" + username + ">>>>title:" + title + " tag:" + tag + " partnerid:"
                + partnerid);
        NameValuePair[] data = { new NameValuePair("partnerid", partnerid), new NameValuePair("title", title),
                new NameValuePair("tag", tag), new NameValuePair("catIds", catIds),
                new NameValuePair("sourceType", sourceType), new NameValuePair("publicType", publicType),
                new NameValuePair("memo", memo), new NameValuePair("folderIds", folderIds),
                new NameValuePair("username", username), new NameValuePair("password", password),
                new NameValuePair("Filedata", Filedata) };
        String response = PostHttp.methodPost(url, data);
        return response;
    }

    /**
     * 上传视频到土豆:纯数据方式上传接口
     * @param partnerid tudou合作ID
     * @param catIds 视频分类
     * @param Filedata 视频数据
     */
    public static String postVideoTUDOU(String title, String tags, String appKey, String channelId, String Filedata) {
        if (!StringUtil.isFine(appKey)) {
            appKey = "6e36a49aec370817";
        }
        if (!StringUtil.isFine(channelId)) {
            channelId = "99";// 99 原创
        }
        String url = "http://api.tudou.com/v3/gw?method=item.upload";
        if (title == null || title.length() < 2) {
            title = "标题太短";
        }
        if (tags == null || tags.length() < 2) {
            tags = "ovp";
        }
        String content = "";// 视频描述
        // String username = "dklich@163.com";//上传的用户名
        // String password = "ovpyuiop";//上传的用户密码
        NameValuePair[] data = { new NameValuePair("appKey", appKey), new NameValuePair("title", title),
                new NameValuePair("tags", tags), new NameValuePair("channelId", channelId),
                new NameValuePair("content", content) };
        String response = PostHttp.methodPost(url, data);
        return response;
    }

    public static void xorEn(File src, File dest, final byte SEED_CONST, final int position) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            byte[] bs = new byte[4096];
            int len = 0;
            int counterRead = 0;
            while ((len = fis.read(bs)) != -1) {
                for (int i = 0; i < len; i++) {
                    if (counterRead > position) {
                        bs[i] ^= SEED_CONST;
                    }
                    counterRead++;
                }
                fos.write(bs, 0, len);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                fos.close();
                fis.close();
            } catch (Exception e) {

            }
        }
    }

    public static int getTagPosition(String src) {
        int position = 0;
        try {
            FLVReader reader = new FLVReader(new File(src));
            ITag tag = null;
            int counter = 0;
            while (reader.hasMoreTags()) {
                counter++;
                if (counter > 4) {
                    break;
                }
                tag = reader.readTag();
                position += tag.getPreviousTagSize();
            }
            position = position + 9 + 4 + 4 + 4 + 4 - 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return position;
    }

    /**
     * 无音频的视频，少取一个tag
     * @param src
     * @return
     */
    public static int getTagPosition2(String src) {
        int position = 0;
        try {
            FLVReader reader = new FLVReader(new File(src));
            ITag tag = null;
            int counter = 0;
            while (reader.hasMoreTags()) {
                counter++;
                if (counter > 5) {
                    break;
                }
                tag = reader.readTag();
                System.out.println(tag.getDataType());
                position += tag.getPreviousTagSize();
            }
            position = position + 9 + 4 + 4 + 4 + 4 + 4 - 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return position;
    }

    public static int getMetaDataSize(String src) {
        int position = 0;
        try {
            FLVReader reader = new FLVReader(new File(src));
            ITag tag = null;
            int counter = 0;
            while (reader.hasMoreTags()) {
                counter++;
                if (counter > 4) {
                    break;
                }
                tag = reader.readTag();
                byte byte_type = tag.getDataType();
                int i = (int) byte_type;
                if (i == 18) {
                    position = tag.getBodySize();
                    break;
                }

            }
            System.out.println(">>>>>>>>>>>>>>>>>>>.position>>>>>" + position);
            return position;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return position;
    }

    /**
     * 需要加密
     * @param target_file_tmp
     * @param target_file
     * @param SEED
     * @param SEED_CONST
     * @return
     */
    public static int saveTargetFile(String target_file_tmp, String target_file, boolean SEED, final byte SEED_CONST,
            int position) {
        int result = 0;
        try {
            // 需要加密 position提取失败，则不做加密
            /*
             * if( SEED){
             * try {
             * target_file = target_file.replaceAll("\\.flv", ".plv");
             * if(position>0){
             * xorEn(new File(target_file_tmp), new File(target_file) , SEED_CONST,position);
             * }else{
             * FileUtil.copy(target_file_tmp, target_file, true);
             * }
             * } catch (Exception e) {
             * e.printStackTrace();
             * }
             * result = UploadService.uploadFile(target_file, target_file, "video/flv", true);
             * }else
             */ {
                // 不需加密,直接上传到dfs

                result = UploadService.uploadFile(target_file_tmp, target_file, "video/flv", true);
            }
            // 如果上传失败 不删除转码后的文件
            if (result == VideoStatus.video_uphdfs_fail) {

                String[] strs = target_file.split("/");
                String fileName = strs[strs.length - 1];
                target_file = "/data/htmlfile/" + fileName;
                String command = "/bin/bash " + Config.get("svndir") + "/WEB-INF/sh/video_copy.sh " + target_file_tmp
                        + " " + target_file;
                Command.run(command, null);
                System.out.println(" ::::::command copy file==" + command);

            } else {
                // FileUtil.unlink(target_file_tmp);
                // 上传完成不删除,待推送
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return result;
    }

    /**
     * 同步文件到主服务器，如果同局域网服务器做了nfs就不用rsync
     * @param dir
     * @param filename
     */
    public static void rsyncMp4(String target_file) {
        String path = target_file.split("video_target/")[0] + "video_target/";
        String command = "/bin/bash " + Config.get("svndir") + "/WEB-INF/sh/rsync_mp4.sh " + path + " " + target_file;
        Command.run(command, null);
    }

    public static int randomSeed() {
        int flag = (int) (Math.random() * 255);
        return flag;
    }

    public static void main(String[] args) throws Exception {

        // cleanWangSuByUrl("http://seg1.videocc.net/pdx/sl8da4jjbx/8/sl8da4jjbx842000bc1ac92fadaaae48_2.pdx");
        /*
         * String video_pool_id= "66d38b7c69c496dd9841be71ae3bdbf4";
         * String hostid = "6";
         * String path = LogicUtil.getFullTargetFilePath(video_pool_id, hostid,1);
         * System.out.println(path);
         */

        // String jsondata = "{ \"streams\": [ { \"index\": 0, \"codec_name\": \"h264\", \"codec_long_name\": \"H.264 / AVC / MPEG-4 AVC /
        // MPEG-4 part 10\", \"profile\": \"High\", \"codec_type\": \"video\", \"codec_time_base\": \"1/50\", \"codec_tag_string\":
        // \"avc1\", \"codec_tag\": \"0x31637661\", \"width\": 640, \"height\": 480, \"has_b_frames\": 1, \"sample_aspect_ratio\": \"0:1\",
        // \"display_aspect_ratio\": \"0:1\", \"pix_fmt\": \"yuv420p\", \"level\": 41, \"is_avc\": \"1\", \"nal_length_size\": \"4\",
        // \"r_frame_rate\": \"25/1\", \"avg_frame_rate\": \"25/1\", \"time_base\": \"1/25\", \"start_pts\": 0, \"start_time\":
        // \"0.000000\", \"duration_ts\": 116, \"duration\": \"4.640000\", \"bit_rate\": \"440706\", \"nb_frames\": \"116\", \"tags\": {
        // \"creation_time\": \"1970-01-01 00:00:00\", \"language\": \"und\", \"handler_name\": \"VideoHandler\" } } ], \"format\": {
        // \"filename\": \"/data/htmlfile/8l8v64820d2e1ba091200f2fc785710f_2.mp4\", \"nb_streams\": 1, \"format_name\":
        // \"mov,mp4,m4a,3gp,3g2,mj2\", \"format_long_name\": \"QuickTime / MOV\", \"start_time\": \"0.000000\", \"duration\": \"4.640000\",
        // \"size\": \"258133\", \"bit_rate\": \"445056\", \"tags\": { \"major_brand\": \"isom\", \"minor_version\": \"512\",
        // \"compatible_brands\": \"isomiso2avc1mp41\", \"creation_time\": \"1970-01-01 00:00:00\", \"encoder\": \"Lavf53.5.0\" } } }";
        // String jsondata="{ \"streams\": [ { \"index\": 0, \"codec_name\": \"mp3\", \"codec_long_name\": \"MP3 (MPEG audio layer 3)\",
        // \"codec_type\": \"audio\", \"codec_time_base\": \"1/44100\", \"codec_tag_string\": \"[0][0][0][0]\", \"codec_tag\": \"0x0000\",
        // \"sample_fmt\": \"s16\", \"sample_rate\": \"44100\", \"channels\": 2, \"bits_per_sample\": 0, \"r_frame_rate\": \"0/0\",
        // \"avg_frame_rate\": \"0/0\", \"time_base\": \"1/14112000\", \"start_pts\": 0, \"start_time\": \"0.000000\", \"duration_ts\":
        // 2119257252, \"duration\": \"150.174125\", \"bit_rate\": \"192000\" } ], \"format\": { \"filename\":
        // \"/data/htmlfile/s/video_source/sl8da4jjbx/d/sl8da4jjbx272a9ef9e5606c3bfa2c3d.mp3\", \"nb_streams\": 1, \"format_name\": \"mp3\",
        // \"format_long_name\": \"MP2/3 (MPEG audio layer 2/3)\", \"start_time\": \"0.000000\", \"duration\": \"150.174125\", \"size\":
        // \"3604179\", \"bit_rate\": \"192000\", \"tags\": { \"title\": \"Life In Technicolor\", \"artist\": \"酷玩乐队\", \"track\": \"1\",
        // \"album\": \"Viva La Vida CD1\", \"genre\": \"摇滚乐1¤7Rock'n'Roll〄1¤7,金属乐1¤7Metal Music〄1¤7\", \"TYER\": \"2008-11-22 0:00:00\" }
        // } }";
        // String jsondata="{ \"streams\": [ { \"index\": 0, \"codec_name\": \"h264\", \"codec_long_name\": \"H.264 / AVC / MPEG-4 AVC /
        // MPEG-4 part 10\", \"profile\": \"Baseline\", \"codec_type\": \"video\", \"codec_time_base\": \"1/60\", \"codec_tag_string\":
        // \"avc1\", \"codec_tag\": \"0x31637661\", \"width\": 1360, \"height\": 768, \"has_b_frames\": 0, \"sample_aspect_ratio\": \"1:1\",
        // \"display_aspect_ratio\": \"85:48\", \"pix_fmt\": \"yuv420p\", \"level\": 32, \"is_avc\": \"1\", \"nal_length_size\": \"4\",
        // \"r_frame_rate\": \"30/1\", \"avg_frame_rate\": \"316300000/10543323\", \"time_base\": \"1/30000\", \"start_pts\": 0,
        // \"start_time\": \"0.000000\", \"duration_ts\": 31629969, \"duration\": \"1054.332300\", \"bit_rate\": \"378736\", \"nb_frames\":
        // \"31630\", \"tags\": { \"creation_time\": \"2013-07-26 08:12:20\", \"language\": \"eng\", \"handler_name\": \"Mainconcept MP4
        // Video Media Handler\" } }, { \"index\": 1, \"codec_name\": \"aac\", \"codec_long_name\": \"AAC (Advanced Audio Coding)\",
        // \"codec_type\": \"audio\", \"codec_time_base\": \"1/44100\", \"codec_tag_string\": \"mp4a\", \"codec_tag\": \"0x6134706d\",
        // \"sample_fmt\": \"s16\", \"sample_rate\": \"44100\", \"channels\": 2, \"bits_per_sample\": 0, \"r_frame_rate\": \"0/0\",
        // \"avg_frame_rate\": \"0/0\", \"time_base\": \"1/44100\", \"start_pts\": 0, \"start_time\": \"0.000000\", \"duration_ts\":
        // 46497684, \"duration\": \"1054.369252\", \"bit_rate\": \"125664\", \"nb_frames\": \"45408\", \"tags\": { \"creation_time\":
        // \"2013-07-26 08:12:20\", \"language\": \"eng\", \"handler_name\": \"Mainconcept MP4 Sound Media Handler\" } } ], \"format\": {
        // \"filename\": \"/data/htmlfile/e/video_source/e464bd6e93/1/e464bd6e93bbf7b7b3a081f8988288a1.mp4\", \"nb_streams\": 2,
        // \"format_name\": \"mov,mp4,m4a,3gp,3g2,mj2\", \"format_long_name\": \"QuickTime / MOV\", \"start_time\": \"0.000000\",
        // \"duration\": \"1054.371700\", \"size\": \"66844274\", \"bit_rate\": \"507178\", \"tags\": { \"major_brand\": \"mp42\",
        // \"minor_version\": \"0\", \"compatible_brands\": \"isommp42\", \"creation_time\": \"2013-07-26 08:12:19\", \"artist\": \"\",
        // \"description\": \"\", \"title\": \"36Revit室外石桌族的制作和嵌套族加深运用以及族参数公式的添加和运用备份2\" } } }";
        // analysisVideoByJsonContent(jsondata);
        // analysisVideoByJsonFormat("/home/henry/abc");
        // extractOutLineFromPDF("/data/svn/demo.polyv.net/sean.pdf");

        /*
         * Map<String,String> videoInfo = new HashMap<String,String>();
         * videoInfo.put("advID", "7bb5389e05ce48448db8");
         * videoInfo.put("matterurl", "/html/adv/video/3/3BGEYCaPIV/thumb/da4e0151aee44139a95f99120509c0d9.flv");
         * String xml = LogicUtil.getAdvVideoCDNXml(videoInfo);
         * System.out.println("============================================================");
         * System.out.println(xml);
         * 
         * System.out.println("============================================================");
         */

    }

    // a integer to xx:xx:xx
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else retStr = "" + i;
        return retStr;
    }

    // 使用漫道发送api
    public static int sendSmsMD(String mobile, String message) {

        String sn = "SDK-WSS-010-09259";
        String pwd = "c-dd08-[";
        Client client;
        try {
            client = new Client(sn, pwd);
            // 短信发送
            // String content=URLEncoder.encode("2889 (保利威视注册验证码，两分钟内有效)【保利威视】", "utf8");
            String result_mt = client.mdsmssend(mobile, message, "", "", "", "");
            long result = Long.parseLong(result_mt);
            logger.info("send sms mobile:" + mobile + " return:" + result_mt);
            if (result > 0) {
                return 1;
            } else {
                return -1;
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return -1;

    }

    private int cachesize = 1024;
    private Deflater compresser = new Deflater();

    public byte[] compressBytes(byte input[]) {
        compresser.reset();
        compresser.setInput(input);
        compresser.finish();
        byte output[] = new byte[0];
        ByteArrayOutputStream o = new ByteArrayOutputStream(input.length);
        try {
            byte[] buf = new byte[cachesize];
            int got;
            while (!compresser.finished()) {
                got = compresser.deflate(buf);
                o.write(buf, 0, got);
            }
            output = o.toByteArray();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return output;
    }

    public static String appendSpeedModeSuffix(String filename, String speedMode) {
        if (speedMode.equals("15x")) {
            return filename + "_15x";
        }
        if (speedMode.equals("20x")) {
            return filename + "_20x";
        }
        if (speedMode.equals("125x")) {
            return filename + "_125x";
        }
        return filename;
    }

    /**
     * 从groupname获取切片服务器源站域名
     * @param groupname
     * @return domain
     */
    public static String getSegmentDomainByGroupName(String groupname) {
        String seg1domain = "segment.polyv.net";
        String seg4domain = "ws4s.videocc.net";
        int groupNumber = Integer.parseInt(groupname.replaceAll("group", ""));
        //
        if (groupNumber == 4) {
            return seg4domain;
        }
        if (groupNumber < 8) {
            return seg1domain;
        }
        return "ws" + groupNumber + ".videocc.net";

    }

    public static void fixMp4(String vid, int df) {
        String video_pool_id = vid.split("_")[0];
        String hostid = vid.split("_")[1];
        Video_pool_db vpdb = new Video_pool_db(hostid);
        DOC doc = vpdb.getOne(video_pool_id);
        if (doc == null || doc.geti("seed") == 1) {
            return;
        }

        String mp4link = doc.get("mp4_link" + df);
        String src = LogicUtil.getTargetFilePath(mp4link, hostid);
        String filePath = dfsService.getFilePath(src, src, "");

        if (!StringUtil.isFine(filePath)) {
            logger.info("fixMp4:" + vid + " br: " + df);
            LogicUtil.makeMp4(video_pool_id, hostid, df);
        }
    }

    public static void makeMp4(String video_pool_id, String hostid, int df) {
        logger.info("make mp4:" + video_pool_id + " df:" + df);
        Video_pool_db vpdb = new Video_pool_db(hostid);
        DOC video_pool = vpdb.getOne(video_pool_id);

        String basefileurl = LogicUtil.getTargetFilePath(video_pool.get("swf_link" + df), hostid);
        String mp4link = video_pool.get("mp4_link" + df);
        if (!StringUtil.isFine(mp4link)) {
            mp4link = video_pool.get("swf_link" + df).replaceAll("flv", "mp4");
        }
        String target_basefileurl = LogicUtil.getTargetFilePath(mp4link, hostid);

        // String target_basefileurl = basefileurl.replaceAll("\\.flv", ".mp4");
        if (!FileUtil.exists(basefileurl)) {
            UploadService.getVideo(basefileurl);
        }
        // group1用wget下载
        if (!FileUtil.exists(basefileurl)) {
            String filepath = dfsService.getFilePath(basefileurl, basefileurl, "");
            WgetUtil.wgetFile(filepath, basefileurl);

        }

        if (FileUtil.getFileSize(basefileurl) == 0) {
            logger.error(" flv to mp4 download failed :::" + basefileurl);

        } else {
            logger.info("check " + target_basefileurl);
            // if (!FileUtil.exists(target_basefileurl)) {
            if (FileUtil.getFileSize(target_basefileurl) == 0 || !FileUtil.exists(target_basefileurl)) {
                try {
                    // String mp4tempfile = "/tmp/formp4_" + video_pool_id + ".mp4";
                    /*
                     * String ffmpegstring = "/data/ffmpeg.3.0.2/bin/ffmpeg -i " + basefileurl
                     * + " -movflags faststart -vcodec copy -acodec copy -y " + target_basefileurl + " 2>/dev/null";
                     * 
                     * logger.info("make mp4:" + ffmpegstring);
                     */

                    String sh = Config.get("svndir") + "WEB-INF/sh/makeMp4.sh";

                    /*
                     * CommandLine cmdLine = CommandLine.parse(ffmpegstring);
                     * DefaultExecutor executor = new DefaultExecutor();
                     * executor.setExitValue(0);
                     * ExecuteWatchdog watchdog = new ExecuteWatchdog(3600 * 1000);
                     * executor.setWatchdog(watchdog);
                     * int exitValue = executor.execute(cmdLine);
                     * logger.info("run exitvalue:" + exitValue);
                     */
                    Runtime rt = Runtime.getRuntime();
                    String cmd = "/bin/bash " + sh + " " + basefileurl + " " + target_basefileurl;
                    Process proc = rt.exec(cmd);
                    int exitVal = proc.waitFor();
                    System.out.println(cmd + " Process exitValue: " + exitVal);

                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            logger.info(
                    "target_basefileurl:" + target_basefileurl + " size:" + FileUtil.getFileSize(target_basefileurl));
            if (FileUtil.getFileSize(target_basefileurl) > 0) {

                int result = UploadService.uploadFile(target_basefileurl, target_basefileurl, "video/flv", true);

            }
            FileUtil.unlink(target_basefileurl);
            FileUtil.unixPath(basefileurl);

        }

    }

    public static void cleanUserHlslevel(String userid) {
        Util.getUrl("http://hls.videocc.net/cachecleaner/" + userid + "/clean_preview");
        Util.getUrl("http://hls.videocc.net/cachecleaner/" + userid + "/clean_hlslevel");
        Util.getUrl("http://hls.videocc.net/cachecleaner/user/" + userid);

    }

    public static void cleanVideoHlslevel(String videoPoolId) {
        Util.getUrl("http://hls.videocc.net/cachecleaner/" + videoPoolId + "/clean_video_hlslevel");
        Util.getUrl("http://hls.videocc.net/cachecleaner/video/" + videoPoolId);

    }

    /**
     * 获取预览的id
     * @param keyStr
     * @param message
     * @return
     */
    public static String getPreviewId(String keyStr, String message) {
        String key = StringUtils.isNotBlank(keyStr) ? keyStr : "lpmkenjibhuvgycftxdrzsoawq0126783459";
        String alphabet = "abcdofghijklnmepqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder(
                String.valueOf(alphabet.charAt((int) (Math.random() * alphabet.length()))));
        String temp;
        int index;
        for (int i = 0; i < message.length(); i++) {
            temp = String.valueOf(message.charAt(i));
            index = alphabet.indexOf(temp);
            if (index == -1) {
                code.append(temp);
            } else {
                code.append(String.valueOf(key.charAt(index)));
            }
        }
        return code.toString();
    }
    
    private static String signAndBase64Encode(byte[] data, String apikey) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");//如果使用sha256则参数是：HmacSHA256
        mac.init(new SecretKeySpec(apikey.getBytes(), "HmacSHA256"));//如果使用sha256则参数是：HmacSHA256
        byte[] signature = mac.doFinal(data);
        return new String(Base64.encodeBase64(signature));
    }
    public static boolean prefetch(List<String> urls){
        try {
            //header
            Map<String, String> header = new HashMap<String, String>();
            header.put("Accept", "application/json");
            header.put("Content-Type", "application/json");
            //username
            String userName = "sean@polyv.net";
            //apikey
            String apiKey = "rusELRdhQTw6ZCQgwkY3";
            
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<urls.size();i++) {
                if(i<urls.size()-1) {
                    sb.append("\"" + urls.get(i) + "\",");
                }else {
                    sb.append("\"" + urls.get(i) + "\"");
                }
                
            }
            //body  json
            String body = "{\"urls\":[" +  sb.toString() + "]}"; 
            
            String fullHttpUrl = "https://open.chinanetcenter.com/ccm/fetch/ItemIdReceiver";

            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod(fullHttpUrl);
            
            
            postMethod.setRequestBody(body);
            
            Date date = new Date();
            SimpleDateFormat rfc822DateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            rfc822DateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
            String dateString = rfc822DateFormat.format(date);
            
            String signature = signAndBase64Encode(dateString.getBytes(), apiKey);
            String userAndPwd = userName + ":" + signature;
            
            String authoriztion = new String(Base64.encodeBase64(userAndPwd.getBytes("UTF-8")));
            
            postMethod.addRequestHeader("Date", dateString);
            postMethod.addRequestHeader("Content-Type", " application/json");
            postMethod.addRequestHeader("Authorization", "Basic " + authoriztion);
            int statusCode  = httpClient.executeMethod(postMethod);
           
            if(statusCode!=200) {
                logger.info("fetch urls failed return http code:" + statusCode);
                return false;
            }
            String response = postMethod.getResponseBodyAsString();
            
            DOC responseDoc = JsonUtil.strToDOC(response);
            logger.info(responseDoc);                

            postMethod.releaseConnection();

            if(responseDoc.geti("Code") == 1) {
                //logger.info(responseDoc.get("Message"));
                return true;
            }else {
                //logger.info(responseDoc.get("Message"));
                return false;
            }
            
        }catch(Exception e) {
            e.printStackTrace();
        }
       
        
        return false;
    }
}
