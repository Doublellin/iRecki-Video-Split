<%-- 
    Document   : echo
    Created on : 2014/12/10, 下午 09:16:59
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>trade success</title>
    </head>
    <body>
        <h1>交易成功!!</h1>
        <%

        out.print("Merchant ID : " + request.getParameter("MerchantID") + "<br/>");
        out.print("Merchant Trade No : " + request.getParameter("MerchantTradeNo") + "<br/>");
        out.print("Payment Date : " + request.getParameter("PaymentDate") + "<br/>");
        out.print("Payment Type : " + request.getParameter("PaymentType") + "<br/>");
        out.print("Payment Type Charge Fee : " + request.getParameter("PaymentTypeChargeFee") + "<br/>");
        out.print("Rtn Code : " + request.getParameter("RtnCode") + "<br/>");
        out.print("Rtn Msg : " + request.getParameter("RtnMsg") + "<br/>");
        out.print("Simulate Paid : " + request.getParameter("SimulatePaid") + "<br/>");
        out.print("Trade Amt : " + request.getParameter("TradeAmt") + "<br/>");        
        out.print("Trade Date : " + request.getParameter("TradeDate") + "<br/>");
        out.print("Trade No : " + request.getParameter("TradeNo") + "<br/>");
        out.print("Check Mac Value : " + request.getParameter("CheckMacValue") + "<br/>");
        %>
    </body>
</html>
