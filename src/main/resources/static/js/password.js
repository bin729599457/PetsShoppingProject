$(function () {
    var baseUrl = 'http://leewaiho.com:20002/users'

    // 获取用户id
    var id = Cookies.get('id')
    if (!id) {
        alert('请先登录')
        window.location.href = './login.html'
    }

    // 点击了保存并重新登录按钮
    $('.btn').click(function () {
        // 判断有没输入新密码
        if ($('#newPassword').val()) {
            // 判断有没输入确认密码
            if ($('#newPassword2').val()) {
                // 判断新密码和确认密码是否一致
                if ($('#newPassword').val() == $('#newPassword2').val()) {
                    // 传给后台的参数
                    $.ajax({
                        type: 'PUT',
                        url: baseUrl + '/updateMsg',
                        data: {
                            id: id,
                            password: $('#newPassword').val()
                        },
                        success: function(res){
                            if (res.success) {
                                // 清空Cookie
                                Cookies.remove('id')
                                // 提示
                                alert('修改密码成功，请重新登录')
                                // 跳转到首页
                                window.location.href = './login.html'
                            } else {
                                alert(msg)
                            }
                        }
                    })
                } else {
                    alert('新密码与确认密码不一致')
                }
            } else {
                alert('请输入确认密码')
            }
        } else {
            alert('请输入新密码')
        }
    })
    // 点击了头部的logo
    $('#toIndex').click(function () {
        window.location.href = './index.html'
    })
})
