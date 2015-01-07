package server.http.servlet.allpay;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import server.database.SQL;

public class TradeResult extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        String MerchantID = request.getParameter("MerchantID");
        String MerchantTradeNo = request.getParameter("MerchantTradeNo");
        String PaymentDate = request.getParameter("PaymentDate");
        String PaymentType = request.getParameter("PaymentType");
        String PaymentTypeChargeFee = request.getParameter("PaymentTypeChargeFee");
        String RtnCode = request.getParameter("RtnCode");
        String RtnMsg = request.getParameter("RtnMsg");
        String SimulatePaid = request.getParameter("SimulatePaid");
        String TradeAmt = request.getParameter("TradeAmt");
        String TradeDate = request.getParameter("TradeDate");
        String TradeNo = request.getParameter("TradeNo");
        String CheckMacValue = request.getParameter("CheckMacValue");

        String s
                = "UPDATE Trade SET "
                + "merchant_id ='" + MerchantID + "' , "
                + "merchant_trade_no ='" + MerchantTradeNo + "' , "
                + "payment_date ='" + PaymentDate + "' , "
                + "payment_type ='" + PaymentType + "' , "
                + "payment_type_charge_fee ='" + PaymentTypeChargeFee + "' , "
                + "rtn_code ='" + RtnCode + "' , "
                + "rtn_msg ='" + RtnMsg + "' , "
                + "simulate_paid ='" + SimulatePaid + "' , "
                + "trade_amt ='" + TradeAmt + "' , "
                + "trade_date ='" + TradeDate + "' , "
                + "trade_no ='" + TradeNo + "' , "
                + "check_mac_value ='" + CheckMacValue + "' "
                + "WHERE merchant_trade_no = '" + MerchantTradeNo + "' ; ";
        
        System.out.println(s);

        SQL sql = new SQL();
        try {
            sql.setData(s);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            System.out.println(ex);
        }

        s = "SELECT id,is_facebook_user FROM trade WHERE merchant_trade_no = '" + MerchantTradeNo + "' ; ";

        String id = "";
        String is_facebook_user = "";

        try {
            ResultSet rs = sql.getData(s);
            if (rs.next()) {
                id = rs.getString("id");
                is_facebook_user = rs.getString("is_facebook_user");
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            System.out.println(ex);
        }

        s = "UPDATE users SET "
                + "point = point+" + TradeAmt
                + " WHERE "
                + "id = '" + id + "' AND "
                + "is_facebook_user = '"+is_facebook_user+"' ; ";

        try {
            sql.setData(s);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            System.out.println(ex);
        }
        
        

        MerchantTradeNo = URLEncoder.encode(MerchantTradeNo, "UTF-8");
        RtnMsg = URLEncoder.encode(RtnMsg, "UTF-8");
        TradeAmt = URLEncoder.encode(TradeAmt, "UTF-8");
        TradeDate = URLEncoder.encode(TradeDate, "UTF-8");
        PaymentType = URLEncoder.encode(PaymentType, "UTF-8");

        String url = "/TradeResult.html?MerchantTradeNo=" + MerchantTradeNo + "&RtnMsg=" + RtnMsg + "&TradeAmt=" + TradeAmt + "&TradeDate=" + TradeDate + "&PaymentType=" + PaymentType;
        resp.sendRedirect(url);

    }
}
