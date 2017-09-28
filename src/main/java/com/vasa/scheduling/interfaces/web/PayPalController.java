package com.vasa.scheduling.interfaces.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/team/payment")
@Controller
public class PayPalController {

/***** PayPal PayNow button	
	<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
	<input type="hidden" name="cmd" value="_xclick">
	<input type="hidden" name="business" value="VAcity@gmail.com">
	<input type="hidden" name="lc" value="US">
	<input type="hidden" name="item_name" value="Subscription">
	<input type="hidden" name="amount" value="30.00">
	<input type="hidden" name="currency_code" value="USD">
	<input type="hidden" name="button_subtype" value="services">
	<input type="hidden" name="no_note" value="0">
	<input type="hidden" name="bn" value="PP-BuyNowBF:btn_paynow_SM.gif:NonHostedGuest">
	<input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_paynow_SM.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
	<img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
	<input name="notify_url" value="mypage.php" type="hidden">
	</form>
*****/
	
}
