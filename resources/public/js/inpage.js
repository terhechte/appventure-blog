/**
 * @author Zeaun Zarrieff on behalf of Gigatas Inc.
 * This script is authorized for free use in accordance with the GPL version 2 license.
 */

if(jQuery){
	function toggle_inpage_menu(){
		jQuery('#inpage_menu_wrapper').toggle();
	}
	
	function smooth_scroller(element_id){
		 jQuery('html, body').animate({
                        scrollTop: jQuery('[data-inpage='+element_id+']').offset().top
                    }, 1000);
	}
	
	function load_inpage_menu_array(load_inpage_menu){
		window.inpage_menu_items = new Array;
		jQuery('[data-inpage]').each(function(){
			inpage_menu_items.push(jQuery(this).attr('data-inpage'));
		});
		load_inpage_menu();
	}
	
	function load_inpage_menu(){
		var inpage_menu_li = [];
		jQuery(window.inpage_menu_items).each(function(){
			var label = String(this);
			var onclick_call = "smooth_scroller('"+label+"')";
			var item_html = "<li class='inpage_menu_li' data-target='" + label + "' onclick=\""+onclick_call+"\">" + label + "</li>";
			jQuery('#inpage_menulist').append(item_html);
		});
	}
}

jQuery(document).ready(function(){
	if(jQuery('[data-inpage]')){
		load_inpage_menu_array(load_inpage_menu);
	}
});
