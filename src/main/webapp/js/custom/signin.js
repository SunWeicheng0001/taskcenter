//$(window).load(function(){
//    $('#signinButton').off('click').on('click',SignIn);
//});
//var pathName=window.document.location.pathname;
//var ContextPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
//function SignIn(){
//    var username = $('#inputUsername').val();
//    var password = $('#inputPassword').val();
//    $.ajax({
//        url : ContextPath + '/user/signin.ajax',
//        type : 'post',
//        dataType : 'json',
//        contentType : 'application/json',
//        data : JSON.stringify({
//            username : username,
//            password : password
//        }),
//        success : function(res) {
//            if (res && res.success) {
//                alert('success');
//            } else if (res) {
//                alert(res.message);
//            } else {
//                alert('failed��');
//            }
//        },
//        error : function(err) {
//            alert('error');
//        }
//    });
//}