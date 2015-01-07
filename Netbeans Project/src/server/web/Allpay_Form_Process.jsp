<%-- 
    Document   : newjsp
    Created on : 2014/11/23, 下午 02:04:47
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
//接收由html傳回的參數。
    String item_name = new String(request.getParameter("ItemName").getBytes("ISO-8859-1"), "utf-8"); //商品名稱
    String total_amt = new String(request.getParameter("TotalAmount").getBytes("ISO-8859-1"), "utf-8"); //商品金額
    String trade_desc = new String(request.getParameter("TradeDesc").getBytes("ISO-8859-1"), "utf-8");//商品敘述
    String choose_payment = new String(request.getParameter("ChoosePayment").getBytes("ISO-8859-1"), "utf-8"); //付款方式
    
    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
    Date date = new Date();
    String merchant_tradedate = sdFormat.format(date);
    //out.print(merchant_tradedate);
//    String merchant_tradedate = new String(request.getParameter("MerchantTradeDate").getBytes("ISO-8859-1"), "utf-8");//自訂時間，方便比對CheckSumValue

    SimpleDateFormat trade_no_Format = new SimpleDateFormat("yyyyMMddhhmmss"); //交易編號預設以 年月日_時分秒
    Date trade_no_date = new Date();
    String trade_no = trade_no_Format.format(trade_no_date);//交易編號

//------------------------------------------固定的參數------------------------------------------------------
//hashkey
    String hash_key = "IJjdx62rRITB54jK";
//hashiv
    String hash_iv = "n1mzdG1DeYiMIcR0";

//1. 請選擇介接網址 (正式環境) //測試環境= http://payment-stage.allpay.com.tw/Cashier/AioCheckOut
    String gateway_url = "https://payment.allpay.com.tw/Cashier/AioCheckOut";

//2. 確認廠商編號 (由AllPay提供) Varchar(10) char(20) 
    String merchant_id = "1064705";

//3. 廠商交易編號 (廠商交易編號不可重覆。英數字。) Varchar(20)  
//String trade_no = "1414226706"; //此編號命名有規則，需參考官方規範。
//4. 廠商交易時間 Varchar(20) 
//String merchant_tradedate = date("Y/m/d H:i:s"); //
//5. 交易類型 Varchar(20) 請帶 aio  
    String payment_type = "aio";

//6. 交易金額 Money 
//String total_amt;
//7. 交易描述 Varchar(200)  
//String trade_desc = "test_shop";
//8. 商品名稱 Varchar(200) 如果商品名稱有多筆，需在金流選擇頁一行一行顯示商品名稱的話，商品名稱請以井號分隔(#)
//String item_name = "test1";
//9. 回傳網址 Varchar(200) 當消費者付款完成後，會將付款結果回傳到該網址。 
    String return_url = "http://www.allpay.com.tw/receive.php";

//10. 選擇預設付款方式 Varchar(20)
  //  String choose_payment = "WebATM";

//11. 檢查碼
//12. Client端回傳網址 Varchar(200) 此網址為付款完成後，銀行將頁面導回到歐付寶時，歐付寶會顯示付款完成頁，該頁面上會有[回到廠商]的按鈕，會員點選按鈕後，會將頁面導回到此設定的網址
    String client_back_url = "http://www.allpay.com.tw/Shopping/Detail";

//13. 商品銷售網址 Varchar(200)
//14. 備註欄位。 Varchar(100) 目前都請放空白。 
//15. 選擇預設付款子項目 Varchar(20) 若正確設定此欄位，使用者則無法看見金流選擇頁，直接使用設定的付款方式，但信用卡(Credit)與儲值消費(TopUpUsed)無此功能。
//16. Client端回傳付款結果網址 Varchar(200) 此網址為付款完成後，銀行將頁面導回到歐付寶時，會將頁面導回到此設定的網址，並帶回付款結果的參數，沒帶此參數則會顯示歐付寶的顯示付款完成頁。OrderResultURL
    String orderResultURL = "http://www.allpay.com.tw/client.php";

//---------------------------------------------------------------------------------------------------------
//out.println(item_name);
/*
     " ChoosePayment=WebATM&ClientBackURL=http://www.allpay.com.tw/Shopping/Detail&ItemName=apple&MerchantID=1064705&MerchantTradeDate=2014/11/01 00:39:22&MerchantTradeNo=1414226706&OrderResultURL=http://www.allpay.com.tw/client.php&PaymentType=aio&ReturnURL=http://www.allpay.com.tw/receive.php&TotalAmount=1&TradeDesc=abc "
     */
