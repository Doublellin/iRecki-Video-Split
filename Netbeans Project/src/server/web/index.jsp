?<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>?????</title>
        <style type="text/css">
            body{
                background-image:url(a.jpg);
                background-size:cover;
            }
            h1{
                text-align:center;	
            }
        </style>
        <script type="text/javascript">
            function check() {

<!-- ?<form>??name??form1?????????????????????? -->
                if (form1.ItemName.value == "")
                {
                    alert("???????!");
                }
<!-- ?<form>??name??form1????????????????????????????????????????? -->
                else if (form1.TotalAmount.value == "")
                {

                    alert("???????!");
                }
                else if (form1.TotalAmount.value <= 0)
                {
                    alert("???????????????!");
                }
<!-- ?<form>??name??form1????????????????????? -->
                else if (form1.TradeDesc.value == "")
                {
                    alert("???????!");
                }
<!-- ?????????????????????????????? -->
                else {
                    if (confirm("?????????[??]???"))
                        form1.submit();
                }
            }
        </script>
    </head>

    <%
    
        String name = request.getParameter("name");
        String money = request.getParameter("money");
        String TradeDesc = request.getParameter("TradeDesc");
    %>
    
    <body>
        <div id="loadingImg" style="display:none">loading...</div>
        <div align="center">   
            <a href="http://chenghsi.com/" style="text-decoration:none;" target="_blank"><img src="logo.png" style="width:300px;height:50px;"/> <font size="7" color="#00FF00">??????</font> </a>
        </div>
        <h1>???? ???? base on V 1.0.7 (??Allpay_form_process.php)</h1>
        <br/>
        <div style=" margin-left:400px;">
            <form id="form1" name="form1" method="post" action="Allpay_Form_Process.jsp"  enctype = "application/x-www-form-urlencoded" target="_blank">
                <b>1. ???? Varchar(200) ????????????????????????????????????????(#) </b><br/>
                ItemName : <input type="text" id="ItemName" name="ItemName" value="<%= name %>" size=60/>
                <br/>
                <br/>
                <b>2. ???? Money </b><br/>
                TotalAmount : <input type="text" id="TotalAmount" name="TotalAmount" value="<%= money %>"/>
                <br/>
                <br/>
                <b>3. ???? Varchar(200) </b><br/>
                TradeDesc : <input type="text" id="TradeDesc" name="TradeDesc" value="<%= TradeDesc %>"/>
                <br/>
                <br/>


                <b>4. ???????? Varchar(20) </b><br/>
                ChoosePayment : 
                <select id="ChoosePayment" name="ChoosePayment">
                    <option value="Credit">Credit:???</option>
                    <option value="WebATM" selected>WebATM:??ATM</option>
                    <!-- //?????????????????
                    <option value="ATM">ATM:?????</option>
                    <option value="CVS">CVS:????</option>
                    <option value="BARCODE">BARCODE:????</option>
                    <option value="Alipay">Alipay: ???</option>
                    <option value="Tenpay">Tenpay: ???</option>
                    <option value="TopUpUsed">TopUpUsed:????</option>
                    -->
                </select>
                <div id="addition"></div>
                <br/>
                <!-- //??????????????
         <b>5. ?????? (???????????????) Varchar(20) </b><br/>
                 MerchantTradeNo : <input type="text" id="MerchantTradeNo" name="MerchantTradeNo">
                 <br/>
                 <br/>
                -->
                <!-- //??????????????
                <br/>                 
        <b>6. ?????? Varchar(20) </b><br/>
                MerchantTradeDate : <input type="text" id="MerchantTradeDate" name="MerchantTradeDate">
            <br/>
                <br/>
                -->
                <!--
    <input type="button" style="width:100px;" id="submit" name="submit" onclick="check()" value="(????)"/>
    <input type="reset"  style="width:100px; margin-left:50px" value="??" />
                -->
                <input type="button"  style="width:100px;" value="????" onClick="check()" /> &nbsp;&nbsp;&nbsp;
                <input type="reset"  style="width:100px; margin-left:30px;"value="????" />
            </form>
    </body>
</html>
