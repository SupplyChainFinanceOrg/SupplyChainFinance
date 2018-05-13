package com.jeesite.modules.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.pdf.PdfReader;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

public class BestSignDemo {

    private String developerId = null;
    private String pem = null;
    private String host = null;

    public static BestSignDemo getInstance(String developerId, String pem, String host) {
        return new BestSignDemo(developerId, pem, host);
    }

    private BestSignDemo(String developerId, String pem, String host) {
        this.developerId = developerId;
        this.pem = pem;
        this.host = host;
    }
    /**
     * 注册个人用户
     * @param account
     * @param mobile
     * @param name
     * @param userType
     * @param mail
     * @param path
     * @return
     * @throws Exception
     */
    public JSONObject userReg(final String account, final String name, final String userType) throws Exception {
        final String path = "/user/reg";

        @SuppressWarnings("serial")
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                //用户号
                put("account", account);
                //用户名称（个人名称，企业名称）
                put("name", name);
                //注册用户类型（1表示个人用户、2表示企业用户）
                put("userType", userType);
                
            }
        };

        String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        String dataString  = JSONObject.toJSONString(data);
        Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
        String resString = (String) res.get("responseData");

        return parseExecutorResult(resString);
    }
    
    /**
     * 设置个人用户证件信息
     * @param account
     * @param identity
     * @param name
     * @return
     * @throws Exception
     */
    public JSONObject personalCredential(final String account,final String identity, final String name) throws Exception{
    	final String path = "/user/setPersonalCredential";
    	@SuppressWarnings("serial")
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                //开发者Account
                put("account", account);
                //注册用户手机号码
                put("identity", identity);
                //用户名称（个人名称，企业名称）
                put("name", name);
            }
        };

        String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        String dataString  = JSONObject.toJSONString(data);
        Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
        String resString = (String) res.get("responseData");

        return parseExecutorResult(resString);
    	
    }
    
    /**
     * 设置企业用户证件信息
     * @param account
     * @param regCode
     * @param orgCode
     * @param taxCode
     * @param name
     * @param legalPerson
     * @param legalPersonIdentity
     * @param legalPersonIdentityType
     * @param legalPersonMobile
     * @param contactMobile
     * @return
     * @throws Exception
     */
    public JSONObject enterpriseCredential(final String account,final String regCode, final String orgCode,final String taxCode,final String name,final String legalPerson,final String legalPersonIdentity,final String legalPersonIdentityType,final String legalPersonMobile,final String contactMobile) throws Exception{
    	final String path = "/user/setEnterpriseCredential";
    	@SuppressWarnings("serial")
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                //开发者Account
                put("account", account);
                put("regCode", regCode);
                put("orgCode", orgCode);
                put("taxCode", taxCode);
                put("name", name);
                put("legalPerson", legalPerson);
                put("legalPersonIdentity", legalPersonIdentity);
                put("legalPersonIdentityType", legalPersonIdentityType);
                put("legalPersonMobile", legalPersonMobile);
                put("contactMobile", contactMobile);
            }
        };

        String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        String dataString  = JSONObject.toJSONString(data);
        Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
        String resString = (String) res.get("responseData");

        return parseExecutorResult(resString);
    	
    }
    
    /**
     * 查询个人用户证件信息
     * @param account
     * @param mobile
     * @param name
     * @param userType
     * @param mail
     * @return
     * @throws Exception
     */
    public JSONObject getPersonalCredential(final String account) throws Exception {
        final String path = "/user/getPersonalCredential";

        @SuppressWarnings("serial")
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                //用户
                put("account", account);
            }
        };

        String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        String dataString  = JSONObject.toJSONString(data);
        Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
        String resString = (String) res.get("responseData");

        return parseExecutorResult(resString);
    }
    
    /**
     * 查询企业用户证件信息
     * @param account
     * @return
     * @throws Exception
     */
    public JSONObject getEnterpriseCredential(final String account) throws Exception {
        final String path = "/user/getEnterpriseCredential";

        @SuppressWarnings("serial")
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                //用户
                put("account", account);
            }
        };

        String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        String dataString  = JSONObject.toJSONString(data);
        Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
        String resString = (String) res.get("responseData");

        return parseExecutorResult(resString);
    }
    
    
    /**
     * 申请数字证书
     * @param account
     * @return
     * @throws Exception
     */
    public JSONObject applyCert(final String account) throws Exception {
        final String path = "/user/applyCert";

        @SuppressWarnings("serial")
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                //用户
                put("account", account);
            }
        };

        String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        String dataString  = JSONObject.toJSONString(data);
        Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
        String resString = (String) res.get("responseData");

        return parseExecutorResult(resString);
    }
    
    /**
     * 获取证书编号
     * @param account
     * @param certType
     * @return
     * @throws Exception
     */
    public JSONObject getCert(final String account, final String certType) throws Exception {
        final String path = "/user/getCert";

        @SuppressWarnings("serial")
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                //用户
                put("account", account);
                //证书类型
                put("certType", certType);
            }
        };

        String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        String dataString  = JSONObject.toJSONString(data);
        Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
        String resString = (String) res.get("responseData");

        return parseExecutorResult(resString);
    }
    
    /**
     * 获取证书详细信息
     * @param account
     * @param certType
     * @return
     * @throws Exception
     */
    public JSONObject certificateInfo(final String account, final String certId) throws Exception {
        final String path = "/user/cert/info";

        @SuppressWarnings("serial")
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                //用户
                put("account", account);
                //证书编号
                put("certId", certId);
            }
        };

        String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        String dataString  = JSONObject.toJSONString(data);
        Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
        String resString = (String) res.get("responseData");

        return parseExecutorResult(resString);
    }
    
    /**
     * 创建用户签名／印章图片
     * @param account
     * @param certType
     * @return
     * @throws Exception
     */
    public JSONObject createASignatureSeal(final String account) throws Exception {
        final String path = "/signatureImage/user/create";

        @SuppressWarnings("serial")
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                //用户
                put("account", account);
                
            }
        };

        String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        String dataString  = JSONObject.toJSONString(data);
        Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
        String resString = (String) res.get("responseData");

        return parseExecutorResult(resString);
    }
    
    /**
     * 上传用户签名/印章图片
     * @param account
     * @return
     * @throws Exception
     */
    public JSONObject uploadAsignatureSeal(final String account, final String imgFile) throws Exception {
        final String path = "/signatureImage/user/upload";
        //图片Base64编码
        final String imageData = Img2Base64Util.getImgStr(imgFile);
        @SuppressWarnings("serial")
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                //用户
                put("account", account);
                //图片文件内容
                put("imageData", imageData);
            }
        };

        String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        String dataString  = JSONObject.toJSONString(data);
        Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
        String resString = (String) res.get("responseData");

        return parseExecutorResult(resString);
    }
    
    
    /**
	 * 模块 2.3
	 * 下载用户签名/印章图片
	 * @throws Exception
	 */

    public byte[] downloadSignatureImage(final String account, final String imageName) throws Exception {
        final String path = "/signatureImage/user/download";

        String url = host + getUrlByRsa(account, imageName,null, path); // rsa的话 pem为私钥
        url = url + "&account=" + account + "&imageName=" + URLEncoder.encode(imageName, "UTF-8");
        System.out.println("url:" + url);

        Map<String, String> headers = new HashMap<String, String>();
        Map<String, Object> res  = HttpSender.getResponseBytes("GET", url, "", headers);
        byte[] data = (byte[]) res.get("responseData");
        return data;
    }
    
    
    /**
     * 上传合同文件
     * @param account
     * @param certType
     * @return
     * @throws Exception
     */
    public JSONObject uploadContract(final String account, final String fdata) throws Exception {
        final String path = "/storage/upload";
        
        
        FileInputStream file = new FileInputStream(fdata);
        
        byte[] bdata = IOUtils.toByteArray(file); 
        //获取文件Base64编码
        final String fdataNew =Base64.encodeBase64String(bdata);
        //获取文件MD5值
        final String fmd5New = DigestUtils.md5Hex(bdata);
        
        
        File fileType = new File(fdata);
        
//        System.out.println("Mime Type of " + fileType.getName() + " is " +  new MimetypesFileTypeMap().getContentType(fileType));
        //获取文件名称
        final String fileName = fileType.getName();
        //获取文件类型
        final String prefix = fileName.substring(fileName.lastIndexOf(".")+1);
//        final String fileTypeNew = new MimetypesFileTypeMap().getContentType(fileType);
        
        
        PdfReader reader = new PdfReader(fdata);
        //获取文件页数
        final Integer pagenum = reader.getNumberOfPages();
        
        System.out.println(account + fdataNew + fmd5New + fileName + prefix + pagenum);
        
        @SuppressWarnings("serial")
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                //用户
                put("account", account);
                //文件数据，base64编码
                put("fdata", fdataNew);
                //文件md5值
                put("fmd5", fmd5New);
                //文件类型
                put("ftype", prefix);
                //文件名
                put("fname", fileName);
                //文件页数
                put("fpages", pagenum);
            }
        };

        String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        String dataString  = JSONObject.toJSONString(data);
        Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
        String resString = (String) res.get("responseData");

        return parseExecutorResult(resString);
    }
    
    
    /**
     * 为PDF文件添加元素
     * @param account
     * @param fid
     * @param jsonArray
     * @return
     * @throws Exception
     */
     public JSONObject addPDFElements(final String account, final String fid, final JSONArray jsonArray) throws Exception {
         final String path = "/storage/addPDFElements";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //用户平台开发者
                 put("account", account);
                 //源文件编号
                 put("fid", fid);
                 //要添加的元素集合
                 put("elements", jsonArray);
                 
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);
     }
     
     
     /**
      * 
      * @param account
      * @param fid
      * @param ftype
      * @return
      * @throws Exception
      */
     public JSONObject fileConversion(final String account, final String fid, final String ftype) throws Exception {
         final String path = "/file/convert";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //用户平台开发者
                 put("account", account);
                 //源文件编号
                 put("fid", fid);
                 //要添加的元素集合
                 put("ftype", ftype);
                 
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     /**
      * 获取文件meta信息
      * @param account
      * @param fid
      * @return
      * @throws Exception
      */
     public JSONObject getMetainfo(final String account, final String fid) throws Exception {
         final String path = "/file/meta";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //用户平台开发者
                 put("account", account);
                 //源文件编号
                 put("fid", fid);
                 
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     /**
      * 创建合同
      * @param account
      * @param fid
      * @return
      * @throws Exception
      */
     public JSONObject createBargain(final String account, final String fid, final String expireTime, final String title, final String description) throws Exception {
         final String path = "/contract/create";
         
         //转换unix时间戳，获取系统时间
//         Date date = new SimpleDateFormat("yyyy/MM/dd").parse(expireTime);
//         System.out.println("date:" + date);
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //用户平台开发者
                 put("account", account);
                 //源文件编号
                 put("fid", fid);
                 //合同过期时间
                 put("expireTime", expireTime);
                 //合同标题
                 put("title", title);
                 //合同描述
                 put("description", description);
                 
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     /**
      * 5.2、添加合同签署者
      * @param contractId
      * @param signer
      * @return
      * @throws Exception
      */
     public JSONObject addSigner(final String contractId, final String signer) throws Exception {
         final String path = "/contract/addSigner";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //合同编号
                 put("contractId", contractId);
                 //要添加的签署者account
                 put("signer", signer);
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     /**
      * 5.3、批量添加合同签署者
      * @param contractId
      * @param jsonArray
      * @return
      * @throws Exception
      */
     public JSONObject addSignersList(final String contractId, final JSONArray jsonArray) throws Exception {
         final String path = "/contract/addSigners";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //合同编号
                 put("contractId", contractId);
                 //要添加的签署者account
                 put("signers", jsonArray);
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     
     /**
      * 5.4、获取合同信息
      * @param contractId
      * @return
      * @throws Exception
      */
     public JSONObject getContractInfo(final String contractId) throws Exception {
         final String path = "/contract/getInfo";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //合同编号
                 put("contractId", contractId);
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     /**
      * 5.5、设置合同签署参数
      * @param contractId
      * @return
      * @throws Exception
      */
     public JSONObject setSignerConfig(
    		 final String contractId,
    		 final String account,
    		 final JSONArray signaturePositionsJsonArray,
    		 final String returnUrl,
    		 final String vcodeMobile,
    		 final String isDrawSignatureImage,
    		 final String signatureImageName,
    		 final String certType
    		 ) throws Exception {
         final String path = "/contract/setSignerConfig";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //合同编号
                 put("contractId", contractId);
                 //签署者
                 put("account", account);
                 //坐标
                 put("signaturePositions", signaturePositionsJsonArray);
                 //手动签署时，当用户签署完成后，指定回跳的页面地址	
                 put("returnUrl", returnUrl);
                 //手动签署时指定接收手机验证码的手机号，如果需要采用手动签署页面，则此项必填	
                 put("vcodeMobile", vcodeMobile);
                 /*
                  * 手动签署时是否手绘签名。取值0/1。
                  * 0手动签署时点击签名图片不会触发手写面板。
                  * 1手动签署时可以点击签名图片触发手写面板。
                  */
                 put("isDrawSignatureImage", isDrawSignatureImage);
                 //签名/印章图片。取值default或者指定的印章名称。（手动签都用默认的）
                 put("signatureImageName", signatureImageName);
                 //指定签署时用的证书类型，此参数必须带上“1.6、申请数字证书”接口返回的certType
                 put("certType", certType);
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     /**
      * 5.6、获取合同签署参数
      * @param contractId
      * @param account
      * @return
      * @throws Exception
      */
     public JSONObject getSignerConfig(
    		 final String contractId,
    		 final String account
    		 ) throws Exception {
         final String path = "/contract/getSignerConfig";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //合同编号
                 put("contractId", contractId);
                 //签署者
                 put("account", account);
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     
     /**
      * 5.7、上传合同签署照片
      * @param contractId
      * @param account
      * @param imagedata
      * @return
      * @throws Exception
      */
//     public JSONObject uploadSignPhoto(
//    		 final String contractId,
//    		 final String account,
//    		 final String imagedata
//    		 ) throws Exception {
//         final String path = "/contract/uploadSignPhoto";
//         
//         FileInputStream file = new FileInputStream(imagedata);
//         
//         byte[] bdata = IOUtils.toByteArray(file); 
//         //获取文件Base64编码
//         final String fdataNew =Base64.encodeBase64String(bdata);
//         
//         @SuppressWarnings("serial")
//         Map<String, Object> data = new HashMap<String, Object>() {
//             {
//                 //合同编号
//                 put("contractId", contractId);
//                 //签署者
//                 put("account", account);
//                 //文件数据，base64编码
//                 put("imagedata", fdataNew);
//             }
//         };
//
//         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥
//
//         Map<String, String> headers = new HashMap<String, String>();
//         headers.put("Content-Type", "application/json");
//         String dataString  = JSONObject.toJSONString(data);
//         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
//         String resString = (String) res.get("responseData");
//
//         return parseExecutorResult(resString);
//
//     }
     
     /**
      * 5.8、签署合同（用户必须有自己的数字证书）
      * @param contractId
      * @param signer
      * @param jsonArray
      * @return
      * @throws Exception
      */
     public JSONObject signContract(
    		 final String contractId,
    		 final String signer,
    		 final JSONArray jsonArray
    		 ) throws Exception {
         final String path = "/contract/sign/cert";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //合同编号
                 put("contractId", contractId);
                 //签署者
                 put("signer", signer);
                 //位置
                 put("signaturePositions", jsonArray);
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     
     /**
      * 
      * @param contractId
      * @return
      * @throws Exception
      */
     public JSONObject getSignerStatus(
    		 final String contractId
    		 ) throws Exception {
         final String path = "/contract/getSignerStatus";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //合同编号
                 put("contractId", contractId);
                 
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     /**
      * 5.10、结束合同
      * @param contractId
      * @return
      * @throws Exception
      */
     public JSONObject overContract(
    		 final String contractId
    		 ) throws Exception {
         final String path = "/contract/finish";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //合同编号
                 put("contractId", contractId);
                 
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     /**
      * 5.11、拒绝签署
      * @param contractId
      * @param signer
      * @param refuseReason
      * @return
      * @throws Exception
      */
     public JSONObject refuse(
    		 final String contractId,
    		 final String signer,
    		 final String refuseReason
    		 ) throws Exception {
         final String path = "/contract/refuse";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //合同编号
                 put("contractId", contractId);
                 //签署者
                 put("signer", signer);
                 //拒绝原因
                 put("refuseReason", refuseReason);
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     /**
      * 5.12、获取下载合同链接
      * @param contractId
      * @return
      * @throws Exception
      */
     public JSONObject getDownloadURLs(
    		 final String contractId
    		 ) throws Exception {
         final String path = "/contract/getDownloadURLs";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //合同编号
                 put("contractId", contractId);
                 
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     /**
      * 5.13、获取合同手动签署页URL
      * @param contractId
      * @param account
      * @param quality
      * @param expireAt
      * @return
      * @throws Exception
      */
     public JSONObject getSignURL(
    		 final String contractId,
    		 final String account,
    		 final String quality,
    		 final String expireAt
    		 ) throws Exception {
         final String path = "/contract/getSignURL";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //合同编号
                 put("contractId", contractId);
                 //指定给哪个用户看
                 put("account", account);
                 //预览图片质量，0-100，数值越高越清晰。
                 put("quality", quality);
                 /*
         		 * 签署链接的过期时间，unix时间戳格式（秒数）。
         		 * 超过此时间则无法打开链接页面，需要获取新的签署合同链接。
         		 * 注：并不是合同的可签署到期时间，只是此签署链接的有效期。
         		 */
                 put("expireAt", expireAt);
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     /**
      * 5.14、获取预览页URL
      * @param contractId
      * @param account
      * @param quality
      * @param expireAt
      * @return
      * @throws Exception
      */
     public JSONObject getPreviewURL(
    		 final String contractId,
    		 final String account,
    		 final String quality,
    		 final String expireAt
    		 ) throws Exception {
         final String path = "/contract/getPreviewURL";
         
         @SuppressWarnings("serial")
         Map<String, Object> data = new HashMap<String, Object>() {
             {
                 //合同编号
                 put("contractId", contractId);
                 //指定给哪个用户看
                 put("account", account);
                 //预览图片质量，0-100，数值越高越清晰。
                 put("quality", quality);
                 /*
         		 * 签署链接的过期时间，unix时间戳格式（秒数）。
         		 * 超过此时间则无法打开链接页面，需要获取新的签署合同链接。
         		 * 注：并不是合同的可签署到期时间，只是此签署链接的有效期。
         		 */
                 put("expireAt", expireAt);
             }
         };

         String url = host + getPostUrlByRsa(data, path); // rsa的话 pem为私钥

         Map<String, String> headers = new HashMap<String, String>();
         headers.put("Content-Type", "application/json");
         String dataString  = JSONObject.toJSONString(data);
         Map<String, Object> res  = HttpSender.getResponseString("POST", url, dataString, headers);
         String resString = (String) res.get("responseData");

         return parseExecutorResult(resString);

     }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
    

    /**
     * 仅限下载用户签名图片，其他接口请自行实现
     * @param account
     * @param imageName
     * @param data
     * @param path
     * @return
     * @throws Exception
     */
    private String getUrlByRsa(String account, String imageName, Map<String, Object> data, String path) throws Exception {

        String randomStr = Utils.rand(1000, 9999) + "";
        String unix = Long.toString(System.currentTimeMillis());
        String rtick = unix + randomStr;

        String dataMd5 = "";
        if(data != null) {
            String jsonData = JSON.toJSONString(data);
            dataMd5 = EncodeUtils.md5(jsonData.getBytes("UTF-8"));
        }
        String sign = String.format("account=%sdeveloperId=%simageName=%srtick=%ssignType=rsa/openapi/v3%s/%s", account,developerId, imageName,rtick, path, dataMd5); // 生成签名字符串
        System.out.println(sign);

        String signDataString = this.getSignData(sign);
        String signData = Base64.encodeBase64String(EncodeUtils.rsaSign(signDataString.getBytes("UTF-8"), pem));

        signData = URLEncoder.encode(signData, "UTF-8");
        path = path + "/?developerId=" + developerId + "&rtick=" + rtick + "&sign=" + signData + "&signType=rsa";
        System.out.println(path);
        return path;
    }

    private String getPostUrlByRsa(Map<String, Object> data, String path) throws Exception {

        String randomStr = Utils.rand(1000, 9999) + "";
        String unix = Long.toString(System.currentTimeMillis());
        String rtick = unix + randomStr;

        String jsonData = JSON.toJSONString(data);
        String dataMd5 = EncodeUtils.md5(jsonData.getBytes("UTF-8"));
        String sign = String.format("developerId=%srtick=%ssignType=rsa/openapi/v3%s/%s", developerId, rtick, path, dataMd5); // 生成签名字符串
        System.out.println(sign);

        String signDataString = this.getSignData(sign);
        String signData = Base64.encodeBase64String(EncodeUtils.rsaSign(signDataString.getBytes("UTF-8"), pem));

        signData = URLEncoder.encode(signData, "UTF-8");
        path = path + "/?developerId=" + developerId + "&rtick=" + rtick + "&sign=" + signData + "&signType=rsa";
        System.out.println(path);
        return path;
    }

    private JSONObject parseExecutorResult(String executorResult) {
        if (StringUtils.isBlank(executorResult)) {
            return null;
        }
        return JSON.parseObject(executorResult);
    }

    private String getSignData(final String... args) {
        StringBuilder builder = new StringBuilder();
        int len = args.length;
        for (int i = 0; i < args.length; i++) {
            builder.append(Utils.convertToUtf8(args[i]));
            if (i < len - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

}
