package server.http.servlet.allpay;

import java.util.*;
import java.math.BigInteger;
import java.security.*;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import server.database.SQL;

public class TradeOrder extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {

            String ID = request.getParameter("ID");
            String IsFacebookUser = request.getParameter("IsFacebookUser");
            String item_name = new String(request.getParameter("ItemName").getBytes("ISO-8859-1"), "utf-8"); //商品名稱
            String total_amt = new String(request.getParameter("TotalAmount").getBytes("ISO-8859-1"), "utf-8"); //商品金額
            String trade_desc = new String(request.getParameter("TradeDesc").getBytes("ISO-8859-1"), "utf-8");//商品敘述
            String choose_payment = new String(request.getParameter("ChoosePayment").getBytes("ISO-8859-1"), "utf-8"); //付款方式

            Date date = new Date();

            String merchant_tradedate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(date);
            String trade_no = new SimpleDateFormat("yyyyMMddhhmmss").format(date);//交易編號 , 預設以 年月日時分秒   

            String s
                    = "INSERT INTO Trade ("
                    + "id , is_facebook_user , merchant_trade_no"
                    + ") VALUES ("
                    + "'" + ID + "' , " + IsFacebookUser + " , '" + trade_no + "'"
                    + ");";

            SQL sql = new SQL();
            try {
                sql.setData(s);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            }

            String hash_key = "IJjdx62rRITB54jK";
            String hash_iv = "n1mzdG1DeYiMIcR0";
            String merchant_id = "1064705";
            String payment_type = "aio";
            String return_url = "http://" + server.config.Config.HTTP_SERVER_PUBLIC_IP + ":" + server.config.Config.HTTP_SERVER_PORT + "/src/server/web/allpay_test/Allpay_Form_ReturnURL.jsp";
            String client_back_url = "http://www.allpay.com.tw/Shopping/Detail";
            String orderResultURL = "http://" + server.config.Config.HTTP_SERVER_PUBLIC_IP + ":" + server.config.Config.HTTP_SERVER_PORT + "/TradeResult";

            String encode_str
                    = "HashKey=" + hash_key
                    + "&ChoosePayment=" + choose_payment
                    + "&ClientBackURL=" + client_back_url
                    + "&ItemName=" + item_name
                    + "&MerchantID=" + merchant_id
                    + "&MerchantTradeDate=" + merchant_tradedate
                    + "&MerchantTradeNo=" + trade_no
                    + "&OrderResultURL=" + orderResultURL
                    + "&PaymentType=" + payment_type
                    + "&ReturnURL=" + return_url
                    + "&TotalAmount=" + total_amt
                    + "&TradeDesc=" + trade_desc
                    + "&HashIV=" + hash_iv;

            encode_str = URLEncoder.encode(encode_str, "utf-8").toLowerCase();

            String CheckMacValue = getCheckMacValue(encode_str);

            String html = "<!doctype html>"
                    + "<head>"
                    + "<script language = \"javascript\"> "
                    + "function sub(){ "
                    + "document.myform.submit();"
                    + "} "
                    + "setTimeout(sub,0);"
                    + "</script>"
                    + "</head>"
                    + "<body>"
                    + "<form name=\"myform\" method=\"post\" action=\"https://payment.allpay.com.tw/Cashier/AioCheckOut\">"
                    + "<input type=\"hidden\" name=\"ItemName\" value=\"" + item_name + "\">"
                    + "<input type=\"hidden\" name=\"TotalAmount\" value=\"" + total_amt + "\">"
                    + "<input type=\"hidden\" name=\"TradeDesc\" value=\"" + trade_desc + "\">"
                    + "<input type=\"hidden\" name=\"ChoosePayment\" value=\"" + choose_payment + "\">"
                    + "<input type=\"hidden\" name=\"MerchantTradeNo\" value=\"" + trade_no + "\">"
                    + "<input type=\"hidden\" name=\"MerchantTradeDate\" value=\"" + merchant_tradedate + "\">"
                    + "<input type=\"hidden\" name='MerchantID' value=\"" + merchant_id + "\">"
                    + "<input type=\"hidden\" name='OrderResultURL' value=\"" + orderResultURL + "\">"
                    + "<input type=\"hidden\" name='ReturnURL' value=\"" + return_url + "\">"
                    + "<input type=\"hidden\" name='PaymentType' value=\"" + payment_type + "\">"
                    + "<input type=\"hidden\" name='ClientBackURL' value=\"" + client_back_url + "\">"
                    + "<input type=\"hidden\" name=\"CheckMacValue\" value=\"" + CheckMacValue + "\">"
                    + "</form>"
                    + "</body>"
                    + "</html>";

            out.println(html);
        }
    }

    String getCheckMacValue(String encode_str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(encode_str.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
