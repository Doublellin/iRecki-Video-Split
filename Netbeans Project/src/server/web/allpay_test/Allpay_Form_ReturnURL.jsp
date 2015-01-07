<%-- 
    Document   : Allpay_Form_ReturnURL
    Created on : 2014/11/26, 下午 04:58:56
    Author     : user
--%>

<%@ page  contentType="text/html" pageEncoding="UTF-8" import = "java.util.*" import ="java.math.BigInteger" import ="java.security.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.io.*" %>

<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="javax.naming.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%

        System.out.println("ok");
//
//        //hashkey
//        String testHashKey = "IJjdx62rRITB54jK";
//        //hashiv
//        String testHashIv = "n1mzdG1DeYiMIcR0";
//
//        //一般回傳參數 //對應到說明書的 7.付款結果通知
//        String CheckMacValue = new String(request.getParameter("CheckMacValue").getBytes("ISO-8859-1"), "utf-8");
//
//        String MerchantID = new String(request.getParameter("MerchantID").getBytes("ISO-8859-1"), "utf-8");
//        String MerchantTradeNo = new String(request.getParameter("MerchantTradeNo").getBytes("ISO-8859-1"), "utf-8");
//        String RtnCode = new String(request.getParameter("RtnCode").getBytes("ISO-8859-1"), "utf-8");
//        String RtnMsg = new String(request.getParameter("RtnMsg").getBytes("ISO-8859-1"), "utf-8");
//        String TradeNo = new String(request.getParameter("TradeNo").getBytes("ISO-8859-1"), "utf-8");
//        String TradeAmt = new String(request.getParameter("TradeAmt").getBytes("ISO-8859-1"), "utf-8");
//        String PaymentDate = new String(request.getParameter("PaymentDate").getBytes("ISO-8859-1"), "utf-8");
//        String PaymentType = new String(request.getParameter("PaymentType").getBytes("ISO-8859-1"), "utf-8");
//        String PaymentTypeChargeFee = new String(request.getParameter("PaymentTypeChargeFee").getBytes("ISO-8859-1"), "utf-8");
//        String TradeDate = new String(request.getParameter("TradeDate").getBytes("ISO-8859-1"), "utf-8");
//        String SimulatePaid = new String(request.getParameter("SimulatePaid").getBytes("ISO-8859-1"), "utf-8");
//
//        //照ksort排序
//        String str = "MerchantID=" + MerchantID
//                + "&MerchantTradeNo=" + MerchantTradeNo
//                + "&RtnCode=" + RtnCode
//                + "&RtnMsg=" + RtnMsg
//                + "&TradeNo=" + TradeNo
//                + "&TradeAmt=" + TradeAmt
//                + "&PaymentDate=" + PaymentDate
//                + "&PaymentType=" + PaymentType
//                + "&PaymentTypeChargeFee=" + PaymentTypeChargeFee
//                + "&TradeDate=" + TradeDate
//                + "&SimulatePaid=" + SimulatePaid;
//        String encode_str
//                = "HashKey=" + testHashKey
//                + "&"
//                + str
//                + "&HashIV=" + testHashIv;
//        /*
//         //---標示流程
//         out.println("CheckMacValue取得流程 =");
//         out.println("<br><br>");
//         out.println("1.先ksort之後字串首尾補HashKey和HashIV");
//         out.println("<br>");
//         out.println("2.URLEncoder");
//         out.println("<br>");
//         out.println("3.toLowerCase");
//         out.println("<br>");
//         out.println("4.MD5加密");
//         out.println("<br><br><br>");
//         out.println("ksort之後 encode_str=");
//         out.println(encode_str);
//         out.println("<br><br><br>");
//         */
//
//        encode_str = java.net.URLEncoder.encode(encode_str, "utf-8");
//        /*
//         out.println("URLEncoder之後 encode_str=");
//         out.println(encode_str);
//         out.println("<br><br><br>");
//         */
//
//        encode_str = encode_str.toLowerCase();
//        /*
//         out.println("toLowerCase之後 encode_str=");
//         out.println(encode_str);
//         out.println("<br><br><br>");
//         */
//
//        String testCheckMacValue; //CheckMacValue檢查
//
//        //MD5加密
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] messageDigest = md.digest(encode_str.getBytes());
//            BigInteger number = new BigInteger(1, messageDigest);
//            String hashtext = number.toString(16);
//            // Now we need to zero pad it if you actually want the full 32 chars.
//            while (hashtext.length() < 32) {
//                hashtext = "0" + hashtext;
//            }
//            testCheckMacValue = hashtext.toUpperCase();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//
//        /*
//         //--測試
//         out.println("MD5加密之後 CheckMacValue=");
//         out.println("<br>");
//         out.println(CheckMacValue);
//         out.println("<br><br><br>");
//         */
//        String log= "交易編號: " + MerchantTradeNo+"\n"
//                + "MerchantID=" + MerchantID + "\n"
//                + "MerchantTradeNo=" + MerchantTradeNo + "\n"
//                + "RtnCode=" + RtnCode + "\n"
//                + "RtnMsg=" + RtnMsg + "\n"
//                + "TradeNo=" + TradeNo + "\n"
//                + "TradeAmt=" + TradeAmt + "\n"
//                + "PaymentDate=" + PaymentDate + "\n"
//                + "PaymentType=" + PaymentType + "\n"
//                + "PaymentTypeChargeFee=" + PaymentTypeChargeFee + "\n"
//                + "TradeDate=" + TradeDate + "\n"
//                + "SimulatePaid=" + SimulatePaid + "\n\n";
//        if (testCheckMacValue.equals(CheckMacValue)) { //比對兩個CheckMacValue是否相等
//            //交易成功，進行對應的處理
//            //process... 
//            //顯示交易成功
//            //out.println("1|OK");
//            //out.println(log);
//            log="付款成功!\n"+log;
//        }else {
//            //顯示交易失敗
//            //out.println("0|ErrorMessage");
//            //out.println(log);
//            log="付款失敗!\n"+log;
//        }
//
//        System.out.println(log);
//
//        //輸入交易紀錄至桌面
//        FileOutputStream fop = null;
//            File file;
//            try {
//
//                    file = new File("C:/Users/Loli/Desktop/trade_log.txt");
//                    fop = new FileOutputStream(file, true);
//
//                    // if file doesnt exists, then create it
//                    if (!file.exists()) {
//                            file.createNewFile();
//                    }
//
//                    // get the content in bytes
//                    byte[] contentInBytes = log.getBytes();
//
//                    fop.write(contentInBytes);
//                    fop.flush();
//                    fop.close();
//
//                    System.out.println("upload trade_log...");
//
//            } catch (IOException e) {
//                    e.printStackTrace();
//            } finally {
//                    try {
//                            if (fop != null) {
//                                    fop.close();
//                            }
//                    } catch (IOException e) {
//                            e.printStackTrace();
//                    }
//            }
%>