//照ksort排序
    String str = "ChoosePayment=" + choose_payment
            + "&ClientBackURL=" + client_back_url
            + "&ItemName=" + item_name
            + "&MerchantID=" + merchant_id
            + "&MerchantTradeDate=" + merchant_tradedate
            + "&MerchantTradeNo=" + trade_no
            + "&OrderResultURL=" + orderResultURL
            + "&PaymentType=" + payment_type
            + "&ReturnURL=" + return_url
            + "&TotalAmount=" + total_amt
            + "&TradeDesc=" + trade_desc;
    String encode_str
            = "HashKey=" + hash_key
            + "&"
            + str
            + "&HashIV=" + hash_iv;

     //---測試
    /*
    out.println("CheckMacValue取得流程 =");
    out.println("<br/><br/>");
    out.println("1.先ksort之後字串首尾補HashKey和HashIV");
    out.println("<br/>");
    out.println("2.URLEncoder");
    out.println("<br/>");
    out.println("3.toLowerCase");
    out.println("<br/>");
    out.println("4.MD5加密");
    out.println("<br/><br/><br/>");

    
    out.println("ksort之後 encode_str=");
    out.println(encode_str);
    out.println("<br/><br/><br/>");
*/
    encode_str = java.net.URLEncoder.encode(encode_str, "utf-8");/*
    out.println("URLEncoder之後 encode_str=");
    out.println(encode_str);
    out.println("<br/><br/><br/>");
*/
    encode_str = encode_str.toLowerCase();/*
    out.println("toLowerCase之後 encode_str=");
    out.println(encode_str);
    out.println("<br/><br/><br/>");
*/
    
    
    
    
    String CheckMacValue;

    //MD5加密
    try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(encode_str.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        CheckMacValue = hashtext.toUpperCase();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }

    //--測試
    /*
    out.println("MD5加密之後 CheckMacValue=");
    out.println("<br/>");
    out.println(CheckMacValue);
    out.println("<br/><br/><br/>");

    String post_str = str + "&CheckMacValue=" + CheckMacValue;
    
    out.print("//以send post request方式");
    out.println("<br/>");
    out.print("最終的post_str=");
    out.println("<br/>");
    out.print(post_str);
    out.println("<br/><br/><br/>");
    
    */
    
    //-------------------------------------------------------------------傳送post_str至 gateway_url---------------------------------------
/*
    String urlParameters = post_str;
    String request_url = "http://translate.intelsoft.az";
    URL url = null;
    try {
        url = new URL(request_url);
    } catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    HttpURLConnection connection = null;
    try {
        connection = (HttpURLConnection) url.openConnection();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    connection.setDoOutput(true);
    connection.setDoInput(true);
    connection.setInstanceFollowRedirects(false);
    try {
        connection.setRequestMethod("POST");
    } catch (ProtocolException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    connection.setRequestProperty("Content-Type", "text/html");
    connection.setRequestProperty("charset", "utf-8");
    connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
    connection.setUseCaches(false);

    DataOutputStream wr;
    try {
        wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }
        wr.close();

        connection.disconnect();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        */

System.out.println("ok");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>無標題文件</title>
        <script language = "javascript">
            //<!-- 
            function sub() {
                document.myform.submit();
            }
            setTimeout(sub, 0);//以毫秒為單位的.1000代表一秒鐘.根據你需要修改這個時間. 
            //--> 
        </script>
    </head>
    <!--若要不顯示資訊 請在body 設定hidden -->
    <body hidden="true">
        <div id="loadingImg" style="display:none"><img src="loading.gif"> loading...</div>
        <h1>交易細節請確認無誤之後，按下[ 確定交易 ]後，將會轉至費款頁面</h1>
        <br/>
        <form name="myform" method="post" action="https://payment.allpay.com.tw/Cashier/AioCheckOut"  enctype = "application/x-www-form-urlencoded">
            <b>1. 商品名稱 Varchar(200) 如果商品名稱有多筆，需在金流選擇頁一行一行顯示商品名稱的話，商品名稱請以井號分隔(#) </b><br/>
            ItemName : <input type="text" id="ItemName" name="ItemName" size=60 value="<% out.print(item_name);%>" readonly="readonly" />
            <br/>
            <br/>
            <b>2. 交易金額 Money </b><br/>
            TotalAmount : <input type="text" id="TotalAmount" name="TotalAmount" value="<% out.print(total_amt);%>" readonly="readonly"/>
            <br/>
            <br/>
            <b>3. 交易描述 Varchar(200) </b><br/>
            TradeDesc : <input type="text" id="TradeDesc" name="TradeDesc" value="<% out.print(trade_desc);%>" readonly="readonly"/>
            <br/>
            <br/>	
            <b>4. 選擇預設付款方式 Varchar(20) </b><br/>
            ChoosePayment : 
            <input type="text" id="ChoosePayment" name="ChoosePayment" value="<% out.print(choose_payment);%>" readonly="readonly"/>

            <div id="addition"></div>

            <br/>

            <b>5. 廠商交易編號 (廠商交易編號不可重覆。英數字。) Varchar(20) </b><br/>
            MerchantTradeNo : <input type="text" id="MerchantTradeNo" name="MerchantTradeNo" value="<% out.print(trade_no);%>" readonly="readonly"/>
            <br/>
            <br/>
            <b>6. 廠商交易時間 Varchar(20) </b><br/>
            MerchantTradeDate : <input type="text" id="MerchantTradeDate" name="MerchantTradeDate" value="<% out.print(merchant_tradedate);%>" readonly="readonly"/>
            <br/>
            <br/>	
            <!--
 <b>7. CheckMacValue </b><br/>
            -->
            CheckMacValue:<input type="hidden" name="CheckMacValue" value="<% out.print(CheckMacValue);%>" readonly="readonly"/> <br/><br/>
            MerchantID:<input type='text' name='MerchantID' value='1064705'/><br/><br/>
            OrderResultURL:<input type='text' name='OrderResultURL' value='http://www.allpay.com.tw/client.php'/><br/><br/>
            ReturnURL:<input type='text' name='ReturnURL' value='http://www.allpay.com.tw/receive.php'/><br/><br/>
            PaymentType:<input type='text' name='PaymentType' value='aio'/><br/><br/>
            ClientBackURL:<input type='text' name='ClientBackURL' value='http://www.allpay.com.tw/Shopping/Detail'/><br/><br/>  

            <!-- //自動轉向就不用設定submit ， 可自行設定
            <input type="submit" id="submit" value="確定交易""/>
            -->
        </form>
    </body>
</html>
