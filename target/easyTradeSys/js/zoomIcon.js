$(document).ready(function(){
	var zoom = 1.3;
	var width = $(this).find(".navImg").width();
    var height = $(this).find(".navImg").height();
	var z_width = width * zoom;
    var z_height = height * zoom;
	$(".navImg").hover(function(){
		 $(this).animate({
                width: z_width,
                height: z_height,
                marginTop: "-"+height*(zoom-1)+"px",
                marginLeft: "-"+width*(zoom-1)/2+"px"
            }, 300);
	},
	function(){
		$(this).animate({
                width: width,
                height: height,
                marginTop: "0px",
                marginLeft: "0px"
            }, 300);
	});
});