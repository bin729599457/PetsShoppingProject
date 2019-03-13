$(function () {
    var baseUrl = 'http://leewaiho.com:20002/users'

    // 先判断有没登录
    var id = Cookies.get('id')
    if (id) {
        // 获取用户信息
        $.post(baseUrl + '/getSingleUsersInfo', { id: id }, function (res) {
            if (res.success) {
                var data = res.obj
                // 渲染出来
                $('#username').val(data.userName)
                $('#phone').text(data.phone)
                $('#address').val(data.address)
            } else {
                alert(res.msg)
            }
        })
    }
    else {
        alert('请先登录')
        window.location.href = './login.html'
    }

    // 当鼠标移入个人中心，显示弹窗
    $('.personal-center').mouseover(function () {
        $('#personalDialog').show()
    })
    // 当鼠标离开个人中心的弹窗，隐藏
    $('#personalDialog').mouseout(function () {
        $(this).hide()
    })

    // 点击了头部的logo
    $('#toIndex').click(function () {
        window.location.href = './index.html'
    })
    // 点击了个人中心-我的订单
    $('#toOrder').click(function () {
        window.location.href = './order.html'
    })
    // 点击了个人中心-我的购物车
    $('#toCart').click(function () {
        window.location.href = './cart.html'
    })
    // 点击了退出登录
    $('#logout').click(function () {
        Cookies.remove('id')
        alert('已退出登录')
        window.location.href = './index.html'
    })

    // 点击了更新按钮
    $('.btn').click(function () {
        if ($('#username').val()) {
            // 传给后台的参数
            $.ajax({
                type: 'PUT',
                url: baseUrl + '/updateMsg',
                data: {
                    id: id,
                    userName: $('#username').val(),
                    address: $('#address').val()
                },
                success: function(res){
                    if (res.success) {
                        alert('更新个人资料成功！')
                    } else {
                        alert(msg)
                    }
                }
            })
        }
        else {
            alert('请输入用户昵称')
        }
    })
})
