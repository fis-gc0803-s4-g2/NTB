$(document).ready(function () {
     $('.carousel').carousel({
                interval: 3000 //changes the speed
            });
            
    $("#button1").click(function () {
	
        var dialog = new BootstrapDialog(
            {
                title: 'Login',
                message: function (var_dialog) {
                    var result = $('<div></div>');
                    var pageToLoad = var_dialog.getData('page_to_load');
                    result.load(pageToLoad);

                    return result;
                },
                data: {
                    "page_to_load": '${pageContext.request.contextPath}/login.html'
                },
                type: BootstrapDialog.TYPE_INFO,
                closable: true,
                buttons: [{
                    label: 'Cancel',
                    action: function (dialog) {
                        typeof dialog.getData('callback') === 'function' && dialog.getData('callback')(false);
                        dialog.close();
                    }
                }, {
                    label: 'Login',
                    cssClass: 'btn-primary',
                    action: function (dialog) {
                        typeof dialog.getData('callback') === 'function' && dialog.getData('callback')(true);
                        //lay thong tin o tren form popup va dung ajax day ve


                       // var info=$('#form1').serialize();
                       // alert(info);

                        //$.ajax({
                          //  type: "POST",
                          //  url: "home_controller.php",
                          //  data: info,
                          //  success: function(data){
                           //     alert(data);
                          //  }
                       // });
                        dialog.close();
                    }
                }]

            },
            function (result) {
                if (result) {
                    alert('Yup.');
                } else {
                    alert('Nope.');
                }
            }
        );
        dialog.open();
        //BootstrapDialog.alert('I want banana!');
    });
	
	//set event for button2
	 $("#button").click(function () {
	  BootstrapDialog.show({
            message: function(dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
        
                return $message;
            },
            data: {
                'pageToLoad': '../cuisine/login.html'
            }
        });
	  });
});